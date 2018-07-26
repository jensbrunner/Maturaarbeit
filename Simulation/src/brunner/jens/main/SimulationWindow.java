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

import brunner.jens.interaction.MouseListener;
import brunner.jens.interaction.MouseMotionListener;
import brunner.jens.interaction.MouseWheelListener;
import brunner.jens.utils.Constants;
import brunner.jens.utils.FloatSlider;

public class SimulationWindow extends JFrame 
{
	public static JLabel timePassed = new JLabel();
	public static JLabel fpsCounter = new JLabel();
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
		
		optionWindow = new OptionWindow();

		
		JButton closeButton = new JButton();
		JButton resetButton = new JButton();
		JButton orbitButton = new JButton();
		
		JButton optionsButton = new JButton();
		optionsButton.setBounds(1920-80, 1080-20, 80, 20);
		optionsButton.setText("Options");
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
		
		JTextField planetAmount = new JTextField();
		
		zoom = new FloatSlider(Scrollbar.HORIZONTAL, 1, 0, 0.0001, 100, 10000, true);
		
		time = new FloatSlider(Scrollbar.HORIZONTAL, 1, 0, 0.0001, 1000, 10000, true);
		
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
		
		resetButton.setBounds(1920-70-70, 0, 70, 20);
		resetButton.setText("Reset");
		resetButton.setFocusable(false);
		resetButton.setBorderPainted(false);
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				planetAmount.postActionEvent();
				Main.reset = true;
			}
		});
		
		orbitButton.setBounds(1920-70-70-70, 0, 70, 20);
		orbitButton.setText("Orbit");
		orbitButton.setFocusable(false);
		orbitButton.setBorderPainted(false);
		orbitButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				Planet curMass = null;
				for(Planet p : PlanetHandler.planets)
				{
					if(curMass == null) curMass = p;
					else if(p.mass > curMass.mass) curMass = p;
				}
				for(Planet p : PlanetHandler.planets)
				{
					if(p == curMass) continue;
					PlanetHandler.makeOrbitAround(curMass, p);
				}
			}
		});
		
		planetAmount.setText(String.valueOf(Main.planetAmount));
		planetAmount.setBounds(1920-70-70-70-100, 0, 100, 20);
		planetAmount.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Main.planetAmount = Integer.parseInt(planetAmount.getText());
					Main.reset = true;
				} catch(NumberFormatException e) {
					System.out.println("Entry is not an integer.");
					planetAmount.setText("NaN");
				}
				
			}
		});
		
		zoomLabel.setText("Zoom: " + String.valueOf(Main.scaleFactor) + "x");
		zoomLabel.setBounds(Constants.WINDOW_DIMENSION.width/2-400+60, 1080-40-10, 200, 10);
		zoomLabel.setFocusable(false);
		zoom.setBounds(Constants.WINDOW_DIMENSION.width/2-400, 1080-15-10, 200, 15);
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
		
		timeLabel.setText("Time: " + String.valueOf(Main.timeScale) + "x");
		timeLabel.setBounds(Constants.WINDOW_DIMENSION.width/2+260, 1080-40-10, 200, 10);
		timeLabel.setFocusable(false);
		time.setBounds(Constants.WINDOW_DIMENSION.width/2+200, 1080-15-10, 200, 15);
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
		
		timePassed.setText(Main.timeCounter + " sec");
		timePassed.setForeground(Color.WHITE);
		timePassed.setFocusable(false);
		timePassed.setBounds(10, 0, 100, 50);
		
		fpsCounter.setText("");
		fpsCounter.setForeground(Color.WHITE);
		fpsCounter.setFocusable(false);
		fpsCounter.setBounds(10+100, 0, 100, 50);

		DrawingComponent draw = new DrawingComponent();
		add(closeButton);
		add(resetButton);
		add(orbitButton);
		add(optionsButton);
		add(planetAmount);
		add(zoom);
		add(zoomLabel);
		add(time);
		add(timeLabel);
		
		add(timePassed);
		add(fpsCounter);
		
		//Adding the listeners
		addMouseListener(new MouseListener());
		addMouseMotionListener(new MouseMotionListener());
		addMouseWheelListener(new MouseWheelListener());
		
		add(draw);
		setVisible(true);
	}
}
