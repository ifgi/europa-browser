package de.ifgi.europa.tests;

import java.net.URISyntaxException;

import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.constants.Constants;

public class TestingSPARQL {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		
		JenaConnector cnn = new JenaConnector(Constants.SII_Lecutre_Endpoint);
		cnn.getListOfProperties();
		
	}

}
