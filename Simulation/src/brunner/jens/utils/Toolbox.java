package brunner.jens.utils;

import brunner.jens.main.Main;

public class Toolbox {

	public static double approxRound(double d, int digits) {
		return Math.floor(Math.pow(10, digits) * (double)d + 0.5) / Math.pow(10, digits);
	}

	//Since there's a zoom function, we need to translate between simulation- and renderspace. Inverse to translate back from render to simulationspace
	public static Vector2 translateToScreen(Vector2 vec, boolean inverse) {
		//To move from (0,0) to the screen center, such that we can then scale.
		Vector2 diff = new Vector2(Constants.WINDOW_DIMENSION.width/2, Constants.WINDOW_DIMENSION.height/2);
		
		//Scale according to zoom level after subtracting the Vector from (0,0) to the screen center.
		Vector2 scaled = Vector2Math.mult(Vector2Math.subtract(vec,diff), inverse == false ? Main.scaleFactor : 1/Main.scaleFactor);
			
		//Move back to screen center
		Vector2 drawPos = Vector2Math.add(scaled, diff);
		return drawPos;
	}
}
