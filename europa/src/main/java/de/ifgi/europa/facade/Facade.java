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

package de.ifgi.europa.facade;

import java.net.URI;
import java.util.ArrayList;
import com.hp.hpl.jena.query.ResultSet;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
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


	
	
	
	
	

	public ResultSet getExternalData(double latitude, double longitude){
		
		return factory.getExternalData(latitude, longitude);
		
	}

	
	public ResultSet getNodeExternalData(String node){
		
		return factory.getNodeExternalData(node);
		
	}
	
	
	/**
	 * List all properties available of a given named graph
	 * 
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
	
	public SOSObservation getObservationByInterval(SOSFeatureOfInterest featureOfInterest, TimeInterval interval){
		
		return factory.getObservationTimeInterval(featureOfInterest, interval);
		
	}
	
}

