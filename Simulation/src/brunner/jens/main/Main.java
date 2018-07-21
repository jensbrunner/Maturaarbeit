package brunner.jens.main;

import brunner.jens.utils.Vector2;

public class Main
{

	public static boolean close, reset, showVelocityArrows, collisions, quadTree;
	public static int planetAmount = 2000;
	public static float scaleFactor = 1, timeScale = 1;
	
	//This is controlled by the "blackHoleCheck" CheckBox in the GUI (see SimulationWindow.java)
	public static Planet centerBlackHole = null;
	
	public static void main(String[] args)
	{
		SimulationWindow simWind = new SimulationWindow();
		PlanetHandler.createRandomPlanets(planetAmount);
		if(centerBlackHole != null) PlanetHandler.planets.add(centerBlackHole);
		
		long currentTime = System.currentTimeMillis();
		
		while(!close) //In the main loop we use the concept of a variable timestep. Taken from https://gafferongames.com/post/fix_your_timestep/ on 18.06.2018.
		{
			if(System.currentTimeMillis()-currentTime < 1)
			{	
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e)
				{
					System.out.println("Loop was unable to sleep. Exiting...");
					System.exit(0);
				}
			}
			long newTime = System.currentTimeMillis();
			long frameTime = newTime-currentTime;
			System.out.println(frameTime);
			currentTime = newTime;
			
			if(reset)
			{
				PlanetHandler.planets.clear();
				PlanetHandler.createRandomPlanets(planetAmount);
				if(centerBlackHole != null)
				{
					PlanetHandler.planets.remove(centerBlackHole);
					centerBlackHole = new Planet(1920/2f, 1080/2f, 0f, 0f, 100000);
					PlanetHandler.planets.add(centerBlackHole);
				}
				reset = false;
			}
			if(Main.centerBlackHole != null) Main.centerBlackHole.position = new Vector2(1920/2f,1080/2f);
			PlanetHandler.updatePlanets(frameTime);
			simWind.repaint();
		}
		System.exit(0);
	}
}