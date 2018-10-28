package xyz.jonathanchung.mathemagics.calc.approximators;

import org.junit.jupiter.api.Test;
import xyz.jonathanchung.mathemagics.calc.Function;
import xyz.jonathanchung.mathemagics.calc.FunctionImpl;

import static org.junit.jupiter.api.Assertions.*;

class IntegrationApproximatorTest {
	@Test
	public void regularAverageTest () {
		FunctionImpl f = new FunctionImpl() {
			@Override
			public double evaluate(double x) {
				return x;
			}
		};

		assertEquals(49.5, IntegrationApproximator.weightedAverage(f, 0, 99, 1, 1));
	}

	@Test
	public void weightedAverageTest () {
		FunctionImpl f = new FunctionImpl() {
			@Override
			public double evaluate(double x) {
				return x*x;
			}
		};

		assertEquals(2.9375, IntegrationApproximator.weightedAverage(f, 1, 3, 9, 5, 2));
	}
}