package xyz.jonathanchung.mathemagics.calc.approximators;

import xyz.jonathanchung.mathemagics.calc.Function;
import xyz.jonathanchung.mathemagics.calc.PrecisionUtils;

public class RootFinder {
	public static final RootFinder DEFAULT = new RootFinder();

	// Constants -------------------------------------------------------------------------------------------------------

	/**
	 * The minimum required precision
	 */
	private double EPSILON = 1e-14;

	/**
	 * The maximum number of iterations before stopping
	 */
	private int MAX_ITERATIONS = 200;



	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * The number of iterations performed before stopping
	 */
	private int numIterations = 0;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Empty constructor for a default root finder
	 */
	public RootFinder () {

	}

	/**
	 * Constructor for a root finder
	 *
	 * @param EPSILON the minimum required precision
	 * @param MAX_ITERATIONS the maximum number of iterations before stopping
	 */
	public RootFinder (double EPSILON, int MAX_ITERATIONS) {
		this.EPSILON = EPSILON;
		this.MAX_ITERATIONS = MAX_ITERATIONS;
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	/**
	 * Get the minimum required precision
	 *
	 * @return the minimum required precision
	 */
	public double getEpsilon () {
		return EPSILON;
	}

	/**
	 * Get the maximum number of iterations
	 *
	 * @return the maximum number of iterations
	 */
	public int getMaxIterations () {
		return MAX_ITERATIONS;
	}

	/**
	 * Get the number of iterations
	 *
	 * @return the number of iterations
	 */
	public int getNumIterations() {
		return numIterations;
	}



	// Root-finding methods --------------------------------------------------------------------------------------------

	/**
	 * Newton's method - converges in O(h^2)
	 *
	 * @param f the function for which to find the root
	 * @param df the derivative function of @code{f}
	 * @param approx the initial approximation of the root
	 *
	 * @return the value of the approximation after @code{numIterations} iterations
	 *         Double.NaN if the root could not be found
	 */
	public double newtonsMethod (Function f, Function df, double approx) {
		double prevApprox;

		// Reset the number of iterations
		this.numIterations = 0;

		// Iterate Newton's method
		for (int i = 0; i < MAX_ITERATIONS; i++, this.numIterations++) {
			prevApprox = approx;
			approx = approx - f.evaluate(approx) / df.evaluate(approx);

			// Return the approximation if it is sufficiently precise
			if (PrecisionUtils.equalsAbs(prevApprox, approx, EPSILON)) {
				return approx;
			}
		}

		// Return NaN if no root is found
		return Double.NaN;
	}

	/**
	 * Bisection method - converges in O(h)
	 *
	 * @param f the function for which to find the root
	 * @param left the leftmost boundary
	 * @param right the rightmost bounday
	 *
	 * @return the value of the approximation after @code{numIterations} iterations
	 *         Double.NaN if the root could not be found
	 */
	public double bisectionMethod(Function f, double left, double right) {
		// Reset the number of iterations
		this.numIterations = 0;

		// Make sure that the function crosses the axis
		final double signL = Math.signum(f.evaluate(left));
		final double signR = Math.signum(f.evaluate(right));
		if (signL == signR) {
			return Double.NaN;
		}

		// Iterate bisection
		double mid = Double.NaN;
		double signM;
		for (int i = 0; i < MAX_ITERATIONS && !PrecisionUtils.equalsAbs(left, right, EPSILON); i++, this.numIterations++) {
			mid = (left + right) / 2;
			signM = Math.signum(f.evaluate(mid));

			if (signL == signM) {
				left = mid;
			} else {
				right = mid;
			}
		}

		return mid;
	}

	/**
	 * Secant method - converges in O(h)
	 *
	 * @param f the function for which to find the root
	 * @param left the leftmost boundary
	 * @param right the rightmost bounday
	 *
	 * @return the value of the approximation after @code{numIterations} iterations
	 *         Double.NaN if the root could not be found
	 */
	public double secantMethod (Function f, double left, double right) {
		// Reset the number of iterations
		this.numIterations = 0;

		// Make sure that the function crosses the axis
		double fLeft  = f.evaluate(left);
		double fRight = f.evaluate(right);
		final double signL = Math.signum(fLeft);
		final double signR = Math.signum(fRight);
		if (signL == signR) {
			return Double.NaN;
		}

		double lastLeft  = left;
		double lastRight = right;

		// Iterate finding the root of the secant
		double mid;
		double signM;
		for (int i = 0; i < MAX_ITERATIONS; i++, this.numIterations++) {
			mid = (fLeft) * (left - right) / (fRight - fLeft) + left;
			signM = Math.signum(f.evaluate(mid));

			if (signL == signM) {
				lastLeft = left;
				left = mid;
				fLeft = f.evaluate(left);
			} else {
				lastRight = right;
				right = mid;
				fRight = f.evaluate(right);
			}

			if (PrecisionUtils.equalsAbs(lastLeft, left, EPSILON) && PrecisionUtils.equalsAbs(lastRight, right, EPSILON)) {
				return mid;
			}
		}

		return Double.NaN;
	}

	/**
	 * Fixed point iteration - converges in O(h)
	 *
	 * @param f the function for which to find the root (x = f(x))
	 * @param approx the initial approximation of the root
	 *
	 * @return the approximation of the root
	 *         NaN if no root is found
	 */
	public double fixedPointIteration (Function f, double approx) {
		double prevApprox;

		// Reset the number of iterations
		this.numIterations = 0;

		for (int i = 0; i < MAX_ITERATIONS; i++, this.numIterations++) {
			prevApprox = approx;
			approx = f.evaluate(approx);

			// Return the approximation if it is sufficiently precise
			if (PrecisionUtils.equalsAbs(prevApprox, approx, EPSILON)) {
				return approx;
			}
		}

		// Return NaN if no root is found
		return Double.NaN;
	}
}
