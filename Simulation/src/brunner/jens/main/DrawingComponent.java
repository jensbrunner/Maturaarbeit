package brunner.jens.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class DrawingComponent extends JComponent
{
	private static final long serialVersionUID = 1L;
	private static Graphics2D g2;
	
	public DrawingComponent() {
		this.setPreferredSize(new Dimension(1920, 1080));
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		g2 = (Graphics2D) g;

		//In this method we need to paint all the planets. We'll ask the PlanetHandler
		//for a list of the planets. TODO: Eventually make drawPlanets method.
		g2.setColor(Color.WHITE);
		for(Planet p : PlanetHandler.planets)
		{
			Vector2 diff = new Vector2(Constants.WINDOW_DIMENSION.width/2, Constants.WINDOW_DIMENSION.height/2);
			Vector2 moved = Vector2Math.subtract(p.position, diff);
			Vector2 scaled = Vector2Math.mult(moved, Main.scaleFactor);
			Vector2 drawPos = Vector2Math.add(scaled, diff);
			DrawingComponent.paintBody(drawPos, p.vel);
		}
		if(Main.quadTree) {
			DrawingComponent.paintTree(BarnesHut.paintTree);
		}
	}
	
	public static void paintBody(Vector2 position, Vector2 vel) {
		float radius = Main.scaleFactor*4/2;
		if(radius < 1) radius = 1;
		g2.fill(new Ellipse2D.Float(position.x-radius, position.y-radius, radius*2, radius*2));
		if(Main.showVelocityArrows)
		{
		//To draw the velocity vectors, we create a line.
		g2.drawLine((int)position.x, (int)position.y, (int)(position.x+vel.x/6), (int)(position.y+vel.y/6));
		}
	}
	public static void paintTree(Quadtree tree)
	{
		if(BarnesHut.root == null) return;
		g2.drawRect(tree.x, tree.y, tree.sideLength, tree.sideLength);
		if(tree.ne != null) paintTree(tree.ne);
		if(tree.nw != null) paintTree(tree.nw);
		if(tree.se != null) paintTree(tree.sw);
		if(tree.sw != null) paintTree(tree.se);
	}
}
