package de.ifgi.europa.gui;

import java.awt.GridLayout;
import java.awt.Point;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JPanel;

/**
 * Adds a WebViewer to the LOD SOS Viewer.
 * @author Matthias Pfeil
 *
 */
public class WebViewerPanel extends JPanel {

	private MainFrame mainFrame;
	final JFXPanel fxPanel = new JFXPanel();
	
	public WebViewerPanel(MainFrame mF) {
		super(new GridLayout(1, 1));
		this.setMainFrame(mF);
		
		this.add(fxPanel);
		
        fxPanel.setLocation(new Point(0, 0));
		
		Platform.runLater(new Runnable() { // this will run initFX as JavaFX-Thread
            public void run() {
                initFX(fxPanel);
            }
        });
	}
	
	/* Creates a WebView and fires up google.com */
    private static void initFX(final JFXPanel fxPanel) {
        Group group = new Group();
        Scene scene = new Scene(group);
        fxPanel.setScene(scene);

        WebView webView = new WebView();

        group.getChildren().add(webView);

        // Obtain the webEngine to navigate
        WebEngine webEngine = webView.getEngine();
        webEngine.load("http://www.google.com/");
    }

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}
