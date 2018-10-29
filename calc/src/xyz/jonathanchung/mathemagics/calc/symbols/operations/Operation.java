package xyz.jonathanchung.mathemagics.calc.symbols.operations;

import xyz.jonathanchung.mathemagics.calc.Function;
import xyz.jonathanchung.mathemagics.calc.Multipliable;
import xyz.jonathanchung.mathemagics.calc.symbols.Constant;
import xyz.jonathanchung.mathemagics.linalg.LinearObject;

public abstract class Operation<T extends Operation> implements
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

	protected Operation (final Function[]... params) {
		int numParams = 0;
		for (Function[] paramList : params) {
			numParams += paramList.length;
		}

		this.params = new Function[numParams];
		numParams = 0;

		for (Function[] paramList : params) {
			for (Function param : paramList) {
				this.params[numParams] = param;
				++numParams;
			}
		}
	}



	// Linear object operations ----------------------------------------------------------------------------------------

	@Override
	public Operation add (Operation other) {
		return new OperationAdd(this, other);
	}

	@Override
	public Operation sub (Operation other) {
		return new OperationAdd(this, other.multiply(-1));
	}

	@Override
	public Operation multiply (double scalar) {
		return new OperationMul(this, new Constant(scalar));
	}



	// Multipliable object operations ----------------------------------------------------------------------------------

	@Override
	public Operation multiply(Operation other) {
		return new OperationDiv(this, other);
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

}
