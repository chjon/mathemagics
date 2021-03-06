package xyz.jonathanchung.mathemagics.linalg;

/**
 * This class defines the functionality of a matrix
 */
public abstract class Matrix<M extends Matrix> implements LinearObject<M> {

	// Constants -------------------------------------------------------------------------------------------------------

	/**
	 * The acceptable error for float equality checks
	 */
	static double EPSILON = 1e-10;



	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * The number of rows in the matrix
	 */
	protected int rows;

	/**
	 * The number of columns in the matrix
	 */
	protected int cols;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor for creating a matrix with n rows and m columns, with a minimum of 1 row and 1 column
	 *
	 * @param n the number of rows
	 * @param m the number of columns
	 */
	public Matrix (int n, int m) {
		// Set the dimensions of the matrix
		this.rows = Math.max(1, n);
		this.cols = Math.max(1, m);
	}

	/**
	 * Constructor for creating a matrix with 1 row and 1 column
	 */
	protected Matrix () {
		this.rows = 1;
		this.cols = 1;
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	/**
	 * Get the number of rows in the matrix
	 *
	 * @return the number of rows in the matrix
	 */
	public final int getRows () {
		return this.rows;
	}

	/**
	 * Get the number of columns in the matrix
	 *
	 * @return the number of columns in the matrix
	 */
	public final int getCols () {
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
	public abstract double get (int row, int col);

	/**
	 * Get the vector containing the specified row
	 *
	 * @param row the index of the desired row
	 *
	 * @return the vector containing the specified row
	 */
	public abstract Vector getRow (int row);

	/**
	 * Get the vector containing the specified column
	 *
	 * @param col the index of the desired column
	 *
	 * @return the vector containing the specified column
	 */
	public abstract Vector getCol (int col);



	// Matrix properties -----------------------------------------------------------------------------------------------

	/**
	 * Determine whether the matrix is square
	 *
	 * @return true if the matrix is square
	 *         false if the matrix is not square
	 */
	public final boolean isSquare () {
		return rows == cols;
	}

	/**
	 * Determine whether a matrix is diagonal
	 *
	 * @return true if the matrix is diagonal
	 *         false if the matrix is not diagonal
	 */
	public abstract boolean isDiagonal ();

	/**
	 * Determine the rank of the matrix
	 *
	 * @return the rank of the matrix
	 */
	public abstract int rank ();

	/**
	 * Determine whether a matrix is in row echelon form
	 *
	 * @return true if the matrix is in REF
	 *         false if the matrix is not in REF
	 */
	public abstract boolean isRef ();



	// Mutators --------------------------------------------------------------------------------------------------------

	/**
	 * Swap the rows of a matrix
	 *
	 * @param row1 the index of the first row to swap
	 * @param row2 the index of the second row to swap
	 */
	public abstract void swapRows (int row1, int row2);

	/**
	 * Swap the columns of a matrix
	 *
	 * @param col1 the index of the first column to swap
	 * @param col2 the index of the second column to swap
	 */
	public abstract void swapCols (int col1, int col2);



	// Matrix operations -----------------------------------------------------------------------------------------------

	/**
	 * Determine whether two matrices are equal (no error)
	 *
	 * @param other the matrix to compare with
	 *
	 * @return true if the matrices are equal
	 *         false if the matrices are not equal
	 */
	public final boolean equals(M other) {
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
	public abstract boolean equals(M other, double epsilon);

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
	public abstract Matrix multiply (M other) throws IncompatibleDimensionException;

	/**
	 * Calculate the transpose of the matrix
	 *
	 * @return the transpose of the matrix
	 */
	public abstract Matrix transpose ();

	/**
	 * Remove a row and column from a matrix
	 *
	 * @param row the row to remove
	 * @param col the column to remove
	 *
	 * @return the matrix with the specified rows and columns removed
	 *         null if there are no rows and columns left
	 */
	public Matrix remove (int row, int col) {
		Matrix noRow = removeRow(row);
		if (noRow == null) {
			return null;
		}

		return noRow.removeCol(col);
	}

	/**
	 * Remove a row from a matrix
	 *
	 * @param row the index of the row to remove
	 *
	 * @return the matrix with the specified row removed
	 */
	public abstract Matrix removeRow (int row);

	/**
	 * Remove a column from a matrix
	 *
	 * @param col the index of the column to remove
	 *
	 * @return the matrix with the specified column removed
	 */
	public abstract Matrix removeCol (int col);
}
