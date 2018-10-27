package xyz.jonathanchung.mathemagics.calc.approximators;

import org.junit.jupiter.api.Test;
import xyz.jonathanchung.mathemagics.calc.Function;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationApproximatorTest {
	@Test
	public void regularAverageTest () {
		Function f = (x) -> (x);
		assertEquals(49.5, IntegrationApproximator.weightedAverage(f, 0, 99, 1, 1));
	}

	@Test
	public void weightedAverageTest () {
		Function f = (x) -> (x * x);
		assertEquals(2.9375, IntegrationApproximator.weightedAverage(f, 1, 3, 9, 5, 2));
	}
}