package xyz.jonathanchung.mathemagics.linalg;

import xyz.jonathanchung.mathemagics.calc.PrecisionUtils;

/**
 * This class describes a 2 * 2 matrix of numbers
 */
public class Matrix2x2 extends MatrixNxN {

	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor for creating a zero matrix with 2 rows and 2 columns
	 */
	public Matrix2x2 () {
		super(2);
	}

	/**
	 * Constructor for creating a matrix from a 2D array, with a minimum of 1 row and 1 column. Jagged arrays get padded
	 * with zeroes
	 *
	 * @param matrix the 2D array to generate the matrix from
	 */
	public Matrix2x2 (double[][] matrix) {
		this();

		//Check for an empty input array
		if (matrix == null || matrix.length == 0) {
			return;
		}

		// Copy from the array
		for (int i = 0; i < this.rows; i++) {
			// Handle null rows
			if (matrix[i] == null) {
				matrix[i] = new double[this.cols];
			}

			// Copy from rows
			System.arraycopy(matrix[i], 0, this.elements[i], 0, Math.min(this.rows, matrix[i].length));
		}
	}

	/**
	 * Copy constructor
	 * @param matrix the matrix to copy from
	 */
	public Matrix2x2 (Matrix2x2 matrix) {
		this();

		for (int i = 0; i < this.rows; i++) {
			System.arraycopy(matrix.elements[i], 0, this.elements[i], 0, this.cols);
		}
	}

	// Matrix properties -----------------------------------------------------------------------------------------------

	/**
	 * Determine whether a matrix is in row echelon form
	 *
	 * @return true if the matrix is in REF
	 *         false if the matrix is not in REF
	 */
	@Override
	public boolean isRef () {
		if (this.elements[0][0] != 0) {
			return this.elements[1][0] == 0;
		} else {
			return this.elements[1][0] == 0 && this.elements[1][1] == 0;
		}
	}

	@Override
	public boolean isDiagonal () {
		return elements[0][1] == 0 && elements[1][0] == 0;
	}

	@Override
	public double determinant() {
		return (elements[0][0] * elements[1][1]) - (elements[0][1] * elements[1][0]);
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
	public Matrix2x2 add (MatrixNxM other) throws IncompatibleDimensionException {
		// Check if the matrices have the same dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IncompatibleDimensionException(this, other);
		}

		Matrix2x2 sum = new Matrix2x2();

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
	public Matrix2x2 sub (MatrixNxM other) throws IncompatibleDimensionException {
		// Check if the matrices have the same dimensions
		if (this.rows != other.rows || this.cols != other.cols) {
			throw new IncompatibleDimensionException(this, other);
		}

		Matrix2x2 difference = new Matrix2x2();

		// Subtract corresponding elements
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.cols; j++) {
				difference.elements[i][j] = this.elements[i][j] - other.elements[i][j];
			}
		}

		return difference;
	}

	/**
	 * Calculate the scalar multiple of a matrix
	 *
	 * @param scalar the desired multiple
	 *
	 * @return the scaled matrix
	 */
	@Override
	public Matrix2x2 multiply (double scalar) {
		Matrix2x2 product = new Matrix2x2();

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

		return	PrecisionUtils.equalsAbs(this.elements[0][0], other.elements[0][0], epsilon) &&
				PrecisionUtils.equalsAbs(this.elements[0][1], other.elements[0][1], epsilon) &&
				PrecisionUtils.equalsAbs(this.elements[1][0], other.elements[1][0], epsilon) &&
				PrecisionUtils.equalsAbs(this.elements[1][1], other.elements[1][1], epsilon);
	}

	@Override
	public Matrix2x2 transpose () {
		Matrix2x2 transpose = new Matrix2x2();
		transpose.elements[0][0] = this.elements[0][0];
		transpose.elements[0][1] = this.elements[1][0];
		transpose.elements[1][0] = this.elements[0][1];
		transpose.elements[1][1] = this.elements[1][1];
		return transpose;
	}
}