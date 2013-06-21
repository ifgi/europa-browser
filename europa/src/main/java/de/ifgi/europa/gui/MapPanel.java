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

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Iterator;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.Configuration;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
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
import gov.nasa.worldwind.layers.Earth.CountryBoundariesLayer;
import gov.nasa.worldwind.layers.Earth.LandsatI3WMSLayer;
import gov.nasa.worldwind.layers.Earth.NASAWFSPlaceNameLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Cone;
import gov.nasa.worldwind.render.Cylinder;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Offset;
import gov.nasa.worldwind.render.PointPlacemark;
import gov.nasa.worldwind.render.PointPlacemarkAttributes;
import gov.nasa.worldwind.render.Renderable;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.terrain.ZeroElevationModel;
import gov.nasa.worldwindx.examples.util.ToolTipController;

import javax.swing.JPanel;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSSensorOutput;
import de.ifgi.europa.core.SOSValue;
import de.ifgi.europa.gui.FilterPanel.Properties;

/**
 * Represents the NASA Globe view.
 * @author Matthias Pfeil
 *
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private MainFrame mainFrame;
	final RenderableLayer layer, dbpedia;
	Globe earth;
	final WorldWindowGLCanvas wwd;
	public static String newline = System.getProperty("line.separator");
	AnnotationWindow tip;
	AnnotationWindowLayer awl;
	
	public MapPanel(MainFrame frame) {
		super(new GridLayout(1, 1));
		
		Configuration.setValue(AVKey.INITIAL_LATITUDE, 51);
        Configuration.setValue(AVKey.INITIAL_LONGITUDE, 10);
        Configuration.setValue(AVKey.INITIAL_ALTITUDE, 120e4);
		
		this.setMainFrame(frame);
		wwd = new WorldWindowGLCanvas();
		layer = new RenderableLayer();
        dbpedia = new RenderableLayer();
        
		new ToolTipController(wwd,AVKey.DISPLAY_NAME,null);
        
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
			dbpedia,
            new CompassLayer(),
            new BMNGWMSLayer(),
            new LandsatI3WMSLayer(),
            new CountryBoundariesLayer(),
            awl,
            new ScalebarLayer(),  
        };
		
		
		
		earth = new Earth();
		earth.setElevationModel(new ZeroElevationModel());
		
		BasicModel modelForWindowA = new BasicModel();
		modelForWindowA.setGlobe(earth);
		modelForWindowA.setLayers(new LayerList(layers));
		
        wwd.setPreferredSize(new java.awt.Dimension(800, 600));
        wwd.setModel(modelForWindowA);
        ViewControlsLayer viewControlsA = new ViewControlsLayer();
        wwd.getModel().getLayers().add(viewControlsA);
        wwd.addSelectListener(new ViewControlsSelectListener(wwd, viewControlsA));
        
        this.add(wwd, java.awt.BorderLayout.CENTER);
        wwd.addSelectListener(new SelectListener() {
			
			@Override
			public void selected(SelectEvent sE) {
				
				if (sE.getEventAction().equals(SelectEvent.LEFT_PRESS)) {
					dbpedia.removeAllRenderables();
					((GraphPanel) getMainFrame().getGraphPanel()).clearGraph();
					Position currentPosition = wwd.getCurrentPosition();
					Object selected = sE.getTopObject();
					System.out.println(selected.getClass().toString());
					if (selected.getClass().toString().compareTo("class gov.nasa.worldwind.render.Cylinder") == 0) {
						Cylinder tempSelected = (Cylinder) selected;
						String caption = (String) tempSelected.getValue(AVKey.DISPLAY_NAME);
						String[] arrCaption = caption.split("\\_");
						ResultSet rs = ((FilterPanel) getMainFrame().getFilterPanel()).getFacade().getExternalData(currentPosition.getLatitude().getDegrees(), currentPosition.getLongitude().getDegrees());

						try {
							addDBpediaToGlobe(rs, arrCaption[0]);
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}
			}
		});
	}	
	
	/**
	 * Add the dbpedia entries to the globe
	 * @param rs ResultSet containing the dbpedia entries
	 */
	public void addDBpediaToGlobe(ResultSet rs, String caption) {
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			((GraphPanel) mainFrame.getGraphPanel()).updateGraph(soln, caption, 0);
			String latitude = soln.getLiteral("?lat").getValue().toString();
			String longitude = soln.getLiteral("?long").getValue().toString();
			String displayText = soln.getLiteral("?label").getValue().toString();
			
			if (soln.get("?population") != null) {
				displayText = displayText + newline + "Population: " + soln.getLiteral("?population").getValue().toString();
			}
			
			PointPlacemark pp = new PointPlacemark(Position.fromDegrees(Double.parseDouble(latitude), Double.parseDouble(longitude), 0));
	        pp.setValue(AVKey.DISPLAY_NAME, displayText);
	        pp.setLineEnabled(false);
	        pp.setAltitudeMode(WorldWind.CLAMP_TO_GROUND);
	        PointPlacemarkAttributes attrs = new PointPlacemarkAttributes();
	        attrs.setImageAddress("dbpedia.jpeg");
	        attrs.setImageColor(new Color(1f, 1f, 1f, 1f));
	        attrs.setScale(1.0);
	        attrs.setLabelOffset(new Offset(0.9d, 0.6d, AVKey.FRACTION, AVKey.FRACTION));
	        pp.setAttributes(attrs);
	        dbpedia.addRenderable(pp);
		}
	}
	
	/**
	 * Remove all renderables from wwd.
	 */
	public void clearGlobe() {
		layer.removeAllRenderables();
		dbpedia.removeAllRenderables();
		wwd.redrawNow();
	}
	
	/**
	 * Adds a new observation as a renderable to the wwd.
	 * @param observation
	 * @param foi
	 * @param viz
	 */
	public void updateGlobe(SOSObservation observation, SOSFeatureOfInterest foi, Properties property, String selectedFOI, int color, Boolean directRedraw) {

		if (observation != null) {
			Double defaultHeight = 10.0;
			Double defaultRadius = 10.0;
			Double val = null;
			String toolTipText = "";
			String toolTipProperty = "";
			String toolTipValue = "";
			String toolTipUom = "";
			String toolTipSamplingTime = "";
			Cylinder existingCylinder = null;
			
			Iterable<Renderable> renderables = layer.getRenderables();
			Iterator<Renderable> iter = renderables.iterator();
			while (iter.hasNext()) {
				Cylinder cylinderToCheck = (Cylinder) iter.next();
				String displayName = (String) cylinderToCheck.getValue(AVKey.DISPLAY_NAME);
				String[] arrName = selectedFOI.split("\\-");
				if (displayName.contains(arrName[1])) {
					System.out.println("found a renderable with the same name");
					existingCylinder = cylinderToCheck;
				}
			}
			
			
			ArrayList<SOSSensorOutput> aSo = observation.getSensorOutput();
			for (SOSSensorOutput sosSensorOutput : aSo) {
				toolTipSamplingTime = toolTipSamplingTime + sosSensorOutput.getSamplingTime();
				ArrayList<SOSValue> asVal = sosSensorOutput.getValue();
				for (SOSValue sosValue : asVal) {
					System.out.println(sosValue.getForProperty().getUri().toString());
					System.out.println(property.getProperty().getUri().toString().toLowerCase());
					if (sosValue.getForProperty().getUri().toString().toLowerCase().compareTo(property.getProperty().getUri().toString().toLowerCase()) == 0) {
						toolTipProperty = toolTipProperty + selectedFOI;
						toolTipValue = toolTipValue + sosValue.getHasValue();
						val = sosValue.getHasValue();
						toolTipUom = toolTipUom + sosValue.getForProperty().getUom();
					}
				}
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
			if (existingCylinder != null) {
				
				toolTipText = existingCylinder.getValue(AVKey.DISPLAY_NAME)+ newline + toolTipProperty + newline + toolTipSamplingTime + newline + toolTipValue + " " + toolTipUom + newline + newline + "Click to see what is around!";
				System.out.println("test");
		        if (property.getVisualization().toLowerCase().compareTo("width") == 0) {
		        	
		        	if (val < 0) {
						val = val * -1;
					
					}
		        	
		        	defaultRadius = defaultRadius*val;
		        	
		        	Cylinder tempCylinder = existingCylinder;
		        	attrs = tempCylinder.getActiveAttributes();
		        	
		        	String tempHeight = tempCylinder.getValue(AVKey.HEIGHT).toString();
		        	Double height = Double.parseDouble(tempHeight);
		        	
		        	existingCylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), height, defaultRadius);
		        	layer.removeRenderable(tempCylinder);
				} else if (property.getVisualization().toLowerCase().compareTo("height") == 0) {
					defaultHeight = defaultHeight*val;
					if (defaultHeight < 0) {
						defaultHeight = defaultHeight * -1;
					}
					Cylinder tempCylinder = existingCylinder;
		        	attrs = tempCylinder.getActiveAttributes();
		        	
		        	String tempWidth = tempCylinder.getValue(AVKey.WIDTH).toString();
		        	Double width = Double.parseDouble(tempWidth);
		        	
		        	existingCylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, width);
		        	layer.removeRenderable(tempCylinder);
				} else if (property.getVisualization().toLowerCase().compareTo("color") == 0) {
					if (color != -1) {
						attrs.setInteriorMaterial(new Material(new Color(color)));
					} else {
						attrs.setInteriorMaterial(new Material(property.getColors()[0]));
					}
				}
		        
		        existingCylinder.setAttributes(attrs);
		        existingCylinder.setVisible(true);
		        existingCylinder.setValue(AVKey.DISPLAY_NAME, toolTipText);
		        layer.addRenderable(existingCylinder);
			} else if(val != 0.0) { //if no cylinder exists create a new one
				Cylinder cylinder = null;
				
				toolTipText = toolTipProperty + newline + toolTipSamplingTime + newline + toolTipValue + " " + toolTipUom + newline + newline + "Click to see what is around!";
				
		        if (property.getVisualization().toLowerCase().compareTo("width") == 0) {
		        	if (val < 0) {
						val = val * -1;
					}
		        	
		        	defaultRadius = defaultRadius*val;
		        	
		        	cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, defaultRadius);
		        	cylinder.setValue(AVKey.HEIGHT, defaultHeight);
					cylinder.setValue(AVKey.WIDTH, defaultRadius);
				} else if (property.getVisualization().toLowerCase().compareTo("height") == 0) {
					defaultHeight = defaultHeight*val;
					if (defaultHeight < 0) {
						defaultHeight = defaultHeight * -1;
					}
					cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, 1000);
					cylinder.setValue(AVKey.HEIGHT, defaultHeight);
					cylinder.setValue(AVKey.WIDTH, 1000);
				} else if (property.getVisualization().toLowerCase().compareTo("color") == 0) {
					if (color != -1) {
						attrs.setInteriorMaterial(new Material(new Color(color)));
					} else {
						attrs.setInteriorMaterial(new Material(property.getColors()[0]));
					}
					cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), 1000, 1000);
					cylinder.setValue(AVKey.HEIGHT, 1000);
					cylinder.setValue(AVKey.WIDTH, 1000);
				}
		        
		        cylinder.setAltitudeMode(WorldWind.ABSOLUTE);
		        cylinder.setAttributes(attrs);
		        cylinder.setVisible(true);
		        cylinder.setValue(AVKey.DISPLAY_NAME, toolTipText);
		        layer.addRenderable(cylinder);
			} else { //if there is no observation just add a different geometry
	        	toolTipText = toolTipText + "No measurements available!";
	       
	        	Cone cone = new Cone(Position.fromDegrees(lat, lon, 0), 1000, 1000);
	        	
	        	cone.setAltitudeMode(WorldWind.ABSOLUTE);
	        	cone.setAttributes(attrs);
	        	cone.setVisible(true);
	        	cone.setValue(AVKey.DISPLAY_NAME, toolTipText);
		        layer.addRenderable(cone);
	        }
		} else {
			Iterable<Renderable> renderables = layer.getRenderables();
			Iterator<Renderable> iter = renderables.iterator();
			while (iter.hasNext()) {
				Cylinder temp = (Cylinder) iter.next();
				String displayName = (String) temp.getValue(AVKey.DISPLAY_NAME);
				if (displayName.toLowerCase().contains(selectedFOI.toLowerCase())) {
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
		
		if (directRedraw) {
			wwd.redrawNow();
		}
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
	
	public void redrawGlobe(){
		wwd.redrawNow();
	}
}