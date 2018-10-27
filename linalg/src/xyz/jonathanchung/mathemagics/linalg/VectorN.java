package xyz.jonathanchung.mathemagics.linalg;

import java.util.Arrays;

/**
 * This class defines an n-dimensional vector
 */
public class VectorN extends Vector<VectorN> {

	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * A sorted array of the vector's entries for minimizing error when calculating magnitude
	 */
	private double[] sorted = null;

	/**
	 * The entries in the vector
	 */
	protected double[] elements;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * The constructor for a vector
	 *
	 * @param n the number of rows in the vector
	 */
	public VectorN(int n) {
		super(n);

		elements = new double[this.rows];
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	/**
	 * Get the element at a specific index
	 *
	 * @param index the index of the desired element
	 *
	 * @return the element at the specified index
	 */
	@Override
	public double get (int index) {
		return this.elements[index];
	}

	/**
	 * Calculate the square of the vector's magnitude
	 *
	 * @return the square of the vector's magnitude
	 */
	@Override
	public double mag2 () {
		// Get the sorted array to minimize error
		if (sorted == null) {
			sorted = new double[this.rows];

			// Copy the vector's elements to the array
			for (int i = 0; i < this.rows; i++) {
				sorted[i] = this.elements[i];
			}

			Arrays.sort(sorted);
		}

		// Calculated by the Pythagorean theorem
		double mag2 = 0;
		for (int i = 0; i < this.rows; i++) {
			mag2 += sorted[i] * sorted[i];
		}

		return mag2;
	}



	// Matrix accessors ------------------------------------------------------------------------------------------------

	@Override
	public VectorN getRow (int row) {
		VectorN desired = new VectorN(1);
		desired.elements[0] = this.elements[0];

		return desired;
	}



	// Matrix mutators -------------------------------------------------------------------------------------------------

	@Override
	public void swapRows(int row1, int row2) {
		final double temp = elements[row1];
		elements[row1] = elements[row2];
		elements[row2] = temp;
	}



	// Linear object operations ----------------------------------------------------------------------------------------

	@Override
	public VectorN add (VectorN other) {
		// Check if the vectors have the same dimensions
		if (this.rows != other.rows) {
			throw new IncompatibleDimensionException(this, other);
		}

		VectorN sum = new VectorN(this.rows);

		//Add corresponding elements
		for (int i = 0; i < this.rows; ++i) {
			sum.elements[i] = this.elements[i] + other.elements[i];
		}

		return sum;
	}

	@Override
	public VectorN sub (VectorN other) {
		// Check if the vectors have the same dimensions
		if (this.rows != other.rows) {
			throw new IncompatibleDimensionException(this, other);
		}

		VectorN difference = new VectorN(this.rows);

		// Subtract corresponding elements
		for (int i = 0; i < this.rows; ++i) {
			difference.elements[i] = this.elements[i] - other.elements[i];
		}

		return difference;
	}

	@Override
	public VectorN multiply (double scalar) {
		VectorN product = new VectorN(this.rows);

		// Multiply each element by the scalar
		for (int i = 0; i < this.rows; ++i) {
			product.elements[i] = this.elements[i] * scalar;
		}

		return product;
	}



	// Matrix operations -----------------------------------------------------------------------------------------------

	@Override
	public boolean equals(VectorN other, double epsilon) {
		// Make sure the two vectors are the same dimension
		if (this.rows != other.rows) {
			return false;
		}

		for (int i = 0; i < this.rows; ++i) {
			if (this.elements[i] != other.elements[i]) {
				return false;
			}
		}

		return true;
	}

	/**
	 * @param other The vector to multiply with this one (it is assumed to be a horizontal vector)
	 *
	 * @return the product of the vectors
	 */
	@Override
	public MatrixNxM multiply (VectorN other) {
		MatrixNxM product = new MatrixNxM(this.rows, other.rows);

		for (int row = 0; row < product.rows; ++row) {
			for (int col = 0; col < product.cols; ++col) {
				product.elements[row][col] = this.elements[row] * other.elements[col];
			}
		}

		return product;
	}

	@Override
	public MatrixNxM transpose () {
		MatrixNxM transpose = new MatrixNxM(1, this.rows);

		System.arraycopy(this.elements, 0, transpose.elements[0], 0, this.rows);

		return transpose;
	}

	@Override
	public boolean isRef() {
		for (int i = 1; i < rows; ++i) {
			if (elements[i] != 0) {
				return false;
			}
		}

		return true;
	}

	@Override
	public VectorN remove(int row, int col) {
		if (col == 0 || this.rows == 1) {
			return null;
		}

		return removeRow(row);
	}

	@Override
	public VectorN removeRow(int row) {
		if (this.rows == 1) {
			return null;
		}

		VectorN newVector = new VectorN(this.rows - 1);

		int newRow = 0;

		for (int i = 0; i < this.rows; ++i) {
			// Skip the row if it is to be removed
			if (i == row) continue;

			++newRow;
			newVector.elements[newRow] = this.elements[i];
		}

		return newVector;
	}



	// Vector operations -----------------------------------------------------------------------------------------------

	/**
	 * Calculate the dot product of two vectors
	 *
	 * @param other the vector to take the dot product with
	 *
	 * @return the dot product of the two vectors
	 */
	@Override
	public double dot (VectorN other) {
		if (other.rows != this.rows) {
			throw new IncompatibleDimensionException(this, other);
		}

		double sum = 0;
		for (int i = 0; i < this.elements.length; i++) {
			sum += this.elements[i] * other.elements[i];
		}

		return sum;
	}
}
