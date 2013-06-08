package de.ifgi.europa.comm;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import de.ifgi.europa.core.Fact;
import de.ifgi.europa.core.FactCollection;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.settings.GlobalSettings;
import de.ifgi.europa.settings.Util;

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
	

	/**
	 * Generic method to execute SPARQL Queries.
	 * 
	 * @author jones
	 * @param SPARQL Query
	 * @return ResultSet
	 */
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
	 * This a set of facts.
	 * 
	 * @deprecated 
	 * @return 
	 * @throws URISyntaxException
	 * @author Alber Sanchez 
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

	
	/**
	 * This queries the properties in a graph in a sparql endpoint. See http://www.w3.org/2005/Incubator/ssn/ssnx/ssn#Property
	 * 
	 * @return The properties found in a sparql endpoint
	 * @throws URISyntaxException
	 */
	public ArrayList<SOSProperty> getListOfProperties() throws URISyntaxException{

		Query query = QueryFactory.create(GlobalSettings.SPARQL_getListOfProperties);
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
	 * This builds facts form the RDF triples about the given subject returned by the sparql endpoint
	 *
	 * @deprecated  
	 * @param subject It's the RDF triple subject
	 * @return A fact
	 * @author Alber Sanchez
	 */
	
	public Fact getFact(URI subject){

		String templateQuery = GlobalSettings.SPARQL_ListSubjectElements;
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
	 *This represents a RDF triple.
	 * 
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
		 * This adds a predicate to the triple
		 * 
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
		 * This adds a predicate to the triple
		 * 
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
		 * This export the triple as a Fact
		 *
		 * @deprecated
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