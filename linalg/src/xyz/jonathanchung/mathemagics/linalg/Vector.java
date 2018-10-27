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
		return get(row);
	}

	/**
	 * Vectors only have one column, so we just return the vector
	 *
	 * @param col the index of the desired column - this is always ignored
	 *
	 * @return the vector
	 */
	@Override
	public final Vector<V> getCol (int col) {
		return this;
	}



	// Matrix properties -----------------------------------------------------------------------------------------------

	/**
	 * A vector is diagonal iff it is square, since it only has one column
	 */
	@Override
	public final boolean isDiagonal () {
		return isSquare();
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
