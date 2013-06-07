package de.ifgi.europa.tests;

import gov.nasa.worldwindx.examples.view.AddAnimator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

public class TestGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Graph graph = new SingleGraph("Tutorial 1");
		
		graph.addNode("A" );
		graph.addNode("B" );
		graph.addNode("C" );
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.display();
		
		//***************************************
		
//		Graph graph2 = new MultiGraph("Bazinga!");
//		// Populate the graph.
//
//		graph2.addNode("A" );
//		graph2.addNode("B" );
//		graph2.addNode("C" );
//		graph2.addEdge("AB", "A", "B");
//		graph2.addEdge("BC", "B", "C");
//		graph2.addEdge("CA", "C", "A");
//		
//		Viewer viewer = graph2.display();
//		// Let the layout work ...
//		//viewer.disableAutoLayout();
//		// Do some work ...
//		
//		
//		viewer.enableAutoLayout();
//	
//		graph2.display();
	}
	

}
