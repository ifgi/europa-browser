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
	
	
	
	public ResultSet getExternalData(double latitude, double longitude){
		
		String SPARQL = GlobalSettings.getExternalData.replace("PARAM_LAT", Double.toString(latitude));
		SPARQL = SPARQL.replace("PARAM_LONG", Double.toString(longitude));
		
        Query query = QueryFactory.create(SPARQL);
        //ARQ.getContext().setTrue(ARQ.useSAX);
 
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://DBpedia.org/sparql", query);

        ResultSet results = qexec.execSelect();
               
        qexec.close();



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
				
		//System.out.println(SPARQL);
		
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
		
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		
		//TODO Delete System.out.println(SPARQL); 
		System.out.println(SPARQL);
		
		ArrayList<SOSSensorOutput> sensorOutput = new ArrayList<SOSSensorOutput>();
		
		SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
		SOSPoint point = new SOSPoint();
		SOSValue value = new SOSValue();
		SOSObservation observation = new SOSObservation();
		SOSSensorOutput output = new SOSSensorOutput();
		

		QuerySolution soln = rs.nextSolution();
		
		point.setAsWKT(soln.get("?wkt").toString());		
		foi.setDefaultGeometry(point);
		
		value.setHasValue(soln.getLiteral("?value").getDouble());			
		output.setValue(value);						
		sensorOutput.add(output);
		
		observation.setSensorOutput(sensorOutput);
		observation.setFeatureOfInterest(foi);					
		output.setSamplingTime(soln.get("?samplingTime").toString());
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

	
	public ArrayList<SOSObservation> getObservationTimeInterval(SOSFeatureOfInterest featureOfInterest, TimeInterval interval){
		
		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		
		String SPARQL = new String();
		SPARQL = GlobalSettings.getObservationsbyTimeInterval.replace("PARAM_DATE1", interval.getStartDate());
		SPARQL = SPARQL.replace("PARAM_DATE2", interval.getEndDate());
		SPARQL = SPARQL.replace("PARAM_FOI", featureOfInterest.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		
		ArrayList<SOSObservation> result = new ArrayList<SOSObservation>();
		
		while (rs.hasNext()) {
			ArrayList<SOSSensorOutput> sensorOutput = new ArrayList<SOSSensorOutput>();
			SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
			SOSPoint point = new SOSPoint();
			SOSValue value = new SOSValue();
			SOSObservation observation = new SOSObservation();
			SOSSensorOutput output = new SOSSensorOutput();

			QuerySolution soln = rs.nextSolution();
			
			point.setAsWKT(soln.get("?wkt").toString());		
			foi.setDefaultGeometry(point);
			
			value.setHasValue(soln.getLiteral("?value").getDouble());			
			output.setValue(value);						
			output.setSamplingTime(soln.getLiteral("?samplingTime").getValue().toString());
			sensorOutput.add(output);
			
			observation.setSensorOutput(sensorOutput);
			observation.setFeatureOfInterest(foi);	
			result.add(observation);
		}

		return result;
	}
	
}
