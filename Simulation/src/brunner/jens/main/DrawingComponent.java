package brunner.jens.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class DrawingComponent extends JComponent
{
	private static Graphics2D g2;
	
	public DrawingComponent() {
		this.setPreferredSize(new Dimension(1920, 1080));
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		//Setup the Graphics2D object.
		g2 = (Graphics2D) g;
		
		//Enable AntiAliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Paint all the bodies.
		for(Body p : BodyHandler.planets)
		{
			g2.setColor(Color.WHITE);
			DrawingComponent.paintBody(translateToScreen(p.position), p.vel);
		}
		
		//If the user has bounded something, paint the borders.
		if(Main.bounded || (Main.isBounding && Main.boundingAcceptable))
		{
			g2.setColor(Color.DARK_GRAY);
			Vector2 bounds = translateToScreen(Main.boundVec);
			Vector2 bounds2 = translateToScreen(Main.boundVec2);
			
			//We slightly adjust the drawn positions based on trial and error to give the border a good look.
			g2.drawRect((int)bounds.x-1, (int)bounds.y-1, (int)(bounds2.x-bounds.x)+2, (int)(bounds2.y-bounds.y)+2);
		}
		
		//If enabled, paint the Barnes-Hut tree.
		if(Main.quadTree && BarnesHut.paintTree != null)
		{
			g2.setColor(Color.WHITE);
			DrawingComponent.paintTree(BarnesHut.paintTree);
		}
	}
	
	public static void paintBody(Vector2 position, Vector2 vel)
	{
		
		//Based on the zoom level, scale the bodies accordingly.
		double radius = Main.scaleFactor*4/2;
		if(radius < 1) radius = 1;
		g2.fill(new Ellipse2D.Double(position.x-radius, position.y-radius, radius*2, radius*2));
		
		
		if(Main.showVelocityArrows)
		{
		//To draw the velocity vectors, a line is sufficient.
		g2.drawLine((int)position.x, (int)position.y, (int)(position.x+vel.x*10), (int)(position.y+vel.y*10));
		}
	}
	public static void paintTree(Quadtree tree)
	{
		//If the tree is constructed.
		if(BarnesHut.root == null) return;
		
		//Translate between simulation- and renderspace.
		Vector2 drawPos = translateToScreen(new Vector2(tree.x, tree.y));
		
		//Draw a square at the location, and then paint all subnodes, if they exist.
		g2.drawRect((int)drawPos.x, (int)drawPos.y, (int)(tree.sideLength*Main.scaleFactor), (int)(tree.sideLength*Main.scaleFactor));
		if(tree.ne != null) paintTree(tree.ne);
		if(tree.nw != null) paintTree(tree.nw);
		if(tree.se != null) paintTree(tree.sw);
		if(tree.sw != null) paintTree(tree.se);
	}
	
	//Since there's a zoom function, we need to transform between simulation- and renderspace.
	public static Vector2 translateToScreen(Vector2 vec) {
		//To move from (0,0) to the screen center, such that we can then scale.
		Vector2 diff = new Vector2(Constants.WINDOW_DIMENSION.width/2, Constants.WINDOW_DIMENSION.height/2);
		
		//Scale according to zoom level after subtracting the Vector from (0,0) to the screen center.
		Vector2 scaled = Vector2Math.mult(Vector2Math.subtract(vec,diff), Main.scaleFactor);
		
		//Move back to screen center
		Vector2 drawPos = Vector2Math.add(scaled, diff);
		return drawPos;
	}
}
