package de.ifgi.europa.tests;

import java.net.URI;
import java.net.URISyntaxException;
import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.Fact;
import de.ifgi.europa.core.FactCollection;

public class TestingSPARQL {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		
		JenaConnector cnn = new JenaConnector(Constants.SII_Lecture_Endpoint);
		//cnn.getListOfProperties();
		
		//JenaConnector cnnLS = new JenaConnector(Constants.LinkedScience_Endpoint);
		//cnnLS.getListGraphs();
		
		
		URI subject = new URI("http://ifgi.uni-muenster.de/hydrolod#OBSERVATION_1");
		Fact f = cnn.getFact(subject);
		
		for(int i=0;i<f.getSize();i++){
			System.out.println(f.getSubject() + " - " + f.getPredicate(i) + " - " + f.getObject(i) + " - " + f.getDatatype(i));
		}
		
		
		
		
	}

}
