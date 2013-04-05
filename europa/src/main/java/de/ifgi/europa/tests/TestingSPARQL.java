package de.ifgi.europa.tests;

import java.net.URISyntaxException;

import de.ifgi.europa.comm.JenaConnector;

public class TestingSPARQL {

	/**
	 * @param args
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException {
		// TODO Auto-generated method stub
		
		JenaConnector cnn = new JenaConnector();
		cnn.getListOfProperties();
		
	}

}
