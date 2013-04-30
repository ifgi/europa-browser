package de.ifgi.europa.gui;

import java.awt.BorderLayout;

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
	    
	    panelCont.add(pnlFilter,BorderLayout.WEST);
	    panelCont.add(pnlMap,BorderLayout.CENTER);
	    panelCont.add(pnlWebViewer,BorderLayout.SOUTH);
	    
	    add(panelCont,BorderLayout.CENTER);
	    
	    pack();
	    setVisible(true);
	}
}
