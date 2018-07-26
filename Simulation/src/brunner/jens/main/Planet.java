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
		double a = Math.random() * 2 * Math.PI;
		double r = 3000 * Math.sqrt(Math.random());
		double x = r * Math.cos(a);
		double y = r * Math.sin(a);
		this.position = new Vector2(x+Constants.WINDOW_DIMENSION.width/2,y+Constants.WINDOW_DIMENSION.height/2);
		//this.vel = new Vector2(ThreadLocalRandom.current().nextInt(-50, 50),
		//							ThreadLocalRandom.current().nextInt(-50, 50));
		this.vel = new Vector2(0,0);
		mass = 1;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
}
