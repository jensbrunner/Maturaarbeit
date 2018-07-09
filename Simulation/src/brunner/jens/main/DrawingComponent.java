package brunner.jens.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class DrawingComponent extends JComponent
{
	private static final long serialVersionUID = 1L;
	
	public DrawingComponent() {
		this.setPreferredSize(new Dimension(1920, 1080));
	}
	
	@Override 
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		//In this method we need to paint all the planets. We'll ask the PlanetHandler
		//for a list of the planets. TODO: Eventually make drawPlanets method.
		g2.setColor(Color.WHITE);
		for(Planet planet : PlanetHandler.planets)
		{
			g2.fillOval((int)planet.position.x-2, (int)planet.position.y-2, 4, 4);
			
			if(Main.showVelocityArrows)
			{
			//To draw the velocity vectors, we create a line.
			g2.drawLine((int)planet.position.x, (int)planet.position.y, (int)(planet.position.x+planet.vel.x/6), (int)(planet.position.y+planet.vel.y/6));
			}
			
			//Paint the Quadtree
			paintTree(BarnesHut.node, g2);
		}
	}
	
	public void paintTree(Quadtree tree, Graphics2D g)
	{
		g.drawRect(tree.x, tree.y, tree.sideLength, tree.sideLength);
		if(tree.getNE() != null) paintTree(tree.getNE(), g);
		if(tree.getNW() != null) paintTree(tree.getNW(), g);
		if(tree.getSE() != null) paintTree(tree.getSE(), g);
		if(tree.getSW() != null) paintTree(tree.getSW(), g);
	}
}
