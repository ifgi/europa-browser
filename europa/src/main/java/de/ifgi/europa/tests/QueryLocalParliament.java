package de.ifgi.europa.tests;

import java.text.ParseException;

import com.hp.hpl.jena.query.ARQ;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.ifgi.europa.comm.Comm;
import de.ifgi.europa.constants.Constants;
import de.ifig.europa.core.TimeInterval;

public class QueryLocalParliament {

    public static void main(String[] args) throws ParseException {

        //QueryLocalParliament queryDBpedia = new QueryLocalParliament();
        //queryDBpedia.queryExternalSources();
        
        Comm cnn = new Comm();
        
        TimeInterval tm = cnn.getPropertyInterval("Wassertemperatur");
        System.out.println("Min: " + tm.getStartDate() + "\nMax: " + tm.getEndDate());
        cnn.getListOfProperties();
    }

    public void queryExternalSources() {
        //Defining SPARQL Query. This query lists, in all languages available, the
        //abstract entries on Wikipedia/DBpedia for the planet Mars.
        String sparqlQueryString2 = " SELECT ?s  " +
                                    " WHERE { ?s ?p <http://purl.oclc.org/NET/ssnx/ssn#Sensor> }";

        Query query = QueryFactory.create(sparqlQueryString2);
        ARQ.getContext().setTrue(ARQ.useSAX);
       //Executing SPARQL Query and pointing to the DBpedia SPARQL Endpoint 
        QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.Standard_Endpoint, query);
       //Retrieving the SPARQL Query results
        ResultSet results = qexec.execSelect();
       //Iterating over the SPARQL Query results
        while (results.hasNext()) {
            QuerySolution soln = results.nextSolution();
            //Printing DBpedia entries' abstract.
            System.out.println(soln.get("?s"));                                                
        }
        qexec.close();

    }

}