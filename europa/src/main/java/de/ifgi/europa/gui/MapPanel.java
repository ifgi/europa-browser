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

import java.awt.GridLayout;
import java.util.Iterator;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Earth;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.ScalebarLayer;
import gov.nasa.worldwind.layers.StarsLayer;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.layers.Earth.BMNGWMSLayer;
import gov.nasa.worldwind.layers.Earth.LandsatI3WMSLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Cylinder;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwindx.examples.util.ToolTipController;

import javax.swing.JPanel;

import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSValue;

/**
 * Represents the NASA Globe view.
 * @author Matthias Pfeil
 *
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	final RenderableLayer layer;
	Globe earth;
	final WorldWindowGLCanvas wwd;
	public static String newline = System.getProperty("line.separator");
	AnnotationWindow tip;
	AnnotationWindowLayer awl;
	
	public MapPanel(MainFrame mF) {
		super(new GridLayout(1, 1));
		this.setMainFrame(mF);
		wwd = new WorldWindowGLCanvas();
		layer = new RenderableLayer();
		
		/*activate tooltip */
		ToolTipController toolTip = new ToolTipController(wwd,AVKey.DISPLAY_NAME,null);
        
		/**
		 * Creates <code>{@link AnnotationWindowLayer}</code> and adds an 
		 * <code>{@link AnnotationWindow}</code> for interactive components inside
		 * the <code>{@link WorldWindowGLCanvas}</code>.
		 */
        awl = new AnnotationWindowLayer(wwd);
        tip = new AnnotationWindow(Position.fromDegrees(0, 0, 0), wwd);
        tip.setPanel(new awTip(getMainFrame()));
        awl.addWindow(tip);
        awl.setEnabled(false);
        
		Layer[] layers = new Layer[]
        {
			layer,
            new StarsLayer(),
            new CompassLayer(),
            new BMNGWMSLayer(),
            new LandsatI3WMSLayer(), 
            awl,
            new ScalebarLayer(),  
        };
		
		earth = new Earth();
		BasicModel modelForWindowA = new BasicModel();
		modelForWindowA.setGlobe(earth);
		modelForWindowA.setLayers(new LayerList(layers));
		
        wwd.setPreferredSize(new java.awt.Dimension(800, 600));
        wwd.setModel(modelForWindowA);
        ViewControlsLayer viewControlsA = new ViewControlsLayer();
        wwd.getModel().getLayers().add(viewControlsA);
        wwd.addSelectListener(new ViewControlsSelectListener(wwd, viewControlsA));
        
        this.add(wwd, java.awt.BorderLayout.CENTER);
        
//        wwd.addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				System.out.println(wwd.getCurrentPosition());
//				((WebViewerPanel) getMainFrame().getWebViewer()).runJavaScript();
//			}
//		});
        
	}	
	
	/**
	 * Remove all renderables from wwd.
	 */
	public void clearGlobe() {
		layer.removeAllRenderables();
		wwd.redrawNow();
	}
	
	/**
	 * Adds a new observation as a renderable to the wwd.
	 * @param observation
	 * @param foi
	 * @param viz
	 */
	public void updateGlobe(SOSObservation observation, String foi, String viz) {

		if (observation != null) {
			Double defaultHeight = 10.0;
			Double defaultRadius = 10.0;
			Double val = 0.0;
			String toolTip = "";
			
			//Build tooltip text
			toolTip = "FOI: " + observation.getFeatureOfInterest().getName();
			for (int i = 0; i < observation.getSensorOutput().size(); i++) {
				SOSValue value = observation.getSensorOutput().get(i).getValue();
				val = value.getHasValue();
				toolTip = toolTip + newline + "Value: " + val;
			}
			
			//Get geometry of observation and parse latitude and longitude
			String wkt = observation.getFeatureOfInterest().getDefaultGeometry().getAsWKT();
			String[] splitArray = wkt.split("\\s+");
			String[] splitArrayLat = splitArray[1].split("\\(");
			String[] splitArrayLon = splitArray[2].split("\\)");
			Double lat = Double.parseDouble(splitArrayLat[1]);
			Double lon = Double.parseDouble(splitArrayLon[0]);
			
			// Create and set an attribute bundle.
	        ShapeAttributes attrs = new BasicShapeAttributes();
	        attrs.setInteriorMaterial(Material.RED);
	        attrs.setInteriorOpacity(0.7);
	        attrs.setEnableLighting(true);
	        attrs.setOutlineMaterial(Material.RED);
	        attrs.setOutlineWidth(2d);
	        attrs.setDrawInterior(true);
	        attrs.setDrawOutline(false);
			
	        //Create cylinder depending on chosen visualization and add it to the renderable layer
	        Cylinder cylinder = null;
	        
	        if (viz.compareTo("width") == 0) {
	        	defaultRadius = defaultRadius*val*10;
	        	cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, defaultRadius);
			} else if (viz.compareTo("height") == 0) {
				defaultHeight = defaultHeight*val*1000;
				cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, 10000);
			} else if (viz.compareTo("color") == 0) {
				attrs.setInteriorMaterial(Material.RED);
				cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, defaultRadius);
			}
	        
	        cylinder.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
	        cylinder.setAttributes(attrs);
	        cylinder.setVisible(true);
	        cylinder.setValue(AVKey.DISPLAY_NAME, toolTip);
	        layer.addRenderable(cylinder);
		} else {
			Iterable<Renderable> renderables = layer.getRenderables();
			Iterator<Renderable> iter = renderables.iterator();
			while (iter.hasNext()) {
				Cylinder temp = (Cylinder) iter.next();
				String displayName = (String) temp.getValue(AVKey.DISPLAY_NAME);
				if (displayName.contains(foi)) {
					layer.removeRenderable(temp);
				}
			}
		}

		//Update AnnotationWindow
		if (layer.getNumRenderables() == 0) {
			removeAnnotationWindowFromGlobe();
		} else {
			addAnnotationWindowToGlobe();
		}
        wwd.redrawNow();
	}
	
	/**
	 * Gets MainFrame
	 * @return mainFrame
	 */
	public MainFrame getMainFrame() {
		return mainFrame;
	}

	/**
	 * Sets MainFrame
	 * @param mainFrame
	 */
	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public void addAnnotationWindowToGlobe() {
		awl.setEnabled(true);
	}
	
	public void removeAnnotationWindowFromGlobe() {
		awl.setEnabled(false);
		wwd.redrawNow();
		wwd.repaint();
	}

}
