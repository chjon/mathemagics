package xyz.jonathanchung.mathemagics.calc;

public interface Function {

	/**
	 * Determine whether the function can be evaluated
	 *
	 * @return true if the function can be evaluated
	 *         false if the function cannot be evaluated
	 */
	boolean canEvaluate ();

	/**
	 * Evaluate the function for a given input
	 *
	 * @param x the input
	 *
	 * @return the result of the function
	 */
	double evaluate (double x);

	public static class ArgumentListLengthException extends RuntimeException {
		public ArgumentListLengthException (int expected, int actual) {
			super("Received " + actual + ", expected " + expected);
		}
	}
}
