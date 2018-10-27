package xyz.jonathanchung.mathemagics.calc.approximators;

import xyz.jonathanchung.mathemagics.calc.Function;

public class IntegrationApproximator {
	/**
	 * Approximate the integral of a function using the trapezoidal rule - error of O((b - a)^3)
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral using the trapezoidal rule
	 */
	public static double trapezoidalRule (Function f, double lowerBound, double upperBound) {
		return weightedArea(f, lowerBound, upperBound, 1, 1);
	}

	/**
	 * Approximate the integral of a function using the composite trapezoidal rule - error of O((b - a)^2)
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral using the composite trapezoidal rule
	 */
	public static double compositeTrapezoidalRule (Function f, double lowerBound, double upperBound, int intervals) {
		// There must be at least one interval
		intervals = Math.max(1, intervals);

		final double width = upperBound - lowerBound;
		final double intervalWidth = width / intervals;
		double sum = f.evaluate(lowerBound) + f.evaluate(upperBound);

		// The composite trapezoidal rule is a special case of a weighted average where all interior points have weight
		// 1 and the boundary points have weight 1 / 2
		for (int i = 1; i < intervals; ++i) {
			double pos = lowerBound + intervalWidth * i;
			sum += 2 * f.evaluate(pos);
		}

		return sum * intervalWidth / 2;
	}

	/**
	 * Approximate the integral of a function using Simpson's rule - error of O((b - a)^5)
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral using Simpson's rule
	 */
	public static double simpsonsRule (Function f, double lowerBound, double upperBound) {
		return weightedArea(f, lowerBound, upperBound, 1, 4, 1);
	}

	/**
	 * Approximate the integral of a function using composite Simpson's rule - error of O((b - a)^4)
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral using composite Simpson's rule
	 */
	public static double compositeSimpsonsRule (Function f, double lowerBound, double upperBound, int intervals) {
		// There must be at least two intervals
		intervals = Math.max(2, intervals);

		// The interval must be divisible by two
		intervals -= intervals % 2;

		final double width = upperBound - lowerBound;
		final double intervalWidth = width / intervals;
		double sum = f.evaluate(lowerBound) + f.evaluate(upperBound);

		for (int i = 1; i < intervals; ++i) {
			double pos = lowerBound + intervalWidth * i;
			if (i % 2 == 1) {
				sum += 4 * f.evaluate(pos);
			} else {
				sum += 2 * f.evaluate(pos);
			}
		}

		return sum * intervalWidth / 6;
	}

	/**
	 * Approximate the integral of a function using Simpson's 3/8th's rule - error of O((b - a)^5)
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral using Simpson's 3/8th's rule
	 */
	public static double simpsons3_8thsRule (Function f, double lowerBound, double upperBound) {
		return weightedArea(f, lowerBound, upperBound, 1, 3, 3, 1);
	}

	/**
	 * Approximate the integral of a function by multiplying a weighted average of the function by the width of the
	 * interval
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral
	 */
	private static double weightedArea (Function f, double lowerBound, double upperBound, double... weights) {
		final double width = upperBound - lowerBound;
		return weightedAverage(f, lowerBound, upperBound, weights) * Math.abs(width);
	}

	/**
	 * Take a weighted average of a function over an interval
	 *
	 * @param f the function for which to take the weighted average
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 * @param weights the weights for the weighted average
	 *
	 * @return the average if there are fewer than two weights
	 *         the weighted average if there are at least two weights
	 */
	public static double weightedAverage (Function f, double lowerBound, double upperBound, double... weights) {
		// Make sure the lower bound is smaller than the upper bound
		if (lowerBound > upperBound) {
			return weightedAverage(f, upperBound, lowerBound, weights);
		}

		// Return the regular (unweighted) average if there are fewer than two weights
		if (weights == null || weights.length <= 1) {
			return (f.evaluate(lowerBound) + f.evaluate(upperBound)) / 2;
		}

		double totalWeight = 0;
		double sum         = 0;

		// Calculate the weighted average
		for (int i = 0; i < weights.length; ++i) {
			// Get the position in the interval
			double pos = lowerBound + i * (upperBound - lowerBound) / (weights.length - 1);

			// Add to the sum and total weight
			sum         += weights[i] * f.evaluate(pos);
			totalWeight += weights[i];
		}

		return sum / totalWeight;
	}
}
