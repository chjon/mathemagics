package xyz.jonathanchung.mathemagics.calc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RootFinderTest {
	@Test
	public void approximationTest () {
		Function f = (x) -> (x * x - 1001);
		Function df = (x) -> (2 * x);

		final double root = 31.638584039112747;

		assertEquals(root, RootFinders.newtonsMethod(f, df, 25, 6));
		assertEquals(root, RootFinders.bisection(f, 0, 100, 55));
	}
}