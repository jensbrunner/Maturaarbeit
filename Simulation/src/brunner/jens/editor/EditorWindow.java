package brunner.jens.editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import brunner.jens.main.Main;

public class EditorWindow extends JFrame{
	public EditorWindow() {
		super("Editor Options");
		setUndecorated(true);
		setSize(200, 20);
		setLocation(1920/2-200, 0);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(null);
		setVisible(false);
		
		JLabel massLabel = new JLabel();
		massLabel.setText("Mass:");
		massLabel.setBounds(0, 0, 100, 20);
		
		JTextField massField = new JTextField();
		massField.setText(String.valueOf(EditorHandler.setMass));
		massField.setBounds(0+40, 0, 30, 20);
		massField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					EditorHandler.setMass = Integer.parseInt(massField.getText());
				} catch(NumberFormatException e) {
					System.out.println("Entry is not an integer.");
					massField.setText("NaN");
				}
			}
		});
		
		JCheckBox velocityCheck = new JCheckBox();
		velocityCheck.setText("Velocity");
		velocityCheck.setSelected(EditorHandler.velocity);
		velocityCheck.setBounds(70, 0, 130, 20);
		velocityCheck.setFocusable(false);
		velocityCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(velocityCheck.isSelected())
				{
					EditorHandler.velocity = true;
				}else
				{
					EditorHandler.velocity = false;
				}
			}
		});
		
		add(massLabel);
		add(massField);
		add(velocityCheck);
	}
}

