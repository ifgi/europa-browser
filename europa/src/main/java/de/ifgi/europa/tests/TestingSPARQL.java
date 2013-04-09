package de.ifgi.europa.tests;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.net.URISyntaxException;
import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.Fact;
import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.factory.LODResourceFactory;

public class TestingSPARQL {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		
		JenaConnector cnn = new JenaConnector(Constants.SII_Lecture_Endpoint);
		//cnn.getListOfProperties();
		
		//JenaConnector cnnLS = new JenaConnector(Constants.LinkedScience_Endpoint);
		//cnnLS.getListGraphs();
		
		
		URI uri = new URI("http://ifgi.uni-muenster.de/hydrolod#OBSERVATION_1");
		LODResourceFactory lrf = new LODResourceFactory();
		LODResource lr = lrf.create(uri);
		SOSObservation so = (SOSObservation)lr;
		

		
		
		
		
	}

}
