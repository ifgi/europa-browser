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

package de.ifgi.europa.comm;

import org.apache.log4j.Logger;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
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
	
	/**
	 * Constructor
	 * @param url An SPARQL endpoint's url
	 * @author jones
	 */
	public JenaConnector(String url) {
		super();
		endpointURL = url;
	}
	
	

}