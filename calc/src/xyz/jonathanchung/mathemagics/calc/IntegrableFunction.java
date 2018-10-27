package xyz.jonathanchung.mathemagics.calc;

/**
 * This interface defines the required functionality of an integrable function
 *
 * @param <T> the object that implements this interface
 */
public interface IntegrableFunction <T> extends Function {
	/**
	 * Find the anti-derivative of the function (+C is assumed to be 0)
	 *
	 * @return the anti-derivative of the function
	 */
	T antiDifferentiate ();

	/**
	 * Integrate the function from a to b
	 *
	 * @param a the lower bound of the interval
	 * @param b the upper bound of the interval
	 *
	 * @return the result of the integration of the function from a to b
	 */
	double integrate (double a, double b);
}
