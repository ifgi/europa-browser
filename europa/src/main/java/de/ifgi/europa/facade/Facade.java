package de.ifgi.europa.facade;

import java.net.URI;
import java.util.ArrayList;

import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSPoint;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.factory.LODResourceFactory;

/**
 * Class responsible for providing the GUI all system functionalities.
 * 
 * @author jones
 * @param args
 * @version 1.0
 * 
 */

public class Facade {

	private static Facade instance;
	private LODResourceFactory factory = new LODResourceFactory();

	public Facade(){

	}


//	public LODResource getResourceAttributes(URI uri){
//
//		return factory.create(uri);
//		
//	}

	
	
	
	
	
	
	
	

	
	/**
	 * @author jones
	 * @return
	 */
	public ArrayList<SOSProperty> listProperties(){
		
		return factory.getListProperties();
		
	}
	
	
	/**
	 * Singleton for getting a facade object instance.
	 * @author jones
	 * @return Facade
	 */
	
	public static Facade getInstance() {
		if (instance == null) {
			instance = new Facade();
		}
		return instance;
	}
		
	/**
	 * Retrieves all graphs available in the triple store.
	 * 
	 * @author jones
	 * @param property
	 * @return ArrayList<URI>
	 */

	public ArrayList<URI> getListGraphs(URI endpoint){
		
		return factory.getListGraphs(endpoint);
		
	}		
	
	/**
	 * Retrieves an array list of features of interest (SOSFeatureOfInterest) for a given property (SOSProperty)
	 * 
	 * @author jones
	 * @param SOSProperty 
	 * @return ArrayList<SOSFeatureOfInterest>
	 */
	
	public ArrayList<SOSFeatureOfInterest> listFeaturesOfInterest(SOSProperty property){
		
		return factory.listFeaturesOfInterest(property);
		
	}
	
	/**
	 * Retrieves the last observation for a given feature of interest (SOSFeatureOfInterest)
	 * 
	 * @author jones
	 * @param SOSFeatureOfInterest
	 * @return SOSObservation
	 * 
	 */
	
	public SOSObservation getFOILastObservation(SOSFeatureOfInterest featureOfInterest){
		
		return factory.getFOILastObservation(featureOfInterest);
		
	}
	
	
	/**
	 * Retrieves a list of observations related to a given feature of interest (SOSFeatureOfInterest), 
	 * constrained by a time interval (TimeInterval).
	 * 
	 * @author jones
	 * @param featureOfInterest
	 * @param SOSFeatureOfInterest TimeInterval
	 * @return ArrayList<SOSObservation>
	 */
	
	public ArrayList<SOSObservation> getObservationByInterval(SOSFeatureOfInterest featureOfInterest, TimeInterval interval){
		
		return factory.getObservationTimeInterval(featureOfInterest, interval);
		
	}
	
}

