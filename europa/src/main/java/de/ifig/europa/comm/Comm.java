package de.ifig.europa.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.ifgi.europa.constants.Constants;
import de.ifig.europa.core.SOSObservation;
import de.ifig.europa.core.TimePeriod;

import javax.swing.text.Position;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.RDFNode;

public class Comm {
	String URL;
	


	/**
	 * This function connects to the SPARQL Endpoint and lists all properties (Stimulus) available . 
	 * 
	 * @param URL
	 * @return
	 */

	public ArrayList<String> getListOfProperties(){
		Query query = QueryFactory.create(Constants.SPARQL_getStimulus);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.SPARQL_Endpoint, query);
		ArrayList<String> result = new ArrayList<String>();
        ResultSet results = qexec.execSelect();

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            result.add(soln.get("?object").toString());
            System.out.println(soln.get("?object"));                                                
        }
        
        qexec.close();
        
		return result;
		
	}

	public TimePeriod getTimePeriod(String property) throws ParseException{
		
		String SPARQL = new String();
		SPARQL = Constants.SPARQL_getPropertyTimeRange.replace("PARAM_PROPERTY", property);		
		Query query = QueryFactory.create(SPARQL);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.SPARQL_Endpoint, query);

		ResultSet results = qexec.execSelect();
        QuerySolution soln = results.nextSolution();

        TimePeriod timePeriod = new TimePeriod(soln.get("min"), soln.get("max"));
        
        qexec.close();
		
		return timePeriod;
	}
	
	public ArrayList<Date> getPropertyIntervall(String property){
		
		return null;		
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
			Position lowerLeft, Position upperRight, String aggregationMethod){
				
		
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