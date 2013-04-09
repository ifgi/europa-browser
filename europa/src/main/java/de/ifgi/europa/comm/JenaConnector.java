package de.ifgi.europa.comm;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.Fact;
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
	String endpointURL;

	public JenaConnector(String url) {
		super();
		endpointURL = url;
	}

	/**
	 * This function connects to the SPARQL Endpoint and lists all properties (Stimulus) available .
	 * @return 
	 * @throws URISyntaxException 
	 */
	public ArrayList<SOSProperty> getListOfProperties() throws URISyntaxException{

		Query query = QueryFactory.create(Constants.SPARQL_getListOfProperties);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);
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
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);

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


	public ArrayList<URI> getListGraphs(){

		Query query = QueryFactory.create(Constants.SPARQL_ListAvailableGraphs);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);

		ArrayList<URI> result = new ArrayList<URI>();

		ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			try {

				result.add(new URI(soln.get("?graph").toString()));
				System.out.println(new URI(soln.get("?graph").toString()));

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		qexec.close();

		return result;	
	}

	public Fact getFact(URI subject){

		String templateQuery = Constants.SPARQL_ListSubjectElements;
		templateQuery = templateQuery.replace("PARAM_URI", subject.toString());
		Query query = QueryFactory.create(templateQuery);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);
		ResultSet results = qexec.execSelect();

		Triple triple = new Triple(subject);

		while (results.hasNext()) {

			QuerySolution soln = results.nextSolution();
			String predicate = soln.get("?p").toString();
			String object = soln.get("?o").toString();
			try {
				URI predicateUri;
				predicateUri = new URI(predicate);
				if(this.isURI(object)){
					URI objectUri = new URI(object);
					triple.add(predicateUri, objectUri);
				}else{
					triple.add(predicateUri, object);
				}
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Fact f = triple.toFact(); 
		return f;
	}

	private boolean isURI(String uri){
		boolean res = true;
		try {
			new URI(uri);
		} catch (URISyntaxException e) {
			res = false;
		}
		return res;
	}



	class Triple{
		private URI subject;
		private ArrayList<String> predicate = new ArrayList<String>();
		private ArrayList<String> object = new ArrayList<String>();
		private ArrayList<String> datatype = new ArrayList<String>();

		public URI getSubject() {
			return subject;
		}

		public void setSubject(URI subject) {
			this.subject = subject;
		}

		Triple(){}

		Triple(URI subject){
			this.subject = subject;
		}

		public void add(URI predicate,String object){
			String tmp[] = object.split("\\^\\^");
			URI datatype = null;
			try {
				if(tmp.length > 1){
					datatype = new URI(tmp[1]);
				}else{
					datatype = null;
				}
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.predicate.add(predicate.toString());
			this.object.add(tmp[0]);

			if(datatype == null){
				this.datatype.add("");
			}else{
				this.datatype.add(datatype.toString());
			}

		}

		public void add(URI predicate,URI object){
			this.predicate.add(predicate.toString());
			this.object.add(object.toString());
			this.datatype.add(null);
		}

		public Fact toFact(){
			Fact res = null;
			try {
				res = new Fact(this.subject.toString(),this.predicate,this.object,this.datatype);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return res;
		}
	}

}