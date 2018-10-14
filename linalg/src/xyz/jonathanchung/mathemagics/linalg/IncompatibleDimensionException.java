package xyz.jonathanchung.mathemagics.linalg;

/**
 * The class {@code IncompatibleDimensionException} and its subclasses are thrown when matrix operations are
 * attempted on matrices of incompatible dimensions for those operations
 */
class IncompatibleDimensionException extends Exception {
	/**
	 * The default constructor for the exception
	 * @param matrix1 the first matrix in the operation
	 * @param matrix2 the second matrix in the operation
	 */
	IncompatibleDimensionException(Matrix matrix1, Matrix matrix2) {
		super(
				"Matrix 1: {" + matrix1.getRows() + "x" + matrix1.getCols() + "}, " +
				"Matrix 2: {" + matrix2.getRows() + "x" + matrix2.getCols() + "}"
		);
	}
}
