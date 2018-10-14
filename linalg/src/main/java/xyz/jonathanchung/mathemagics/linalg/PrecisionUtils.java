package xyz.jonathanchung.mathemagics.linalg;

public class PrecisionUtils {
	public static double absError (double d1, double d2) {
		return Math.abs(d1 - d2);
	}

	public static double relError (double d1, double d2) {
		return Math.abs((d1 - d2) / d2);
	}

	public static boolean equalsRel (double d1, double d2, double epsilon) {
		if (d1 == d2) return true;
		return	relError(d1, d2) <= epsilon ||
				relError(d2, d1) <= epsilon;
	}

	public static boolean equalsAbs (double d1, double d2, double epsilon) {
		if (d1 == d2) return true;
		return	absError(d1, d2) <= epsilon;
	}
}
