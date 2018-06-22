package brunner.jens.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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
		setLayout(new FlowLayout());
		
		getContentPane().setBackground(Color.BLACK);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton closeButton = new JButton();
		closeButton.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Main.close = true;
			}

		});

		add(new DrawingComponent());
		add(closeButton);
		setVisible(true);
	}
}
