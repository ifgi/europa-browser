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
import java.util.Date;

/**
 * This class represents a sensor output 
 * @author alber
 *
 */
public class SOSSensorOutput  extends LODResource {

	private URI uri;		


	SOSSensor sensor;
	String label;
	SOSValue value;
	String samplingTime;
	boolean isFilled = false;

	/**
	 * Establish if the sensor output has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}

	/**
	 * Returns the sensor output's URI.
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * Sets the sensor output's URI.
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Returns the sensor associated with the sensor output.
	 * @return
	 */
	public SOSSensor getSensor() {
		return sensor;
	}
	
	/**
	 * Sets the sensor associated with the sensor output.
	 * @return
	 */
	public void setSensor(SOSSensor sensor) {
		this.sensor = sensor;
	}
	
	/**
	 * Returns the sensor output's label
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the sensor output's label
	 * @return
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Returns the sensor output's value.
	 * @return
	 */
	public SOSValue getValue() {
		return value;
	}
	
	/**
	 * Sets the sensor output's value.
	 * @return
	 */
	public void setValue(SOSValue value) {
		this.value = value;
	}
	
	/**
	 * Returns the sensor output's sampling time.
	 * @return
	 */
	public String getSamplingTime() {
		return samplingTime;
	}
	
	/**
	 * Sets the sensor output's sampling time.
	 * @param samplingTime
	 */
	public void setSamplingTime(String samplingTime) {
		this.samplingTime = samplingTime;
	}

	/**
	 * Constructor.
	 */
	public SOSSensorOutput() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSSensorOutput(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Sensor output's URI.
	 * @param sensor Sensor associated to the sensor output.
	 * @param label Sensor output's label.
	 * @param value Sensor output's value.
	 * @param samplingTime Sensor output's sampling time.
	 */
	public SOSSensorOutput(URI uri, SOSSensor sensor, String label,
			SOSValue value, String samplingTime) {
		super();
		this.uri = uri;
		this.sensor = sensor;
		this.label = label;
		this.value = value;
		this.samplingTime = samplingTime;
		this.isFilled = true;
	}



}
