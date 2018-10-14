package xyz.jonathanchung.mathemagics.linalg;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.jonathanchung.mathemagics.calc.PrecisionUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatrixTest {

	@Test
	public void zeroDimensionConstructorTest () {
		Matrix matrix = new Matrix(0, 0);
		assertEquals(matrix.getRows(), 1);
		assertEquals(matrix.getCols(), 1);
	}

	@Test
	public void negativeDimensionConstructorTest () {
		Matrix matrix = new Matrix(-15, 10);
		assertEquals(matrix.getRows(), 1);
		assertEquals(matrix.getCols(), 10);

		matrix = new Matrix(15, -1);
		assertEquals(matrix.getRows(), 15);
		assertEquals(matrix.getCols(), 1);
	}

	@Test
	public void arrayConstructorTest () {
		double[][] vals = {
				{3, 1, 4},
				{1, 5, 9},
				{2, 6, 5},
		};

		Matrix matrix = new Matrix(vals);

		for (int i = 0; i < matrix.getRows(); i++) {
			for (int j = 0; j < matrix.getCols(); j++) {
				assertTrue(PrecisionUtils.equalsAbs(vals[i][j], matrix.get(i, j), 0));
			}
		}
	}

	@Test
	public void jaggedArrayConstructorTest () {
		double[][] vals = {
				{3, 1, 4, 1, 5, 9            },
				{2, 1, 8, 2, 8               },
				{1, 1, 2, 3, 5, 8, 13, 21, 34},
		};

		Matrix matrix = new Matrix(vals);

		for (int i = 0; i < matrix.getRows(); i++) {
			for (int j = 0; j < matrix.getCols(); j++) {
				if (j >= vals[i].length) {
					assertTrue(PrecisionUtils.equalsAbs(0, matrix.get(i, j), 0));
				} else {
					assertTrue(PrecisionUtils.equalsAbs(vals[i][j], matrix.get(i, j), 0));
				}
			}
		}
	}

	@Test
	public void matrixEqualityTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{0,  -3,  8,  3, -2},
		});

		Matrix matrix2 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		assertTrue(matrix1.equals(matrix2));

		matrix2 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5.001},
				{-5, -4, -3, -2, -1.000},
				{ 0, -3,  8,  3, -2.000},
		});

		assertFalse(matrix1.equals(matrix2));
		assertTrue(matrix1.equals(matrix2, 0.01));
	}

	@Test
	public void matrixInequalityTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		Matrix matrix2 = new Matrix(new double[][] {
				{-1, -2.0, -3, -4, -5},
				{-5, -4.3, -3, -2, -1},
				{ 0, -3.0,  8,  3, -2},
		});

		assertFalse(matrix1.equals(matrix2));
	}

	@Test
	public void matrixSumTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		Matrix matrix2 = new Matrix(new double[][] {
				{1, 2,  3,  4, 5},
				{5, 4,  3,  2, 1},
				{0, 3, -8, -3, 2},
		});

		Matrix sumMatrix = new Matrix(3, 5);

		try {
			assertTrue(matrix1.equals(sumMatrix.add(matrix1)));
			assertTrue(sumMatrix.equals(matrix1.add(matrix2)));
		} catch (IncompatibleDimensionException ex) {
			Assertions.fail("Dimensions are the same - IncompatibleDimensionException should not be thrown");
		}
	}

	@Test
	public void matrixDifferenceTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		Matrix matrix2 = new Matrix(new double[][] {
				{1, 2,  3,  4, 5},
				{5, 4,  3,  2, 1},
				{0, 3, -8, -3, 2},
		});

		Matrix differenceMatrix = new Matrix(new double[][] {
				{ -2, -4, -6, -8, -10},
				{-10, -8, -6, -4,  -2},
				{  0, -6, 16,  6,  -4},
		});

		try {
			assertTrue(matrix1.equals(differenceMatrix.subtract(matrix1)));
			assertTrue(differenceMatrix.equals(matrix1.subtract(matrix2)));
		} catch (IncompatibleDimensionException ex) {
			Assertions.fail("Dimensions are the same - IncompatibleDimensionException should not be thrown");
		}
	}

	@Test
	public void matrixTransposeTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		Matrix matrix2 = new Matrix(new double[][] {
				{-1, -5,  0},
				{-2, -4, -3},
				{-3, -3,  8},
				{-4, -2,  3},
				{-5, -1, -2},
		});

		assertTrue(matrix1.transpose().equals(matrix2));
		assertTrue(matrix1.transpose().transpose().equals(matrix1));
	}

	@Test
	public void matrixProductTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		Matrix matrix2 = new Matrix(new double[][] {
				{ 55,  35, -20},
				{ 35,  55, -16},
				{-20, -16,  86},
		});


		try {
			assertTrue(matrix1.multiply(matrix1.transpose()).equals(matrix2));
		} catch (IncompatibleDimensionException ex) {
			Assertions.fail("Dimensions are valid - IncompatibleDimensionException should not be thrown");
		}
	}

	@Test
	public void matrixProductInvalidDimensionTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{-1, -2, -3, -4, -5},
				{-5, -4, -3, -2, -1},
				{ 0, -3,  8,  3, -2},
		});

		try {
			matrix1.multiply(matrix1);
			Assertions.fail("Dimensions are invalid - IncompatibleDimensionException should be thrown");
		} catch (IncompatibleDimensionException ex) {
			// This is expected
		}
	}

	@Test
	public void matrixRefTest () {
		Matrix matrix1 = new Matrix(new double[][] {
				{ 0, -3,  8,  3, -2},
				{-5, -4, -3, -2, -1},
				{-1, -2, -3, -4, -5},
		});

		Matrix ref = new Matrix(new double[][] {
				{-5, -4, -3.0, -2.0, -1},
				{ 0, -3,  8.0,  3.0, -2},
				{ 0,  0, -5.6, -4.8, -4},
		});

		assertTrue(matrix1.ref().equals(ref));

		Matrix matrix2 = new Matrix(new double[][] {
				{ 1,  2, 1},
				{-2, -3, 1},
				{ 3,  5, 0},
		});

		Matrix ref2 = new Matrix(new double[][] {
				{3, 5,    0},
				{0, 1/3d, 1},
				{0, 0,    0},
		});

		assertTrue(matrix2.ref().equals(ref2));
	}
}