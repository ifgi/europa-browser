package de.ifgi.europa.gui;

import java.awt.GridLayout;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;

import javax.swing.JPanel;

public class MapPanel extends JPanel {

	private MainFrame mainFrame;
	
	public MapPanel(MainFrame mF) {
		super(new GridLayout(1, 1));
		this.setMainFrame(mF);
		
		WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        wwd.setPreferredSize(new java.awt.Dimension(800, 600));
        this.add(wwd, java.awt.BorderLayout.CENTER);
        wwd.setModel(new BasicModel());
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}
