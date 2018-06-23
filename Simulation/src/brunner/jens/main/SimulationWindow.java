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
import javax.swing.JTextField;
import javax.swing.border.Border;

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
		JTextField planetAmount = new JTextField();
		
		closeButton.setBounds(1920-70, 0, 70, 20);
		closeButton.setText("Close");
		closeButton.setFocusable(false);
		closeButton.setBorderPainted(false);
		closeButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0) {
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
			public void actionPerformed(ActionEvent arg0) {
				planetAmount.postActionEvent();
				Main.reset = true;
			}
		});
		
		planetAmount.setText("500");
		planetAmount.setBounds(1920-70-70-100, 0, 100, 20);
		planetAmount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
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
		
		
		

		DrawingComponent draw = new DrawingComponent();
		draw.add(closeButton);
		draw.add(resetButton);
		draw.add(planetAmount);
		add(draw);
		setVisible(true);
	}
}
