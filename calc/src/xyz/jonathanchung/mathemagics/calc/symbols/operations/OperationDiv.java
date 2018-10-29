package xyz.jonathanchung.mathemagics.calc.symbols.operations;

import xyz.jonathanchung.mathemagics.calc.Function;

/**
 * This class defines a quotient
 */
public class OperationDiv extends Operation {
	public OperationDiv(final Function param1, final Function param2) {
		super(param1, param2);
	}

	@Override
	public double evaluate(double x) {
		return params[0].evaluate(x) / params[1].evaluate(x);
	}
}
