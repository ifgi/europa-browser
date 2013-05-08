package de.ifgi.europa.factory;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSPoint;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensing;
import de.ifgi.europa.core.SOSSensor;
import de.ifgi.europa.core.SOSSensorOutput;
import de.ifgi.europa.core.SOSValue;

public class LODResourceFactoryTest {

	@Test
	public void testCreate() {
		
		URI uriObs = null;
		URI uriSensing = null;
		URI uriFOI = null;
		URI uriPoint = null;
		URI uriProperty = null;
		URI uriSensor = null;
		URI uriValue = null;
		URI uriOutput = null;
		
		String pathObs = "http://ifgi.uni-muenster.de/hydrolod#OBSERVATION_20";
		String pathSensing ="http://ifgi.uni-muenster.de/hydrolod#SENSING_1";
		String pathFOI ="http://ifgi.uni-muenster.de/hydrolod#FOI_1";
		String pathPoint ="http://ifgi.uni-muenster.de/hydrolod#POINT_1";
		String pathPorperty = "http://ifgi.uni-muenster.de/hydrolod#PROPERTY_1";
		String pathSensor = "http://ifgi.uni-muenster.de/hydrolod#SENSOR_1";
		String pathValue = "http://ifgi.uni-muenster.de/hydrolod#OBS_VALUE_1";
		String pathOutput = "http://ifgi.uni-muenster.de/hydrolod#SENSOR_OUTPUT_1";
		
		try {
			uriObs = new URI(pathObs);
			uriSensing= new URI(pathSensing);
			uriFOI= new URI(pathFOI);
			uriPoint= new URI(pathPoint);
			uriProperty = new URI(pathPorperty);
			uriSensor = new URI(pathSensor);
			uriValue = new URI(pathValue);
			uriOutput = new URI(pathOutput);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LODResourceFactory lrf = new LODResourceFactory();
		assertNotNull(lrf);
		LODResource lr;
		
		lr = lrf.create(uriObs);
		assertNotNull(lr);
		SOSObservation so = (SOSObservation)lr;
		assertNotNull(so);
		
		lr = lrf.create(uriFOI);
		assertNotNull(lr);
		SOSFeatureOfInterest foi = (SOSFeatureOfInterest)lr;
		assertNotNull(foi);
		
//		lr = lrf.create(uriPoint);
//		assertNotNull(lr);
//		SOSPoint point = (SOSPoint)lr;
//		assertNotNull(point);
		
//		lr = lrf.create(uriProperty);
//		assertNotNull(lr);
//		SOSProperty property = (SOSProperty)lr;
//		assertNotNull(property);
//		
//		lr = lrf.create(uriSensor);
//		assertNotNull(lr);
//		SOSSensor sensor = (SOSSensor)lr;
//		assertNotNull(sensor);
//		
//		lr = lrf.create(uriValue);
//		assertNotNull(lr);
//		SOSValue value = (SOSValue)lr;
//		assertNotNull(value);
//		
//		lr = lrf.create(uriOutput);
//		assertNotNull(lr);
//		SOSSensorOutput out = (SOSSensorOutput)lr;
//		assertNotNull(out);
//		
//		lr = lrf.create(uriSensing);
//		assertNotNull(lr);
//		SOSSensing ss = (SOSSensing)lr;
//		assertNotNull(ss);
		
		
		
	}

}
