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
		setSize(400, 50);
		setLocation(1920/2-200, 0);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(null);
		setVisible(false);
		
		JCheckBox dragCheck = new JCheckBox();
		dragCheck.setText("Drag");
		dragCheck.setSelected(EditorHandler.drag);
		dragCheck.setBounds(0, 0, 130, 20);
		dragCheck.setFocusable(false);
		dragCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(dragCheck.isSelected())
				{
					EditorHandler.drag = true;
				}else
				{
					EditorHandler.drag = false;
				}
			}
		});
		
		JLabel massLabel = new JLabel();
		massLabel.setText("Mass:");
		massLabel.setBounds(0, 20, 100, 20);
		
		JTextField massField = new JTextField();
		massField.setText(String.valueOf(EditorHandler.setMass));
		massField.setBounds(0+30, 20, 30, 20);
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
		
		add(dragCheck);
		add(massLabel);
		add(massField);
	}
}

