package de.ifgi.europa.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class WebViewerPanel extends JPanel {

	private MainFrame mainFrame;
	
	public WebViewerPanel(MainFrame mF) {
		super(new GridLayout(1, 1));
		this.setMainFrame(mF);
		
		add(new JButton("WebViewer"));
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}
