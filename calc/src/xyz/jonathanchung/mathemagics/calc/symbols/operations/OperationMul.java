package xyz.jonathanchung.mathemagics.calc.symbols.operations;

import xyz.jonathanchung.mathemagics.calc.Function;

/**
 * This class defines a product
 */
public class OperationMul extends Operation {
	public OperationMul(final Function... params) {
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
