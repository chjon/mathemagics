package xyz.jonathanchung.mathemagics.calc;

/**
 * This interface defines the required functionality of a differentiable function
 *
 * @param <T> the object that implements this interface
 */
public interface DifferentiableFunction<T> extends Function {
	/**
	 * Differentiate the function
	 *
	 * @return the derivative of the function
	 */
	T differentiate ();
}
