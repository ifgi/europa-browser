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

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.ViewerListener;
import org.graphstream.ui.swingViewer.ViewerPipe;
import org.graphstream.graph.Node;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class GraphPanel extends JPanel implements ViewerListener {
	
	private MainFrame mainFrame;
	protected boolean loop = true;
	Graph g;
    Viewer v;
    View view;
    
	public GraphPanel(MainFrame frame) {
		super(new BorderLayout());
		this.setMainFrame(frame);
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		g = new MultiGraph("mg");
		g.addAttribute("ui.quality");
		g.addAttribute("ui.antialias");
        v = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        
        final ViewerPipe fromViewer = v.newViewerPipe();
		fromViewer.addViewerListener(this);
		fromViewer.addSink(g);

        g.addAttribute("ui.antialias");
        g.addAttribute("ui.quality");
        
        v.enableAutoLayout();
        add(v.addDefaultView(false), BorderLayout.CENTER);
        
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
	Node attachTo;
	public void updateGraph(QuerySolution soln, String selectedFOI, int flag) {
		if (g.getNode("A") == null) {
			g.addNode("A").addAttribute("ui.label", selectedFOI);
			g.getNode("A").addAttribute("ui.style", "size: 32px; fill-mode: image-scaled; fill-image: url('"+getClass().getResource("/foi.png").toString()+"'); text-alignment:at-right;");
			attachTo = g.getNode("A");
		} else if (flag == 0) {
			attachTo = g.getNode("A");
		} else {
			attachTo = g.getNode(selectedFOI);
		}
		
		String nodeName = "";
		String edgeLabel = "";
		String nodeLabel = "";
		String nodeAttribtue = "";
		
		if (flag == 0) {
			nodeName = soln.get("?subject").toString();
			nodeLabel = soln.get("?label").toString();
			edgeLabel = "";
			nodeAttribtue = "size: 32px; fill-mode: image-scaled; fill-image: url('"+getClass().getResource("/uri.png").toString()+"'); text-padding: 25px, 2px; text-alignment:at-right;";
		} else {
			edgeLabel = soln.get("?predicate").toString();
			if (soln.get("?object").isLiteral()) {
				nodeName = soln.get("?object").toString();
				nodeLabel = soln.get("?object").toString();
				nodeAttribtue = "size: 28px; fill-mode: image-scaled; fill-image: url('"+getClass().getResource("/literal.png").toString()+"'); text-padding: 25px, 2px; text-alignment:at-right;";
			} else {
				nodeName = soln.get("?object").toString();
				nodeLabel = soln.get("?labelNode").toString();
				nodeAttribtue = "size: 32px; fill-mode: image-scaled; fill-image: url('"+getClass().getResource("/uri.png").toString()+"'); text-padding: 25px, 2px; text-alignment:at-right;";
			}
		}
		
		if (g.getNode(nodeName) == null) {
			g.addNode(nodeName).addAttribute("ui.label", nodeLabel);
			g.getNode(nodeName).addAttribute("ui.style", nodeAttribtue);
			g.addEdge(attachTo.toString()+nodeName, attachTo.toString(), nodeName,true).addAttribute("ui.label", edgeLabel);
			g.getEdge(attachTo.toString()+nodeName).addAttribute("ui.style", "fill-color: blue;");
		}
		
		v.getDefaultView().updateUI();
	}
	
	public void clearGraph() {
		if (g.getNode("A") != null) {
			g.clear();
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
		String style = g.getNode(id).getAttribute("ui.style");
		if (!style.contains("literal")) {
			if(g.getNode(id).getLeavingEdgeSet().size() == 0) {
				ResultSet rs = ((FilterPanel) getMainFrame().getFilterPanel()).getFacade().getNodeExternalData(g.getNode(id).toString());
				while(rs.hasNext()) {
					QuerySolution soln = rs.nextSolution();
					updateGraph(soln, id, 1);
				}
			} else {
				if(id != "A") {
					while(g.getNode(id).getLeavingEdgeIterator().hasNext())
					{
						Edge e = g.getNode(id).getLeavingEdgeIterator().next();
						g.removeNode(e.getNode1());
					}
				}
			}
		}
	}

	@Override
	public void buttonReleased(String id) {

	}

	@Override
	public void viewClosed(String arg0) {

	}
	
	public Viewer getViewer(){
		return v;
	}
}
