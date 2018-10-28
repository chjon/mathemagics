package xyz.jonathanchung.mathemagics.calc.symbols;

import xyz.jonathanchung.mathemagics.calc.Function;

public class Variable implements Function {

	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * The name of the variable
	 */
	private String name;

	/**
	 * The value of the variable - defaults to NaN
	 */
	private double value;



	// Constructors ----------------------------------------------------------------------------------------------------

	/**
	 * Create a new variable
	 *
	 * @param name the name of the variable
	 * @param value the value of the variable for evaluation
	 */
	public Variable (String name, double value) {
		// Make sure the variable has a name
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException("");
		}

		this.name  = name;
		this.value = value;
	}

	/**
	 * Create a new variable with a value of NaN
	 *
	 * @param name the name of the variable
	 */
	public Variable (String name) {
		this(name, Double.NaN);
	}



	// Accessors -------------------------------------------------------------------------------------------------------

	public final String getName () {
		return name;
	}

	public double getValue () {
		return value;
	}

	/**
	 * Determine whether the variable has a value
	 *
	 * @return whether the variable has a value
	 */
	@Override
	public boolean canEvaluate () {
		return !Double.isNaN(value);
	}



	// Mutators --------------------------------------------------------------------------------------------------------

	public void setName (String name) {
		this.name = name;
	}

	public void setValue(double value) {
		this.value = value;
	}



	// Function methods ------------------------------------------------------------------------------------------------

	@Override
	public double evaluate(double x) {
		if (!canEvaluate()) {
			throw new UndefinedVariableException(this);
		}

		return getValue();
	}
}
