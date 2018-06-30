package brunner.jens.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import brunner.jens.utils.Constants;
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
		JCheckBox blackHoleCheck = new JCheckBox();
		JCheckBox showVelocityCheck = new JCheckBox();
		
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
		
		planetAmount.setText("500");
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
		
		blackHoleCheck.setText("Center Black Hole");
		blackHoleCheck.setSelected(false);
		blackHoleCheck.setBounds(1920-130, 1080-20, 130, 20);
		blackHoleCheck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(blackHoleCheck.isSelected())
				{
					Main.centerBlackHole = new Planet(1920/2f, 1080/2f, 0f, 0f, 10000);
					PlanetHandler.planets.add(Main.centerBlackHole);
				}else
				{
					PlanetHandler.planets.remove(Main.centerBlackHole);
					Main.centerBlackHole = null;
				}
			}
		});
		
		showVelocityCheck.setText("Display Velocity");
		showVelocityCheck.setSelected(false);
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

		DrawingComponent draw = new DrawingComponent();
		draw.add(closeButton);
		draw.add(resetButton);
		draw.add(orbitButton);
		draw.add(planetAmount);
		draw.add(blackHoleCheck);
		draw.add(showVelocityCheck);
		add(draw);
		setVisible(true);
	}
}
