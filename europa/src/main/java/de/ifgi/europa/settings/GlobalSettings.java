package de.ifgi.europa.settings;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GlobalSettings {

	/**
	 * Address to sample SPARQL Endpoints 
	 */
	public static String CurrentSPARQLEndpoint = "";
	public static String CurrentNamedGraph = "";
	//public static String LinkedScience_Endpoint = "http://spatial.linkedscience.org/sparql";

	/**
	 * Lists all graphs available in the triple store.
	 * @author jones
	 */
	
	public static String SPARQL_ListAvailableGraphs = "SELECT DISTINCT ?graph WHERE { GRAPH ?graph { ?s ?p ?o } }";
	
	/**
	 * Standard prefixes used by all SPARQL Queries used in the project
	 * @author jones
	 */
	private static String prefixes = " PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +  
			" PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " + 
			" PREFIX xsd:  <http://www.w3.org/2001/XMLSchema#>" +  
			" PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " + 
			" PREFIX dc:  	<http://dublincore.org/documents/dcmi-type-vocabulary/#>" +  
			" PREFIX foaf: <http://xmlns.com/foaf/spec/#> " + 
			" PREFIX geo:  <http://www.opengis.net/def/geosparql/>" +  
			" PREFIX dbpedia:  <http://dbpedia.org/resource/> " + 
			" PREFIX hyd:  <http://ifgi.uni-muenster.de/hydrolodVocabulary#>" +  
			" PREFIX my:   <http://ifgi.uni-muenster.de/hydrolod#> "; 


	/**
	 * Lists all features of interest for a given SOSParameter
	 * @author jones 
	 * 
	 * */
	
	public static String listFeaturesOfInterest = prefixes +
			" SELECT ?foi  " +
			" WHERE { GRAPH <PARAM_GRAPH> {<PARAM_PROPERTY> purl:isPropertyOf ?foi }} ";

	/**
	 * Retrieves the last observation for a given feature of interest (SOSFeatureOfInterest)
	 * @author jones
	 */
	
	public static String geFOILastObservation = prefixes +
			" SELECT  ?wkt ?value ?samplingTime " + 
			" WHERE { GRAPH <PARAM_GRAPH> { " +
			"   ?property purl:isPropertyOf ?foi . " +
			"   ?property purl:isPropertyOf <PARAM_FOI> ." +
			"   ?observation purl:featureOfInterest ?foi . " +
			"   ?observation purl:observationResult ?sensorOutput . " +
			"   ?observation purl:startTime ?start . " +
			"   ?observation purl:endTime ?end . " +
			"   ?sensorOutput purl:hasValue ?observationValue . " +
			"   ?sensorOutput purl:observationSamplingTime ?samplingTime . " +
			"   ?observationValue purl:hasValue ?value . " +
			"   ?foi geo:defaultGeometry ?point . " +
			"   ?point geo:asWKT ?wkt  . " +
			"} } ORDER BY DESC(?end) LIMIT 1";
	
	
	/**
	 * Retrieves a list of observations related to a given feature of interest (SOSFeatureOfInterest), 
	 * constrained by a time interval (TimeInterval).
	 * @author jones
	 */
	
	public static String getObservationsbyTimeInterval=prefixes+
			" SELECT  ?wkt ?value ?samplingTime " +
			"           WHERE { GRAPH <PARAM_GRAPH> { " +
			"           ?property purl:isPropertyOf ?foi . " +
		    "           ?property purl:isPropertyOf <PARAM_FOI> . " +
		    "           ?observation purl:featureOfInterest ?foi . " +
		    "           ?observation purl:observationResult ?sensorOutput . " +
		    "           ?sensorOutput purl:hasValue ?observationValue . " +
		    "           ?sensorOutput purl:observationSamplingTime ?samplingTime . " +
		    "           ?observationValue purl:hasValue ?value . " +
		    "           ?foi geo:defaultGeometry ?point . " +
		    "           ?point geo:asWKT ?wkt  . " +
		    "           FILTER (xsd:dateTime(?samplingTime) >= \"PARAM_DATE1\"^^xsd:dateTime && " +  
	    	"					xsd:dateTime(?samplingTime) <= \"PARAM_DATE2\"^^xsd:dateTime) .  " +
	    	"} } ORDER BY ?samplingTime ";
	
	
	
	
	/**
	 * Returns all PROPERTIES from a given SPARQL Endpoint and Named Graph.
	 */
	public static String SPARQL_getListProperties = "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +  
													"SELECT ?property " + 
													"WHERE { GRAPH <PARAM_GRAPH> {  " + 
													"?property a purl:Property . " +  
													" } } ORDER BY ?property";

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Returns all PROPERTIES from a SPARQL ENDPOINT.
	 */
	public static String SPARQL_getListOfProperties = "PREFIX purl:  <http://purl.oclc.org/NET/ssnx/ssn#> " +  
			"SELECT ?s ?p  ?o " + 
			"WHERE {  " + 
			"?s ?p ?o . " + 
			"?s a purl:Property . " +  
			"?s purl:isDescribedBy ?o . } ";

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

	public static String SPARQL_getObservation_TimeInterval ="prefix purl:  <http://purl.oclc.org/NET/ssnx/ssn#>  " +
			"prefix my:   <http://ifgi.uni-muenster.de/hydrolod#> " +  
			"SELECT * WHERE { " + 
			"	?output a purl:SensorOutput . " + 
			"    ?output purl:hasValue ?value . " + 
			"    ?output purl:observationSamplingTime ?time . " + 
			"    ?value a purl:ObservationValue . " + 
			"    FILTER (xsd:dateTime(?time) >= \"PARAM_DATE3\"^^xsd:dateTime && " + 
			"            xsd:dateTime(?time) <= \"PARAM_DATE2\"^^xsd:dateTime) . " + 
			"   ?value purl:forProperty ?property . " + 
			"   FILTER (?property = PARAM_PROPERTY1 ||  " + 
			"           ?property = PARAM_PROPERTY2  ||  " + 
			"           ?property = PARAM_PROPERTY3 ) .}";

	
	public static String SPARQL_ListSubjectElements = "SELECT DISTINCT ?s ?p ?o ?dataType WHERE { <PARAM_URI> ?p ?o .}";

	public static String DATE_Format = "yyyy-MM-dd HH:mm:ss";





	public static final Map<String,String> ONTOLOGY_OBJECTS_MAPPING;
	static {
		Map<String, String> aMap = new HashMap<String, String>(); 
		aMap.put("UNKNOWN", "");
		aMap.put("FEATUREOFINTEREST", "http://purl.oclc.org/NET/ssnx/ssn#FeatureOfInterest");
		aMap.put("OBSERVATION", "http://purl.oclc.org/NET/ssnx/ssn#Observation");
		aMap.put("POINT", "http://www.opengis.net/ont/geosparql#Point");
		aMap.put("PROPERTY", "http://purl.oclc.org/NET/ssnx/ssn#Property");
		aMap.put("SENSING", "http://purl.oclc.org/NET/ssnx/ssn#Sensing");
		aMap.put("SENSOR", "http://purl.oclc.org/NET/ssnx/ssn#Sensor");
		aMap.put("SENSOROUTPUT", "http://purl.oclc.org/NET/ssnx/ssn#SensorOutput");
		aMap.put("STIMULUS", "http://purl.oclc.org/NET/ssnx/ssn#Stimulus");
		aMap.put("VALUE", "http://purl.oclc.org/NET/ssnx/ssn#ObservationValue");
		ONTOLOGY_OBJECTS_MAPPING = Collections.unmodifiableMap(aMap);
	}


	public enum ObjectTypes {
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

	//Ontology properties
	public static String PREDICATE_Type = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	public static String PREDICATE_Id = "http://www.w3.org/1999/02/22-rdf-syntax-ns#ID";
	public static String PREDICATE_Identifier = "http://dublincore.org/documents/dcmi-type-vocabulary/#identifier";
	public static String PREDICATE_Name = "http://xmlns.com/foaf/spec/#name";
	public static String PREDICATE_Label = "http://www.w3.org/2000/01/rdf-schema#label";
	public static String PREDICATE_DefaultGeometry = "http://www.opengis.net/ont/geosparql#defaultGeometry";
	public static String PREDICATE_StartTime = "http://purl.oclc.org/NET/ssnx/ssn#startTime";
	public static String PREDICATE_EndTime = "http://purl.oclc.org/NET/ssnx/ssn#endTime";
	public static String PREDICATE_FeatureOfInterest = "http://purl.oclc.org/NET/ssnx/ssn#featureOfInterest";
	public static String PREDICATE_ObservedProperty = "http://purl.oclc.org/NET/ssnx/ssn#observedProperty";
	public static String PREDICATE_ObservedBy = "http://purl.oclc.org/NET/ssnx/ssn#observedBy";
	public static String PREDICATE_SensingMethodUsed = "http://purl.oclc.org/NET/ssnx/ssn#sensingMethodUsed";
	public static String PREDICATE_ObservationResult = "http://purl.oclc.org/NET/ssnx/ssn#observationResult";
	public static String PREDICATE_AsWkt = "http://www.opengis.net/ont/geosparql#asWKT";
	public static String PREDICATE_IsDescribedBy = "http://purl.oclc.org/NET/ssnx/ssn#isDescribedBy";
	public static String PREDICATE_Detects = "http://purl.oclc.org/NET/ssnx/ssn#detects";
	public static String PREDICATE_HasValue = "http://purl.oclc.org/NET/ssnx/ssn#hasValue";


}
