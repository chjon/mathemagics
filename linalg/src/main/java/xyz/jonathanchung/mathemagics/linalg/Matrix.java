package xyz.jonathanchung.mathemagics.linalg;

/**
 * This class describes an n * m matrix of numbers
 */
public class Matrix {

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
	public int getCols() {
		return this.cols;
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
}
