package xyz.jonathanchung.mathemagics.calc;

public class Polynomial implements Function {
	public static final Polynomial ZERO = new Polynomial(0);
	public static final Polynomial ONE  = new Polynomial(1);

	/**
	 * Array of all the coefficients in increasing order by degree
	 */
	private final double[] coeffs;

	/**
	 * Constructor for a polynomial given coefficients
	 *
	 * @param coeffs the coefficients for the polynomial, from the least-order term to the highest-order term
	 */
	public Polynomial (final double... coeffs) {
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

		// Solve by Horner's rule
		for (int i = coeffs.length - 1; i >= 0; i--) {
			ans *= x;
			ans += coeffs[i];
		}

		return ans;
	}

	/**
	 * Get the degree of the polynomial
	 *
	 * @return the degree of the polynomial
	 */
	public int degree () {
		return coeffs.length - 1;
	}

	/**
	 * Determine whether a polynomial is zero
	 * @return true if the polynomial is zero
	 *         false if the polynomial is non-zero
	 */
	public boolean isZero () {
		return equals(ZERO);
	}

	/**
	 * Add two polynomials
	 *
	 * @param other the polynomial to add to this one
	 *
	 * @return the sum of the two polynomials
	 */
	public Polynomial add (final Polynomial other) {
		// Check if either polynomial is zero
		if (this.isZero()) {
			return other;
		} else if (other.isZero()) {
			return this;
		}

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
	 * Multiply two polynomials
	 *
	 * @param other the polynomial to multiply by
	 *
	 * @return the product of the multiplication of the polynomials
	 */
	public Polynomial multiply (final Polynomial other) {
		// Check if either polynomial is zero
		if (this.isZero() || other.isZero()) {
			return ZERO;
		}

		final int degree = this.degree() + other.degree();
		final double[] resultCoeffs = new double[degree + 1];

		// Multiply every term in the polynomial by every term in the other polynomial
		for (int i = 0; i < this.coeffs.length; i++) {
			for (int j = 0; j < other.coeffs.length; j++) {
				resultCoeffs[i + j] += this.coeffs[i] * other.coeffs[j];
			}
		}

		return new Polynomial(resultCoeffs);
	}

	/**
	 * Raise a polynomial to an exponent
	 *
	 * @param exponent the exponent to which to raise the polynomial
	 *
	 * @return the @code{exponent} power of the polynomial
	 */
	public Polynomial pow (int exponent) {
		// Handle the zero exponent
		if (exponent == 0) {
			if (this.isZero()) {
				return null;
			} else {
				return ONE;
			}

		// Handle an exponent of 1
		} else if (exponent == 1) {
			return this;
		}

		// Perform the exponentiation
		Polynomial pow = this.pow(exponent / 2);
		pow = pow.multiply(pow);
		if (exponent % 2 == 0) {
			return pow;
		} else {
			return this.multiply(pow);
		}
	}

	/**
	 * Determine whether two polynomials are equal (have the same degree)
	 *
	 * @param other the polynomial to check with
	 *
	 * @return true if the polynomials are equal
	 *         false if the polynomials are not equal
	 */
	public boolean equals (final Polynomial other) {
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
