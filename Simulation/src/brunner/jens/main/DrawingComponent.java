package brunner.jens.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class DrawingComponent extends JComponent
{
	@Override 
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		//In this method we need to paint all the planets. We'll ask the PlanetHandler
		//for a list of the planets. TODO: Eventually make drawPlanets method.
		g2.setColor(Color.DARK_GRAY);
		for(Planet planet : PlanetHandler.planets)
		{
			g2.fill(new Ellipse2D.Float(planet.position.x-planet.mass/2, planet.position.y-planet.mass/2, planet.mass, planet.mass));
			//g2.drawLine((int)planet.position.x, (int)planet.position.y, (int)planet.position.x + (int)planet.vel.x, (int)planet.position.y + (int)planet.vel.y);
		}
	}
}
