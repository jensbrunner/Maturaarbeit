package brunner.jens.main;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;

import brunner.jens.utils.Constants;

public class SimulationWindow extends JFrame 
{
	private static final long serialVersionUID = 1L;

	public SimulationWindow()
	{
		super("n-Body Simulation");
		//setLayout(null); If we remove the comment, the Component will not be displayed. Check documentation
		setVisible(true);
		setSize(Constants.WINDOW_DIMENSION);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new DrawingComponent());
		Container c = this.getContentPane();
		c.setBackground(Color.BLACK);
	}
}
