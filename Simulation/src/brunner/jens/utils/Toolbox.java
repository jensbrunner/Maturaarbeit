package brunner.jens.utils;

public class Toolbox {

	public static double approxRound(double d, int digits) {
		return Math.floor(Math.pow(10, digits) * (double)d + 0.5) / Math.pow(10, digits);
	}
	
}
