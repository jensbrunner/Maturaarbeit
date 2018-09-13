package brunner.jens.utils;

import java.util.ArrayList;

import brunner.jens.main.Body;
import brunner.jens.main.BodyHandler;
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

	public static void makeOrbitAround(Body center, Body orbiter)
	{
		if(center != orbiter) {
			Vector2 distanceVector = Vector2Math.subtract(orbiter.position, center.position);
			double dist = Vector2Math.magnitude(distanceVector);

			//Use Fz(centripetal force) = Fg(gravitational force) and solve for the velocity
			double velocityMag = Math.sqrt(Constants.GRAVITATIONAL_CONSTANT * center.mass * (1/dist));


			double xDist = orbiter.position.x - center.position.x;
			double yDist = orbiter.position.y - center.position.y;

			//To create a perpendicular vector, switch components and one sign (+ -> - or - -> +).
			Vector2 constructVector = Vector2Math.mult(new Vector2(-yDist, xDist), 1/dist);
			Vector2 newVelocityVector = Vector2Math.mult(constructVector, velocityMag);

			orbiter.vel = newVelocityVector;
		}
	}

	public static void createGalaxy(Vector2 center, int amount, double radius)
	{
		ArrayList<Body> created = BodyHandler.createRandomBodies(amount, center, radius);
		Body galaxyCenter = new Body(center, Constants.ZERO_VECTOR, amount*10);
		for(Body b : created) {
			Toolbox.makeOrbitAround(galaxyCenter, b);
		}
		BodyHandler.planets.add(galaxyCenter);
	}

	//In order to compute the approximate total energy of the system, we will sum all the kinetic and gravitational potential energies.
	public static double getTotalEnergy()
	{
		if(Main.curBodyAmount > 1) {
			double total = 0f;
			for(int i = 0; i < BodyHandler.planets.size(); i++)
			{
				Body p = BodyHandler.planets.get(i);

				//Ekin = 0.5*m*v^2, man beachte im Code dass vx^2+vy^2 = v^2
				total += 0.5 * p.mass * (p.vel.x*p.vel.x+p.vel.y*p.vel.y);

				for(int j = i+1; i < BodyHandler.planets.size(); i++) {

					Body p2 = BodyHandler.planets.get(j);
					double dist = Vector2Math.distance(p.position, p2.position);
					if(dist != 0) total -= (Constants.GRAVITATIONAL_CONSTANT * p.mass * p2.mass)/dist;
				}

			}
			return total;
		}
		return 0.0;
	}
}
