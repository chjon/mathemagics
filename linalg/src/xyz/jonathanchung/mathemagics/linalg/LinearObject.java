package xyz.jonathanchung.mathemagics.linalg;

public interface LinearObject <T> {
	/**
	 * Calculate the sum of two linear objects
	 *
	 * @param other the linear object to add to this one
	 *
	 * @return the sum of the two linear objects
	 */
	T add (final T other);

	/**
	 * Calculate the difference of two linear objects
	 *
	 * @param other the linear object to subtract from this one
	 *
	 * @return the difference of two linear objects
	 */
	T sub (final T other);

	/**
	 * Multiply the linear object by a scalar
	 *
	 * @param scalar the value by which to multiply the linear object
	 *
	 * @return the product of the multiplication of this linear object by the scalar
	 */
	T multiply (double scalar);
}
