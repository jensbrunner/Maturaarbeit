package brunner.jens.main;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Vector2;

public class Main
{

	public static boolean close, reset, showVelocityArrows, collisions, quadTree, creatingGalaxy;
	public static int iterations, lastIterations, planetAmount = 2000, blackHoleMass = 10000;
	public static double scaleFactor = 1, timeScale = 1, timeCounter = 0;
	public static long lastTime;
	
	public static int initalSpreadRadius = 3000;
	
	public static  double smoothingParam = 20;
	
	public static boolean bounded, isBounding, boundingAcceptable;
	public static Vector2 boundVec, boundVec2;
	
	//This is controlled by the "blackHoleCheck" CheckBox in the GUI (see SimulationWindow.java)
	public static Body centerBlackHole = null;
	
	static long starttime = System.currentTimeMillis();
	public static void main(String[] args)
	{
		SimulationWindow simWind = new SimulationWindow();
		BodyHandler.createRandomBodies(planetAmount, Constants.SCREEN_CENTER, Main.initalSpreadRadius);
		if(centerBlackHole != null) BodyHandler.planets.add(centerBlackHole);
		
		long currentTime = System.currentTimeMillis();
		
		while(!close) //In the main loop we use the concept of a variable timestep. Taken from https://gafferongames.com/post/fix_your_timestep/ on 18.06.2018.
		{
			long newTime = System.currentTimeMillis();
			long frameTime = newTime-currentTime;
			timeCounter += frameTime/1000d * Main.timeScale;
			double rounded = Math.floor(10000 * (double)timeCounter + 0.5) / 10000;
			SimulationWindow.timePassed.setText(rounded + " sec");
			//System.out.println(frameTime);
			currentTime = newTime;
			if(currentTime - lastTime > 1000)
			{
				showFPS(currentTime - lastTime);
				lastIterations = iterations;
				lastTime = currentTime;
			}

			
			/*//---------------DEBUG-------------
			if(currentTime - starttime > 5000) {
				System.out.println("Körper: " + Main.planetAmount);
				System.out.println("Verteilungsraum: " + Main.initalSpreadRadius);
				System.out.println("MAC: " + Constants.MAC);
				System.out.println("Frames: " + iterations);
				System.out.println("Zeit: " + (currentTime-starttime));
				System.out.println("----------------------");
				Main.planetAmount+=1000;
				starttime = System.currentTimeMillis();
				Main.reset = true;
			}
			//---------------DEBUG-------------*/
			
			
			
			if(reset)
			{
				BodyHandler.planets.clear();
				BodyHandler.createRandomBodies(planetAmount, Constants.SCREEN_CENTER, Main.initalSpreadRadius);
				if(centerBlackHole != null)
				{
					BodyHandler.planets.remove(centerBlackHole);
					centerBlackHole = new Body(Constants.SCREEN_CENTER, Constants.ZERO_VECTOR, Main.blackHoleMass);
					centerBlackHole.fixed = true;
					BodyHandler.planets.add(centerBlackHole);
				}
				timeCounter = 0;
				iterations = 0;
				reset = false;
			}
				
			/*if(Main.centerBlackHole != null) {
				System.out.println("Energy Total: " + PlanetHandler.getFrameTotalEnergy());
			}*/
			BodyHandler.updatePlanets(frameTime);
			//System.out.println(BarnesHut.numNodes);
			
			//The Quadtree is no longer used for the rest of this frame.
			//BarnesHut.numNodes = 0;
			simWind.repaint();
			iterations++;
		}
		System.exit(0);
	}
	
	public static void showFPS(long dt) {
		float fpsCount = (float)((iterations-lastIterations) * (1000./dt));
		if(fpsCount < 0)
		{
			SimulationWindow.fpsCounter.setText("FPS: ERROR"); 
			return;
		}
		SimulationWindow.fpsCounter.setText("FPS: " + Math.floor(100 * (double)fpsCount + 0.5) / 100);
	}
}