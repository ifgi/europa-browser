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

package de.ifgi.europa.tests;

import gov.nasa.worldwindx.examples.view.AddAnimator;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

public class TestGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Graph graph = new MultiGraph("Tutorial 1");
		
		graph.addNode("A" );
		graph.addNode("B" );
		graph.addNode("C" );
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		
		graph.addAttribute("ui.quality");
		
		
	
		
		
		SpriteManager sman = new SpriteManager(graph);
		
		Sprite s = sman.addSprite("S1");
		s.setPosition(2, 1, 0);
		
		s.attachToNode("A");
		
		
		s.attachToEdge("AB");
		
		s.setPosition(0.5);
		
		graph.addAttribute("ui.stylesheet", "graph { fill-color: red; }");
		graph.addAttribute("ui.stylehseet", "url('http://www.deep.in/the/site/mystylesheet')");
		graph.addAttribute("ui.stylesheet", "url(file:///somewhere/over/the/rainbow/stylesheet')");
		
		
		graph.display();

	}
	

}
