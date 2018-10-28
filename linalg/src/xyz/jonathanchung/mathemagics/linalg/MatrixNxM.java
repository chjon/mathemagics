package xyz.jonathanchung.mathemagics.linalg;

import xyz.jonathanchung.mathemagics.calc.PrecisionUtils;

/**
 * This class describes an n * m matrix of numbers
 */
public class MatrixNxM extends Matrix<MatrixNxM> {

	// Fields ----------------------------------------------------------------------------------------------------------

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
	public MatrixNxM(int n, int m) {
		super(n, m);

		this.elements = new double[this.rows][this.cols];
	}

	/**
	 * Shortcut constructor for creating a square zero matrix with n rows and n columns
	 *
	 * @param n the number of rows/columns
	 */
	public MatrixNxM(int n) {
		this(n, n);
	}

	/**
	 * Constructor for creating a matrix from a 2D array, with a minimum of 1 row and 1 column. Jagged arrays get padded
	 * with zeroes
	 *
	 * @param matrix the 2D array to generate the matrix from
	 */
	public MatrixNxM(double[][] matrix) {
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
	public MatrixNxM(MatrixNxM matrix) {
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

	@Override
	public double get (int row, int col) {
		return this.elements[row][col];
	}

	@Override
	public VectorN getRow (int row) {
		VectorN rowVector = new VectorN(this.cols);

		for (int i = 0; i < this.cols; i++) {
			rowVector.elements[i] = this.elements[row][i];
		}

		return rowVector;
	}

	@Override
	public VectorN getCol (int col) {
		VectorN colVector = new VectorN(this.rows);

		for (int i = 0; i < this.rows; i++) {
			colVector.elements[i] = this.elements[i][col];
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
	@Override
	public int rank () {
		MatrixNxM ref = this;

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

	@Override
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

	@Override
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

	@Override
	public void swapRows (int row1, int row2) {
		double[] row1Ref = elements[row1];
		elements[row1] = elements[row2];
		elements[row2] = row1Ref;
	}

	@Override
	public void swapCols (int col1, int col2) {
		double temp;

		for (int i = 0; i < this.rows; i++) {
			temp = elements[i][col1];
			elements[i][col1] = elements[i][col2];
			elements[i][col2] = temp;
		}
	}

	// Linear object operations ----------------------------------------------------------------------------------------

	/**
	 * Add a matrix to this one by adding their corresponding elements
	 *
	 * @param other the matrix to add
	 *
	 * @return the matrix containing the sum of the two matrices
	 *
	 * @throws IncompatibleDimensionException when the matrices do not have the same dimensions
	 */
	@Override
	public MatrixNxM add (MatrixNxM other) throws IncompatibleDimensionException {
		// Check if the matrices have the same dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IncompatibleDimensionException(this, other);
		}

		MatrixNxM sum = new MatrixNxM(this.rows, this.cols);

		// Add corresponding elements
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
	@Override
	public MatrixNxM sub (MatrixNxM other) throws IncompatibleDimensionException {
		// Check if the matrices have the same dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IncompatibleDimensionException(this, other);
		}

		MatrixNxM difference = new MatrixNxM(this.rows, this.cols);

		// Subtract corresponding elements
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				difference.elements[i][j] = this.elements[i][j] - other.elements[i][j];
			}
		}

		return difference;
	}

	@Override
	public MatrixNxM multiply (double scalar) {
		MatrixNxM product = new MatrixNxM(this.rows, this.cols);

		// Multiply each element by the scalar
		for (int productRow = 0; productRow < product.rows; productRow++) {
			for (int productCol = 0; productCol < product.cols; productCol++) {
				product.elements[productRow][productCol] = this.elements[productRow][productCol] * scalar;
			}
		}

		return product;
	}



	// Matrix operations -----------------------------------------------------------------------------------------------

	@Override
	public boolean equals(MatrixNxM other, double epsilon) {
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

	@Override
	public MatrixNxM multiply (MatrixNxM other) throws IncompatibleDimensionException {
		//Check if the matrix has the same number of columns as the other matrix has rows
		if (this.cols != other.rows) {
			throw new IncompatibleDimensionException(this, other);
		}

		MatrixNxM product = new MatrixNxM(this.rows, other.cols);

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

	@Override
	public MatrixNxM transpose () {
		MatrixNxM transpose = new MatrixNxM(this.cols, this.rows);

		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				transpose.elements[j][i] = this.elements[i][j];
			}
		}

		return transpose;
	}

	@Override
	public MatrixNxM remove (int row, int col) {
		if (this.cols == 1 || this.rows == 1) {
			return null;
		}

		MatrixNxM newMatrix = new MatrixNxM(this.rows - 1, this.cols - 1);

		int newRow = 0;

		for (int i = 0; i < this.rows; ++i) {
			// Skip the row if it is to be removed
			if (i == row) continue;

			int newCol = 0;

			for (int j = 0; j < this.cols; ++j) {
				// Skip the column if it is to be removed
				if (j == col) continue;

				newMatrix.elements[newRow][newCol] = this.elements[i][j];
				++newCol;
			}

			++newRow;
		}

		return newMatrix;
	}

	@Override
	public MatrixNxM removeRow (int row) {
		if (this.rows == 1) {
			return null;
		}

		MatrixNxM newMatrix = new MatrixNxM(this.rows - 1, this.cols);

		int newRow = 0;

		for (int i = 0; i < this.rows; ++i) {
			// Skip the row if it is to be removed
			if (i == row) continue;

			System.arraycopy(this.elements[i], 0, newMatrix.elements[newRow], 0, this.cols);
			++newRow;
		}

		return newMatrix;
	}

	@Override
	public MatrixNxM removeCol(int col) {
		if (this.cols == 1) {
			return null;
		}

		MatrixNxM newMatrix = new MatrixNxM(this.rows, this.cols - 1);

		for (int i = 0; i < this.rows; ++i) {
			int newCol = 0;

			for (int j = 0; j < this.cols; ++j) {
				// Skip the column if it is to be removed
				if (j == col) continue;

				newMatrix.elements[i][newCol] = this.elements[i][j];
				++newCol;
			}
		}

		return newMatrix;
	}

	/**
	 * Determine the matrix's row echelon form (REF) with partial pivoting
	 *
	 * @return the matrix's REF
	 */
	public MatrixNxM ref () {
		// Copy the matrix
		MatrixNxM ref = new MatrixNxM(this);

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
