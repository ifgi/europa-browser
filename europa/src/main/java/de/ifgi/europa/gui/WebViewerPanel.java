package de.ifgi.europa.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
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
    private JFXPanel jfxPanel;  
    private JButton btnReload;  
    private MainFrame mainFrame;
    private Browser browser;
	
	public WebViewerPanel(MainFrame mF) {
		super(new BorderLayout());
		this.setMainFrame(mF);

		jfxPanel = new JFXPanel();  
        createScene();  
         
        setLayout(new BorderLayout());  
        add(jfxPanel, BorderLayout.CENTER);  
         
        btnReload = new JButton();  
        btnReload.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                Platform.runLater(new Runnable() {
 
                    @Override
                    public void run() {
                       browser.webEngine.reload();
                    }
                });
            }
        });  
        btnReload.setText("Reload");  
         
        add(btnReload, BorderLayout.PAGE_START);
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

                browser = new Browser();
                Scene scene = new Scene(browser,500,500);  
                stage.setScene(scene);                
                 
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
	
	public void runJavaScript() {
		Platform.runLater(new Runnable() {
			 
            @Override
            public void run() {
            	
            	browser.webEngine.executeScript("showGoogle()");
            }
        });
		
	}
}

class Browser extends Region {
	
	final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
	
	public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load("http://ifgibox.de/m_pfei05/nasasigma/index_old.html");
        //add the web view to the scene
        getChildren().add(browser);
 
    }
	
	@Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}