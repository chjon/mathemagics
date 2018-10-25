package xyz.jonathanchung.mathemagics.stats;

public class StatisticalFunctions {
	/**
	 * Calculate the sum of a list of data
	 *
	 * @param data a list of values to add
	 *
	 * @return the sum of all the values
	 */
	public static double sum (double... data) {
		double sum = 0;
		for (double datum : data) {
			sum += datum;
		}

		return sum;
	}

	/**
	 * Calculate the sample average
	 *
	 * @param data the data to average
	 *
	 * @return 0 if the list of data is empty
	 *         the sample average of the data if the list is not empty
	 */
	public static double avgS (double... data) {
		if (data == null || data.length == 0) {
			return 0;
		}

		return sum(data) / (data.length + 1);
	}

	/**
	 * Calculate the population average
	 *
	 * @param data the data to average
	 *
	 * @return 0 if the list of data is empty
	 *         the population average of the data if the list is not empty
	 */
	public static double avgP (double... data) {
		if (data == null || data.length == 0) {
			return 0;
		}

		return sum(data) / (data.length);
	}

	/**
	 * Calculate the average
	 *
	 * @param data the data to average
	 *
	 * @return 0 if the list of data is empty
	 *         the average of the data if the list is not empty
	 */
	public static double avg (double... data) {
		return avgP(data);
	}

	/**
	 * Calculate the sum of the squares of the deviations
	 *
	 * @param avg the average of the data
	 * @param data the data to calculate for
	 *
	 * @return the sum of the squares of the deviations
	 */
	private static double deviationsSquared (double avg, double... data) {
		double sum = 0;
		for (double datum : data) {
			double deviation = datum - avg;
			sum += deviation * deviation;
		}

		return sum;
	}

	/**
	 * Calculate the sum of the squares of the deviations (defaults to sample average)
	 *
	 * @param data the data to calculate for
	 *
	 * @return the sum of the squares of the deviations
	 */
	private static double deviationsSquared (double... data) {
		return deviationsSquared(avgS(data), data);
	}

	/**
	 * Calculate the variance
	 *
	 * @param data the data to find the variance for
	 *
	 * @return the variance of the data
	 */
	public static double variance (double... data) {
		return deviationsSquared(avg(data), data) / (data.length);
	}

	/**
	 * Calculate the sample standard deviation
	 *
	 * @param avgS the sample average of the data
	 * @param data the data for which to calculate the sample standard deviation
	 *
	 * @return the sample standard deviation
	 */
	public static double stdDevS (double avgS, double... data) {
		return Math.sqrt(deviationsSquared(avgS, data) / (data.length));
	}

	/**
	 * Calculate the sample standard deviation
	 *
	 * @param data the data for which to calculate the sample standard deviation
	 *
	 * @return the sample standard deviation
	 */
	public static double stdDevS (double... data) {
		return stdDevS(avgS(data), data);
	}

	/**
	 * Calculate the population standard deviation
	 *
	 * @param avgP the population average of the data
	 * @param data the data for which to calculate the population standard deviation
	 *
	 * @return the population standard deviation
	 */
	public static double stdDevP (double avgP, double... data) {
		return Math.sqrt(deviationsSquared(avgP, data) / (data.length - 1));
	}

	/**
	 * Calculate the population standard deviation
	 *
	 * @param data the data for which to calculate the population standard deviation
	 *
	 * @return the population standard deviation
	 */
	public static double stdDevP (double... data) {
		return stdDevP(avgP(data), data);
	}

	/**
	 * Calculate the indicator of bias in the data. If the return value exceeds 1.96, we can be 95% certain that there
	 * is bias in the data set
	 *
	 * @param data the data to check for bias	
	 *
	 * @return a value indicating the certainty that there is a bias
	 */
	public static double biasIndicator (double... data) {
		double avgS = avgS(data);
		return Math.sqrt(data.length) * avgS / stdDevS(avgS, data);
	}
}
