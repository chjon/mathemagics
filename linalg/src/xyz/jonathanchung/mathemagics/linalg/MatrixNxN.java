package xyz.jonathanchung.mathemagics.linalg;

/**
 * This class describes an n * n square matrix of numbers
 */
public class MatrixNxN extends MatrixNxM {

	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor for creating a zero matrix with n rows and n columns
	 */
	public MatrixNxN (int n) {
		super(n, n);
	}

	/**
	 * Constructor for creating a matrix from a 2D array, with a minimum of 1 row and 1 column. Jagged arrays get padded
	 * with zeroes
	 *
	 * @param matrix the 2D array to generate the matrix from
	 */
	public MatrixNxN (double[][] matrix) {
		this(matrix.length);

		for (int i = 0; i < this.rows; ++i) {
			// Handle null rows
			if (matrix[i] == null) {
				final double[] row = new double[this.cols];
				matrix[i] = row;
			}

			// Copy from non-null rows
			System.arraycopy(matrix[i], 0, this.elements[i], 0, Math.min(cols, matrix[i].length));
		}
	}

	/**
	 * Copy constructor
	 * @param matrix the matrix to copy from
	 */
	public MatrixNxN (MatrixNxN matrix) {
		this(matrix.rows);

		for (int i = 0; i < this.rows; i++) {
			System.arraycopy(matrix.elements[i], 0, this.elements[i], 0, this.cols);
		}
	}



	// Matrix properties -----------------------------------------------------------------------------------------------

	/**
	 * Determine the determinant of the matrix using the cofactor expansion
	 *
	 * @return the determinant of the matrix
	 */
	public double determinant () {
		if (this.rows == 1) {
			return this.elements[0][0];
		}

		double sum = 0;

		for (int i = 0; i < cols; ++i) {
			double cofactor = remove(0, i).determinant();
			if (i % 2 == 1) {
				cofactor *= -1;
			}

			sum += this.elements[0][i] * cofactor;
		}

		return sum;
	}



	// Matrix operations -----------------------------------------------------------------------------------------------

	@Override
	public MatrixNxN remove (int row, int col) {
		MatrixNxN newMatrix = new MatrixNxN(this.rows - 1);

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
}
