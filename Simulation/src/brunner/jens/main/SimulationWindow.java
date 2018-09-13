package brunner.jens.main;

import java.awt.Color;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;

import brunner.jens.interaction.KeyListener;
import brunner.jens.interaction.MouseListener;
import brunner.jens.interaction.MouseMotionListener;
import brunner.jens.interaction.MouseWheelListener;
import brunner.jens.utils.Constants;
import brunner.jens.utils.FloatSlider;
import brunner.jens.utils.Toolbox;

public class SimulationWindow extends JFrame 
{
	
	public static SimulationWindow simWind;
	
	public static JLabel timePassed = new JLabel();
	public static JLabel fpsCounter = new JLabel();
	public static JLabel curBodyLabel = new JLabel();
	public static JLabel zoomLabel = new JLabel();
	public static FloatSlider zoom;
	public static JLabel timeLabel = new JLabel();
	public static FloatSlider time;
	public static OptionWindow optionWindow;
	
	public SimulationWindow()
	{
		super("n-Body Simulation");
		//setLayout(null); If we remove the comment, the Component will not be displayed. Check documentation
		//setSize(Constants.WINDOW_DIMENSION);
		
		setUndecorated(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 		
		getContentPane().setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Init optionWindow in here as it is "part" of the SimulationWindow and should thus be controlled by it.
		optionWindow = new OptionWindow();

		JButton closeButton = new JButton();
		closeButton.setBounds(1920-70, 0, 70, 20);
		closeButton.setText("Close");
		closeButton.setFocusable(false);
		closeButton.setBorderPainted(false);
		closeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Main.close = true;
			}
		});
		
		JTextField bodyAmount = new JTextField();
		bodyAmount.setText(String.valueOf(Main.initPlanetAmount));
		bodyAmount.setBounds(1920-70-70-70-100, 0, 100, 20);
		bodyAmount.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Main.initPlanetAmount = Integer.parseInt(bodyAmount.getText());
					Main.reset = true;
					//requestFocus();
				} catch(NumberFormatException e) {
					System.out.println("Entry is not an integer.");
					bodyAmount.setText("NaN");
				}
				
			}
		});
		
		JButton resetButton = new JButton();
		resetButton.setBounds(1920-70-70, 0, 70, 20);
		resetButton.setText("Reset");
		resetButton.setFocusable(false);
		resetButton.setBorderPainted(false);
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				bodyAmount.postActionEvent();
				Main.reset = true;
			}
		});
		
		JButton orbitButton = new JButton();
		orbitButton.setBounds(1920-70-70-70, 0, 70, 20);
		orbitButton.setText("Orbit");
		orbitButton.setFocusable(false);
		orbitButton.setBorderPainted(false);
		orbitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Body curMass = null;
				for(Body p : BodyHandler.planets)
				{
					if(curMass == null) curMass = p;
					else if(p.mass > curMass.mass) curMass = p;
				}
				for(Body p : BodyHandler.planets)
				{
					if(p == curMass) continue;
					Toolbox.makeOrbitAround(curMass, p);
				}
			}
		});
		
		JButton optionsButton = new JButton();
		optionsButton.setBounds(1920-138, 1080-20, 138, 20);
		optionsButton.setText("Advanced Options");
		optionsButton.setFocusable(false);
		optionsButton.setBorderPainted(false);
		optionsButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				optionWindow.setVisible(!optionWindow.isVisible());
			}
		});
		
		
		zoom = new FloatSlider(Scrollbar.HORIZONTAL, 1, 0, 0.0001, 100, 10000, true);
		zoomLabel.setText("Zoom: " + String.valueOf(Main.scaleFactor) + "x");
		zoomLabel.setBounds(Constants.WINDOW_DIMENSION.width/2-400+60, 1080-40-10, 200, 10);
		zoomLabel.setFocusable(false);
		zoom.setBounds(Constants.WINDOW_DIMENSION.width/2-400, 1080-15-10, 200, 15);
		zoom.setBackground(Color.DARK_GRAY);
		zoom.addAdjustmentListener(new AdjustmentListener()
		{

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Main.scaleFactor = zoom.getFloatValue();
				double rounded = Math.floor(10000 * (double)Main.scaleFactor + 0.5) / 10000;
				zoomLabel.setText("Zoom: " + rounded + "x");
				System.out.println("Change zoom to: " + Main.scaleFactor + "x");
			}

		});
		
		JButton zoomReset = new JButton();
		zoomReset.setBounds(Constants.WINDOW_DIMENSION.width/2-200+5, 1080-15-10, 50, 15);
		zoomReset.setText("1x");
		zoomReset.setFocusable(false);
		zoomReset.setBorderPainted(false);
		zoomReset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Main.scaleFactor = 1;
				SimulationWindow.zoom.setFloatValue(1);
				double rounded = Math.floor(10000 * (double)Main.scaleFactor + 0.5) / 10000;
				SimulationWindow.zoomLabel.setText("Zoom: " + rounded+"x");
			}
		});
		
		time = new FloatSlider(Scrollbar.HORIZONTAL, 1, 0, 0.0001, 1000, 10000, true);
		timeLabel.setText("Time: " + String.valueOf(Main.timeScale) + "x");
		timeLabel.setBounds(Constants.WINDOW_DIMENSION.width/2+260, 1080-40-10, 200, 10);
		timeLabel.setFocusable(false);
		time.setBounds(Constants.WINDOW_DIMENSION.width/2+200, 1080-15-10, 200, 15);
		time.setBackground(Color.DARK_GRAY);
		time.addAdjustmentListener(new AdjustmentListener()
		{

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Main.timeScale = time.getFloatValue();
				double rounded = Math.floor(10000 * (double)Main.timeScale + 0.5) / 10000;
				timeLabel.setText("Time: " + rounded + "x");
				System.out.println("Change time to: " + Main.timeScale + "x");
			}
		});
		
		JButton timeReset = new JButton();
		timeReset.setBounds(Constants.WINDOW_DIMENSION.width/2+200+200+5, 1080-15-10, 50, 15);
		timeReset.setText("1x");
		timeReset.setFocusable(false);
		timeReset.setBorderPainted(false);
		timeReset.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Main.timeScale = 1;
				SimulationWindow.time.setFloatValue(1);
				double rounded = Math.floor(10000 * (double)Main.timeScale + 0.5) / 10000;
				SimulationWindow.timeLabel.setText("Time: " + rounded+"x");
			}
		});
		
		timePassed.setText(Main.timeCounter + " sec");
		timePassed.setForeground(Color.WHITE);
		timePassed.setFocusable(false);
		timePassed.setBounds(10, 0, 100, 50);
		
		fpsCounter.setText("");
		fpsCounter.setForeground(Color.WHITE);
		fpsCounter.setFocusable(false);
		fpsCounter.setBounds(10+100, 0, 100, 50);
		
		curBodyLabel.setText("Bodies: ");
		curBodyLabel.setForeground(Color.WHITE);
		curBodyLabel.setFocusable(false);
		curBodyLabel.setBounds(10+200, 0, 100, 50);

		add(closeButton);
		add(resetButton);
		add(orbitButton);
		add(optionsButton);
		add(bodyAmount);
		add(zoom);
		add(zoomLabel);
		add(time);
		add(timeLabel);
		add(zoomReset);
		add(timeReset);
		
		add(timePassed);
		add(fpsCounter);
		add(curBodyLabel);
		
		//Adding the listeners
		addMouseListener(new MouseListener());
		addMouseMotionListener(new MouseMotionListener());
		addMouseWheelListener(new MouseWheelListener());
		addKeyListener(new KeyListener());
		
		DrawingComponent draw = new DrawingComponent();
		draw.setFocusable(false);
		add(draw);
		setFocusable(true);
		setVisible(true);
		
		
	}
	
	public static void updateBodyLabel(int amount) {
		Main.curBodyAmount = amount;
		curBodyLabel.setText("" + amount);
	}
	
	//Since JTextField tends to stupidly retain focus,  we want to add a function that gives the focus back to the main SimulationWindow, such that keyboard inputs can be properly recorded.
	public static void resetFocus() {
		simWind.requestFocusInWindow();
	}
}
