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
	
	public static void updatePlanets(long frameTime)
	{
		for(Planet planet : planets)
		{
			//If this planet is marked for deletion, don't do anything with it.
			if(planet.delete) continue;
			
			computeAndSetPullForce(planet);
			
			//Handle the planet's collision with other planets. Some will be marked with the delete boolean
			handleCollision(planet);
			
			//System.out.println(frameTime/1000.0f);
			
			//Compute the acceleration (directly correlated to the force), independant of time! a=F/m
			Vector2 accel = new Vector2((planet.force.x/planet.mass), (planet.force.y/planet.mass));
			
			//However dv=a*dt
			planet.vel = Vector2Math.add(planet.vel, Vector2Math.mult(accel, frameTime/1000.0f));
			
			//Now we have to reset the force to 0 after computing the accel and adding
			//it to the velocity. Otherwise we would be constantly increasing the amount
			//of force per execution.
			planet.force = Constants.ZERO_VECTOR;
			
			//Finally, we update the positions. Note that even here, we have to account for
			//the time that is passing as s=v*t
			Vector2 dDistVec= new Vector2((frameTime/1000.0f)*(planet.vel.x), (frameTime/1000.0f)*(planet.vel.y));
			planet.position = Vector2Math.add(planet.position, Vector2Math.mult(dDistVec, 1));
		}
		ArrayList<Planet> toRemove = new ArrayList<Planet>();
		for(Planet planet : planets) {
			if(planet.delete) toRemove.add(planet);
		}
		planets.removeAll(toRemove);
	}
	
	private static void computeAndSetPullForce(Planet planet)
	{
		for(Planet otherPlanet : planets)
		{
			//Also here, if we are deleting the planet, don't interact with it.
			if(otherPlanet != planet && !otherPlanet.delete)
			{
				//First we get the x,y and magnitudal distance between the two bodies.
				int xDist = (int) (otherPlanet.position.x - planet.position.x);
				int yDist = (int) (otherPlanet.position.y - planet.position.y);
				float dist = Vector2Math.distance(planet.position, otherPlanet.position);
				
				//Now we compute first the total and then the component forces
				//Depending on choice, use r or r^2
				float force = Constants.GRAVITATIONAL_CONSTANT * ((planet.mass*otherPlanet.mass)/(dist*dist)); 
				float forceX = force * xDist/dist;
				float forceY = force * yDist/dist;
				
				//Given the component forces, we construct the force vector and apply it to the body.
				Vector2 forceVec = new Vector2(forceX, forceY);
				planet.force = Vector2Math.add(planet.force, forceVec);
			}
		}
	}
	
	private static void handleCollision(Planet planet)
	{
		for(Planet otherPlanet : planets)
		{
			//We need to make sure we don't collide with oneself and don't collide with planets marked for delete (already collided with)
			float diameter = (float) (2.0f*Math.sqrt(otherPlanet.mass));
			if(otherPlanet != planet && !otherPlanet.delete && Vector2Math.distance(planet.position, otherPlanet.position) < diameter/2) 
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
	
}
