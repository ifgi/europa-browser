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
import gov.nasa.worldwind.layers.Earth.LandsatI3WMSLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
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
	
	public MapPanel(MainFrame mF) {
		super(new GridLayout(1, 1));
		
		Configuration.setValue(AVKey.INITIAL_LATITUDE, 51);
        Configuration.setValue(AVKey.INITIAL_LONGITUDE, 10);
        Configuration.setValue(AVKey.INITIAL_ALTITUDE, 120e4);
		
		this.setMainFrame(mF);
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
            new StarsLayer(),
            new CompassLayer(),
            new BMNGWMSLayer(),
            new LandsatI3WMSLayer(),
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
					Position currentPosition = wwd.getCurrentPosition();
					Object selected = sE.getTopObject();
					System.out.println(selected.getClass().toString());
					if (selected.getClass().toString().compareTo("class gov.nasa.worldwind.render.Cylinder") == 0) {
						Cylinder tempSelected = (Cylinder) selected;
						String caption = (String) tempSelected.getValue(AVKey.DISPLAY_NAME);
						String[] arrCaption = caption.split("\\#");
						ResultSet rs = ((FilterPanel) getMainFrame().getFilterPanel()).getFacade().getExternalData(currentPosition.getLatitude().getDegrees(), currentPosition.getLongitude().getDegrees());
//						((GraphPanel) mainFrame.getGraphPanel()).updateGraph(rs, arrCaption[1]);
						((GraphPanel) mainFrame.getGraphPanel()).nada();
						try {
							addDBpediaToGlobe(rs);
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
	public void addDBpediaToGlobe(ResultSet rs) {
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			String latitude = soln.getLiteral("?lat").getValue().toString();
			String longitude = soln.getLiteral("?long").getValue().toString();
			
			PointPlacemark pp = new PointPlacemark(Position.fromDegrees(Double.parseDouble(latitude), Double.parseDouble(longitude), 0));
	        pp.setValue(AVKey.DISPLAY_NAME, soln.getLiteral("?label").getValue().toString());
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
	public void updateGlobe(SOSObservation observation, SOSFeatureOfInterest foi, Properties property, String selectedFOI) {

		if (observation != null) {
			Double defaultHeight = 10.0;
			Double defaultRadius = 10.0;
			Double val = 0.0;
			String toolTipText = "";
			String toolTipProperty = "";
			String toolTipValue = "";
			String toolTipUom = "";
			String toolTipSamplingTime = "";
			
			ArrayList<SOSSensorOutput> aSo = observation.getSensorOutput();
			for (SOSSensorOutput sosSensorOutput : aSo) {
				toolTipSamplingTime = toolTipSamplingTime + sosSensorOutput.getSamplingTime();
				ArrayList<SOSValue> asVal = sosSensorOutput.getValue();
				for (SOSValue sosValue : asVal) {
					if (sosValue.getForProperty().getUri().toString().toLowerCase().compareTo(property.getProperty().getUri().toString().toLowerCase()) == 0) {
//						System.out.println("--Prop --> " + sosValue.getForProperty().getUri());
						toolTipProperty = toolTipProperty + selectedFOI;
//						System.out.println("--Value --> " + sosValue.getHasValue());
						toolTipValue = toolTipValue + sosValue.getHasValue();
						val = sosValue.getHasValue();
//						System.out.println("--Uom --> " + sosValue.getForProperty().getUom());
						toolTipUom = toolTipUom + sosValue.getForProperty().getUom();
					}
				}
			}
			
			//Build tooltip text
			toolTipText = toolTipProperty + newline + toolTipSamplingTime + newline + toolTipValue + " " + toolTipUom + newline + newline + "Click to see what is around!";
			
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
	        
	        if (property.getVisualization().compareTo("width") == 0) {
	        	defaultRadius = defaultRadius*val*1000;
	        	if (val < 0) {
					val = val * -1;
				}
	        	cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, val);
			} else if (property.getVisualization().toLowerCase().compareTo("height") == 0) {
				defaultHeight = defaultHeight*val*10;
				if (defaultHeight < 0) {
					defaultHeight = defaultHeight * -1;
				}
				cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), defaultHeight, 10000);
			} else if (property.getVisualization().toLowerCase().compareTo("color") == 0) {
				attrs.setInteriorMaterial(new Material(property.getColors()[0]));
				cylinder = new Cylinder(Position.fromDegrees(lat, lon, 0), 1000, 1000);
			}
	        
	        cylinder.setAltitudeMode(WorldWind.ABSOLUTE);
	        cylinder.setAttributes(attrs);
	        cylinder.setVisible(true);
	        cylinder.setValue(AVKey.DISPLAY_NAME, toolTipText);
	        layer.addRenderable(cylinder);
	        
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