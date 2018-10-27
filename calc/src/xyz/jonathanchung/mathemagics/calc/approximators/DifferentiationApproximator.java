package xyz.jonathanchung.mathemagics.calc.approximators;

import xyz.jonathanchung.mathemagics.calc.Function;

public class DifferentiationApproximator {
	/**
	 * Approximate the first derivative with a centred divided difference - converges in O(h^2)
	 *
	 * @param f the function for which to approximate the derivative
	 * @param x the value at which to approximate the derivative
	 * @param h the size of the forward and backward changes in x
	 *
	 * @return an approximation of the derivative of f(x) using the centred divided difference method
	 */
	public static double dxCentredDividedDifference (Function f, double x, double h) {
		return (f.evaluate(x + h) - f.evaluate(x - h)) / (2 * h);
	}

	/**
	 * Approximate the first derivative with a backward divided difference - converges in O(h)
	 *
	 * @param f the function for which to approximate the derivative
	 * @param x the value at which to approximate the derivative
	 * @param h the size of the change in x
	 *
	 * @return an approximation of the derivative of f(x) using the backward divided difference method (1 step)
	 */
	public static double dxBackwardDividedDifference1Step (Function f, double x, double h) {
		return (f.evaluate(x) - f.evaluate(x - h)) / h;
	}

	/**
	 * Approximate the first derivative with a backward divided difference - converges in O(h)
	 *
	 * @param f the function for which to approximate the derivative
	 * @param x the value at which to approximate the derivative
	 * @param h the size of the change in x
	 *
	 * @return an approximation of the derivative of f(x) using the backward divided difference method (2 steps)
	 */
	public static double dxBackwardDividedDifference2Step (Function f, double x, double h) {
		return (3 * f.evaluate(x) - 4 * f.evaluate(x - h) + f.evaluate(x - 2 * h)) / h;
	}

	/**
	 * Approximate the first derivative with a backward divided difference - converges in O(h^2)
	 *
	 * @param f the function for which to approximate the derivative
	 * @param x the value at which to approximate the derivative
	 * @param h the size of the change in x
	 *
	 * @return an approximation of the derivative of f(x) using the backward divided difference method (3 steps)
	 */
	public static double dxBackwardDividedDifference3Step (Function f, double x, double h) {
		return (5 * f.evaluate(x - h) - 8 * f.evaluate(x - 2 * h) + 3 * f.evaluate(x - 3 * h)) / (2 * h);
	}

	/**
	 * Approximate the second derivative with a centred divided difference - converges in O(h^2)
	 *
	 * @param f the function for which to approximate the derivative
	 * @param x the value at which to approximate the derivative
	 * @param h the size of the forward and backward changes in x
	 *
	 * @return an approximation of the derivative of f(x) using the centred divided difference method
	 */
	public static double d2xCentredDividedDifference (Function f, double x, double h) {
		return (f.evaluate(x + h) - 2 * f.evaluate(x) + f.evaluate(x - h)) / (h * h);
	}

	/**
	 * Approximate the first derivative with a backward divided difference - converges in O(h)
	 *
	 * @param f the function for which to approximate the derivative
	 * @param x the value at which to approximate the derivative
	 * @param h the size of the change in x
	 *
	 * @return an approximation of the derivative of f(x) using the backward divided difference method (2 steps)
	 */
	public static double d2xBackwardDividedDifference2Step (Function f, double x, double h) {
		return (f.evaluate(x) - 2 * f.evaluate(x - h) + 2 * f.evaluate(x - 2 * h)) / (h * h);
	}
}
