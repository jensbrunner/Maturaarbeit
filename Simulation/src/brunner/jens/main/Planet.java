package brunner.jens.main;

import java.util.concurrent.ThreadLocalRandom;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;

public class Planet 
{
	public Vector2 position;
	public float mass;
	public Vector2 vel, force;
	public boolean delete;
	
	public Planet(float x, float y, float xVel, float yVel, float mass) 
	{
		this.position = new Vector2(x,y);
		this.mass = mass;
		vel = new Vector2(xVel, yVel);
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
	
	public Planet() //We also want a constructor to make random planets.
	{
		this.position = new Vector2(ThreadLocalRandom.current().nextInt(0, Constants.WINDOW_DIMENSION.width),
									ThreadLocalRandom.current().nextInt(0, Constants.WINDOW_DIMENSION.height));
		this.vel = new Vector2(ThreadLocalRandom.current().nextInt(-100, 100),
									ThreadLocalRandom.current().nextInt(-100, 100));
		mass = 10;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
}
