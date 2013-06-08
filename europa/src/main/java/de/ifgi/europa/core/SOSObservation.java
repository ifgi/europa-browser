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

package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;

/**
 * Represents an observation.
 * @author alber
 *
 */
public class SOSObservation  extends LODResource {

	 URI uri;
	 Date startTime;
	 Date endTime;
	 SOSFeatureOfInterest featureOfInterest;
	 ArrayList<SOSProperty> property;
	 SOSSensor sensor;
	 SOSSensing sensing;
	 ArrayList<SOSSensorOutput> sensorOutput;
	 String label;
	boolean isFilled = false;
	
	/**
	 * Returns the URI.
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Sets the observation's URI
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Returns the observatio's start time
	 * @return start time
	 */
	public Date getStartTime() {
		return startTime;
	}
	
	/**
	 * Sets the observation's start time.
	 * @param startTime Start time.
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	/**
	 * Rreturns the observation's end time.
	 * @return End time.
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * Sets the end time.
	 * @param endTime End time.
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	/**
	 * Returns the observation's feature of interests.
	 * @return A FOI.
	 */
	public SOSFeatureOfInterest getFeatureOfInterest() {
		return featureOfInterest;
	}
	
	/**
	 * Sets the observation's featur of interest.
	 * @param featureOfInterest A FOI.
	 */
	public void setFeatureOfInterest(SOSFeatureOfInterest featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
	}
	
	/**
	 * Returns the observation's properties.
	 * @return A set of properties.
	 */
	public ArrayList<SOSProperty> getProperty() {
		return property;
	}
	
	/**
	 * Set the observation's properties.
	 * @param property A set of properties.
	 */
	public void setProperty(ArrayList<SOSProperty> property) {
		this.property = property;
	}
	
	/**
	 * Returns the observation's sensor.
	 * @return A sensor.
	 */
	public SOSSensor getSensor() {
		return sensor;
	}
	
	/**
	 * Sets the oservation's sensor.
	 * @param sensor A sensor.
	 */
	public void setSensor(SOSSensor sensor) {
		this.sensor = sensor;
	}
	
	/**
	 * Returns the observation's sensing.
	 * @return A sensing.
	 */
	public SOSSensing getSensing() {
		return sensing;
	}
	
	/**
	 * Sets the observation's sensing.
	 * @param sensing
	 */
	public void setSensing(SOSSensing sensing) {
		this.sensing = sensing;
	}
	
	/**
	 * Get the observation's sensor outputs.
	 * @return A set of sensor outputs.
	 */
	public ArrayList<SOSSensorOutput> getSensorOutput() {
		return sensorOutput;
	}
	
	/**
	 * Sets the observation's sensor outputs.
	 * @param sensorOutput A set of sensor outputs.
	 */
	public void setSensorOutput(ArrayList<SOSSensorOutput> sensorOutput) {
		this.sensorOutput = sensorOutput;
	}
	
	/**
	 * Returns the observation's label.
	 * @return A label.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the observation's label.
	 * @param label A label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Constructor.
	 */
	public SOSObservation() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri Observations's URI.
	 */
	public SOSObservation(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Observation's URI.
	 * @param startTime Observation's start time.
	 * @param endTime Observation's end time.
	 * @param featureOfInterest Observation's feature of  interest.
	 * @param property Observation's properties.
	 * @param sensor Observation's sensor.
	 * @param sensing Observation's sensing.
	 * @param sensorOutput Observation's sensor outputs.
	 * @param label Observation's label.
	 */
	public SOSObservation(URI uri, Date startTime, Date endTime,
			SOSFeatureOfInterest featureOfInterest,
			ArrayList<SOSProperty> property, SOSSensor sensor,
			SOSSensing sensing, ArrayList<SOSSensorOutput> sensorOutput,
			String label) {
		super();
		this.uri = uri;
		this.startTime = startTime;
		this.endTime = endTime;
		this.featureOfInterest = featureOfInterest;
		this.property = property;
		this.sensor = sensor;
		this.sensing = sensing;
		this.sensorOutput = sensorOutput;
		this.label = label;
		this.isFilled = true;
	}

}
