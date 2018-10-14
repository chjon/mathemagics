package xyz.jonathanchung.mathemagics.calc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RootFinderTest {
	private static final double EXPECTED_PRECISION = 1e-10;
	private static final RootFinder RF = RootFinder.DEFAULT;

	@Test
	public void newtonsRootTest () {
		final Function f  = (x) -> (x * x + 10 * x - 100);
		final Function df = (x) -> (2 * x + 10);
		final double root = 6.1803398874989;

		assertEquals(root, RF.newtonsMethod(f, df, 5), EXPECTED_PRECISION);
		assertTrue(RF.getNumIterations() > 0 && RF.getNumIterations() < RF.getMaxIterations());
	}

	@Test
	public void newtonsNoRootTest () {
		final Function f  = (x) -> (x * x + 10);
		final Function df = (x) -> (2 * x);

		assertTrue(Double.isNaN(RF.newtonsMethod(f, df, 5)));
		assertEquals(RF.getNumIterations(), RF.getMaxIterations());
	}

	@Test
	public void bisectionRootTest () {
		final Function f  = (x) -> (x * x + 10 * x - 100);
		final double root = 6.1803398874989;

		assertEquals(root, RF.bisectionMethod(f, 0, 100), EXPECTED_PRECISION);
		assertTrue(RF.getNumIterations() > 0 && RF.getNumIterations() < RF.getMaxIterations());
	}

	@Test
	public void bisectionNoRootTest () {
		final Function f  = (x) -> (x * x + 10);

		assertTrue(Double.isNaN(RF.bisectionMethod(f, 0, 100)));
		assertEquals(RF.getNumIterations(), 0);
	}

	@Test
	public void secantRootTest () {
		final Function f  = (x) -> (x * x + 10 * x - 100);
		final double root = 6.1803398874989;

		assertEquals(root, RF.secantMethod(f, 0, 100), EXPECTED_PRECISION);
		assertTrue(RF.getNumIterations() > 0 && RF.getNumIterations() < RF.getMaxIterations());
	}

	@Test
	public void secantNoRootTest () {
		final Function f  = (x) -> (x * x + 10);

		assertTrue(Double.isNaN(RF.secantMethod(f, 0, 100)));
		assertEquals(RF.getNumIterations(), 0);
	}

	@Test
	public void fixedPointRootTest () {
		final Function f = (x) -> (Math.sqrt(100 - 10 * x));
		final double root = 6.1803398874989;

		assertEquals(root, RF.fixedPointIteration(f, 5), EXPECTED_PRECISION);
		assertTrue(RF.getNumIterations() > 0 && RF.getNumIterations() < RF.getMaxIterations());
	}

	@Test
	public void fixedPointNoRootTest () {
		final Function f = (x) -> (Math.sqrt(-10));

		assertTrue(Double.isNaN(RF.fixedPointIteration(f, 5)));
		assertEquals(RF.getNumIterations(), RF.getMaxIterations());
	}
}