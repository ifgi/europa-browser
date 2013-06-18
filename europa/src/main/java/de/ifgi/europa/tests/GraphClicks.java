package de.ifgi.europa.tests;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.Viewer;
import javax.swing.*;
import java.awt.*;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;

public class GraphClicks extends JFrame implements Runnable {
    public static void main(String[] args) {
            GraphClicks test = new GraphClicks();
            SwingUtilities.invokeLater(test);
        }

    @Override
    public void run() {
        Graph g = new MultiGraph("mg");
        Viewer v = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        g.addAttribute("ui.antialias");
        g.addAttribute("ui.quality");
        g.addAttribute("ui.stylesheet", styleSheet);

        v.enableAutoLayout();
        add(v.addDefaultView(false), BorderLayout.CENTER);

        gen.addSink(g);
        gen.begin();
        for(int i= 0; i<100; i++) { 
            gen.nextEvents();
        }
        gen.end();
        gen.removeSink(g);

        setSize(800, 600);
        setVisible(true);
    }

    protected static String styleSheet =
            "graph {" + 
            "   padding: 60px;" +
            "}";
}