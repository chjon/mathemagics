package xyz.jonathanchung.mathemagics.linalg;

import xyz.jonathanchung.mathemagics.calc.PrecisionUtils;

/**
 * This class describes an n * m matrix of numbers
 */
public class Matrix {
	/**
	 * The acceptable error for float equality checks
	 */
	static double EPSILON = 1e-10;

	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * The number of rows in the matrix
	 */
	protected final int rows;

	/**
	 * The number of columns in the matrix
	 */
	protected final int cols;

	/**
	 * The elements of the matrix
	 */
	protected final double[][] elements;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor for creating a zero matrix with n rows and m columns, with a minimum of 1 row and 1 column
	 *
	 * @param n the number of rows
	 * @param m the number of columns
	 */
	public Matrix(int n, int m) {
		// Set the dimensions of the matrix
		this.rows = Math.max(1, n);
		this.cols = Math.max(1, m);

		this.elements = new double[this.rows][this.cols];
	}

	/**
	 * Shortcut constructor for creating a square zero matrix with n rows and n columns
	 *
	 * @param n the number of rows/columns
	 */
	public Matrix (int n) {
		this(n, n);
	}

	/**
	 * Constructor for creating a matrix from a 2D array, with a minimum of 1 row and 1 column. Jagged arrays get padded
	 * with zeroes
	 *
	 * @param matrix the 2D array to generate the matrix from
	 */
	public Matrix (double[][] matrix) {
		//Check for an empty input array
		if (matrix == null || matrix.length == 0) {
			this.rows = 1;
			this.cols = 1;

			this.elements = matrix;

		// Copy from the array
		} else {
			int cols = 1;

			// Find the largest row of a jagged array
			for (double[] row : matrix) {
				if (row != null) {
					cols = Math.max(cols, row.length);
				}
			}

			this.rows = matrix.length;
			this.cols = cols;
			this.elements = new double[this.rows][this.cols];

			for (int i = 0; i < this.rows; i++) {
				// Handle null rows
				if (matrix[i] == null) {
					final double[] row = new double[this.cols];
					matrix[i] = row;
				}

				// Copy from non-null rows
				System.arraycopy(matrix[i], 0, this.elements[i], 0, matrix[i].length);
			}
		}
	}

