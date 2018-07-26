package brunner.jens.main;

import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class BarnesHut
{

	//TODO SET TO NULL AFTER USING?
	public static Quadtree root;
	public static Quadtree paintTree;

	public static void insertBodies()
	{
		//This is the root node. For now, arbitrary values. Reset to null as not to take values from last frame.
		root = new Quadtree(-100000, -100000, 200000);

		for(Planet p : PlanetHandler.planets)
		{
			root.putBody(p);
		}
		//Update the tree we want DrawingComponent to paint once the root tree is fully constructed.
		paintTree = root;
	}

	public static void doPhysics(Planet p, Quadtree tree)
	{
		if(tree.bodies.size() == 1 && tree.bodies.get(0) != p)
		{
			PlanetHandler.computeForceBarnes(p, tree.bodies.get(0));
			
		}else if(tree.bodies.size() > 1 && tree.sideLength / Vector2Math.distance(p.position, new Vector2(tree.centerOfMass.x, tree.centerOfMass.y)) < 1)
		{
			Planet pseudoPlanet = new Planet(tree.centerOfMass.x, tree.centerOfMass.y, 0f, 0f, tree.totalMass);
			PlanetHandler.computeForceBarnes(p, pseudoPlanet);
			
		}else
		{
			if(tree.ne != null) doPhysics(p, tree.ne);
			if(tree.nw != null) doPhysics(p, tree.nw);
			if(tree.se != null) doPhysics(p, tree.se);
			if(tree.sw != null) doPhysics(p, tree.sw);
		}
	}
}
