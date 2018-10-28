package xyz.jonathanchung.mathemagics.calc;

/**
 * This class defines functionality for an object that can be multiplied by another object of the same type
 *
 * @param <T> the type of multipliable object
 */
public interface Multipliable <T> {
	/**
	 * Determine the product of two math objects
	 *
	 * @param other the object by which to multiply
	 *
	 * @return the product of the two math objects
	 */
	T multiply (T other);
}
