package xyz.jonathanchung.mathemagics.calc;

public class IntegrationApproximator {
	/**
	 * Approximate the integral of a function using the composite trapezoidal rule
	 *
	 * @param f the function whose integral to approximate
	 * @param lowerBound the lower bound of the interval
	 * @param upperBound the upper bound of the interval
	 *
	 * @return the approximation of the function's integral using the composite trapezoidal rule
	 */
	public static double compositeTrapezoidalRule (Function f, double lowerBound, double upperBound) {
		// This is just a special case of the weighted average
		return weightedAverage(f, lowerBound, upperBound, 1, 2, 1);
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
