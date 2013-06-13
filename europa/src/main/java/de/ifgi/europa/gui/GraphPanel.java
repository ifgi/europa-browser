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
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		graph  = new MultiGraph("mg");
		
        viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        
//        Generator gen = new BarabasiAlbertGenerator(1);
//        gen.addSink(graph);
//		gen.begin();
//		for(int i=0; i<50; i++) {
//		    gen.nextEvents();
//		}
//		gen.end();
        
        graph.setStrict(false);
        graph.setAutoCreate(true);

        graph.addNode("A");
        graph.addEdge("AB", "A", "B").addAttribute("ui.label", "test");
        graph.addEdge("AC", "A", "C");
        graph.addEdge("AD", "A", "D");
//        
//        for (Node node : graph) {
//        	node.addAttribute("ui.label", node.getId());
//        }
               
        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);
//        graph.addAttribute("ui.stylesheet", "url('http://graphstream-project.org/media/data/css/fill_mode_pride.css')");
//        graph.addAttribute("ui.stylesheet", "url('http://graphstream-project.org/media/data/css/fill_mode_radial1.css')");
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
