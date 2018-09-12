package brunner.jens.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import brunner.jens.editor.EditorHandler;
import brunner.jens.editor.EditorWindow;
import brunner.jens.utils.Constants;


public class OptionWindow extends JFrame{
	
	public OptionWindow() {
		super("Advanced Options");
		setSize(300, 500);
		setLocation(1920-300, 1080/2-250);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(null);
		setVisible(false);
		
		JCheckBox blackHoleCheck = new JCheckBox();
		blackHoleCheck.setText("Center Black Hole");
		blackHoleCheck.setSelected(Main.centerBlackHole != null);
		blackHoleCheck.setBounds(0, 0, 130, 20);
		blackHoleCheck.setFocusable(false);
		blackHoleCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(blackHoleCheck.isSelected())
				{
					Main.centerBlackHole = new Body(Constants.SCREEN_CENTER, Constants.ZERO_VECTOR, Main.blackHoleMass);
					Main.centerBlackHole.fixed = true;
					BodyHandler.planets.add(Main.centerBlackHole);
				}else
				{
					BodyHandler.planets.remove(Main.centerBlackHole);
					Main.centerBlackHole = null;
				}
			}
		});
		
		JCheckBox showVelocityCheck = new JCheckBox();
		showVelocityCheck.setText("Display Velocity");
		showVelocityCheck.setSelected(Main.showVelocityArrows);
		showVelocityCheck.setBounds(0, 0+20, 130, 20);
		showVelocityCheck.setFocusable(false);
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
		
		JCheckBox quadTreeCheck = new JCheckBox();
		quadTreeCheck.setText("Display Quadtree");
		quadTreeCheck.setSelected(Main.quadTree);
		quadTreeCheck.setBounds(0, 0+40, 130, 20);
		quadTreeCheck.setFocusable(false);
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
		
		JCheckBox collisionCheck = new JCheckBox();
		collisionCheck.setText("Collisions");
		collisionCheck.setSelected(Main.collisions);
		collisionCheck.setBounds(0, 0+60, 130, 20);
		collisionCheck.setFocusable(false);
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
		
		JCheckBox boundsCheck = new JCheckBox();
		boundsCheck.setText("Bounded");
		boundsCheck.setSelected(Main.bounded);
		boundsCheck.setBounds(0, 0+80, 130, 20);
		boundsCheck.setFocusable(false);
		boundsCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(boundsCheck.isSelected())
				{
					Main.isBounding = true;
				}else
				{
					Main.boundingAcceptable = false;
					Main.bounded = false;
				}
			}
		});
		
		JLabel smoothingLabel = new JLabel();
		smoothingLabel.setText("Smoothing:");
		smoothingLabel.setBounds(0, 0+100, 100, 20);
		
		JTextField smoothingField = new JTextField();
		smoothingField.setText(String.valueOf(Main.smoothingParam));
		smoothingField.setBounds(0+70, 0+100, 30, 20);
		smoothingField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Main.smoothingParam = Double.parseDouble(smoothingField.getText());
				} catch(NumberFormatException e) {
					System.out.println("Entry is not an double.");
					smoothingField.setText("NaN");
				}
			}
		});
		
		JLabel spreadLabel = new JLabel();
		spreadLabel.setText("Radius of spread:");
		spreadLabel.setBounds(0, 0+120, 100, 20);
		
		JTextField spreadField = new JTextField();
		spreadField.setText(String.valueOf(Main.initalSpreadRadius));
		spreadField.setBounds(0+105, 0+120, 40, 20);
		spreadField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Main.initalSpreadRadius = Integer.parseInt(spreadField.getText());
					Main.reset = true;
				} catch(NumberFormatException e) {
					System.out.println("Entry is not an integer.");
					smoothingField.setText("NaN");
				}
			}
		});
		
		JCheckBox newgalaxyCheck = new JCheckBox();
		newgalaxyCheck.setText("New Galaxy");
		newgalaxyCheck.setFocusable(false);
		newgalaxyCheck.setBounds(0, 0+140, 100, 20);
		newgalaxyCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(newgalaxyCheck.isSelected()) {
					Main.creatingGalaxy = true;
				}else {
					Main.creatingGalaxy = false;
				}
				
			}
		});
		
		JCheckBox editorCheck = new JCheckBox();
		editorCheck.setText("Editor");
		editorCheck.setFocusable(false);
		editorCheck.setBounds(0, 0+160, 100, 20);
		editorCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(editorCheck.isSelected()) {
					EditorHandler.enterEditor();
				}else {
					EditorHandler.exitEditor();
				}
				
			}
		});
		
		add(blackHoleCheck);
		add(showVelocityCheck);
		add(quadTreeCheck);
		add(collisionCheck);
		add(boundsCheck);
		add(smoothingField);
		add(smoothingLabel);
		add(newgalaxyCheck);
		add(spreadLabel);
		add(spreadField);
		add(editorCheck);
	}
}
