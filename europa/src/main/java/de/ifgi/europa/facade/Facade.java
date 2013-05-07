package de.ifgi.europa.facade;

import java.net.URI;
import java.util.ArrayList;

import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSPoint;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.factory.LODResourceFactory;

/**
 * @param args
 * @author jones
 * @version 1.0
 */

public class Facade {

	private static Facade instance;
	private LODResourceFactory factory;

	public Facade(){
		initMethods();
	}

	public static Facade getInstance() {
		if (instance == null) {
			instance = new Facade();
		}
		return instance;
	}

	private void initMethods() {
		factory = new LODResourceFactory(); 
	}

	public LODResource getResourceAttributes(URI uri){

		return factory.create(uri);
	}
	
	public ArrayList<SOSProperty> listProperties(){
		
		return factory.listAvailableProperties();
		
	}

	public ArrayList<SOSFeatureOfInterest> listFeaturesOfInterest(SOSProperty property){
		
		return factory.listFeaturesOfInterest(property);
		
	}
	
	public SOSObservation getFOILastObservation(SOSFeatureOfInterest featureOfInterest){
		
		return factory.getFOILastObservation(featureOfInterest);
		
	}
	
}

