package xyz.jonathanchung.mathemagics.calc.symbols.operations;

import xyz.jonathanchung.mathemagics.calc.Function;

/**
 * This class defines a sum
 */
public class OperationAdd extends Operation {

	// Constructors ----------------------------------------------------------------------------------------------------

	public OperationAdd(final Function... params) {
		super(params);
	}

	/**
	 * Combine addition objects into a single addition object
	 *
	 * @param toAdd the addition objects to combine
	 *
	 * @return an addition object with all the additions combined
	 */
	private static OperationAdd combineParams (final OperationAdd... toAdd) {
		int numParams = 0;
		for (OperationAdd opAdd : toAdd) {
			numParams += opAdd.params.length;
		}

		Function[] params = new Function[numParams];
		numParams = 0;

		for (OperationAdd opAdd : toAdd) {
			for (Function param : opAdd.params) {
				params[numParams] = param;
				++numParams;
			}
		}

		return new OperationAdd(params);
	}



	// Function operations ---------------------------------------------------------------------------------------------

	@Override
	public double evaluate(double x) {
		double sum = 0;

		for (Function param : params) {
			sum += param.evaluate(x);
		}

		return sum;
	}



	// Linear object operations ----------------------------------------------------------------------------------------

	@Override
	public Operation add (Operation other) {
		if (other instanceof OperationAdd) {
			return combineParams(this, (OperationAdd) other);
		} else {
			return super.add(other);
		}
	}
}
