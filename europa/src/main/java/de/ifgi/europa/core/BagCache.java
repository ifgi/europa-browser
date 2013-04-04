package de.ifgi.europa.core;

import java.util.ArrayList;

public class BagCache {

	private static BagCache INSTANCE = new BagCache();
	
	private ArrayList<SOSFeatureOfInterest> featureOfInterest;
	private ArrayList<SOSObservation> observation;
	private ArrayList<SOSPoint> point;
	private ArrayList<SOSProperty> property;
	private ArrayList<SOSSensing> sensing;
	private ArrayList<SOSSensor> sensor;
	private ArrayList<SOSSensorOutput> sensorOutput;
	private ArrayList<SOSStimulus> stimulus;
	private ArrayList<SOSValue> value;
	
	private BagCache() {
 
	}

	public static BagCache getInstance() {
        return INSTANCE;
    }
	
	
	
	
	
	
}
