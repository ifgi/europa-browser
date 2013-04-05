package de.ifgi.europa.comm;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;

import javax.swing.text.Position;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class JenaConnector {
	String URL;
	
	/**
	 * This function connects to the SPARQL Endpoint and lists all properties (Stimulus) available .
	 * @return 
	 * @throws URISyntaxException 
	 */
	public ArrayList<SOSProperty> getListOfProperties() throws URISyntaxException{
		
		Query query = QueryFactory.create(Constants.SPARQL_getListOfProperties);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.Standard_Endpoint, query);
		ArrayList<SOSProperty> result = new ArrayList<SOSProperty>();
        ResultSet results = qexec.execSelect();

        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            SOSProperty tmpProperty = new SOSProperty();
            tmpProperty.setUri(new URI(soln.get("?property").toString()));
            //tmpProperty.setDescription(soln.get("?propertyDescription").toString());
            result.add(tmpProperty);
                        
            System.out.println(tmpProperty.getUri());                                                
        }
        
        qexec.close();
        
		return null;
		
	}

	/**
	 * 
	 * @param property
	 * @return
	 * @throws ParseException
	 */
	
	public de.ifgi.europa.core.TimeInterval getPropertyInterval(String property) throws ParseException{
		
		String SPARQL = new String();
		SPARQL = Constants.SPARQL_getPropertyInterval.replace("PARAM_PROPERTY", property);		
		Query query = QueryFactory.create(SPARQL);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.Standard_Endpoint, query);

		ResultSet results = qexec.execSelect();
        QuerySolution soln = results.nextSolution();

        de.ifgi.europa.core.TimeInterval timePeriod = new de.ifgi.europa.core.TimeInterval(soln.get("min"), soln.get("max"));
        
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
	
}