package de.ifgi.europa.tests;

import java.net.URI;
import java.util.ArrayList;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.facade.Facade;
import de.ifgi.europa.settings.GlobalSettings;


public class TestCurrentMethods {

	public static void main(String[] args) {
		
		GlobalSettings.CurrentNamedGraph = "http://europa.ifgi.uni-muenster.de/browser/";	
		
		//GlobalSettings.CurrentNamedGraph = "file:///C:/Parliament/test";
		
		//GlobalSettings.CurrentSPARQLEndpoint="http://giv-siidemo.uni-muenster.de:8081/parliament/sparql";
		GlobalSettings.CurrentSPARQLEndpoint="http://recife:8081/parliament/sparql";
		
		
		ArrayList<SOSProperty> prop = new ArrayList<SOSProperty>();
		
		prop = Facade.getInstance().listProperties();
		
		System.out.println("**** GET LIST OF PROPERTIES **** \n");
		
		for (int i = 0; i < prop.size(); i++) {
			System.out.println(prop.get(i).getUri());
		}
		
		
		ArrayList<SOSFeatureOfInterest> sosprop = new ArrayList<SOSFeatureOfInterest>(); 
		SOSProperty property = new SOSProperty();
		property.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Wassertemperatur"));
		//property.setUri(URI.create("http://www.opengis.net/def/property/OGC/0/SamplingTime"));
		sosprop = Facade.getInstance().listFeaturesOfInterest(property);
		
		System.out.println("\n**** GET FOIS PER PROPERTY **** \n");
		
		for (int i = 0; i < sosprop.size(); i++) {
			System.out.println(sosprop.get(i).getUri());
			
		}		
		
		
		
		ArrayList<SOSObservation> observation = new ArrayList<SOSObservation>();
		SOSFeatureOfInterest featureOfInterest = new SOSFeatureOfInterest();
		featureOfInterest.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Nalje_Siel_126001"));
		
		TimeInterval interval = new TimeInterval("2000-11-19T13:02:00Z", "2014-11-19T13:04:00Z");
		observation = Facade.getInstance().getObservationByInterval(featureOfInterest, interval);
		
		System.out.println("\n**** GET OBSERVATIONS BY TIME-INTERVAL **** \n");
		for (int i = 0; i < observation.size(); i++) {
			
			System.out.println("WKT --> " + observation.get(i).getFeatureOfInterest().getDefaultGeometry().getAsWKT());
			System.out.println("Date --> " + observation.get(i).getSensorOutput().get(0).getSamplingTime());
			System.out.println("Value --> " + observation.get(i).getSensorOutput().get(0).getValue().getHasValue());
		}

		
		SOSObservation observation2 = new SOSObservation();
		SOSFeatureOfInterest featureOfInterest2 = new SOSFeatureOfInterest();
		featureOfInterest2.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Nalje_Siel_126001"));
		
		observation2 = Facade.getInstance().getFOILastObservation(featureOfInterest2);
		
		System.out.println("\n**** FOI LAST OBSERVATION ****\n");
					
		System.out.println("WKT --> " + observation2.getFeatureOfInterest().getDefaultGeometry().getAsWKT());
		System.out.println("Date --> " + observation2.getSensorOutput().get(0).getSamplingTime());
		System.out.println("Value --> " + observation2.getSensorOutput().get(0).getValue().getHasValue());

		
		
		System.out.println("******************************");
		
		
		
		
	}
}
