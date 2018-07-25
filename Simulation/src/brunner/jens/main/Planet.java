package brunner.jens.main;

import java.util.concurrent.ThreadLocalRandom;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;

public class Planet 
{
	public Vector2 position;
	public double mass;
	public Vector2 vel, force;
	public boolean fixed = false, delete = false;
	
	public Planet(double x, double y, double xVel, double yVel, double mass) 
	{
		this.position = new Vector2(x,y);
		this.mass = mass;
		vel = new Vector2(xVel, yVel);
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
	
	public Planet() //We also want a constructor to make random planets.
	{
		this.position = new Vector2(ThreadLocalRandom.current().nextDouble(-3000+1920/2, 3000),
									ThreadLocalRandom.current().nextDouble(-3000+1080/2, 3000));
		//this.vel = new Vector2(ThreadLocalRandom.current().nextInt(-50, 50),
		//							ThreadLocalRandom.current().nextInt(-50, 50));
		this.vel = new Vector2(0,0);
		mass = 0.01;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
}
