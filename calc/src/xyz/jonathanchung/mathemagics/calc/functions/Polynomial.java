package xyz.jonathanchung.mathemagics.calc.functions;

import xyz.jonathanchung.mathemagics.calc.DifferentiableFunction;
import xyz.jonathanchung.mathemagics.calc.DivisionByZeroException;
import xyz.jonathanchung.mathemagics.calc.Function;
import xyz.jonathanchung.mathemagics.calc.IntegrableFunction;
import xyz.jonathanchung.mathemagics.calc.symbols.Constant;
import xyz.jonathanchung.mathemagics.calc.symbols.operations.OperationAdd;
import xyz.jonathanchung.mathemagics.calc.symbols.operations.OperationDiv;
import xyz.jonathanchung.mathemagics.calc.symbols.operations.OperationMul;
import xyz.jonathanchung.mathemagics.linalg.LinearObject;

public class Polynomial implements
		LinearObject<Polynomial>,
		DifferentiableFunction<Polynomial>,
		IntegrableFunction<Polynomial> {

	// Constants -------------------------------------------------------------------------------------------------------

	public static final Polynomial ZERO = new Polynomial(Constant.ZERO);
	public static final Polynomial ONE  = new Polynomial(Constant.ONE);



	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * Array of all the coefficients in increasing order by degree
	 */
	private final Function[] coeffs;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor for a polynomial given coefficients
	 *
	 * @param coeffs the coefficients for the polynomial, from the least-order term to the highest-order term
	 */
	public Polynomial (final Function... coeffs) {
		this(coeffs, false);
	}

	/**
	 * Constructor for a polynomial given coefficients
	 *
	 * @param coeffs the coefficients for the polynomial, from the least-order term to the highest-order term
	 * @param useArray whether the array that is passed in can be used as the member array
	 */
	private Polynomial (final Function[] coeffs, boolean useArray) {
		// Check whether to use the given array
		if (useArray) {
			this.coeffs = coeffs;
			return;
		}

		// Handle an empty coefficient array
		if (coeffs == null || coeffs.length == 0) {
			this.coeffs = new Function[1];
			return;

			// Handle a coefficient array of length 1
		} else if (coeffs.length == 1) {
			this.coeffs = new Function[] {coeffs[0]};
			return;
		}

		// Find the degree of the polynomial
		int degree = coeffs.length - 1;
		for (int i = degree; i >= 0 && coeffs[i] == Constant.ZERO; --i) {
			degree--;
		}

		// Create coefficient array
		this.coeffs = new Function[degree + 1];
		System.arraycopy(coeffs, 0, this.coeffs, 0, degree + 1);
	}



	// Accessors -------------------------------------------------------------------------------------------------------

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
	 * Get a string representation of the polynomial
	 *
	 * @return a string representation of the polynomial
	 */
	@Override
	public String toString() {
		// Check if this is the zero polynomial
		if (isZero()) {
			return "0";
		}

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < coeffs.length; ++i) {
			Function coeff = coeffs[i];
			if (coeff.equals(Constant.ZERO)) continue;

			sb.append(coeff).append(" x^(").append(i).append(") ");
		}

		return sb.toString();
	}



	// Linear object operations ----------------------------------------------------------------------------------------

	@Override
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
		final Function[] tempCoeffs = new Function[Math.max(thisLength, otherLength)];

		// Fill the array of coefficients with the sums of the polynomial coefficients
		for (int i = 0; i < thisLength || i < otherLength; ++i) {
			Function coeff = Constant.ZERO;

			// Check if this polynomial has coefficients of degree i
			if (i < thisLength) {
				coeff = new OperationAdd(coeff, this.coeffs[i]);
			}

			// Check if the other polynomial has coefficients of degree i
			if (i < otherLength) {
				coeff = new OperationAdd(coeff, other.coeffs[i]);
			}

			// Put the sum into the array if it is non-zero
			if (!coeff.equals(Constant.ZERO)) {
				tempCoeffs[i] = coeff;

				// Update the degree for non-zero coefficients
				degree = i;
			}
		}

		// Use the temporary coefficient array if it is the correct length
		if (degree == tempCoeffs.length - 1) {
			return new Polynomial(tempCoeffs, false);
		}

		// Create a new coefficient array if the original is the wrong length
		final Function[] newCoeffs = new Function[degree + 1];
		System.arraycopy(tempCoeffs, 0, newCoeffs, 0, degree + 1);

		return new Polynomial(newCoeffs, true);
	}

	@Override
	public Polynomial sub (final Polynomial other) {
		// Check if either polynomial is zero
		if (this.isZero()) {
			return other.multiply(-1);
		} else if (other.isZero()) {
			return this;
		}

		final int thisLength  = this.coeffs.length;
		final int otherLength = other.coeffs.length;
		int degree = 0;

		// Create array to hold the coefficients temporarily
		final Function[] tempCoeffs = new Function[Math.max(thisLength, otherLength)];

		// Fill the array of coefficients with the sums of the polynomial coefficients
		for (int i = 0; i < thisLength || i < otherLength; ++i) {
			Function coeff = Constant.ZERO;

			// Check if this polynomial has coefficients of degree i
			if (i < thisLength) {
				coeff = new OperationAdd(coeff, this.coeffs[i]);
			}

			// Check if the other polynomial has coefficients of degree i
			if (i < otherLength) {
				coeff = new OperationAdd(coeff, new OperationMul(other.coeffs[i], new Constant(-1)));
			}

			// Put the sum into the array if it is non-zero
			if (!coeff.equals(Constant.ZERO)) {
				tempCoeffs[i] = coeff;

				// Update the degree for non-zero coefficients
				degree = i;
			}
		}

		// Use the temporary coefficient array if it is the correct length
		if (degree == tempCoeffs.length - 1) {
			return new Polynomial(tempCoeffs, false);
		}

		// Create a new coefficient array if the original is the wrong length
		final Function[] newCoeffs = new Function[degree + 1];
		System.arraycopy(tempCoeffs, 0, newCoeffs, 0, degree + 1);

		return new Polynomial(newCoeffs, true);
	}

	/**
	 * Multiply the polynomial by a scalar
	 *
	 * @param scalar the scalar by which to multiply the polynomial
	 *
	 * @return the product of the multiplication of the polynomial and the scalar
	 */
	@Override
	public Polynomial multiply (final double scalar) {
		// Check for zeros
		if (scalar == 0 || isZero()) {
			return ZERO;
		}

		// Create an array to hold the new coefficients
		final Function[] resultCoeffs = new Function[this.coeffs.length];

		// Multiply every term in the polynomial by the scalar
		for (int i = 0; i < this.coeffs.length; ++i) {
			resultCoeffs[i] = new OperationMul(this.coeffs[i], new Constant(scalar));
		}

		return new Polynomial(resultCoeffs, true);
	}



	// Function operations ---------------------------------------------------------------------------------------------


	@Override
	public boolean canEvaluate() {
		return true;
	}

	@Override
	public double evaluate(double x) {
		double ans = 0;

		// Solve by Horner's rule
		for (int i = coeffs.length - 1; i >= 0; --i) {
			ans *= x;
			ans += coeffs[i].evaluate(x);
		}

		return ans;
	}



	// Differentiable function operations ------------------------------------------------------------------------------

	@Override
	public Polynomial differentiate () {
		// The derivative of a constant is zero
		if (this.coeffs.length == 1) {
			return Polynomial.ZERO;
		}

		final Function[] newCoeffs = new Function[this.coeffs.length - 1];

		// Multiply each term by its exponent
		for (int i = 0; i < newCoeffs.length; ++i) {
			newCoeffs[i] = new OperationMul(this.coeffs[i + 1], new Constant(i + 1));
		}

		return new Polynomial(newCoeffs, true);
	}



	// Integrable function operations ----------------------------------------------------------------------------------

	@Override
	public Polynomial antiDifferentiate() {
		// The anti-derivative of zero is a constant
		if (this.isZero()) {
			return Polynomial.ZERO;
		}

		final Function[] newCoeffs = new Function[this.coeffs.length + 1];

		// Divide each term by its exponent
		for (int i = 1; i < newCoeffs.length; ++i) {
			newCoeffs[i] = new OperationDiv(this.coeffs[i - 1], new Constant(i));
		}

		return new Polynomial(newCoeffs, true);
	}

	@Override
	public double integrate(double a, double b) {
		Polynomial antiDerivative = antiDifferentiate();
		return antiDerivative.evaluate(b) - antiDerivative.evaluate(a);
	}



	// Polynomial operations -------------------------------------------------------------------------------------------

	/**
	 * Determine whether two polynomials are equal (have the same degree)
	 *
	 * @param other the polynomial to check with
	 *
	 * @return true if the polynomials are equal
	 *         false if the polynomials are not equal
	 */
	public boolean equals (final Polynomial other) {
		// Check if the other polynomial is null
		if (other == null) {
			return false;
		}

		// Make sure the polynomials are of the same degree
		if (this.degree() != other.degree()) return false;

		// Compare each coefficient
		for (int i = 0; i < coeffs.length; ++i) {
			if (this.coeffs[i] != other.coeffs[i]) return false;
		}

		return true;
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

		// Create an array to hold the new coefficients
		final int degree = this.degree() + other.degree();
		final Function[] resultCoeffs = new Function[degree + 1];

		// Multiply every term in the polynomial by every term in the other polynomial
		for (int i = 0; i < this.coeffs.length; ++i) {
			for (int j = 0; j < other.coeffs.length; ++j) {
				resultCoeffs[i + j] = new OperationAdd(resultCoeffs[i], new OperationMul(this.coeffs[i], other.coeffs[j]));
			}
		}

		return new Polynomial(resultCoeffs, true);
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
	 * Perform polynomial division (long division)
	 *
	 * @param divisor the polynomial to divideBy by
	 *
	 * @return the quotient of the polynomial division
	 */
	public Polynomial divideBy(Polynomial divisor) {
		// Check if we are dividing by zero
		if (divisor.isZero()) {
			throw new DivisionByZeroException();
		}

		// Check if the quotient is zero
		final int degree = this.degree() - divisor.degree();
		if (this.isZero() || degree < 0) {
			return Polynomial.ZERO;
		}

		Function[] resultCoeffs   = new Function[degree + 1];
		Function[] dividendCoeffs = new Function[this.coeffs.length];
		System.arraycopy(this.coeffs, 0, dividendCoeffs, 0, dividendCoeffs.length);

		int dividendIndex = dividendCoeffs.length - 1;
		int resultIndex = resultCoeffs.length - 1;

		// Divide each term
		while (resultIndex >= 0) {
			final Function factor = new OperationDiv(dividendCoeffs[dividendIndex], divisor.coeffs[divisor.coeffs.length - 1]);
			resultCoeffs[resultIndex] = factor;

			// Subtract a multiple of the divisor from the dividend
			for (int divisorIndex = divisor.coeffs.length - 1; divisorIndex >= 0; divisorIndex--, dividendIndex--) {
				dividendCoeffs[dividendIndex] = new OperationAdd(dividendCoeffs[dividendIndex], new OperationMul(factor, divisor.coeffs[divisorIndex]));
			}

			dividendIndex += divisor.coeffs.length - 1;
			--resultIndex;
		}

		return new Polynomial(resultCoeffs, true);
	}
}
