package xyz.jonathanchung.mathemagics.stats;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatisticalFunctionsTest {
	@Test
	public void medianSortedOddTest () {
		double[] sortedData = {
				1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 6, 6
		};

		assertEquals(3, StatisticalFunctions.medianSorted(sortedData));
	}

	@Test
	public void medianSortedEvenTest () {
		double[] sortedData = {
				1, 2, 2, 3, 3, 3, 4, 4, 4, 5, 6, 6
		};

		assertEquals(3.5, StatisticalFunctions.medianSorted(sortedData));
	}

	@Test
	public void modeSortedTest () {
		double[] sortedData = {
				1, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 6, 6
		};

		assertEquals(3, StatisticalFunctions.modeSorted(sortedData));
	}
}