package de.ifgi.europa.constants;

public class Constants {

	/**
	 * Address to the SPARQL Endpoint 
	 */
	public static String SPARQL_Endpoint = "http://giv-siidemo.uni-muenster.de:8081/parliament/sparql";
	public static String SPARQL_getStimulus = "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +  
											  "SELECT ?object "  + 
											  "WHERE {  " + 
											  "    ?subject a purl:Stimulus . " + 
											  "    ?subject purl:isDescribedBy ?object . " + 
											  "	    FILTER( " + 
											  "	        !EXISTS { " + 
											  "	                ?subject purl:isDescribedBy <http://www.opengis.net/def/property/OGC/0/SamplingTime>. " + 
											  "	            } " + 
											  "	        )}";  
	
	public static String SPARQL_getPropertyTimeRange =  "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#>  " +
														"PREFIX my: <http://ifgi.uni-muenster.de/hydrolod#> " +
														"PREFIX hyd:  <http://ifgi.uni-muenster.de/hydrolodVocabulary#> " +
														"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>  " + 
														"SELECT (MIN(?value) AS ?min) (MAX(?value) AS ?max) " +
														"WHERE{ " +
														"    ?stimulus purl:isDescribedBy hyd:PARAM_PROPERTY . " +
														"    ?stimulus a purl:Stimulus . " +
														"   ?stimulus purl:isProxyFor ?property . " +
														"    ?observation purl:observationResult ?results . " +
														"    ?results purl:hasValue ?value . " +
														"    FILTER( " +
														"        datatype(?value ) = xsd:dateTime )}"; 

}
