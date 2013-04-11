package de.ifgi.europa.tests;


import java.net.URI;
import java.net.URISyntaxException;

import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSSensing;
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
		
		
		URI uri = new URI("http://ifgi.uni-muenster.de/hydrolod#SENSING_1");
		LODResourceFactory lrf = new LODResourceFactory();
		LODResource lr = lrf.create(uri);
		SOSSensing so = (SOSSensing)lr;
		

		
		
		
		
	}

}
