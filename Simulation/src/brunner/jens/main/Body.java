package brunner.jens.main;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;

public class Body 
{
	public Vector2 position;
	public double mass;
	public Vector2 vel, lastv, force;
	public boolean fixed = false, delete = false;
	
	public Body(Vector2 pos, Vector2 _vel, double mass) 
	{
		this.position = pos;
		this.mass = mass;
		vel = _vel;
		lastv = Constants.ZERO_VECTOR;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
	
	//We also want a constructor to make random planets.
	public Body(Vector2 center, double radius)
	{
		double a = Math.random() * 2 * Math.PI;
		double r = radius * Math.sqrt(Math.random());
		double x = r * Math.cos(a);
		double y = r * Math.sin(a);
		this.position = new Vector2(center.x+x,center.y+y);
		//this.vel = new Vector2(ThreadLocalRandom.current().nextInt(-50, 50),
		//							ThreadLocalRandom.current().nextInt(-50, 50));
		this.vel = new Vector2(0,0);
		lastv = Constants.ZERO_VECTOR;
		mass = 1;
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
}
