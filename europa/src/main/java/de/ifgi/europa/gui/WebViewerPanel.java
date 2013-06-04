package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.sun.javafx.application.PlatformImpl;

/**
 * Adds a WebViewer to the LOD SOS Viewer.
 * @author Matthias Pfeil
 *
 */
public class WebViewerPanel extends JPanel {

	private Stage stage;  
    private WebView browser;  
    private JFXPanel jfxPanel;  
    private JButton swingButton;  
    private WebEngine webEngine;
    private MainFrame mainFrame;
	
	public WebViewerPanel(MainFrame mF) {
//		super(new GridLayout(1, 1));
		super(new BorderLayout());
		this.setMainFrame(mF);

		jfxPanel = new JFXPanel();  
        createScene();  
         
        setLayout(new BorderLayout());  
        add(jfxPanel, BorderLayout.CENTER);  
         
        swingButton = new JButton();  
        swingButton.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
 
                    @Override
                    public void run() {
                        webEngine.reload();
                    }
                });
            }
        });  
        swingButton.setText("Reload");  
         
        add(swingButton, BorderLayout.SOUTH);
	}
	
	/** 
     * createScene 
     * 
     * Note: Key is that Scene needs to be created and run on "FX user thread" 
     *       NOT on the AWT-EventQueue Thread 
     * 
     */  
    private void createScene() {  
        PlatformImpl.startup(new Runnable() {  
            @Override
            public void run() {  
                 
                stage = new Stage();  
                 
//                stage.setTitle("Hello Java FX");  
//                stage.setResizable(true);  
   
                Group root = new Group();  
                Scene scene = new Scene(root,80,20);  
                stage.setScene(scene);  
                 
                // Set up the embedded browser:
                browser = new WebView();
                webEngine = browser.getEngine();
                webEngine.load("http://www.google.com");
                
                ObservableList<Node> children = root.getChildren();
                children.add(browser);                     
                 
                jfxPanel.setScene(scene);  
            }  
        });  
    }

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}