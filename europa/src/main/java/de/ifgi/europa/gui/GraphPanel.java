/**
   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.ViewerListener;
import org.graphstream.ui.swingViewer.ViewerPipe;

import com.hp.hpl.jena.query.ResultSet;

public class GraphPanel extends JPanel implements ViewerListener {
	
	private MainFrame mainFrame;
	protected boolean loop = true;
	Graph graph;
    Viewer viewer;
    View view;
    
	public GraphPanel(MainFrame mF) {
		super(new BorderLayout());
		this.setMainFrame(mF);
		
		Graph g = new MultiGraph("mg");
        Viewer v = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        
        final ViewerPipe fromViewer = v.newViewerPipe();
		fromViewer.addViewerListener(this);
		fromViewer.addSink(g);

        g.addAttribute("ui.antialias");
        g.addAttribute("ui.quality");

        v.enableAutoLayout();
        add(v.addDefaultView(false), BorderLayout.CENTER);
//        view = v.addDefaultView(false);
////    graph.addAttribute("ui.stylesheet", "node#A {size: 25px; fill-color: red; text-alignment:at-right;} node {size: 15px; fill-color: green; text-alignment:at-left;} edge {fill-color: blue; text-alignment: along; text-style: bold;}");
//        add(view, BorderLayout.CENTER);
        
        
        g.addNode("A").addAttribute("ui.label", "TEST");
        
        v.getDefaultView().addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("viewer clicked");
				fromViewer.pump();
			}
		});
	}
	
	Integer count = 0;
	public void updateGraph(ResultSet rs, String selectedFOI) {
		graph.addNode("A").addAttribute("ui.label", selectedFOI);
		for(int i = 0; i < 5; i++) {
			count = i;
			String temp = count.toString();
			graph.addNode(temp).addAttribute("ui.label", temp);
			graph.addEdge("A"+temp, "A", temp).addAttribute("ui.label", "edge");
		}
		
		view.updateUI();	
//		graph.addNode("A").addAttribute("ui.label", selectedFOI);
//		while (rs.hasNext()) {
////			QuerySolution soln = rs.nextSolution();
//			String nodeName = count.toString();
//			graph.addNode(nodeName).addAttribute("ui.label", "asdasdsad");
////			graph.addEdge("A"+count.toString(), "A", count.toString()).addAttribute("ui.label", "edge");
//			count++;
//		}
	
	}
	
	public void nada() {
		System.out.println("ich tue heute mal nichts!");
		graph.addNode("A").addAttribute("ui.label", "CENTER");
		for(int i = 0; i < 5; i++) {
			count = i;
			String temp = count.toString();
			graph.addNode(temp).addAttribute("ui.label", temp);
			graph.addEdge("A"+temp, "A", temp).addAttribute("ui.label", "edge");
		}
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

	@Override
	public void buttonPushed(String id) {
		// TODO Auto-generated method stub
		System.out.println("Button pushed on node "+id);
	}

	@Override
	public void buttonReleased(String id) {
		// TODO Auto-generated method stub
		System.out.println("Button released on node "+id);
	}

	@Override
	public void viewClosed(String arg0) {
		// TODO Auto-generated method stub
		loop = false;
	}
	

}
