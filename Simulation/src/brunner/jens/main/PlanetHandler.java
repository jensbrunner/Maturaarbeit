package brunner.jens.main;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class PlanetHandler 
{
	public static CopyOnWriteArrayList<Planet> planets = new CopyOnWriteArrayList<Planet>(); 

	public static void createRandomPlanets(int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			planets.add(new Planet());
		}
	}

	public static void updatePlanets(long frameTime)
	{
		//We use a counting iterator because it's easier to solve the problem where we calculate the force per pair twice instead of just once necessary. 
		for(int i = 0; i < planets.size(); i++)
		{
			Planet planet = planets.get(i);

			//If this planet is marked for deletion, don't do anything with it.
			if(planet.delete) continue;

			computeForce(planet, i);

			//Handle the planet's collision with other planets. Some will be marked with the delete boolean
			//handleCollision(planet);

			//Compute the acceleration (directly correlated to the force), independant of time! a=F/m
			Vector2 accel = new Vector2((planet.force.x/planet.mass), (planet.force.y/planet.mass));

			//However dv=a*dt
			planet.vel = Vector2Math.add(planet.vel, Vector2Math.mult(accel, frameTime/1000.0f));

			//Now we have to reset the force to 0 after computing the accel and adding
			//it to the velocity. Otherwise we would be constantly increasing the amount
			//of force per execution.
			planet.force = Constants.ZERO_VECTOR;
		}

		//Finally, we update the positions. Note that even here, we have to account for the time that is passing as s=v*t.
		for(Planet planet : planets) {
			Vector2 dDistVec= new Vector2((frameTime/1000.0f)*(planet.vel.x), (frameTime/1000.0f)*(planet.vel.y));
			planet.position = Vector2Math.add(planet.position, Vector2Math.mult(dDistVec, 1));
		}

		ArrayList<Planet> toRemove = new ArrayList<Planet>();
		for(Planet planet : planets) {
			if(planet.delete) toRemove.add(planet);
		}
		planets.removeAll(toRemove);
	}

	private static void computeForce(Planet planet, int i)
	{
		for(int j = i+1; j < planets.size(); j++)
		{
			Planet otherPlanet = planets.get(j);

			//Also here, if we are deleting the planet, don't interact with it.
			if((planet.position.x != otherPlanet.position.x && planet.position.y != otherPlanet.position.y) && !otherPlanet.delete)
			{
				//First we get the x,y and magnitudal distance between the two bodies.
				Vector2 distanceVector = Vector2Math.subtract(otherPlanet.position, planet.position);
				float dist = Vector2Math.magnitude(distanceVector);


				//This is a virtual limit for how close the bodies can get. It doesn't actually limit the proximity to another body on screen.
				if(dist < 3f) dist = 3f;

				//Now we compute first the total and then the component forces
				//Depending on choice, use r or r^2 - this will be TODO later
				Vector2 forceVector = Vector2Math.mult(distanceVector, Constants.GRAVITATIONAL_CONSTANT*planet.mass*otherPlanet.mass*(1/(dist*dist*dist)));

				planet.force = Vector2Math.add(planet.force, forceVector);
				otherPlanet.force = Vector2Math.subtract(otherPlanet.force, forceVector);
			}
		}
	}

	private static void handleCollision(Planet planet)
	{
		for(Planet otherPlanet : planets)
		{
			//We need to make sure we don't collide with oneself and don't collide with planets marked for delete (already collided with)
			float diameter = (float) (2.0f*Math.sqrt(otherPlanet.mass));
			//if(otherPlanet != planet && !otherPlanet.delete && Vector2Math.distance(planet.position, otherPlanet.position) < diameter/2) 
			if(otherPlanet != planet && !otherPlanet.delete && Vector2Math.distance(planet.position, otherPlanet.position) < 2f) 
			{
				//Collision detected. Now we compute post-collision forces. We want to absorb the smaller into the heavier planet, otherwise it looks really wonky.
				if(planet.mass == otherPlanet.mass)
				{
					otherPlanet.delete = true;
					planet.mass = planet.mass + otherPlanet.mass;
				}
				else if(planet.mass > otherPlanet.mass)
				{
					otherPlanet.delete = true;
					planet.mass = planet.mass + otherPlanet.mass;
				}
				else if(planet.mass < otherPlanet.mass)
				{
					planet.delete = true;
					otherPlanet.mass = planet.mass + otherPlanet.mass;
				}

				//The collision velocity is computed by treating this as a perfectly elastic instantaneous change in impulse where all energy is conserved. First in the x-direction, then the y.
				Vector2 collVel = new Vector2((planet.mass*planet.vel.x+otherPlanet.mass*otherPlanet.vel.x)/(planet.mass+otherPlanet.mass), (planet.mass*planet.vel.y+otherPlanet.mass*otherPlanet.vel.y)/(planet.mass+otherPlanet.mass));
				planet.vel = collVel;
			}
		}
	}

	public static void makeOrbitAround(Planet center, Planet orbiter)
	{
		Vector2 distanceVector = Vector2Math.subtract(orbiter.position, center.position);
		float dist = Vector2Math.magnitude(distanceVector);
		
		//Use Fz(centripetal force) = Fg(gravitational force) and solve for the velocity
		float velocityMag = (float) Math.sqrt(Constants.GRAVITATIONAL_CONSTANT * center.mass * (1/dist));


		float xDist = orbiter.position.x - center.position.x;
		float yDist = orbiter.position.y - center.position.y;

		//To create a perpendicular vector, switch components and one sign (+ -> - or - -> +).
		Vector2 constructVector = Vector2Math.mult(new Vector2(-yDist, xDist), 1/dist);
		Vector2 newVelocityVector = Vector2Math.mult(constructVector, velocityMag);

		orbiter.vel = newVelocityVector;
	}
}
