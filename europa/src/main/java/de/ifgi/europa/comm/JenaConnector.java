package de.ifgi.europa.comm;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.constants.Util;
import de.ifgi.europa.core.Fact;
import de.ifgi.europa.core.FactCollection;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;

import javax.swing.text.Position;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class JenaConnector {
	String endpointURL;
	static Logger  logger = Logger.getLogger("JenaConnector.class");
	

	public ResultSet executeSPARQLQuery(String SPARQL){
		
		Query query = QueryFactory.create(SPARQL);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);
		ResultSet results = qexec.execSelect();

		return results;
		
	}
	
	
	public JenaConnector(String url) {
		super();
		endpointURL = url;
	}

	/**
	 * This is generic
	 * @return 
	 * @throws URISyntaxException
	 * @author Alber Sánchez 
	 */

	public FactCollection executeQuery(String SPARQL){
		FactCollection fc = new FactCollection();
		Query query = QueryFactory.create(SPARQL);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);
		ResultSet results = qexec.execSelect();
		while(results.hasNext()){
			QuerySolution soln = results.nextSolution();
			String subject = soln.get("?s").toString();
			String predicate = soln.get("?p").toString();
			String object = soln.get("?o").toString();
			try {
				URI subjectUri = new URI(subject);
				URI predicateUri = new URI(predicate);
				Triple triple = new Triple(subjectUri);
				//The object could be either a URI or a literal
				if(Util.isURI(object)){
					URI objectUri = new URI(object);
					triple.add(predicateUri, objectUri);
				}else{
					triple.add(predicateUri, object);
				}
				fc.add(triple.toFact());
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fc = new FactCollection(fc.getFactCollection());//Collapses the fact collection
		return fc;
	}

	public ArrayList<SOSProperty> getListOfProperties() throws URISyntaxException{

		Query query = QueryFactory.create(Constants.SPARQL_getListOfProperties);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(this.endpointURL, query);
		ArrayList<SOSProperty> result = new ArrayList<SOSProperty>();
		ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			SOSProperty tmpProperty = new SOSProperty(new URI(soln.get("?property").toString()));
			result.add(tmpProperty);
			//System.out.println(tmpProperty.getUri());                                                
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

	public de.ifgi.europa.core.TimeInterval getPropertyInterval(String property) throws ParseException{

		//TODO: Delete me
		//String SPARQL = new String();
		//SPARQL = Constants.SPARQL_getPropertyInterval.replace("PARAM_PROPERTY", property);		
		//Query querTripley = QueryFactory.create(SPARQL);
		String query = null;
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



	/**
	 * This ask for all the triples with the same subject
	 * @param subject It's the RDF triple subject
	 * @return A fact
	 * @author Alber Sánchez
	 */
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
				//The object could be either a URI or a literal
				if(Util.isURI(object)){
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

	/**
	 * @author Alber Sánchez
	 * 
	 */
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

		/**
		 * @author Alber Sánchez
		 * @param predicate
		 * @param object
		 */
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

		/**
		 * @author Alber Sánchez
		 * @param predicate
		 * @param object
		 */
		public void add(URI predicate,URI object){
			this.predicate.add(predicate.toString());
			this.object.add(object.toString());
			this.datatype.add(null);
		}

		/**
		 * @author Alber Sánchez
		 * @return
		 */
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