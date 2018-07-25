package brunner.jens.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;

import brunner.jens.utils.Constants;
import brunner.jens.utils.FloatSlider;
import brunner.jens.utils.Vector2;
import brunner.jens.utils.Vector2Math;

public class SimulationWindow extends JFrame 
{
	private static final long serialVersionUID = 1L;

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
		
		JButton closeButton = new JButton();
		JButton resetButton = new JButton();
		JButton orbitButton = new JButton();
		JTextField planetAmount = new JTextField();
		
		FloatSlider zoom = new FloatSlider(Scrollbar.HORIZONTAL, 1, 0, 0.0001, 100, 1000, true);
		JLabel zoomLabel = new JLabel();
		
		FloatSlider time = new FloatSlider(Scrollbar.VERTICAL, 1, 0, 0.0001, 1000, 1000, true);
		JLabel timeLabel = new JLabel();
		
		JCheckBox blackHoleCheck = new JCheckBox();
		JCheckBox showVelocityCheck = new JCheckBox();
		JCheckBox quadTreeCheck = new JCheckBox();
		JCheckBox collisionCheck = new JCheckBox();
		
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
		
		zoomLabel.setText(String.valueOf(Main.scaleFactor) + "x");
		zoomLabel.setBounds(1920/2-15, 1080-20-20, 80, 10);
		zoomLabel.setFocusable(false);
		zoom.setBounds(1920/2-100, 1080-15, 200, 15);
		zoom.addAdjustmentListener(new AdjustmentListener()
		{

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Main.scaleFactor = (float)zoom.getFloatValue();
				double rounded = Math.floor(10000 * (double)Main.scaleFactor + 0.5) / 10000;
				zoomLabel.setText(rounded + "x");
				System.out.println("Change zoom to: " + Main.scaleFactor + "x");
			}

		});
		
		timeLabel.setText(String.valueOf(Main.timeScale) + "x");
		timeLabel.setBounds(1920-80, 1080/2, 80, 10);
		timeLabel.setFocusable(false);
		time.setBounds(1920-15, 1080/2-100, 15, 200);
		time.addAdjustmentListener(new AdjustmentListener()
		{

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				Main.timeScale = (float)time.getFloatValue();
				double rounded = Math.floor(10000 * (double)Main.timeScale + 0.5) / 10000;
				timeLabel.setText(rounded + "x");
				System.out.println("Change time to: " + Main.timeScale + "x");
			}

		});
		
		blackHoleCheck.setText("Center Black Hole");
		blackHoleCheck.setSelected(Main.centerBlackHole != null);
		blackHoleCheck.setBounds(1920-130, 1080-20, 130, 20);
		blackHoleCheck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(blackHoleCheck.isSelected())
				{
					Main.centerBlackHole = new Planet(1920/2f, 1080/2f, 0f, 0f, 100000);
					Main.centerBlackHole.fixed = true;
					PlanetHandler.planets.add(Main.centerBlackHole);
				}else
				{
					PlanetHandler.planets.remove(Main.centerBlackHole);
					Main.centerBlackHole = null;
				}
			}
		});
		
		showVelocityCheck.setText("Display Velocity");
		showVelocityCheck.setSelected(Main.showVelocityArrows);
		showVelocityCheck.setBounds(1920-130, 1080-20-20, 130, 20);
		showVelocityCheck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(showVelocityCheck.isSelected())
				{
					Main.showVelocityArrows = true;
				}else
				{
					Main.showVelocityArrows = false;
				}
			}
		});
		
		quadTreeCheck.setText("Display Quadtree");
		quadTreeCheck.setSelected(Main.quadTree);
		quadTreeCheck.setBounds(1920-130, 1080-20-20-20, 130, 20);
		quadTreeCheck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(quadTreeCheck.isSelected())
				{
					Main.quadTree = true;
				}else
				{
					Main.quadTree = false;
				}
			}
		});
		collisionCheck.setText("Collisions");
		collisionCheck.setSelected(Main.collisions);
		collisionCheck.setBounds(1920-130, 1080-20-20-20-20, 130, 20);
		collisionCheck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(collisionCheck.isSelected())
				{
					Main.collisions = true;
				}else
				{
					Main.collisions = false;
				}
			}
		});

		DrawingComponent draw = new DrawingComponent();
		draw.add(closeButton);
		draw.add(resetButton);
		draw.add(orbitButton);
		draw.add(planetAmount);
		draw.add(zoom);
		draw.add(zoomLabel);
		draw.add(time);
		draw.add(timeLabel);
		draw.add(blackHoleCheck);
		draw.add(showVelocityCheck);
		draw.add(quadTreeCheck);
		draw.add(collisionCheck);
		add(draw);
		setVisible(true);
	}
}
