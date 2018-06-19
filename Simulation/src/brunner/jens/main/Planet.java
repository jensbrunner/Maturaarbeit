package brunner.jens.main;

import java.util.concurrent.ThreadLocalRandom;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;

public class Planet 
{
	public Vector2 position;
	public int mass;
	public Vector2 vel, force;
	public boolean delete;
	
	public Planet(int x, int y, int mass) 
	{
		this.position = new Vector2(x,y);
		this.mass = mass;
		vel = Constants.ZERO_VECTOR;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
	
	public Planet() //We also want a constructor to make random planets.
	{
		this.position = new Vector2(ThreadLocalRandom.current().nextInt(0, Constants.WINDOW_DIMENSION.width),
									ThreadLocalRandom.current().nextInt(0, Constants.WINDOW_DIMENSION.height));
		mass = ThreadLocalRandom.current().nextInt(5,1000);
		vel = Constants.ZERO_VECTOR;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}	
}
