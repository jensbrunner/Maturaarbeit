package brunner.jens.main;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class BarnesHut
{

	//TODO SET TO NULL AFTER USING?
	public static Quadtree root;
	public static Quadtree paintTree;

	//public static int numNodes = 0;
	
	public static void insertBodies()
	{
		//To get the optimal Quadtree, find the topleft-most body and the largest coordinate value from that top
		//TODO: For some reason this technique is extremely costly as the tree grows bigger.
		/*double xmin = Double.MAX_VALUE;
		double ymin = Double.MAX_VALUE;
		
		double max = Double.MIN_VALUE;
		for(Planet p : PlanetHandler.planets) {
			double x = p.position.x;
			double y = p.position.y;
			if(x < xmin) xmin = x;
			if(y < ymin) ymin = y;
			
			if(x-xmin > max) {
				max = x-xmin;
			}
			if(y-ymin > max) {
				max = y-ymin;
			}
		}*/
		
		//This is the root node. Position it according to the values found prior. Add a buffer of 1 distance unit.
		//BarnesHut.root = new Quadtree(xmin-1, ymin-1, max+1);
		//Anything outside this tree will not be interacted WITH. But can still be interacted UPON by other nodes.
		BarnesHut.root = new Quadtree(-20000+Constants.SCREEN_CENTER.x, -20000+Constants.SCREEN_CENTER.y, 40000);
		
		for(Body p : BodyHandler.planets)
		{
			BarnesHut.root.putBody(p);
		}
		//Update the tree we want DrawingComponent to paint once the root tree is fully constructed.
		if(Main.quadTree) {
			BarnesHut.paintTree = BarnesHut.root;
		}
	}

	public static void doPhysics(Body p, Quadtree tree)
	{
		if(tree.bodies.size() == 1 && tree.bodies.get(0) != p)
		{
			BodyHandler.computeForceBarnes(p, tree.bodies.get(0));
			
		}else if(tree.bodies.size() > 1 && tree.sideLength / Vector2Math.distance(p.position, new Vector2(tree.centerOfMass.x, tree.centerOfMass.y)) < Constants.MAC)
		{
			Body pseudoPlanet = new Body(tree.centerOfMass, Constants.ZERO_VECTOR, tree.totalMass);
			BodyHandler.computeForceBarnes(p, pseudoPlanet);
			
		}else
		{
			if(tree.ne != null) doPhysics(p, tree.ne);
			if(tree.nw != null) doPhysics(p, tree.nw);
			if(tree.se != null) doPhysics(p, tree.se);
			if(tree.sw != null) doPhysics(p, tree.sw);
		}
	}
}
