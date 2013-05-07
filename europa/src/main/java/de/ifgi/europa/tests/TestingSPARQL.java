package de.ifgi.europa.tests;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensing;
import de.ifgi.europa.facade.Facade;
import de.ifgi.europa.factory.LODResourceFactory;

public class TestingSPARQL {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	
	public static void main(String[] args) throws URISyntaxException {
		
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
			System.out.println(sosprop.get(i).getUri());
		}
		
		
	}

}
