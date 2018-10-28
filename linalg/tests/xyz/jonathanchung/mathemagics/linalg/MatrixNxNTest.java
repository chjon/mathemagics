package xyz.jonathanchung.mathemagics.linalg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatrixNxNTest {

	@Test
	public void remove() {
		MatrixNxN matrix = new MatrixNxN(new double[][] {
				{1, 0, 0},
				{0, 2, 0},
				{0, 0, 3},
		});

		MatrixNxN removed = new MatrixNxN(new double[][] {
				{2, 0},
				{0, 3},
		});

		assertTrue(removed.equals(matrix.remove(0, 0)));
	}

	@Test
	public void determinant() {
		MatrixNxN matrix = new MatrixNxN(new double[][] {
				{1, 0},
				{0, 2},
		});

		assertEquals(2, matrix.determinant());
	}
}