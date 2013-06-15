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

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;

public class TestGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.setProperty("gs.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		
		Graph graph = new MultiGraph("sg");
		graph.addAttribute("ui.stylesheet", "url('http://graphstream-project.org/media/data/css/fill_mode_pride.css')");
		
		Integer count = 0;
		graph.addNode("A").addAttribute("ui.label", "start");
		for(int i = 0; i < 5; i++) {
			count = i;
			String temp = count.toString();
			graph.addNode(temp).addAttribute("ui.label", temp);
			graph.addEdge("A"+temp, "A", temp).addAttribute("ui.label", "edge");
		}
		
		Generator gen = new BarabasiAlbertGenerator(1);
		gen.addSink(graph);
		gen.begin();
		
		for (int i = 0; i < graph.getNodeCount(); i++) {
			
		}
		gen.end();
//		graph.addAttribute("ui.stylesheet", "node {shape: freeplane; fill-color:white; stroke-mode:plain; size-mode: fit;} edge {shape: freeplane; text-color:red;}");
//		graph.addNode("A").addAttribute("ui.label", "Sofia Vergara");
//		graph.addNode("B").addAttribute("ui.label", "1");
//		graph.addNode("C").addAttribute("ui.label", "1972-07-10");;
//		graph.addNode("D").addAttribute("ui.label", "Model and actress");;
//		graph.addEdge("AB", "A", "B").addAttribute("ui.label", "children");
//		graph.addEdge("AC", "A", "C").addAttribute("ui.label", "birthDate");
//		graph.addEdge("AD", "A", "D").addAttribute("ui.label", "shortDescription");
		
		graph.addAttribute("ui.stylesheet", "node#A {size: 25px; fill-color: red; text-alignment:at-right;} node {size: 15px; fill-color: green; text-alignment:at-left;} edge {fill-color: blue; text-alignment: along; text-style: bold;}");
		
		graph.display();
		
//		Graph graph = new SingleGraph("Barabasi-Albert");
//		graph.addAttribute("ui.stylesheet", "url('http://graphstream-project.org/media/data/css/fill_mode_pride.css')");
//		// Between 1 and 3 new links per node added.
//		Generator gen = new BarabasiAlbertGenerator(1);
//		graph.display();
//	
//		gen.addSink(graph);
//		gen.begin();
//		for(int i=0; i<100; i++) {
//		    gen.nextEvents();
//		}
//		gen.end();
		
		

	}
	

}
