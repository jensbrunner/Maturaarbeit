package brunner.jens.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class DrawingComponent extends JComponent
{
	private static final long serialVersionUID = 1L;

	@Override 
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;

		//In this method we need to paint all the planets. We'll ask the PlanetHandler
		//for a list of the planets. TODO: Eventually make drawPlanets method.
		g2.setColor(Color.WHITE);
		for(Planet planet : PlanetHandler.planets)
		{
			float diameter = (float) (2.0f*Math.sqrt(planet.mass));
			//g2.fill(new Ellipse2D.Float(planet.position.x-diameter/2, planet.position.y-diameter/2, diameter, diameter));
			g2.fill(new Ellipse2D.Float(planet.position.x-5, planet.position.y-5, 10, 10));
		}
	}
}
