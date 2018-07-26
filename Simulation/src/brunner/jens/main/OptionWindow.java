package brunner.jens.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public class OptionWindow extends JFrame{
	public OptionWindow() {
		super("Options");
		setUndecorated(true);
		setSize(130, 100);
		
		setLocation(1920-125, 1080-120);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(null);
		
		JCheckBox blackHoleCheck = new JCheckBox();
		blackHoleCheck.setText("Center Black Hole");
		blackHoleCheck.setSelected(Main.centerBlackHole != null);
		blackHoleCheck.setBounds(0, 0, 130, 20);
		blackHoleCheck.setBackground(Color.BLACK);
		blackHoleCheck.setFocusable(false);
		blackHoleCheck.setForeground(Color.WHITE);
		blackHoleCheck.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				if(blackHoleCheck.isSelected())
				{
					Main.centerBlackHole = new Planet(1920/2f, 1080/2f, 0f, 0f, Main.blackHoleMass);
					Main.centerBlackHole.fixed = true;
					PlanetHandler.planets.add(Main.centerBlackHole);
				}else
				{
					PlanetHandler.planets.remove(Main.centerBlackHole);
					Main.centerBlackHole = null;
				}
			}
		});
		
		JCheckBox showVelocityCheck = new JCheckBox();
		showVelocityCheck.setText("Display Velocity");
		showVelocityCheck.setSelected(Main.showVelocityArrows);
		showVelocityCheck.setBounds(0, 0+20, 130, 20);
		showVelocityCheck.setBackground(Color.BLACK);
		showVelocityCheck.setFocusable(false);
		showVelocityCheck.setForeground(Color.WHITE);
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
		quadTreeCheck.setBackground(Color.BLACK);
		quadTreeCheck.setFocusable(false);
		quadTreeCheck.setForeground(Color.WHITE);
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
		collisionCheck.setBackground(Color.BLACK);
		collisionCheck.setFocusable(false);
		collisionCheck.setForeground(Color.WHITE);
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
		boundsCheck.setBackground(Color.BLACK);
		boundsCheck.setFocusable(false);
		boundsCheck.setForeground(Color.WHITE);
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
		
		add(blackHoleCheck);
		add(showVelocityCheck);
		add(quadTreeCheck);
		add(collisionCheck);
		add(boundsCheck);
	}
}