	/**
	 * Copy constructor
	 * @param matrix the matrix to copy from
	 */
	public Matrix (Matrix matrix) {
		this.rows = matrix.rows;
		this.cols = matrix.cols;
		this.elements = new double[this.rows][this.cols];

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				this.elements[i][j] = matrix.elements[i][j];
			}
		}
	}

	// Accessors -------------------------------------------------------------------------------------------------------

	/**
	 * Get the number of rows in the matrix
	 *
	 * @return the number of rows in the matrix
	 */
	public int getRows () {
		return this.rows;
	}

	/**
	 * Get the number of columns in the matrix
	 *
	 * @return the number of columns in the matrix
	 */
	public int getCols () {
		return this.cols;
	}

	/**
	 * Get a specified element from the matrix
	 *
	 * @param row the row of the desired element
	 * @param col the column of the desired element
	 *
	 * @return the element at the specified row and column
	 */
	public double get (int row, int col) {
		return this.elements[row][col];
	}

	/**
	 * Get the vector containing the specified row
	 *
	 * @param row the index of the desired row
	 *
	 * @return the vector containing the specified row
	 */
	public Vector getRow (int row) {
		Vector rowVector = new Vector(this.cols);

		for (int i = 0; i < this.cols; i++) {
			rowVector.elements[i][0] = this.elements[row][i];
		}

		return rowVector;
	}

	/**
	 * Get the vector containing the specified column
	 *
	 * @param col the index of the desired column
	 *
	 * @return the vector containing the specified column
	 */
	public Vector getCol (int col) {
		Vector colVector = new Vector(this.rows);

		for (int i = 0; i < this.rows; i++) {
			colVector.elements[i][0] = this.elements[i][col];
		}

		return colVector;
	}

	/**
	 * @return a string representation of the matrix
	 */
	@Override
	public String toString () {
		StringBuilder output = new StringBuilder();
		output.append('{').append(this.rows).append('x').append(this.cols).append("}[");

		for (int i = 0; i < this.rows; i++) {
			output.append('[');

			for (int j = 0; j < this.cols; j++) {
				if (j != this.cols - 1) {
					output.append(this.elements[i][j]).append(',');
				} else {
					output.append(this.elements[i][j]);
				}
			}

			if (i != this.rows - 1) {
				output.append("],");
			} else {
				output.append(']');
			}
		}

		output.append(']');
		return output.toString();
	}

	// Matrix properties -----------------------------------------------------------------------------------------------

	/**
	 * Determine the rank of the matrix
	 *
	 * @return the rank of the matrix
	 */
	public int rank () {
		Matrix ref = this;

		// Calculate the REF if the matrix is not already in REF
		if (!isRef()) {
			ref = ref();
		}

		// Determine the rank of the matrix
		for (int i = 0; i < ref.rows; ++i) {
			// Check if every element in the row is zero
			boolean rowIsZero = true;
			for (int j = i; j < ref.cols && rowIsZero; ++j) {
				if (ref.elements[i][j] != 0) {
					rowIsZero = false;
				}
			}

			// The matrix's rank is the index of the first zero row
			if (rowIsZero) {
				return i;
			}
		}

		return rows;
	}

	/**
	 * Determine whether the matrix is square
	 *
	 * @return true if the matrix is square
	 *         false if the matrix is not square
	 */
	public boolean isSquare () {
		return rows == cols;
	}

	/**
	 * Determine whether a matrix is in row echelon form
	 *
	 * @return true if the matrix is in REF
	 *         false if the matrix is not in REF
	 */
	public boolean isRef () {
		// The index of the first non-zero entry in the previous row
		int leadingIndex = -1;

		for (int i = 0; i < rows; ++i) {
			// Find the index of the first non-zero entry in the row
			int j = 0;
			while (j < cols && elements[i][j] == 0) {
				++j;
			}

			// Non-zero entries must be to the right of the leading non-zero entry of the previous row for REF
			if (j <= leadingIndex) {
				return false;

			// Set the leading index to that of the current row
			} else {
				leadingIndex = j;
			}
		}

		return true;
	}

	/**
	 * Determine whether a matrix is diagonal
	 *
	 * @return true if the matrix is diagonal
	 *         false if the matrix is not diagonal
	 */
	public boolean isDiagonal () {
		// Diagonal matrices must be square
		if (!isSquare()) {
			return false;
		}

		// Check elements in the matrix to determine whether it is diagonal
		for (int i = 0; i < rows; ++i) {
			for (int j = 0; j < cols; ++j) {
				// Only elements on the diagonal can be non-zero
				if (i != j && elements[i][j] != 0) {
					return false;
				}
			}
		}

		return true;
	}

	// Mutators --------------------------------------------------------------------------------------------------------

	/**
	 * Swap the rows of a matrix
	 *
	 * @param row1 the index of the first row to swap
	 * @param row2 the index of the second row to swap
	 */
	public void swapRows (int row1, int row2) {
		double[] row1Ref = elements[row1];
		elements[row1] = elements[row2];
		elements[row2] = row1Ref;
	}

	/**
	 * Swap the columns of a matrix
	 *
	 * @param col1 the index of the first column to swap
	 * @param col2 the index of the second column to swap
	 */
	public void swapCols (int col1, int col2) {
		double temp;

		for (int i = 0; i < this.rows; i++) {
			temp = elements[i][col1];
			elements[i][col1] = elements[i][col2];
			elements[i][col2] = temp;
		}
	}

	// Matrix operations -----------------------------------------------------------------------------------------------

	/**
	 * Determine whether two matrices are equal (no error)
	 *
	 * @param other the matrix to compare with
	 *
	 * @return true if the matrices are equal
	 *         false if the matrices are not equal
	 */
	public boolean equals(Matrix other) {
		return equals(other, EPSILON);
	}

	/**
	 * Determine whether two matrices are equal (with some acceptable error)
	 *
	 * @param other the matrix to compare with
	 * @param epsilon the maximum acceptable relative error
	 *
	 * @return true if the matrices are equal
	 *         false if the matrices are not equal
	 */
	public boolean equals(Matrix other, double epsilon) {
		// Check for equal dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			return false;
		}

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				if (!PrecisionUtils.equalsAbs(this.elements[i][j], other.elements[i][j], epsilon)) return false;
			}
		}

		return true;
	}

	/**
	 * Add a matrix to this one by adding their corresponding elements
	 *
	 * @param other the matrix to add
	 *
	 * @return the matrix containing the sum of the two matrices
	 *
	 * @throws IncompatibleDimensionException when the matrices do not have the same dimensions
	 */
	public Matrix add (Matrix other) throws IncompatibleDimensionException {
		//Check if the matrices have the same dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IncompatibleDimensionException(this, other);
		}

		Matrix sum = new Matrix(this.rows, this.cols);

		//Add corresponding elements
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				sum.elements[i][j] = this.elements[i][j] + other.elements[i][j];
			}
		}

		return sum;
	}

	/**
	 * Subtract a matrix from this one by subtracting their corresponding elements
	 *
	 * @param other the matrix to subtract
	 *
	 * @return the matrix containing the difference of the two matrices
	 *
	 * @throws IncompatibleDimensionException when the matrices do not have the same dimensions
	 */
	public Matrix subtract (Matrix other) throws IncompatibleDimensionException {
		//Check if the matrices have the same dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IncompatibleDimensionException(this, other);
		}

		Matrix difference = new Matrix(this.rows, this.cols);

		//Subtract corresponding elements
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				difference.elements[i][j] = this.elements[i][j] - other.elements[i][j];
			}
		}

		return difference;
	}

	/**
	 * Calculate the product of two matrices; the value of each element of the matrix is calculated by taking the
	 * corresponding dot products of the first matrix's row vectors with the second matrix's column vectors
	 *
	 * @param other The matrix to multiply with this one
	 *
	 * @return The product of the matrix multiplication
	 *
	 * @throws IncompatibleDimensionException When the number of columns in the first matrix is not the same as the
	 * number of columns in the second matrix
	 */
	public Matrix multiply (Matrix other) throws IncompatibleDimensionException {
		//Check if the matrix has the same number of columns as the other matrix has rows
		if (this.cols != other.rows) {
			throw new IncompatibleDimensionException(this, other);
		}

		Matrix product = new Matrix(this.rows, other.cols);

		for (int productRow = 0; productRow < product.rows; productRow++) {
			for (int productCol = 0; productCol < product.cols; productCol++) {
				float sum = 0;

				//Take the corresponding dot product
				for (int i = 0; i < this.cols; i++) {
					sum += this.elements[productRow][i] * other.elements[i][productCol];
				}

				product.elements[productRow][productCol] = sum;
			}
		}

		return product;
	}

	/**
	 * Calculate the scalar multiple of a matrix
	 *
	 * @param scalar the desired multiple
	 *
	 * @return the scaled matrix
	 */
	public Matrix multiply (double scalar) {
		Matrix product = new Matrix(this.rows, this.cols);

		// Multiply each element by the scalar
		for (int productRow = 0; productRow < product.rows; productRow++) {
			for (int productCol = 0; productCol < product.cols; productCol++) {
				product.elements[productRow][productCol] = this.elements[productRow][productCol] * scalar;
			}
		}

		return product;
	}

	/**
	 * Calculate the transpose of the matrix
	 *
	 * @return the transpose of the matrix
	 */
	public Matrix transpose () {
		Matrix transpose = new Matrix(this.cols, this.rows);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				transpose.elements[j][i] = this.elements[i][j];
			}
		}

		return transpose;
	}

	/**
	 * Determine the matrix's row echelon form (REF) with partial pivoting
	 *
	 * @return the matrix's REF
	 */
	public Matrix ref () {
		// Copy the matrix
		Matrix ref = new Matrix(this);

		// Reduce each column
		for (int i = 0; i < ref.rows && i < ref.cols; i++) {
			// Find the largest value in the column to be used as a pivot
			for (int j = i + 1; j < ref.rows; j++) {
				if (Math.abs(ref.elements[j][i]) > Math.abs(ref.elements[i][i])) {
					ref.swapRows(i, j);
				}
			}

			// Reduce each of the rows below the current row
			for (int j = i + 1; j < ref.rows; j++) {
				// Reduce the row
				double ratio = ref.elements[j][i] / ref.elements[i][i];
				for (int k = i + 1; k < ref.cols; k++) {
					ref.elements[j][k] -= ref.elements[i][k] * ratio;
				}

				ref.elements[j][i] = 0;
			}
		}

		return ref;
	}
}
