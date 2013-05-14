package de.ifgi.europa.gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.globes.Earth;
import gov.nasa.worldwind.globes.Globe;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.layers.ScalebarLayer;
import gov.nasa.worldwind.layers.StarsLayer;
import gov.nasa.worldwind.layers.ViewControlsLayer;
import gov.nasa.worldwind.layers.ViewControlsSelectListener;
import gov.nasa.worldwind.layers.Earth.BMNGWMSLayer;
import gov.nasa.worldwind.layers.Earth.LandsatI3WMSLayer;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.BasicMarkerShape;
import gov.nasa.worldwind.render.markers.Marker;

import javax.swing.JPanel;

import de.ifgi.europa.core.SOSObservation;

public class MapPanel extends JPanel {

	private MainFrame mainFrame;
	final MarkerLayer layer = new MarkerLayer();
	final LayerList ll = new LayerList();
	final WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
	
	public MapPanel(MainFrame mF) {
		super(new GridLayout(1, 1));
		this.setMainFrame(mF);
		
        layer.setOverrideMarkerElevation(true);
        layer.setKeepSeparated(false);
        layer.setElevation(0d);
        
        AnnotationWindowLayer awl = new AnnotationWindowLayer(wwd);
        AnnotationWindow tip = new AnnotationWindow(Position.fromDegrees(0, 0, 0), wwd);
        tip.setPanel(new awTip());
        awl.addWindow(tip);
        
		Layer[] layers = new Layer[]
        {
            new StarsLayer(),
            new CompassLayer(),
            new BMNGWMSLayer(),
            new LandsatI3WMSLayer(),
            layer,
            awl,
            new ScalebarLayer(),
        };
		
		Globe earth = new Earth();
		BasicModel modelForWindowA = new BasicModel();
		modelForWindowA.setGlobe(earth);
		modelForWindowA.setLayers(new LayerList(layers));
        wwd.setPreferredSize(new java.awt.Dimension(800, 600));
        wwd.setModel(modelForWindowA);
        ViewControlsLayer viewControlsA = new ViewControlsLayer();
        wwd.getModel().getLayers().add(viewControlsA);
        wwd.addSelectListener(new ViewControlsSelectListener(wwd, viewControlsA));
        
        
        this.add(wwd, java.awt.BorderLayout.CENTER);
        
        wwd.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
	}
	
	public void updateGlobe(SOSObservation observation) {
		String wkt = observation.getFeatureOfInterest().getDefaultGeometry().getAsWKT();
		String[] splitArray = wkt.split("\\s+");
		String[] splitArrayLat = splitArray[0].split("\\(");
		String[] splitArrayLon = splitArray[1].split("\\)");
		Double lat = Double.parseDouble(splitArrayLat[1]);
		Double lon = Double.parseDouble(splitArrayLon[0]);
		ArrayList<Marker> markers = new ArrayList<Marker>();
        Marker marker = new BasicMarker(Position.fromDegrees(Double.parseDouble(splitArrayLat[1]), Double.parseDouble(splitArrayLon[0]), 0), new BasicMarkerAttributes(Material.YELLOW, BasicMarkerShape.ORIENTED_CYLINDER_LINE, 0.9));
        marker.setPosition(Position.fromDegrees(lat, lon, 0));
        marker.setHeading(Angle.fromDegrees(52 * 5));
        markers.add(marker);
        layer.setMarkers(markers);
        wwd.redrawNow();
	}

	public MainFrame getMainFrame() {
		return mainFrame;
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

}
