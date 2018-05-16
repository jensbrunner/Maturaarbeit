package brunner.jens.main;

import java.util.ArrayList;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class PlanetHandler 
{
	public static ArrayList<Planet> planets = new ArrayList<Planet>(); 
	
	public static void createRandomPlanets(int amount)
	{
		for(int i = 0; i <= amount; i++)
		{
			planets.add(new Planet());
		}
	}
	
	public static void updatePlanets()
	{
		for(Planet planet : planets)
		{
			computeAndSetPullForce(planet);
			
			//This will be running about every 1/62.5th of a second, adjust accel appropriately
			Vector2 accel = new Vector2((1f/62.5f)*(planet.force.x/planet.mass), (1f/62.5f)*(planet.force.y/planet.mass));
			planet.vel = Vector2Math.add(planet.vel, accel);
			
			//Now we have to reset the force to 0 after computing the accel and adding
			//it to the velocity. Otherwise we would be constantly increasing the amount
			//of force per execution.
			planet.force = Constants.ZERO_VECTOR;
			
			//Finally, we update the positions. Note that even here, we have to account for
			//the time that is passing as s=v*t
			Vector2 dDistVec= new Vector2((1f/62.5f)*(planet.vel.x), (1f/62.5f)*(planet.vel.y));
			planet.position = Vector2Math.add(planet.position, dDistVec);
		}
	}
	
	public static void computeAndSetPullForce(Planet planet)
	{
		for(Planet otherPlanet : planets)
		{
			if(otherPlanet != planet)
			{
				//First we get the x,y and magnitudal distance between the two bodies.
				int xDist = (int) (otherPlanet.position.x - planet.position.x);
				int yDist = (int) (otherPlanet.position.y - planet.position.y);
				float dist = Vector2Math.distance(planet.position, otherPlanet.position);
				
				//Now we compute first the total and then the component forces
				float force = Constants.GRAVITATIONAL_CONSTANT * ((planet.mass*otherPlanet.mass)/(dist));
				float forceX = force * xDist/dist;
				float forceY = force * yDist/dist;
				
				//Given the component forces, we construct the force vector and apply it to the body.
				Vector2 forceVec = new Vector2(forceX, forceY);
				planet.force = Vector2Math.add(planet.force, forceVec);
			}
		}
	}
	
}
