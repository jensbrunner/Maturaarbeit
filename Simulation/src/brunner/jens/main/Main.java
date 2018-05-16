package brunner.jens.main;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class Main
{

	public static void main(String[] args)
	{
		SimulationWindow simWind = new SimulationWindow();
		PlanetHandler.createRandomPlanets(10);
		
		while(true) {
			long start = System.currentTimeMillis();
			PlanetHandler.updatePlanets();
			simWind.repaint();
			
			if(System.currentTimeMillis()-start < Constants.MS_PER_FRAME)
			{	
				try 
				{
					Thread.sleep(start + Constants.MS_PER_FRAME - System.currentTimeMillis());
				} catch (InterruptedException e) 
				{
					System.out.println("Loop was unable to sleep. Exiting...");
					System.exit(0);
				}
			}
			System.out.println(System.currentTimeMillis()-start);
		}
	}
}
w