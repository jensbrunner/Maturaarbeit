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
	public Body(Vector2 center, double radius, boolean randomVelocity, boolean randomMass)
	{
		double a = Math.random() * 2 * Math.PI;
		double r = radius * Math.sqrt(Math.random());
		double x = r * Math.cos(a);
		double y = r * Math.sin(a);
		position = new Vector2(center.x+x,center.y+y);
		if(randomVelocity) {
			
			//Der Ausdruck "Math.random() >= 1 ? Math.random() * 50 : Math.random() * -50" entscheidet zufällig zwischen positiv oder negativem Wert zwischen 0 und weniger als 50 respektive -50.
			vel = new Vector2(Math.random() >= 0.5 ? Math.random() * Main.maxInitVel : Math.random() * -Main.maxInitVel, Math.random() >= 0.5 ? Math.random() * Main.maxInitVel : Math.random() * -Main.maxInitVel);
		}else {
			vel = new Vector2(0,0);
		}
		lastv = Constants.ZERO_VECTOR;
		if(randomMass) {
			mass = 1 + Math.random() >= 0.5 ? Math.random() * Main.maxRandomMass : Math.random() * -Main.maxRandomMass;
		}else {
			mass = 1;
		}
		force = Constants.ZERO_VECTOR;
		delete = false;
	}
}
