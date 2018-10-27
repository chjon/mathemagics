package xyz.jonathanchung.mathemagics.linalg;

import java.util.Arrays;

public class Vector extends Matrix {

	// Fields ----------------------------------------------------------------------------------------------------------

	private double[] sorted = null;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * The constructor for a vector
	 *
	 * @param n the number of rows in the vector
	 */
	public Vector(int n) {
		super(n, 1);
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	/**
	 * Get the element at a specific index
	 *
	 * @param index the index of the desired element
	 *
	 * @return the element at the specified index
	 */
	public double get (int index) {
		return this.elements[index][0];
	}

	/**
	 * Calculate the square of the vector's magnitude
	 *
	 * @return the square of the vector's magnitude
	 */
	public double mag2 () {
		// Get the sorted array to minimize error
		if (sorted == null) {
			sorted = new double[this.rows];

			// Copy the vector's elements to the array
			for (int i = 0; i < this.rows; i++) {
				sorted[i] = this.elements[i][0];
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

	/**
	 * Calculate the magnitude of the vector
	 *
	 * @return the magnitude of the vector
	 */
	public double mag () {
		return Math.sqrt(mag2());
	}



	// Vector operations -----------------------------------------------------------------------------------------------

	/**
	 * Calculate the dot product of two vectors
	 *
	 * @param other the vector to take the dot product with
	 *
	 * @return the dot product of the two vectors
	 */
	public double dot (Vector other) {
		double sum = 0;
		for (int i = 0; i < this.elements.length; i++) {
			sum += this.elements[i][0] * other.elements[i][0];
		}

		return sum;
	}
}
