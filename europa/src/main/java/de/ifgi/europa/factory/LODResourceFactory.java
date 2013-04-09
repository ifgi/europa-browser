package de.ifgi.europa.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.constants.Constants.ObjectTypes;
import de.ifgi.europa.core.Fact;
import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSPoint;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensing;
import de.ifgi.europa.core.SOSSensor;
import de.ifgi.europa.core.SOSSensorOutput;

public class LODResourceFactory {



	/**
	 * Creates an object with all their attribute values
	 * @param uri URI of the object
	 * @return
	 */
	public LODResource create(URI uri){

		//TODO: Send to constants
		String templateTypeof = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
		//TODO: Send to constants - FOI
		String templateId = "http://www.w3.org/1999/02/22-rdf-syntax-ns#ID"; 
		String templateIdentifier = "http://dublincore.org/documents/dcmi-type-vocabulary/#identifier";
		String templateName = "http://xmlns.com/foaf/spec/#name";
		String templateLabel = "http://www.w3.org/2000/01/rdf-schema#label"; 
		String templateDefaultGeometry = "http://www.opengis.net/ont/geosparql#defaultGeometry";
		//TODO: Send to constants - OBSERVATION
		String templateStartTime = "http://purl.oclc.org/NET/ssnx/ssn#startTime";
		String templateEndTime = "http://purl.oclc.org/NET/ssnx/ssn#endTime";
		String templateFeatureOfInterest = "http://purl.oclc.org/NET/ssnx/ssn#featureOfInterest";
		String templateProperty = "http://purl.oclc.org/NET/ssnx/ssn#observedProperty";
		String templateSensor = "http://purl.oclc.org/NET/ssnx/ssn#observedBy";
		String templateSensing = "http://purl.oclc.org/NET/ssnx/ssn#sensingMethodUsed";
		String templateSensorOutput = "http://purl.oclc.org/NET/ssnx/ssn#observationResult";



		LODResource res = null;
		ObjectTypes objType = null;
		JenaConnector cnn = new JenaConnector(Constants.SII_Lecture_Endpoint);
		Fact fact = cnn.getFact(uri);
		//Find the TYPEOF property
		String p;
		String o;
		for (int i=0; i<fact.getSize(); i++){
			p = fact.getPredicate(i);
			if(p.equals(templateTypeof)){
				o = fact.getObject(i);
				objType = this.getObjectType(o);
				break;
			}
		}
		//Create the object depending on the type
		String label = null;
		switch (objType) {
		case FEATUREOFINTEREST:
			long id = -1; 
			String identifier = null;
			String name = null;
			SOSPoint defaultGeometry = null;
			for (int i=0; i<fact.getSize(); i++){
				p = fact.getPredicate(i);
				o = fact.getObject(i);
				if(p.equals(templateId)){
					id = Long.parseLong(o, 10);
				}else if(p.equals(templateIdentifier)){
					identifier = o;
				}else if(p.equals(templateName)){
					name = o;
				}else if(p.equals(templateLabel)){
					label = o;
				}else if(p.equals(templateDefaultGeometry)){
					try {
						defaultGeometry = new SOSPoint(new URI(o));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			SOSFeatureOfInterest sfoi = new SOSFeatureOfInterest(id, uri, identifier, name, label, defaultGeometry);
			res = sfoi;
			break;
		case OBSERVATION:
			Date startTime = null;
			Date endTime = null;
			SOSFeatureOfInterest featureOfInterest = null;
			ArrayList<SOSProperty> property = new ArrayList<SOSProperty>();
			SOSSensor sensor = null;
			SOSSensing sensing = null;
			ArrayList<SOSSensorOutput> sensorOutput = new ArrayList<SOSSensorOutput>();
			for (int i=0; i<fact.getSize(); i++){
				p = fact.getPredicate(i);
				o = fact.getObject(i);
				if(p.equals(templateStartTime)){
					try {
						startTime = new SimpleDateFormat(Constants.DATE_Format, Locale.ENGLISH).parse(o);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateEndTime)){
					try {
						endTime = new SimpleDateFormat(Constants.DATE_Format, Locale.ENGLISH).parse(o);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateFeatureOfInterest)){
					try {
						featureOfInterest = new SOSFeatureOfInterest(new URI(o));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateProperty)){
					try {
						SOSProperty prop = new SOSProperty(new URI(o));
						property.add(prop);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateSensor)){
					try {
						sensor = new SOSSensor(new URI(o));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateSensing)){
					try {
						sensing = new SOSSensing(new URI(o));
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateSensorOutput)){

					try {
						SOSSensorOutput sOutput = new SOSSensorOutput(new URI(o));
						sensorOutput.add(sOutput);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}else if(p.equals(templateLabel)){
					label = o;
				}
			}
			SOSObservation so = new SOSObservation(uri, startTime,endTime,featureOfInterest,property,sensor,sensing,sensorOutput,label);
			res = so;
			break;
		case POINT:
			break;			
		case PROPERTY:
			break;			
		case SENSING:
			break;			
		case SENSOR:
			break;
		case SENSOROUTPUT:
			break;			
		case STIMULUS:
			break;			
		case VALUE:
			break;
		default:
			break;
		}
		return res;
	}


	/**
	 * Finds the kind of object given the type
	 * @param type
	 * @return
	 */
	private ObjectTypes getObjectType(String type){
		ObjectTypes res = ObjectTypes.UNKNOWN;

		Map<String,String> map = Constants.ONTOLOGY_OBJECTS_MAPPING;
		//Gets the object type as string from the mapping
		String tmpObjectType = null;
		for (String key : map.keySet()) {
			String value = map.get(key);
		    if(value.equals(type)){
		    	tmpObjectType = key;
		    	break;
		    }
		}
		//Gets the enumeration element from the object type
		for (ObjectTypes t : ObjectTypes.values()) {
			if(tmpObjectType.equals(t.toString())){
				res = t;
				break;
			}
		}		
		return res;
	}

	/**
	 * Creates a new object with attribute values filled
	 * @param lr An object whose values could be missing
	 * @return An object whose attribute values are filled
	 */
	public LODResource fill(LODResource lr){

		//
		//URI uri = lr.getUri();



		//Find the type of object

		if(lr instanceof SOSFeatureOfInterest){
			//	SOSFeatureOfInterest sfoi = 
		}


		return null;
	}

}
