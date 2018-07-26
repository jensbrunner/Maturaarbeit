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
		
		for(Planet p : PlanetHandler.planets)
		{
			g2.setColor(Color.WHITE);
			DrawingComponent.paintBody(translateToScreen(p.position), p.vel);
		}
		if(Main.bounded || (Main.isBounding && Main.boundingAcceptable)) {
			g2.setColor(Color.DARK_GRAY);
			Vector2 bounds = translateToScreen(Main.boundVec);
			Vector2 bounds2 = translateToScreen(Main.boundVec2);
			
			//We slightly adjust the drawed positions based on trial and error to give the border a good look.
			g2.drawRect((int)bounds.x-1, (int)bounds.y-1, (int)(bounds2.x-bounds.x)+2, (int)(bounds2.y-bounds.y)+2);
		}
		if(Main.quadTree) {
			g2.setColor(Color.WHITE);
			DrawingComponent.paintTree(BarnesHut.paintTree);
		}
	}
	
	public static void paintBody(Vector2 position, Vector2 vel) {
		double radius = Main.scaleFactor*4/2;
		if(radius < 1) radius = 1;
		g2.fill(new Ellipse2D.Double(position.x-radius, position.y-radius, radius*2, radius*2));
		if(Main.showVelocityArrows)
		{
		//To draw the velocity vectors, we create a line.
		g2.drawLine((int)position.x, (int)position.y, (int)(position.x+vel.x*10), (int)(position.y+vel.y*10));
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
	
	public static Vector2 translateToScreen(Vector2 vec) {
		Vector2 diff = new Vector2(Constants.WINDOW_DIMENSION.width/2, Constants.WINDOW_DIMENSION.height/2);
		Vector2 scaled = Vector2Math.mult(Vector2Math.subtract(vec,diff), Main.scaleFactor);
		Vector2 drawPos = Vector2Math.add(scaled, diff);
		return drawPos;
	}
}
