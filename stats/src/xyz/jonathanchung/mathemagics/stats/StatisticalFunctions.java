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
	 * Calculate the mean of the data
	 *
	 * @param data the data for which to calculate the mean
	 *
	 * @return the mean of the data
	 */
	public static double mean (double... data) {
		if (data == null || data.length == 0) {
			return 0;
		}

		return sum(data) / data.length;
	}

	/**
	 * Calculate the median of a sorted array. Behaviour is undefined for unsorted arrays
	 *
	 * @param sortedData the sorted data for which to calculate the median
	 *
	 * @return the median of the sorted array
	 */
	public static double medianSorted (double... sortedData) {
		if (sortedData == null || sortedData.length == 0) {
			return 0;
		}

		// Handle odd-length data
		if (sortedData.length % 2 == 1) {
			return sortedData[sortedData.length / 2];
		}

		// Handle even-length data
		int midIndex = sortedData.length / 2;
		return (sortedData[midIndex] + sortedData[midIndex - 1]) / 2d;
	}

	/**
	 * Calculate the one of the modes of a sorted array. Behaviour is undefined for unsorted arrays
	 *
	 * @param sortedData the sorted data for which to calculate the mode
	 *
	 * @return the mode of the sorted array
	 */
	public static double modeSorted (double... sortedData) {
		if (sortedData == null || sortedData.length == 0) {
			return 0;
		}

		int maxLength = 1;
		int index = 0;
		int runningLength = 1;

		for (int i = 1; i < sortedData.length; i++) {
			// Increment the length so far
			if (sortedData[i] == sortedData[i - 1]) {
				++runningLength;
				continue;
			}

			// Update the max length
			if (runningLength > maxLength) {
				maxLength = runningLength;
				index = i - 1;
			}

			// Reset the length so far
			runningLength = 1;
		}

		// Return the first element that occurred the most times
		if (runningLength > maxLength) {
			return sortedData[sortedData.length - 1];
		} else {
			return sortedData[index];
		}
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
	 * @return the population average of the data
	 */
	public static double avgP (double... data) {
		return mean(data);
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
		return mean(data);
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
