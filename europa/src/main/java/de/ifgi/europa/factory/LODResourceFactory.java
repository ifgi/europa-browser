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

package de.ifgi.europa.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

import scala.collection.mutable.SynchronizedPriorityQueue;

import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSPoint;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensorOutput;
import de.ifgi.europa.core.SOSValue;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.settings.GlobalSettings;

public class LODResourceFactory {
	
	Logger  logger = Logger.getRootLogger();
	
	public ResultSet getExternalData(double latitude, double longitude){
		
		String SPARQL = GlobalSettings.getExternalData.replace("PARAM_LAT", Double.toString(latitude));
		SPARQL = SPARQL.replace("PARAM_LONG", Double.toString(longitude));
		logger.debug("getExternalData query: " + SPARQL);
		
        Query query = QueryFactory.create(SPARQL);
        ARQ.getContext().setTrue(ARQ.useSAX);
 
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://DBpedia.org/sparql", query);

        ResultSet results = qexec.execSelect();
               
        qexec.close();

        System.out.println(SPARQL);

		return results;
	}
	
	
	
	
	
	public ResultSet getNodeExternalData(String node){
		
		String SPARQL = GlobalSettings.getNodeExternalData.replace("PARAM_SUBJECT", node);
		logger.debug("getNodeExternalData query: " + SPARQL);
		
        Query query = QueryFactory.create(SPARQL);
        ARQ.getContext().setTrue(ARQ.useSAX);
        
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://DBpedia.org/sparql", query);

        ResultSet results = qexec.execSelect();
               
        qexec.close();

        System.out.println(SPARQL);

		return results;
	}
	
	
	
	/**
	 *  Lists all properties available in the current named graph.
	 *  
	 * @author jones
	 * @return ArrayList<SOSProperty>
	 */
	
