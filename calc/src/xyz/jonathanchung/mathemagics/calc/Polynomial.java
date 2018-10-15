package xyz.jonathanchung.mathemagics.calc;

public class Polynomial implements Function {
	/**
	 * Array of all the coefficients in increasing order by degree
	 */
	private final double[] coeffs;

	/**
	 * Empty constructor for a default polynomial
	 */
	public Polynomial () {
		this(0);
	}

	public Polynomial (double... coeffs) {
		// Handle an empty coefficient array
		if (coeffs == null || coeffs.length == 0) {
			this.coeffs = new double[1];
			return;

		// Handle a coefficient array of length 1
		} else if (coeffs.length == 1) {
			this.coeffs = new double[] {coeffs[0]};
			return;
		}

		// Find the degree of the polynomial
		int degree = coeffs.length - 1;
		for (int i = degree; i >= 0 && coeffs[i] == 0; i--) {
			degree--;
		}

		// Create coefficient array
		this.coeffs = new double[degree + 1];
		System.arraycopy(coeffs, 0, this.coeffs, 0, degree + 1);
	}

	@Override
	public double evaluate(double x) {
		double ans = 0;

		// Handle non-constant terms
		for (int i = coeffs.length - 1; i > 0; i--) {
			ans += coeffs[i];
			ans *= x;
		}

		// Handle constant term
		if (coeffs.length > 0) {
			ans += coeffs[0];
		}

		return ans;
	}

	public int degree () {
		return coeffs.length - 1;
	}

	/**
	 * Add two polynomials
	 *
	 * @param other the polynomial to add to this one
	 *
	 * @return the sum of the two polynomials
	 */
	public Polynomial add (Polynomial other) {
		final int thisLength  = this.coeffs.length;
		final int otherLength = other.coeffs.length;
		int degree = 0;

		// Create array to hold the coefficients temporarily
		final double[] tempCoeffs = new double[Math.max(thisLength, otherLength)];

		// Fill the array of coefficients with the sums of the polynomial coefficients
		for (int i = 0; i < thisLength || i < otherLength; i++) {
			double coeff = 0;

			// Check if this polynomial has coefficients of degree i
			if (i < thisLength) {
				coeff += this.coeffs[i];
			}

			// Check if the other polynomial has coefficients of degree i
			if (i < otherLength) {
				coeff += other.coeffs[i];
			}

			// Put the sum into the array if it is non-zero
			if (coeff != 0) {
				tempCoeffs[i] = coeff;

				// Update the degree for non-zero coefficients
				degree = i;
			}
		}

		// Use the temporary coefficient array if it is the correct length
		if (degree == tempCoeffs.length - 1) {
			return new Polynomial(tempCoeffs);
		}

		// Create a new coefficient array if the original is the wrong length
		final double[] newCoeffs = new double[degree + 1];
		System.arraycopy(tempCoeffs, 0, newCoeffs, 0, degree);

		return new Polynomial(newCoeffs);
	}

	/**
	 * Determine whether two polynomials are equal (have the same degree)
	 *
	 * @param other the polynomial to check with
	 *
	 * @return true if the polynomials are equal
	 *         false if the polynomials are not equal
	 */
	public boolean equals (Polynomial other) {
		// Make sure the polynomials are of the same degree
		if (this.degree() != other.degree()) return false;

		// Compare each coefficient
		for (int i = 0; i < coeffs.length; i++) {
			if (this.coeffs[i] != other.coeffs[i]) return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < coeffs.length; i++) {
			double coeff = coeffs[i];
			if (coeff == 0) continue;

			if (coeff > 0) {
				sb.append("+");
			}

			sb.append(coeff).append(" x^(").append(i).append(") ");
		}

		if (sb.length() == 0) {
			return "0";
		}

		return sb.toString();
	}
}
