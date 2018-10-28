package xyz.jonathanchung.mathemagics.calc.symbols;

import xyz.jonathanchung.mathemagics.calc.Function;
import xyz.jonathanchung.mathemagics.calc.Multipliable;
import xyz.jonathanchung.mathemagics.linalg.LinearObject;

public abstract class Operation implements
		LinearObject<Operation>,
		Function,
		Multipliable<Operation> {

	// Fields ----------------------------------------------------------------------------------------------------------

	/**
	 * The function's parameters
	 */
	protected final Function[] params;



	// Constructors ----------------------------------------------------------------------------------------------------

	public Operation (final Function... params) {
		this.params = params;
	}



	// Linear object operations ----------------------------------------------------------------------------------------

	@Override
	public Operation add(Operation other) {
		return new Sum(this, other);
	}

	@Override
	public Operation sub(Operation other) {
		return new Difference(this, other);
	}

	@Override
	public Operation multiply(double scalar) {
		return new Product(this, new Constant(scalar));
	}



	// Multipliable object operations ----------------------------------------------------------------------------------

	@Override
	public Operation multiply(Operation other) {
		return new Quotient(this, other);
	}



	// Function properties ---------------------------------------------------------------------------------------------

	@Override
	public final boolean canEvaluate() {
		for (Function param : params) {
			if (!param.canEvaluate()) {
				return false;
			}
		}

		return true;
	}



	// Operation implementations ---------------------------------------------------------------------------------------

	/**
	 * This class defines a sum
	 */
	public static class Sum extends Operation {
		public Sum (final Function... params) {
			super(params);
		}

		@Override
		public double evaluate(double x) {
			double sum = 0;

			for (Function param : params) {
				sum += param.evaluate(x);
			}

			return sum;
		}
	}

	/**
	 * This class defines a difference
	 */
	public static class Difference extends Operation {
		public Difference (final Function param1, final Function param2) {
			super(param1, param2);
		}

		@Override
		public double evaluate(double x) {
			return params[0].evaluate(x) - params[1].evaluate(x);
		}
	}

	/**
	 * This class defines a product
	 */
	public static class Product extends Operation {
		public Product (final Function... params) {
			super(params);
		}

		@Override
		public double evaluate(double x) {
			double product = 1;

			for (Function param : params) {
				product *= param.evaluate(x);
			}

			return product;
		}
	}

	/**
	 * This class defines a quotient
	 */
	public static class Quotient extends Operation {
		public Quotient (final Function param1, final Function param2) {
			super(param1, param2);
		}

		@Override
		public double evaluate(double x) {
			return params[0].evaluate(x) / params[1].evaluate(x);
		}
	}
}
