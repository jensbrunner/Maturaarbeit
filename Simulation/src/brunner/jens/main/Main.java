package brunner.jens.main;

public class Main
{

	public static boolean close, reset = false;
	public static int planetAmount = 500;
	
	public static void main(String[] args)
	{
		SimulationWindow simWind = new SimulationWindow();
		PlanetHandler.createRandomPlanets(planetAmount);
		
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
				reset = false;
			}
			
			PlanetHandler.updatePlanets(frameTime);
			
			simWind.repaint();
		}
		System.exit(0);
	}
}