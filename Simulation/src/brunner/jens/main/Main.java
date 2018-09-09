package brunner.jens.main;

import brunner.jens.utils.Constants;
import brunner.jens.utils.Toolbox;
import brunner.jens.utils.Vector2;

public class Main
{

	//Booleans that control much of sim functionality, alongside some values pertaining to those functionalities.
	public static boolean close, reset, showVelocityArrows, collisions, quadTree, creatingGalaxy, barneshut = true, frameTest = false;
	public static int iterations, lastIterations, planetAmount = 2000, blackHoleMass = 10000;
	public static double scaleFactor = 1, timeScale = 1, timeCounter = 0;
	public static int initalSpreadRadius = 1000;
	public static  double smoothingParam = 20;
	public static boolean bounded, isBounding, boundingAcceptable;
	public static Vector2 boundVec, boundVec2;
	
	//This is controlled by the "blackHoleCheck" CheckBox in the GUI (see SimulationWindow.java)
	public static Body centerBlackHole = null;
	
	//Timestamp variables necessary to keep track of elapsed time for the gameloop and other features.
	static long startTime = System.currentTimeMillis();
	
	
	public static void main(String[] args)
	{
		SimulationWindow simWind = new SimulationWindow();
		BodyHandler.createRandomBodies(planetAmount, Constants.SCREEN_CENTER, Main.initalSpreadRadius);
		if(centerBlackHole != null) BodyHandler.planets.add(centerBlackHole);
		
		//We have to initialise the variable outside of the loop.
		long currentTime = System.currentTimeMillis();
		long lastFPSTime = startTime;
		
		//Gameloop
		while(!close) //In the main loop we use the concept of a variable timestep. Taken from https://gafferongames.com/post/fix_your_timestep/ on 18.06.2018.
		{
			long newTime = System.currentTimeMillis();
			long frameTime = newTime-currentTime;
			timeCounter += frameTime/1000d * Main.timeScale;
			SimulationWindow.timePassed.setText(Toolbox.approxRound(timeCounter, 3) + " sec");
			currentTime = newTime;
			
			//Every second, update the FPS counter
			if(currentTime - lastFPSTime > 1000)
			{
				showFPS(currentTime - lastFPSTime);
				lastIterations = iterations;
				lastFPSTime = currentTime;
			}

			//if(frameTest) Main.debug(currentTime);

			if(reset)
			{
				Main.reset();
			}
			
			//Update simulation with frameTime as parameter.
			BodyHandler.updateBodies(frameTime);
		
			//Render
			simWind.repaint();
			
			//This variable keeps track of the amount of frames since start.
			iterations++;
		}
		System.exit(0);
	}
	
	private static void reset() {
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
	
	private static void debug(long currentTime) {
		if(currentTime - startTime > 5000) {
			System.out.println("Modus: " + Main.barneshut);
			System.out.println("Körper: " + Main.planetAmount);
			System.out.println("Verteilungsraum: " + Main.initalSpreadRadius);
			System.out.println("MAC: " + Constants.MAC);
			System.out.println("Frames: " + iterations);
			System.out.println("Zeit: " + (currentTime-startTime));
			System.out.println("----------------------");
			Main.planetAmount+=1000;
			if(Main.planetAmount > 10000) {
				if(Main.barneshut) {
					System.exit(0);
				barneshut = false;
				planetAmount = 0;
				}else {
					System.exit(0);
				}
			}
			startTime = System.currentTimeMillis();
			Main.reset = true;
		}
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