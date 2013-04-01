package de.ifgi.europa.constants;

public class Constants {

	/**
	 * Address to the SPARQL Endpoint 
	 */
	public static String Standard_Endpoint = "http://giv-siidemo.uni-muenster.de:8081/parliament/sparql";
	
	/**
	 * Returns all PROPERTIES from a SPARQL ENDPOINT.
	 */
	public static String SPARQL_getListOfProperties = "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +  
													  "SELECT ?object "  + 
													  "WHERE {  " + 
													  "    ?subject a purl:Stimulus . " + 
													  "    ?subject purl:isDescribedBy ?object . " + 
													  "	    FILTER( " + 
													  "	        !EXISTS { " + 
													  "	                ?subject purl:isDescribedBy <http://www.opengis.net/def/property/OGC/0/SamplingTime>. " + 
													  "	            } " + 
													  "	           )}";  
	/**
	 * Returns the min and max dates for a given property.
	 * INPUT: a property (hyd:PARAM_PROPERTY)
	 */
	public static String SPARQL_getPropertyInterval  =  "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#>  " +
														"PREFIX my: <http://ifgi.uni-muenster.de/hydrolod#> " +
														"PREFIX hyd:  <http://ifgi.uni-muenster.de/hydrolodVocabulary#> " +
														"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>  " + 
														"SELECT (MIN(?value) AS ?min) (MAX(?value) AS ?max) " +
														"WHERE{ " +
														"    ?property purl:isDescribedBy hyd:PARAM_PROPERTY . " +
														"    ?property a purl:Property . " +
														"    ?obsValue a purl:ObservationValue  . " +
														"    ?obsValue purl:forProperty ?property . " +
														"    ?obsValue purl:hasValue ?value .}";
	
	/**
	 * Returns the last observation available for a given property.
	 * INPUT: a property (hyd:PARAM_PROPERTY)
	 */
	public static String SPARQL_getLastObservation  =   "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +
														"PREFIX my: <http://ifgi.uni-muenster.de/hydrolod#> " +
														"PREFIX hyd:  <http://ifgi.uni-muenster.de/hydrolodVocabulary#> " +
														"PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>  " +
														"SELECT ?samplingTime ?value  " +
														"WHERE{ " +
														"    ?property purl:isDescribedBy hyd:PARAM_PROPERTY . " +
														"    ?property a purl:Property . " +
														"    ?obsValue a purl:ObservationValue . " +
														"    ?obsValue purl:forProperty ?property . " +
														"    ?obsValue purl:hasValue ?value . " +
														"    ?sensorOutput a purl:SensorOutput . " +
														"    ?sensorOutput purl:hasValue ?obsValue . " +
														"    ?sensorOutput purl:observationSamplingTime ?samplingTime . " +
														"    } " +
														"ORDER BY DESC (?samplingTime) " +
														"LIMIT 1";

}