	public ArrayList<SOSProperty> getListProperties(){
		
		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		String SPARQL = new String();
		
		SPARQL = GlobalSettings.SPARQL_getListProperties.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		logger.debug("getListProperties query: " + SPARQL);
		
		ArrayList<SOSProperty> result = new ArrayList<SOSProperty>();

		ResultSet results = cnn.executeSPARQLQuery(SPARQL);
		
		//System.out.println(SPARQL);
		
		while (results.hasNext()) {
			SOSProperty property = new SOSProperty();
			QuerySolution soln = results.nextSolution();

			try {
				
				property.setUri(new URI(soln.get("?property").toString()));
				
				result.add(property);

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		return result;
	}
	
	
	/**
	 * Retrieves all graphs available in the triple store.
	 * 
	 * @author jones
	 * @param property
	 * @return ArrayList<URI>
	 */

	public ArrayList<URI> getListGraphs(URI endpoint){

		Query query = QueryFactory.create(GlobalSettings.SPARQL_ListAvailableGraphs);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(GlobalSettings.CurrentSPARQLEndpoint, query);

		ArrayList<URI> result = new ArrayList<URI>();

		ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			try {

				result.add(new URI(soln.get("?graph").toString()));

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		qexec.close();

		return result;	
	}
		
	/**
	 * Retrieves an array list of features of interest (SOSFeatureOfInterest) for a given property (SOSProperty)
	 * 
	 * @author jones
	 * @param SOSProperty 
	 * @return ArrayList<SOSFeatureOfInterest>
	 */
	
	public ArrayList<SOSFeatureOfInterest> listFeaturesOfInterest(SOSProperty property){

		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		
		String SPARQL = new String();
		SPARQL = GlobalSettings.listFeaturesOfInterest.replace("PARAM_PROPERTY", property.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		logger.debug("listFeaturesOfInterest query: " + SPARQL);
		
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		ArrayList<SOSFeatureOfInterest> result = new ArrayList<SOSFeatureOfInterest>();
		

		while (rs.hasNext()) {
			SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
			
			QuerySolution soln = rs.nextSolution();
			foi.setUri(URI.create(soln.get("?foi").toString()));						
			
			result.add(foi);
		}

		return result;
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

		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		String SPARQL = new String();
		SPARQL = GlobalSettings.geFOILastObservation.replace("PARAM_FOI", featureOfInterest.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		logger.debug("getFOILastObservation query: " + SPARQL);
		
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		
		
		ArrayList<SOSSensorOutput> sensorOutputArray = new ArrayList<SOSSensorOutput>();
		ArrayList<SOSValue> valueArray = new ArrayList<SOSValue>();
		ArrayList<SOSProperty> propArray = new ArrayList<SOSProperty>(); 
		SOSObservation observation = new SOSObservation();
		SOSSensorOutput output = new SOSSensorOutput();
		SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
		SOSPoint point = new SOSPoint();

		int counter = 0;
		
		//Assuming only one DataArray per observation
		//Assuming only one SensorOurput can be the latest one
		while(rs.hasNext()){
			QuerySolution soln = rs.nextSolution();	

			SOSValue value = new SOSValue();
			SOSProperty prop = new SOSProperty();

			if(counter == 0){
				point.setAsWKT(soln.get("?wkt").toString());
				output.setSamplingTime(soln.get("?samplingTime").toString());
			}
			
			prop.setUri(URI.create(soln.get("?prop").toString()));
			prop.setUom(soln.get("?uom").toString());
			propArray.add(prop);
			
			value.setHasValue(soln.getLiteral("?value").getDouble());
			value.setForProperty(prop);
			
			valueArray.add(value);

			counter++;
		}
		
		foi.setDefaultGeometry(point);
		
		output.setValue(valueArray);
		sensorOutputArray.add(output);
		
		observation.setFeatureOfInterest(foi);
		observation.setProperty(propArray);
		observation.setSensorOutput(sensorOutputArray);

		return observation;		
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
	public SOSObservation getObservationTimeInterval(SOSFeatureOfInterest featureOfInterest, TimeInterval interval){
		
		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		
		String SPARQL = new String();
		SPARQL = GlobalSettings.getObservationsbyTimeInterval.replace("PARAM_DATE1", interval.getStartDate());
		SPARQL = SPARQL.replace("PARAM_DATE2", interval.getEndDate());
		SPARQL = SPARQL.replace("PARAM_FOI", featureOfInterest.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		logger.debug("getObservationTimeInterval query: " + SPARQL);
		
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		
		System.out.println(SPARQL);
		
		ArrayList<SOSSensorOutput> sensorOutputArray = new ArrayList<SOSSensorOutput>();
		ArrayList<SOSValue> valueArray = new ArrayList<SOSValue>(); 
		ArrayList<SOSProperty> propertyArray = new ArrayList<SOSProperty>(); 
		
		SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
		SOSPoint point = new SOSPoint();
		SOSObservation observation = new SOSObservation();
		
		
		int counter = 0;
		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			
			if(counter == 0){
				point.setAsWKT(soln.get("?wkt").toString());
				foi.setDefaultGeometry(point);
			}

			String st = soln.getLiteral("?samplingTime").getValue().toString();
			double val = soln.getLiteral("?value").getDouble();
			String uom = soln.getLiteral("?uom").getValue().toString();
			String propUri = soln.get("?prop").toString();

			SOSProperty prop = findPropertyByUri(propertyArray, propUri);
			if(prop ==  null){
				prop = new SOSProperty();
				prop.setUri(URI.create(propUri));
				prop.setUom(uom);
				prop.addFoi(foi);
			}
			SOSSensorOutput output = findSensorOutputByDate(sensorOutputArray, st);
			boolean soIsThere = true;
			if(output == null){
				soIsThere = false;
				output = new SOSSensorOutput();
				output.setSamplingTime(st);
			}
			
			SOSValue value = new SOSValue();
			value.setHasValue(val);
			value.setForProperty(prop);
			valueArray.add(value);
			
			output.getValue().add(value);
			if(!soIsThere){
				sensorOutputArray.add(output);		
			}
			counter++;
		}

		observation.setSensorOutput(sensorOutputArray);
		observation.setFeatureOfInterest(foi);
		return observation;
	}

	/**
	 * Search for a property in the array using a URI
	 * @param prArray An array of properties.
	 * @param propUri A URI.
	 * @return A property.
	 */
	private SOSProperty findPropertyByUri(ArrayList<SOSProperty> prArray, String propUri){
		SOSProperty res = null;
		for (SOSProperty sosProperty : prArray) {
			if(sosProperty.getUri().toString().equals(propUri)){
				res = sosProperty;
				break;
			}
		}
		return res;
	}
	
	/**
	 * Searchs for a sensor output in the array using a date.
	 * @param soArray Sensor output array.
	 * @param date A date.
	 * @return The found sensor output otherwise null.
	 */
	private SOSSensorOutput findSensorOutputByDate(ArrayList<SOSSensorOutput> soArray, String date){
		SOSSensorOutput res = null;
		for (SOSSensorOutput so : soArray) {
			if(so.getSamplingTime().equals(date)){
				res = so;
				break;
			}
		}
		return res;
	}

	
	
	
}
