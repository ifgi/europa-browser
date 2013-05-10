package de.ifgi.europa.tests;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensing;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.facade.Facade;
import de.ifgi.europa.factory.LODResourceFactory;

public class TestingSPARQL {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	
	public static void main(String[] args) throws URISyntaxException {
		
		
//		SELECT COUNT(*)
//		WHERE { GRAPH <http://parliament.semwebcentral.org/parliament#TestGraph> {
//		?A ?B ?C }
//		} 

		//JenaConnector cnn = new JenaConnector(Constants.SII_Lecture_Endpoint);
		//cnn.getListOfProperties();
		
		//JenaConnector cnnLS = new JenaConnector(Constants.LinkedScience_Endpoint);
		//cnnLS.getListGraphs();
				
//		URI uri = new URI("http://ifgi.uni-muenster.de/hydrolod#SENSING_1");
//		LODResourceFactory lrf = new LODResourceFactory();
//		LODResource lr = lrf.create(uri);
//		SOSSensing so = (SOSSensing)lr;
//		System.out.println(so.getUri());
		
//		Facade facade = new Facade();
//		
//		ArrayList<SOSProperty> sosprop = new ArrayList<SOSProperty>(); 
//		
//		sosprop = facade.listProperties();
//		
//		for (int i = 0; i < sosprop.size(); i++) {
//			System.out.println(sosprop.get(i).getUri());
//		}
		
		
		
		
		Facade facade = new Facade();
		
		ArrayList<SOSFeatureOfInterest> sosprop = new ArrayList<SOSFeatureOfInterest>(); 
		SOSProperty property = new SOSProperty();
		property.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#PROPERTY_1"));
		
		sosprop = facade.listFeaturesOfInterest(property);
		
		for (int i = 0; i < sosprop.size(); i++) {
			System.out.println(sosprop.get(i).getName());
		}
		


//		Facade facade = new Facade();
					
		ArrayList<SOSObservation> observation = new ArrayList<SOSObservation>();
		SOSFeatureOfInterest featureOfInterest = new SOSFeatureOfInterest();
		featureOfInterest.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#FOI_1"));
		
		TimeInterval interval = new TimeInterval("2012-11-19T13:02:00Z", "2012-11-19T13:04:00Z");
		observation = facade.getObservationByInterval(featureOfInterest, interval);
		
		for (int i = 0; i < observation.size(); i++) {
			System.out.println("WKT --> " + observation.get(i).getFeatureOfInterest().getDefaultGeometry().getAsWKT());
			System.out.println("Date --> " + observation.get(i).getSensorOutput().get(0).getSamplingTime());
			System.out.println("Value --> " + observation.get(i).getSensorOutput().get(0).getValue().getHasValue());
		}

		
		
		ArrayList<URI> uris = new ArrayList<URI>();
		uris = facade.getListGraphs();
		
		for (int i = 0; i < uris.size(); i++) {
			try {
				System.out.println(uris.get(i).toURL().toString());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
