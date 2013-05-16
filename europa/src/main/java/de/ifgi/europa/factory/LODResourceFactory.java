package de.ifgi.europa.factory;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import cern.colt.matrix.impl.SparseDoubleMatrix1D;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.core.Fact;
import de.ifgi.europa.core.FactCollection;
import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSPoint;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensing;
import de.ifgi.europa.core.SOSSensor;
import de.ifgi.europa.core.SOSSensorOutput;
import de.ifgi.europa.core.SOSStimulus;
import de.ifgi.europa.core.SOSValue;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.settings.GlobalSettings;
import de.ifgi.europa.settings.GlobalSettings.ObjectTypes;

public class LODResourceFactory {
	
	LODResourceCache cache = LODResourceCache.getInstance();

	public ArrayList<SOSProperty> listAvailableProperties() {
		ArrayList<SOSProperty> res = new ArrayList<SOSProperty>();
		
		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		FactCollection fc = cnn.executeQuery(GlobalSettings.SPARQL_getListOfProperties);

		String templateLabel = GlobalSettings.PREDICATE_Label; 
		String templateFeatureOfInterest = GlobalSettings.PREDICATE_FeatureOfInterest;
		String templateDescription = GlobalSettings.PREDICATE_IsDescribedBy;
		
		for(int j=0;j < fc.Size();j++){
			Fact fact = fc.get(j);
			ArrayList<SOSFeatureOfInterest> foi = new ArrayList<SOSFeatureOfInterest>();
			SOSFeatureOfInterest featureOfInterest;
			String p;
			String o;
			String description = null;
			String label = null;
			for (int i=0; i<fact.getSize(); i++){
				p = fact.getPredicate(i);
				o = fact.getObject(i);
				if(p.equals(templateDescription)){
					description = o;
				}else if(p.equals(templateFeatureOfInterest)){
					try {
						featureOfInterest = new SOSFeatureOfInterest(new URI(o));
						foi.add(featureOfInterest);
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(p.equals(templateLabel)){
					label = o;
				}
			}
			
			try {
				URI uri = new URI(fact.getSubject());
				SOSProperty sprop = new SOSProperty(uri, foi, description, label);
				res.add(sprop);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return res;
	}

	/**
	 * Creates an object with all their attribute values
	 * @param uri URI of the object
	 * @return
	 * @author Alber Sanchez
	 */
	@SuppressWarnings("null")
	public LODResource create(URI uri){
		LODResource res = null;
		//Check the cache for existing versions of the object
		res = cache.get(uri);
		if(res == null){
			//Predicates
			String templateTypeof = GlobalSettings.PREDICATE_Type;
			String templateId = GlobalSettings.PREDICATE_Id; 
			String templateIdentifier = GlobalSettings.PREDICATE_Identifier;
			String templateName = GlobalSettings.PREDICATE_Name;
			String templateLabel = GlobalSettings.PREDICATE_Label; 
			String templateDefaultGeometry = GlobalSettings.PREDICATE_DefaultGeometry;
			String templateStartTime = GlobalSettings.PREDICATE_StartTime;
			String templateEndTime = GlobalSettings.PREDICATE_EndTime;
			String templateFeatureOfInterest = GlobalSettings.PREDICATE_FeatureOfInterest;
			String templateProperty = GlobalSettings.PREDICATE_ObservedProperty;
			String templateSensor = GlobalSettings.PREDICATE_ObservedBy;
			String templateSensing = GlobalSettings.PREDICATE_SensingMethodUsed;
			String templateSensorOutput = GlobalSettings.PREDICATE_ObservationResult;
			String templateAsWKT = GlobalSettings.PREDICATE_AsWkt;
			String templateDescription = GlobalSettings.PREDICATE_IsDescribedBy;
			String templateStimulus = GlobalSettings.PREDICATE_Detects;
			String templateValue = GlobalSettings.PREDICATE_HasValue;


			ObjectTypes objType = null;
			JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
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
			long id = -1; 
			String label = null;
			String description = null;
			String identifier = null;
			SOSSensor sensor = null;
			ArrayList<SOSProperty> propertyList = new ArrayList<SOSProperty>();
			switch (objType) {
			case FEATUREOFINTEREST:
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
				SOSSensing sensing = null;
				ArrayList<SOSSensorOutput> sensorOutput = new ArrayList<SOSSensorOutput>();
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateStartTime)){
						try {
							startTime = new SimpleDateFormat(GlobalSettings.DATE_Format, Locale.ENGLISH).parse(o);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if(p.equals(templateEndTime)){
						try {
							endTime = new SimpleDateFormat(GlobalSettings.DATE_Format, Locale.ENGLISH).parse(o);
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
							propertyList.add(prop);
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
				SOSObservation so = new SOSObservation(uri, startTime,endTime,featureOfInterest,propertyList,sensor,sensing,sensorOutput,label);
				res = so;
				break;
			case POINT:
				String asWKT = null;
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateAsWKT)){
						asWKT  = o;
					}else if(p.equals(templateLabel)){
						label = o;
					}
				}
				SOSPoint sp = new SOSPoint(uri,asWKT,label);
				res = sp;
				break;
			case PROPERTY:
				ArrayList<SOSFeatureOfInterest> foi = new ArrayList<SOSFeatureOfInterest>();
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateDescription)){
						description = o;
					}else if(p.equals(templateFeatureOfInterest)){
						try {
							featureOfInterest = new SOSFeatureOfInterest(new URI(o));
							foi.add(featureOfInterest);
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if(p.equals(templateLabel)){
						label = o;
					}
				}
				SOSProperty sprop = new SOSProperty(uri, foi, description, label);
				res = sprop;
				break;			
			case SENSING:
				ArrayList<String> descriptionList = new ArrayList<String>();
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateDescription)){
						descriptionList.add(o);
					}else if(p.equals(templateLabel)){
						label = o;
					}
				}
				SOSSensing ss = new SOSSensing(uri, descriptionList, label);
				res = ss;
				break;			
			case SENSOR:
				ArrayList<SOSSensing> sensingList = new ArrayList<SOSSensing>();
				ArrayList<SOSStimulus> stimulusList = null;
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateSensing)){
						SOSSensing sen = null;
						try {
							sen = new SOSSensing(new URI(o));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
						sensingList.add(sen);
					}else if(p.equals(templateStimulus)){
						SOSStimulus ssti = null;
						try {
							ssti = new SOSStimulus(new URI(o));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						stimulusList.add(ssti);
					}else if(p.equals(templateIdentifier)){
						identifier = o;
					}else if(p.equals(templateDescription)){
						description = o;
					}else if(p.equals(templateLabel)){
						label = o;
					}
				}
				SOSSensor ssen = new SOSSensor(uri, sensingList, stimulusList, propertyList, label, description, identifier);
				res = ssen;
				break;
			case SENSOROUTPUT:
				SOSValue value = null;
				Date samplingTime = null;
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateSensor)){
						try {
							sensor = new SOSSensor(new URI(o));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if(p.equals(templateValue)){
						try {
							value = new SOSValue(new URI(o));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else if(p.equals(templateLabel)){
						label = o;
					}
				}
				SOSSensorOutput sout = new SOSSensorOutput(uri, sensor, label,value, samplingTime.toString());
				res = sout;
				break;			
			case STIMULUS:
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateProperty)){
						SOSProperty prop = null;
						try {
							prop = new SOSProperty(new URI(o));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						propertyList.add(prop);
					}else if(p.equals(templateDescription)){
						description = o;
					}else if(p.equals(templateLabel)){
						label = o;
					}
				}			
				SOSStimulus ssti = new SOSStimulus(uri, propertyList, description, label);
				res = ssti;
				break;			
			case VALUE:
				double valueNumber = 0;
				SOSProperty property = null;
				for (int i=0; i<fact.getSize(); i++){
					p = fact.getPredicate(i);
					o = fact.getObject(i);
					if(p.equals(templateId)){
						id = Long.parseLong(o, 10);
					}else if(p.equals(templateValue)){
						valueNumber = Double.parseDouble(o);
					}else if(p.equals(templateProperty)){
						try {
							property = new SOSProperty(new URI(o));
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				SOSValue sv = new SOSValue(id, valueNumber, property);
				res = sv;
				break;
			default:
				break;
			}
		}
		return res;
	}


	/**
	 * Finds the kind of object given the type
	 * @param type
	 * @return
	 * @author Alber Sanchez
	 */
	
	private ObjectTypes getObjectType(String type){
		ObjectTypes res = ObjectTypes.UNKNOWN;

		Map<String,String> map = GlobalSettings.ONTOLOGY_OBJECTS_MAPPING;
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
			if(t !=null && tmpObjectType !=null){
				if(tmpObjectType.equals(t.toString())){
					res = t;
					break;
				}
			}
		}		
		return res;
	}

	/**
	 * Creates a new object with attribute values filled
	 * @param lr An object whose values could be missing
	 * @return An object whose attribute values are filled
	 * @author Alber Sanchez
	 */
	public LODResource fill(LODResource lr){
		URI uri = lr.getUri();
		LODResource newLr = this.create(uri);
		return newLr;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 *  Lists all properties available
	 *  
	 * @author jones
	 * @return
	 */
	
	public ArrayList<SOSProperty> getListProperties(){
		
		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		String SPARQL = new String();
		
		SPARQL = GlobalSettings.SPARQL_getListProperties.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		ArrayList<SOSProperty> result = new ArrayList<SOSProperty>();

		ResultSet results = cnn.executeSPARQLQuery(SPARQL);
		
		//System.out.println(SPARQL);
		
		while (results.hasNext()) {
			SOSProperty property = new SOSProperty();
			QuerySolution soln = results.nextSolution();

			try {
				
				property.setUri(new URI(soln.get("?property").toString()));
				
				result.add(property);

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		return result;
	}
	
	
	/**
	 * Retrieves all graphs available in the triple store.
	 * 
	 * @author jones
	 * @param property
	 * @return ArrayList<URI>
	 */

	public ArrayList<URI> getListGraphs(URI endpoint){

		Query query = QueryFactory.create(GlobalSettings.SPARQL_ListAvailableGraphs);
		QueryExecution qexec = QueryExecutionFactory.sparqlService(GlobalSettings.CurrentSPARQLEndpoint, query);

		ArrayList<URI> result = new ArrayList<URI>();

		ResultSet results = qexec.execSelect();

		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();

			try {

				result.add(new URI(soln.get("?graph").toString()));

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		qexec.close();

		return result;	
	}
		
	/**
	 * Retrieves an array list of features of interest (SOSFeatureOfInterest) for a given property (SOSProperty)
	 * 
	 * @author jones
	 * @param SOSProperty 
	 * @return ArrayList<SOSFeatureOfInterest>
	 */
	
	public ArrayList<SOSFeatureOfInterest> listFeaturesOfInterest(SOSProperty property){

		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		
		String SPARQL = new String();
		SPARQL = GlobalSettings.listFeaturesOfInterest.replace("PARAM_PROPERTY", property.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
				
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		ArrayList<SOSFeatureOfInterest> result = new ArrayList<SOSFeatureOfInterest>();
		SOSFeatureOfInterest foi = new SOSFeatureOfInterest();

		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			foi.setUri(URI.create(soln.get("?foi").toString()));			
			result.add(foi);
		}

		return result;
	}
	

	/**
	 * Retrieves the last observation for a given feature of interest (SOSFeatureOfInterest)
	 * 
	 * @author jones
	 * @param SOSFeatureOfInterest
	 * @return SOSObservation
	 * 
	 */
	
	public SOSObservation getFOILastObservation(SOSFeatureOfInterest featureOfInterest){

		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		String SPARQL = new String();
		SPARQL = GlobalSettings.geFOILastObservation.replace("PARAM_FOI", featureOfInterest.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
						
		ArrayList<SOSFeatureOfInterest> feature = new ArrayList<SOSFeatureOfInterest>();
		ArrayList<SOSSensorOutput> sensorOutput = new ArrayList<SOSSensorOutput>();
		
		SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
		SOSPoint point = new SOSPoint();
		SOSValue value = new SOSValue();
		SOSObservation observation = new SOSObservation();
		SOSSensorOutput output = new SOSSensorOutput();
		

		QuerySolution soln = rs.nextSolution();
		
		point.setAsWKT(soln.get("?wkt").toString());		
		foi.setDefaultGeometry(point);
		
		value.setHasValue(soln.getLiteral("?value").getDouble());			
		output.setValue(value);						
		sensorOutput.add(output);
		
		observation.setSensorOutput(sensorOutput);
		observation.setFeatureOfInterest(foi);					
		output.setSamplingTime(soln.get("?samplingTime").toString());
		return observation;		
	}
	
	/**
	 * Retrieves a list of observations related to a given feature of interest (SOSFeatureOfInterest), 
	 * constrained by a time interval (TimeInterval).
	 * 
	 * @author jones
	 * @param featureOfInterest
	 * @param SOSFeatureOfInterest TimeInterval
	 * @return ArrayList<SOSObservation>
	 */

	
	public ArrayList<SOSObservation> getObservationTimeInterval(SOSFeatureOfInterest featureOfInterest, TimeInterval interval){
		
		JenaConnector cnn = new JenaConnector(GlobalSettings.CurrentSPARQLEndpoint);
		
		String SPARQL = new String();
		SPARQL = GlobalSettings.getObservationsbyTimeInterval.replace("PARAM_DATE1", interval.getStartDate());
		SPARQL = SPARQL.replace("PARAM_DATE2", interval.getEndDate());
		SPARQL = SPARQL.replace("PARAM_FOI", featureOfInterest.getUri().toString());
		SPARQL = SPARQL.replace("PARAM_GRAPH", GlobalSettings.CurrentNamedGraph);
		
		ResultSet rs = cnn.executeSPARQLQuery(SPARQL);
		
		ArrayList<SOSObservation> result = new ArrayList<SOSObservation>();
		
		while (rs.hasNext()) {
			ArrayList<SOSSensorOutput> sensorOutput = new ArrayList<SOSSensorOutput>();
			SOSFeatureOfInterest foi = new SOSFeatureOfInterest();
			SOSPoint point = new SOSPoint();
			SOSValue value = new SOSValue();
			SOSObservation observation = new SOSObservation();
			SOSSensorOutput output = new SOSSensorOutput();

			QuerySolution soln = rs.nextSolution();
			
			point.setAsWKT(soln.get("?wkt").toString());		
			foi.setDefaultGeometry(point);
			
			value.setHasValue(soln.getLiteral("?value").getDouble());			
			output.setValue(value);						
			output.setSamplingTime(soln.getLiteral("?samplingTime").getValue().toString());
			sensorOutput.add(output);
			
			observation.setSensorOutput(sensorOutput);
			observation.setFeatureOfInterest(foi);	
			result.add(observation);
		}

		return result;
	}
	
}
