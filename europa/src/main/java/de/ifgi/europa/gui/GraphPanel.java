package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.stream.AttributeSink;
import org.graphstream.stream.ElementSink;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.Sink;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.ViewerListener;
import org.graphstream.ui.swingViewer.ViewerPipe;

public class GraphPanel extends JPanel {
	
	private MainFrame mainFrame;
	protected boolean loop = true;
	Graph graph;
    Viewer viewer;
    View view;
    
	public GraphPanel(MainFrame mF) {
		super(new BorderLayout());
		this.setMainFrame(mF);
		
		
		graph  = new MultiGraph("mg");
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        
        graph.setStrict(false);
        graph.setAutoCreate(true);

        graph.addEdge("AB", "A", "B").addAttribute("ui.label", "TEST");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
        
        for (Node node : graph) {
        	node.addAttribute("ui.label", node.getId());
        }
        
        final ViewerPipe fromViewerPipe = viewer.newViewerPipe();
        fromViewerPipe.addViewerListener(new ViewerListener() {
			
			@Override
			public void viewClosed(String arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void buttonReleased(String id) {
				// TODO Auto-generated method stub
				System.out.println("Button released on node "+id);
			}
			
			@Override
			public void buttonPushed(String id) {
				// TODO Auto-generated method stub
				System.out.println("Button pushed on node "+id);
			}
		});
        fromViewerPipe.addSink(graph);
        
        
        
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
        
        view.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				fromViewerPipe.pump();
			}
		});
        
        add(view, BorderLayout.CENTER);
        
        
        
	}

	/**
	 * @return the mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * @param mainFrame the mainFrame to set
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	

}
