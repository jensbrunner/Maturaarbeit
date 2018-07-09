package brunner.jens.main;

public class BarnesHut
{

	//TODO SET TO NULL AFTER USING!?!?
	public static Quadtree node;
	
	public static boolean insertBodies()
	{
		//This is the root node. For now, arbitrary values.
		node = new Quadtree(-10000, -10000, 20000);
		
		for(Planet p : PlanetHandler.planets)
		{
			node.putBody(p);
		}
		return true;
	}
	
}
