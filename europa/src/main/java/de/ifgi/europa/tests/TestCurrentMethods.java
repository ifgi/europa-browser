/**
   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package de.ifgi.europa.tests;

import java.net.URI;
import java.util.ArrayList;

import de.ifgi.europa.core.SOSFeatureOfInterest;
import de.ifgi.europa.core.SOSObservation;
import de.ifgi.europa.core.SOSProperty;
import de.ifgi.europa.core.SOSSensorOutput;
import de.ifgi.europa.core.SOSValue;
import de.ifgi.europa.core.TimeInterval;
import de.ifgi.europa.facade.Facade;
import de.ifgi.europa.settings.GlobalSettings;


public class TestCurrentMethods {

	public static void main(String[] args) {
				
		GlobalSettings.CurrentNamedGraph = "http://ifgi.uni-muenster.de/test/";//GlobalSettings.CurrentNamedGraph = "http://europa.ifgi.uni-muenster.de/browser/";
		GlobalSettings.CurrentSPARQLEndpoint="http://giv-siidemo.uni-muenster.de:8081/parliament/sparql";//GlobalSettings.CurrentSPARQLEndpoint="http://recife:8081/parliament/sparql";
		
		
		//TEST GET LIST OF PROPERTIES
		ArrayList<SOSProperty> prop = Facade.getInstance().listProperties();
		System.out.println("**** GET LIST OF PROPERTIES **** \n");
		for (int i = 0; i < prop.size(); i++) {
			System.out.println(prop.get(i).getUri());
		}
		
		
		//TEST FOIs PER PROPERTY
		ArrayList<SOSFeatureOfInterest> sosprop = new ArrayList<SOSFeatureOfInterest>(); 
		SOSProperty property = new SOSProperty(URI.create("http://ifgi.uni-muenster.de/hydrolod#Wassertemperatur"));
		//property.setUri(URI.create("http://www.opengis.net/def/property/OGC/0/SamplingTime"));
		sosprop = Facade.getInstance().listFeaturesOfInterest(property);
		System.out.println("\n**** GET FOIS PER PROPERTY **** \n");
		for (int i = 0; i < sosprop.size(); i++) {
			System.out.println(sosprop.get(i).getUri());
		}		
		
		
		//TEST GET OBSERVATIONS BY TIME INTERVAL 
		SOSObservation observation = new SOSObservation();
		SOSFeatureOfInterest featureOfInterest = new SOSFeatureOfInterest();
		featureOfInterest.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Suelfeld_31010094"));//featureOfInterest.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Nalje_Siel_126001"));
		TimeInterval interval = new TimeInterval("2013-06-12T00:00:00Z", "2013-06-12T06:00:00Z");//TimeInterval interval = new TimeInterval("2000-11-19T13:02:00Z", "2014-11-19T13:04:00Z");
		
		System.out.println("\n**** GET OBSERVATIONS BY TIME-INTERVAL **** \n");
		observation = Facade.getInstance().getObservationByInterval(featureOfInterest, interval);
		System.out.println("WKT --> " + observation.getFeatureOfInterest().getDefaultGeometry().getAsWKT());
		
		ArrayList<SOSSensorOutput> aSo = observation.getSensorOutput();
		int cso = 0;
		for (SOSSensorOutput sosSensorOutput : aSo) {
			System.out.println("-Date" + cso + " --> " + sosSensorOutput.getSamplingTime());
			ArrayList<SOSValue> asVal = sosSensorOutput.getValue();
			int cov = 0;
			for (SOSValue sosValue : asVal) {
				System.out.println("--Value" + cov + " --> " + sosValue.getHasValue());
				System.out.println("--Uom" + cov + " --> " + sosValue.getForProperty().getUom());
				System.out.println("--Prop" + cov + " --> " + sosValue.getForProperty().getUri());
				
				cov++;
			}
			cso++;
		}
		 
		
		//TEST FOI GET LAST OBSERVATION
		SOSObservation observation2 = new SOSObservation();
		SOSFeatureOfInterest featureOfInterest2 = new SOSFeatureOfInterest();
		featureOfInterest2.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Duesseldorf_2750010"));//featureOfInterest2.setUri(URI.create("http://ifgi.uni-muenster.de/hydrolod#Nalje_Siel_126001"));
		observation2 = Facade.getInstance().getFOILastObservation(featureOfInterest2);
		System.out.println("\n**** FOI LAST OBSERVATION ****\n");
		System.out.println("WKT --> " + observation2.getFeatureOfInterest().getDefaultGeometry().getAsWKT());
		System.out.println("Date --> " + observation2.getSensorOutput().get(0).getSamplingTime());
		
		int counter = 0;
		for (SOSValue val : observation2.getSensorOutput().get(0).getValue()){
			System.out.println("Value" + counter + " --> " + val.getHasValue());
			System.out.println("Uom" + counter + " --> " + val.getForProperty().getUom());
			System.out.println("Prop" + counter + " --> " + val.getForProperty().getUri());
			counter++;
		}
		System.out.println("******************************");
		
		
		
		
	}
}
