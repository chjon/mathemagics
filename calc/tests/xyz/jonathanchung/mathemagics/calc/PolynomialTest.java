package xyz.jonathanchung.mathemagics.calc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolynomialTest {
	@Test
	public void degreeTest () {
		Polynomial poly = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		assertEquals(9, poly.degree());
	}

	@Test
	public void equalsSelfTest () {
		Polynomial poly = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		assertTrue(poly.equals(poly));
	}

	@Test
	public void equalityTest () {
		Polynomial poly1 = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		Polynomial poly2 = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

		assertTrue(poly1.equals(poly2));
	}

	@Test
	public void inequalityTest () {
		Polynomial poly1 = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		Polynomial poly2 = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 7);

		assertFalse(poly1.equals(poly2));
	}

	@Test
	public void degreeInequalityTest () {
		Polynomial poly1 = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		Polynomial poly2 = new Polynomial(0, 1, 2, 3, 4, 5, 6, 7, 8);

		assertFalse(poly1.equals(poly2));
	}

	@Test
	public void leadingZerosTest () {
		Polynomial poly1 = new Polynomial(3, 4, 3, 0, 0, 0);
		Polynomial poly2 = new Polynomial(3, 4, 3);

		assertTrue(poly1.equals(poly2));
	}

	@Test
	public void constantTest () {
		Polynomial poly = new Polynomial(1, 2, 3);

		assertEquals(1, poly.evaluate(0));
	}

	@Test
	public void identityTest () {
		Polynomial poly = new Polynomial(0, 1);

		assertEquals(4, poly.evaluate(4));
	}

	@Test
	public void quadraticTest () {
		Polynomial poly = new Polynomial(3, 2, 3);

		assertEquals(19, poly.evaluate(2));
	}

	@Test
	public void sumTest () {
		Polynomial poly1 = new Polynomial(1, 2, 3, 4);
		Polynomial poly2 = new Polynomial(4, 3, 2, 1);
		Polynomial sum   = new Polynomial(5, 5, 5, 5);

		assertTrue(sum.equals(poly1.add(poly2)));
	}

	@Test
	public void sumDiffDegreeTest () {
		Polynomial poly1 = new Polynomial(1, 2, 3, 4);
		Polynomial poly2 = new Polynomial(4, 2, 2);
		Polynomial sum   = new Polynomial(5, 4, 5, 4);

		assertTrue(sum.equals(poly1.add(poly2)));
	}
}