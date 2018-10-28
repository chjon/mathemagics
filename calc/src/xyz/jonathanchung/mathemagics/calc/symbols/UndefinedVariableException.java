package xyz.jonathanchung.mathemagics.calc.symbols;

public class UndefinedVariableException extends RuntimeException {
	public UndefinedVariableException(Variable var) {
		super(var.getName());
	}
}
