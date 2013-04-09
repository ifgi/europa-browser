package de.ifgi.europa.constants;

public class Constants {

	/**
	 * Address to sample SPARQL Endpoints 
	 */
	public static String SII_Lecture_Endpoint = "http://giv-siidemo.uni-muenster.de:8081/parliament/sparql";
	public static String LinkedScience_Endpoint = "http://spatial.linkedscience.org/sparql";
	
	/**
	 * Returns all PROPERTIES from a SPARQL ENDPOINT.
	 */
	public static String SPARQL_getListOfProperties = "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +  
													  "SELECT ?property ?propertyDescription  "  + 
													  "WHERE {  " + 
													  "    ?property a purl:Property . " + 
													  "     ?property purl:isDescribedBy ?propertyDescription . } "; 
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

	
	public static String SPARQL_Fill_Property = "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#>" +
												"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
												"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
												"SELECT ?foi ?id ?description ?label " + 
												"WHERE { " + 
												"     <PARAM_URI> purl:isPropertyOf ?foi .  " + 
												"     <PARAM_URI> rdf:ID ?id . " + 
												"     <PARAM_URI> purl:isDescribedBy ?description . " + 
												"     <PARAM_URI> rdfs:label ?label .}"; 
	
	
	public static String SPARQL_Fill_Sensor =   "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +
												"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
												"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
												"SELECT ?label ?id ?property ?sensing " + 
												"WHERE { " +
												"     <PARAM_URI> rdfs:label ?label . " +
												"     <PARAM_URI> rdf:ID ?id . " +
												"     <PARAM_URI> purl:observes ?property . " +
												"     <PARAM_URI> purl:implements ?sensing .}";
	
	
	public static String SPARQL_Fill_Sensing =  "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +
												"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
												"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
												"SELECT ?label ?id ?isDescribedBy " + 
												"WHERE { " +
												"     <PARAM_URI> rdfs:label ?label . " +
												"     <PARAM_URI> rdf:ID ?id . " +
												"     <PARAM_URI> purl:isDescribedBy ?isDescribedBy .} ";

	public static String SPARQL_Fill_Observation =  "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +
													"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
													"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
													"SELECT ?label ?id ?observationResult ?sensingMethodUsed ?observedBy ?observedProperty ?featureOfInterest ?startTime ?endTime " +
													"		WHERE{ " +
													"		     <PARAM_URI> rdfs:label ?label . " +
													"		     <PARAM_URI> rdf:ID ?id . " +
													"		     <PARAM_URI> purl:observationResult ?observationResult . " +
													"		     <PARAM_URI> purl:sensingMethodUsed ?sensingMethodUsed . " +
													"		     <PARAM_URI> purl:observedBy ?observedBy . " +
													"		     <PARAM_URI> purl:observedProperty ?observedProperty . " +
													"		     <PARAM_URI> purl:featureOfInterest ?featureOfInterest . " +
													"		     <PARAM_URI> purl:endTime ?endTime . " +
													"		     <PARAM_URI> purl:startTime ?startTime . " +
													"		} ";
	
	
	public static String SPARQL_Fill_Sensor_Output ="PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +
													"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
													"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
													"SELECT ?label ?id ?hasValue ?isProducedBy ?observationSamplingTime " +
													"		WHERE{ " +
													"		     <PARAM_URI> rdfs:label ?label . " +
													"		     <PARAM_URI> rdf:ID ?id . " +
												    " 			 <PARAM_URI> purl:hasValue ?hasValue . " +
													"	         <PARAM_URI> purl:isProducedBy ?isProducedBy . " +
													"	         <PARAM_URI> purl:observationSamplingTime ?observationSamplingTime .} ";
	
	
	public static String SPARQL_Fill_FOI =  "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " + 
											"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
											"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
											"PREFIX foaf: <http://xmlns.com/foaf/spec/#> " +
											"PREFIX geo: <http://www.opengis.net/ont/geosparql#> " +
											"PREFIX dc: <http://dublincore.org/documents/dcmi-type-vocabulary/#> " +
											"SELECT ?label ?id ?name ?defaultGeometry ?identifier " +
											"WHERE{ " +
											"     <PARAM_URI> rdfs:label ?label . " +
											"     <PARAM_URI> rdf:ID ?id . " +
											"     <PARAM_URI> foaf:name ?name . " +
											"     <PARAM_URI> geo:defaultGeometry ?defaultGeometry . " +
											"     <PARAM_URI> dc:identifier ?identifier . " +
											"}"; 
	
	public static String SPARQL_Fill_Observation_Value ="PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " + 
														"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
														"SELECT ?id ?forProperty ?hasValue " +
														"WHERE{ " +
														"     <PARAM_URI> rdf:ID ?id . " +
														"     <PARAM_URI> purl:forProperty ?forProperty . " +
														"     <PARAM_URI> purl:hasValue ?hasValue . " +
														"}";  
	
	public static String SPARQL_Fill_Point ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 
											"PREFIX geo: <http://www.opengis.net/ont/geosparql#> " + 
											"SELECT ?label ?asWKT " + 
											"WHERE{ " + 
											"     <PARAM_URI> rdfs:label ?label . " + 
											"     <PARAM_URI> geo:asWKT ?asWKT .}";
	
	
	public static String SPARQL_ListAvailableGraphs = "SELECT DISTINCT ?graph WHERE { GRAPH ?graph { ?s ?p ?o } }";
	
	public static String SPARQL_ListSubjectElements = "SELECT DISTINCT ?s ?p ?o ?dataType WHERE { <PARAM_URI> ?p ?o .}";
											
	
	public enum FactTypes {
		UNKNOWN,
		FEATUREOFINTEREST,
		OBSERVATION,
		POINT,
		PROPERTY,
		SENSING,
		SENSOR,
		SENSOROUTPUT,
		STIMULUS,
		VALUE
	}
}
