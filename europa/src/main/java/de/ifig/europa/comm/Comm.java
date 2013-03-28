package de.ifig.europa.comm;

import java.util.ArrayList;
import java.util.Date;

import javax.swing.text.Position;

public class Comm {
	String URL;
	


/**
 * This function connects to the SPARQL Endpoint and lists all properties (Stimulus) available . 
 * @param URL
 * @return
 */


	public ArrayList<String> getListOfProperties(){
		return null;
		
	}


	public ArrayList<Date> getPropertyIntervall(String property){
		
		return null;		
	}

	/**
	 * Lists the properties regarding a certain BBOX and time interval.
	 * @param property
	 * @param startTime
	 * @param endTime
	 * @param lowerLeft
	 * @param upperRight
	 * @return
	 */
	public ArrayList<Date> getPropertyBBOX(String property, Date startTime, Date endTime, Position lowerLeft, Position upperRight){
		
		return null;		
	}

	/**
	 * 
	 * @param property
	 * @param startTime
	 * @param endTime
	 * @param lowerLeft
	 * @param upperRight
	 * @param aggregationMethod
	 * @return
	 */
	public ArrayList<SOSObservation> dataLoader(String property, Date startTime, Date endTime,
			Position lowerLeft, Position upperRight, String aggregationMethod){
				
		
		return null;
		
	}
	
	/**
	 * Gets the last observation of a certain property
	 * @param property
	 * @return
	 */
	
	public SOSObservation getLastObservation(String property){
		return null;
		
	}
	
}