package de.ifig.europa.comm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.TimeInterval;

import javax.swing.text.Position;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class Comm {
	String URL;
	
	/**
	 * This function connects to the SPARQL Endpoint and lists all properties (Stimulus) available .
	 * @return 
	 */
	public ArrayList<String> getListOfProperties(){
		Query query = QueryFactory.create(Constants.SPARQL_getListOfProperties);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.Standard_Endpoint, query);
		ArrayList<String> result = new ArrayList<String>();
        ResultSet results = qexec.execSelect();

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            result.add(soln.get("?propertyDescription").toString());
            System.out.println(soln.get("?propertyDescription"));                                                
        }
        
        qexec.close();
        
		return result;
		
	}

	/**
	 * 
	 * @param property
	 * @return
	 * @throws ParseException
	 */
	
	public TimeInterval getPropertyInterval(String property) throws ParseException{
		
		String SPARQL = new String();
		SPARQL = Constants.SPARQL_getPropertyInterval.replace("PARAM_PROPERTY", property);		
		Query query = QueryFactory.create(SPARQL);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.Standard_Endpoint, query);

		ResultSet results = qexec.execSelect();
        QuerySolution soln = results.nextSolution();

        TimeInterval timePeriod = new TimeInterval(soln.get("min"), soln.get("max"));
        
        qexec.close();
		
		return timePeriod;
	}
	

	/**
	 * Lists the properties regarding a certain BBOX and time interval.
	 * 
	 * @param property
	 * @param startTime
	 * @param endTime
	 * @param lowerLeft
	 * @param upperRight
	 * @return
	 * 
	 */
	
	public ArrayList<Date> getPropertyBBOX(String property, Date startTime, Date endTime, Position lowerLeft, Position upperRight){
		
		return null;		
	}

	/**
	 * 
	 * @param property
	 * @param startTime
	 * @param endTime
	 * @param lowerLeft
	 * @param upperRight
	 * @param aggregationMethod
	 * @return
	 */
	public ArrayList<SOSObservation> dataLoader(String property, Date startTime, Date endTime,
			gov.nasa.worldwind.geom.Position lowerLeft, gov.nasa.worldwind.geom.Position upperRight, String aggregationMethod){
				
		
		return null;
		
	}
	
	/**
	 * Gets the last observation of a certain property
	 * 
	 * @param property
	 * @return
	 */
	
	public SOSObservation getLastObservation(String property){
		

		return null;
		
	}
	
}