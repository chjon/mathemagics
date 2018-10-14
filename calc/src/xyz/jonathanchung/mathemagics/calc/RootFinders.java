package xyz.jonathanchung.mathemagics.calc;

public class RootFinders {
	/**
	 * Newton's method - converges in O(h^2)
	 *
	 * @param f the function for which to find the root
	 * @param df the derivative function of @code{f}
	 * @param approx the initial approximation of the root
	 * @param numIterations the number of iterations to run before stopping
	 *
	 * @return the value of the approximation after @code{numIterations} iterations
	 */
	public static double newtonsMethod (Function f, Function df, double approx, int numIterations) {
		for (int i = 0; i < numIterations; i++) {
			approx = approx - f.evaluate(approx) / df.evaluate(approx);
		}

		return approx;
	}

	/**
	 * Bisection method - converges in O(h)
	 *
	 * @param f the function for which to find the root
	 * @param left the leftmost boundary
	 * @param right the rightmost bounday
	 * @param numIterations the number of iterations to run before stopping
	 *
	 * @return the value of the approximation after @code{numIterations} iterations
	 *         Double.NaN if the root could not be found
	 */
	public static double bisection (Function f, double left, double right, int numIterations) {
		// Make sure that the function crosses the axis
		double signL = Math.signum(f.evaluate(left));
		double signR = Math.signum(f.evaluate(right));
		if (signL == signR) {
			return Double.NaN;
		}

		double mid = Double.NaN;
		double signM;
		for (int i = 0; i < numIterations; i++) {
			mid = (left + right) / 2;
			signM = Math.signum(f.evaluate(mid));

			if (signL == signM) {
				left = mid;
				signL = signM;
			} else {
				right = mid;
			}
		}

		return mid;
	}
}
