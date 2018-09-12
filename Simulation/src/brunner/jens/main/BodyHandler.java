package brunner.jens.main;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class BodyHandler 
{
	public static CopyOnWriteArrayList<Body> planets = new CopyOnWriteArrayList<Body>(); 

	public static ArrayList<Body> createRandomBodies(int amount, Vector2 center, double radius)
	{
		ArrayList<Body> newbodies = new ArrayList<Body>();
		for(int i = 0; i < amount; i++)
		{
			Body b = new Body(center, radius);
			planets.add(b);
			newbodies.add(b);
		}
		return newbodies;
	}

	public static void updateBodies(long frameTime)
	{
		if(Main.barneshut) BarnesHut.insertBodies();

		//We use a counting iterator because it's easier to solve the problem where we calculate the force per pair twice instead of just once necessary. 
		for(int i = 0; i < planets.size(); i++)
		{
			Body planet = planets.get(i);

			//If this planet is marked for deletion, don't do anything with it. || Don't affect fixed planets.
			if(planet.delete || planet.fixed) continue;

			if(Main.barneshut)
			{
				BarnesHut.doPhysics(planet, BarnesHut.root);
			}else
			{
				computeForce(planet, i);
			}

			//Handle the planet's collision with other planets. Some will be marked with the delete boolean
			if(Main.collisions) handleCollision(planet);

			//Compute the acceleration a=F/m
			Vector2 accel = new Vector2((planet.force.x/planet.mass), (planet.force.y/planet.mass));

			Vector2 finalv = Vector2Math.mult(accel, frameTime * Main.timeScale);

			//Since we want to model reality, use the AVERAGE velocity during the frame, that is the area of a triangle in a v-t graph.
			planet.lastv = Vector2Math.mult(finalv, 0.5);
			planet.vel = Vector2Math.add(planet.vel, planet.lastv);

			//net force PER FRAME, so reset it.
			planet.force = Constants.ZERO_VECTOR;
		}

		//Finally, we update the positions. Note that even here, we have to account for the time that is passing as s=v*t. This has to be done after all of the force calculations have occured.
		//Also: the BarnesHut operation must be done on a static tree with non-moving particles. Thus handling the actual movement has to be separate.
		for(Body planet : planets)
		{
			Vector2 moveV = Vector2Math.add(planet.vel, planet.lastv);
			planet.position = Vector2Math.add(planet.position, Vector2Math.mult(moveV, Main.timeScale*frameTime)); //Ommitting the division of frametime by 1000 is a choice of design, accelerating the speed by 1k.

			//Add the last half the finalv to finish the frame with the correct end-velocity.
			planet.vel = Vector2Math.add(planet.vel, planet.lastv);

			//If we don't handle the bounds/adjust the positions after having calculated the positions from the velocity, they'll all be frametime * velocity beyond the border, which looks terribly glitchy.
			if(Main.bounded) handleBounds(planet);
		}

		ArrayList<Body> toRemove = new ArrayList<Body>();
		for(Body planet : planets)
		{
			if(planet.delete) toRemove.add(planet);
		}
		planets.removeAll(toRemove);
	}

	private static void computeForce(Body planet, int i)
	{
		for(int j = i+1; j < planets.size(); j++)
		{
			Body otherPlanet = planets.get(j);

			//Also here, if we are deleting the planet, don't interact with it.
			if((planet.position.x != otherPlanet.position.x && planet.position.y != otherPlanet.position.y) && !otherPlanet.delete)
			{
				
				//First we get the x,y and magnitudal distance between the two bodies.
				Vector2 distVec = Vector2Math.subtract(otherPlanet.position, planet.position);
				double dist = Vector2Math.magnitude(distVec);

				//Depending on choice, use r or r^2 - this will be TODO later
				//Vector2 forceVector = Vector2Math.mult(distVec, Constants.GRAVITATIONAL_CONSTANT*planet.mass*otherPlanet.mass*(1/(dist*dist*dist)));
				Vector2 forceVector = Vector2Math.mult(Vector2Math.mult(distVec, 1/dist), Constants.GRAVITATIONAL_CONSTANT*planet.mass*otherPlanet.mass*(1/(dist*dist)));

				if(dist < Main.smoothingParam) {
					forceVector = Constants.ZERO_VECTOR;
				}

				planet.force = Vector2Math.add(planet.force, forceVector);
				otherPlanet.force = Vector2Math.subtract(otherPlanet.force, forceVector);
			}
		}
	}

	public static void computeForceBarnes(Body planet, Body otherPlanet)
	{
		if((planet.position.x != otherPlanet.position.x && planet.position.y != otherPlanet.position.y) && !otherPlanet.delete)
		{
			Vector2 distVec = Vector2Math.subtract(otherPlanet.position, planet.position);
			double dist = Vector2Math.magnitude(distVec);
			
			Vector2 forceVector = Vector2Math.mult(Vector2Math.mult(distVec, 1/dist), Constants.GRAVITATIONAL_CONSTANT*planet.mass*otherPlanet.mass*(1/(dist*dist)));

			if(dist < Main.smoothingParam) {
				forceVector = Constants.ZERO_VECTOR;
			}

			planet.force = Vector2Math.add(planet.force, forceVector);
		}
	}

	private static void handleCollision(Body planet)
	{
		for(Body otherPlanet : planets)
		{
			if(otherPlanet != planet && !otherPlanet.delete && Vector2Math.distance(planet.position, otherPlanet.position) < 2)
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

	private static void handleBounds(Body p) {
		if(Main.bounded) {
			if(p.position.x < Main.boundVec.x)
			{
				p.position.x = Main.boundVec.x;
				p.vel.x *= -0.1;
			}
			if(p.position.x > Main.boundVec2.x)
			{
				p.position.x = Main.boundVec2.x;
				p.vel.x *= -0.1;
			}
			if(p.position.y < Main.boundVec.y)
			{
				p.position.y = Main.boundVec.y;
				p.vel.y *= -0.1;
			}
			if(p.position.y > Main.boundVec2.y)
			{
				p.position.y = Main.boundVec2.y;
				p.vel.y *= -0.1;
			}
			else {
				return;
			}
		}
	}

	public static void makeOrbitAround(Body center, Body orbiter)
	{
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

	public static void createGalaxy(Vector2 center, int amount, double radius)
	{
		ArrayList<Body> created = BodyHandler.createRandomBodies(amount, center, radius);
		Body galaxyCenter = new Body(center, Constants.ZERO_VECTOR, amount*10);
		for(Body b : created) {
			BodyHandler.makeOrbitAround(galaxyCenter, b);
		}
		BodyHandler.planets.add(galaxyCenter);
	}

	public static double getFrameTotalEnergy()
	{
		double total = 0f;
		for(Body p : BodyHandler.planets)
		{
			total += 0.5 * p.mass * (p.vel.x*p.vel.x+p.vel.y*p.vel.y);
			double dist = Vector2Math.distance(p.position, Main.centerBlackHole.position);
			if(dist == 0f) dist = 1f;
			total += (Constants.GRAVITATIONAL_CONSTANT * p.mass * Main.centerBlackHole.mass)/dist;
		}
		return total;
	}
}
