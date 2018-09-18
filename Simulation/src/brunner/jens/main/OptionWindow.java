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
		
		//-------------------------Black Hole------------------------------
		
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
		
		//------------------------Velocityarrows-------------------------------
		
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
		
		//---------------------Quadtree----------------------------------
		
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
				if(quadTreeCheck.isSelected() && Main.barneshut)
				{
					Main.quadTree = true;
				}else
				{
					Main.quadTree = false;
				}
			}
		});
		
		//------------------------Collision-------------------------------
		
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
		
		//----------------------Bounds---------------------------------
		
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
		
		//------------------------Smoothing-------------------------------
		
		JLabel smoothingLabel = new JLabel();
		smoothingLabel.setText("Smoothing:");
		
		//We move the label to the right by 22 pixels because of layout purposes. That's the width of the checkbox.
		smoothingLabel.setBounds(0+22, 0+100, 100, 20);
		
		JTextField smoothingField = new JTextField();
		smoothingField.setText(String.valueOf(Main.smoothingParam));
		smoothingField.setBounds(150, 0+100, 40, 20);
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
		
		//------------------------Spread-------------------------------
		
		JLabel spreadLabel = new JLabel();
		spreadLabel.setText("Radius of spread:");
		spreadLabel.setBounds(0+22, 0+120, 100, 20);
		
		JTextField spreadField = new JTextField();
		spreadField.setText(String.valueOf(Main.initalSpreadRadius));
		spreadField.setBounds(150, 0+120, 40, 20);
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
		
		//---------------------New Galaxy----------------------------------
		
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
		
		//------------------------Editor-------------------------------
		
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
		
		//----------------------Initial Orbit---------------------------------
		
		JCheckBox initOrbitCheck = new JCheckBox();
		initOrbitCheck.setText("Initial Orbit");
		initOrbitCheck.setSelected(Main.initOrbit);
		initOrbitCheck.setFocusable(false);
		initOrbitCheck.setBounds(0, 0+180, 100, 20);
		initOrbitCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(initOrbitCheck.isSelected()) {
					Main.initOrbit = true;
				}else {
					Main.initOrbit = false;
				}
				
			}
		});
		
		//-------------------------Random Velocity------------------------------
		
		JCheckBox randomVelocityCheck = new JCheckBox();
		randomVelocityCheck.setText("Random Velocity");
		randomVelocityCheck.setSelected(Main.randomVelocity);
		randomVelocityCheck.setFocusable(false);
		randomVelocityCheck.setBounds(0, 0+200, 140, 20);
		randomVelocityCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(randomVelocityCheck.isSelected()) {
					Main.randomVelocity = true;
				}else {
					Main.randomVelocity = false;
				}
				
			}
		});
		
		JLabel initVelLabel = new JLabel();
		initVelLabel.setText("Max Initial Velocity:");
		initVelLabel.setBounds(0 + 22, 0+220, 160, 20);
		
		JTextField initVelField = new JTextField();
		
		//Since velocity values are really small in the simulation, and we want to convey a certain sense of speed, scale up the real values, and scale them down after typing them into the field.
		initVelField.setText(String.valueOf(Main.maxInitVel*100.0));
		initVelField.setBounds(150, 0+220, 40, 20);
		initVelField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Main.maxInitVel = Double.parseDouble(initVelField.getText())/100.0;
				} catch(NumberFormatException e) {
					System.out.println("Entry is not a double.");
					smoothingField.setText("NaN");
				}
			}
		});
		
		//------------------------Random Mass-------------------------------
		
		JCheckBox randomMass = new JCheckBox();
		randomMass.setText("Random Mass");
		randomMass.setSelected(Main.randomMass);
		randomMass.setFocusable(false);
		randomMass.setBounds(0, 0+240, 140, 20);
		randomMass.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(randomMass.isSelected()) {
					Main.randomMass = true;
				}else {
					Main.randomMass = false;
				}
			}
		});
		
		JLabel massLabel = new JLabel();
		massLabel.setText("Max Random Mass:");
		massLabel.setBounds(0 + 22, 0+260, 160, 20);
		
		JTextField massField = new JTextField();
		
		//Since velocity values are really small in the simulation, and we want to convey a certain sense of speed, scale up the real values, and scale them down after typing them into the field.
		massField.setText(String.valueOf(Main.maxRandomMass));
		massField.setBounds(150, 0+260, 40, 20);
		massField.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				try
				{
					Main.maxRandomMass = Double.parseDouble(massField.getText());
				} catch(NumberFormatException e) {
					System.out.println("Entry is not a double.");
					smoothingField.setText("NaN");
				}
			}
		});
		
		//------------------------Simulation Algorithm-------------------------------
		
		JCheckBox barnesCheck = new JCheckBox();
		barnesCheck.setText("Barnes-Hut");
		barnesCheck.setSelected(Main.barneshut);
		barnesCheck.setFocusable(false);
		barnesCheck.setBounds(0, 0+280, 100, 20);
		barnesCheck.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(barnesCheck.isSelected()) {
					Main.barneshut = true;
				}else {
					Main.barneshut = false;
					quadTreeCheck.setSelected(false);
					Main.quadTree = false;
				}
				
			}
		});
		
		//----------------------------------------
		
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
		add(randomVelocityCheck);
		add(initOrbitCheck);
		add(initVelLabel);
		add(initVelField);
		add(randomMass);
		add(massLabel);
		add(massField);
		add(barnesCheck);
	}
}
