package xyz.jonathanchung.mathemagics.linalg;

public abstract class Vector<V extends Vector> extends Matrix<V> {

	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * The constructor for a vector
	 *
	 * @param n the number of rows in the vector
	 */
	public Vector(int n) {
		super(n, 1);
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	/**
	 * Get the element at a specific index
	 *
	 * @param index the index of the desired element
	 *
	 * @return the element at the specified index
	 */
	public abstract double get (int index);

	/**
	 * Calculate the square of the vector's magnitude
	 *
	 * @return the square of the vector's magnitude
	 */
	public abstract double mag2 ();

	/**
	 * Calculate the magnitude of the vector
	 *
	 * @return the magnitude of the vector
	 */
	public double mag () {
		return Math.sqrt(mag2());
	}



	// Matrix accessors ------------------------------------------------------------------------------------------------

	/**
	 * Get a specified element from the vector
	 *
	 * @param row the row of the desired element
	 * @param col the column of the desired element this is always ignored
	 *
	 * @return the element at the specified row
	 */
	@Override
	public final double get (int row, int col) {
		if (col != 0) {
			throw new ArrayIndexOutOfBoundsException(col);
		}

		return get(row);
	}

	/**
	 * Vectors only have one column, so we just return the vector
	 *
	 * @param col the index of the desired column
	 *
	 * @return the vector
	 */
	@Override
	public final Vector<V> getCol (int col) {
		if (col != 0) {
			throw new ArrayIndexOutOfBoundsException(col);
		}

		return this;
	}



	// Matrix properties

	/**
	 * Vectors only have one column, so they must have a rank of 1
	 */
	@Override
	public final int rank () {
		return 1;
	}



	// Matrix mutators -------------------------------------------------------------------------------------------------

	@Override
	public final void swapCols(int col1, int col2) {
		if (col1 != 0) {
			throw new ArrayIndexOutOfBoundsException(col1);
		} else if (col2 != 0) {
			throw new ArrayIndexOutOfBoundsException(col2);
		}

		// Do nothing; vectors only have one column
	}



	// Matrix properties -----------------------------------------------------------------------------------------------

	/**
	 * A vector is diagonal iff it is square, since it only has one column
	 */
	@Override
	public final boolean isDiagonal () {
		return isSquare();
	}



	// Matrix operations -----------------------------------------------------------------------------------------------

	/**
	 * Vectors only have one column, so it will be null after the column is removed
	 *
	 * @param col the index of the column to remove
	 *
	 * @return null
	 */
	@Override
	public final Vector removeCol(int col) {
		return null;
	}



	// Vector operations -----------------------------------------------------------------------------------------------

	/**
	 * Calculate the dot product of two vectors
	 *
	 * @param other the vector to take the dot product with
	 *
	 * @return the dot product of the two vectors
	 */
	public abstract double dot (V other);
}
