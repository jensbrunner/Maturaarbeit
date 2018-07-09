package brunner.jens.main;

public class Main
{

	public static boolean close, reset, showVelocityArrows = false;
	public static boolean quadTree = true;
	public static int planetAmount = 200;
	
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
			currentTime = newTime;
			
			if(reset)
			{
				PlanetHandler.planets.clear();
				PlanetHandler.createRandomPlanets(planetAmount);
				if(centerBlackHole != null)
				{
					PlanetHandler.planets.remove(centerBlackHole);
					centerBlackHole = new Planet(1920/2f, 1080/2f, 0f, 0f, 10000);
					PlanetHandler.planets.add(centerBlackHole);
				}
				reset = false;
			}
			
			PlanetHandler.updatePlanets(frameTime);
			
			simWind.repaint();
		}
		System.exit(0);
	}
}