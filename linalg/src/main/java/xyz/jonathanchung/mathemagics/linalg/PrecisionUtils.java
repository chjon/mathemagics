package xyz.jonathanchung.mathemagics.linalg;

public class PrecisionUtils {
	public static double absError (double d1, double d2) {
		return Math.abs(d1 - d2);
	}

	public static double relError (double d1, double d2) {
		return Math.abs((d1 - d2) / d2);
	}

	public static boolean equals (double d1, double d2, double acceptableError) {
		if (d1 == d2) return true;
		return	relError(d1, d2) <= acceptableError ||
				relError(d2, d1) <= acceptableError;
	}
}
