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

/**
 * This class represents a sensor http://www.w3.org/2005/Incubator/ssn/ssnx/ssn#Sensor
 * @author alber
 *
 */
public class SOSSensor  extends LODResource {

	 URI uri;
	 ArrayList<SOSSensing> sensing;
	 ArrayList<SOSStimulus> stimulus;
	 ArrayList<SOSProperty> property;
	 String label;
	 String description;
	 String identifier;
	boolean isFilled = false;
	
	/**
	 * Establish if the sensor has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}
	
	/**
	 * Returns the sensor's URI.
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Sets the sensor's URI.
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * Returns the set of sensings associated with the sensor.
	 * @return
	 */
	public ArrayList<SOSSensing> getSensing() {
		return sensing;
	}

	/**
	 * Sets the set of sensings associated with the sensor.
	 * @param sensing
	 */
	public void setSensing(ArrayList<SOSSensing> sensing) {
		this.sensing = sensing;
	}

	/**
	 * Returns the set of stimulus associated with the sensor.
	 * @return
	 */
	public ArrayList<SOSStimulus> getStimulus() {
		return stimulus;
	}

	/**
	 * Sets the set of stimulus associated with the sensor.
	 * @param stimulus
	 */
	public void setStimulus(ArrayList<SOSStimulus> stimulus) {
		this.stimulus = stimulus;
	}

	/**
	 * Returns the set of properties associated to the sensor.
	 * @return
	 */
	public ArrayList<SOSProperty> getProperty() {
		return property;
	}

	/**
	 * Sets the properties associated to the sensor.
	 * @param property
	 */
	public void setProperty(ArrayList<SOSProperty> property) {
		this.property = property;
	}

	/**
	 * Returns the property's label.
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the property's label.
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Returns the property's description.
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the property's description.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the property's identifier.
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * Sets the property's identifier.
	 * @param identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * Constructor.
	 */
	public SOSSensor() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSSensor(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Sesor's URI.
	 * @param sensing Set of sensings associated to the sensor.
	 * @param stimulus Set of stimulus associated to the sensor.
	 * @param property Set of properties associated to the sensor.
	 * @param label Sesor's URI.
	 * @param description Sesor's URI.
	 * @param identifier Sesor's URI.
	 */
	public SOSSensor(URI uri, ArrayList<SOSSensing> sensing,
			ArrayList<SOSStimulus> stimulus, ArrayList<SOSProperty> property,
			String label, String description, String identifier) {
		super();
		this.uri = uri;
		this.sensing = sensing;
		this.stimulus = stimulus;
		this.property = property;
		this.label = label;
		this.description = description;
		this.identifier = identifier;
		this.isFilled = true;
	}


}
