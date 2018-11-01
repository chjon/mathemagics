package xyz.jonathanchung.mathemagics.calc.symbols;

import xyz.jonathanchung.mathemagics.calc.Function;
import xyz.jonathanchung.mathemagics.calc.Multipliable;
import xyz.jonathanchung.mathemagics.linalg.LinearObject;

public final class Constant implements
		LinearObject<Constant>,
		Multipliable<Constant>,
		Function {

	// Constants -------------------------------------------------------------------------------------------------------

	/**
	 * The zero constant
	 */
	public static final Constant ZERO = new Constant(0);

	/**
	 * The one constant
	 */
	public static final Constant ONE = new Constant(1);



	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * The numerical value of the constant
	 */
	private final double value;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Constructor for a constant
	 *
	 * @param value the numerical value of the constant
	 */
	public Constant (double value) {
		this.value = value;
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	public double getValue () {
		return value;
	}

	public boolean isZero () {
		return this.value == 0;
	}



	// Function operations ---------------------------------------------------------------------------------------------

	@Override
	public boolean canEvaluate() {
		return true;
	}

	@Override
	public double evaluate (double x) {
		return value;
	}



	// Linear object operations ----------------------------------------------------------------------------------------

	@Override
	public Constant add (Constant other) {
		return new Constant(this.value + other.value);
	}

	@Override
	public Constant sub (Constant other) {
		return new Constant(this.value - other.value);
	}

	@Override
	public Constant multiply (double scalar) {
		return new Constant(this.value * scalar);
	}



	// Multipliable object operations ----------------------------------------------------------------------------------

	@Override
	public Constant multiply (Constant other) {
		return new Constant(this.value * other.value);
	}



	// Constant operations ---------------------------------------------------------------------------------------------

	public Constant add (double other) {
		return new Constant(this.value + other);
	}

	public Constant sub (double other) {
		return new Constant(this.value - other);
	}
}
