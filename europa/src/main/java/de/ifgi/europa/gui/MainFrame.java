package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @author 
 *
 */

public class MainFrame extends JFrame {

	private JPanel panelCont = new JPanel(new BorderLayout());

	private JPanel pnlWebViewer;
	private JPanel pnlMap;
	private JPanel pnlFilter;

	public MainFrame() {
		super("LOD SOS Viewer");
	    setSize(800,600);
	    setLocation(0,0);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    pnlFilter = new FilterPanel(this);
	    pnlMap = new MapPanel(this);
	    pnlWebViewer = new WebViewerPanel(this);
	    JPanel splittedPanel = new JPanel(new GridLayout(2,1));
	    splittedPanel.add(pnlMap);
	    splittedPanel.add(pnlWebViewer);
	    panelCont.add(pnlFilter,BorderLayout.WEST);
	    panelCont.add(splittedPanel,BorderLayout.CENTER);

	    add(panelCont,BorderLayout.CENTER);

	    pack();
	    setVisible(true);
	}
	
	public JPanel getMapPanel() {
		return pnlMap;
	}
	
	public JPanel getFilterPanel() {
		return pnlFilter;
	}
	
	public JPanel getWebViewer() {
		return pnlWebViewer;
	}
}