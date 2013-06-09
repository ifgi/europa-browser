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

public class SOSStimulus  extends LODResource {

	URI uri;
	ArrayList<SOSProperty> property;
	String description;
	String label;
	boolean isFilled = false;
	
	/**
	 * Establish if the stimulus has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}
	
	/**
	 * Returns the stimulus' URI.
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Sets the stimulus' URI.
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Returns the properties associated with the stimulus.
	 * @return
	 */
	public ArrayList<SOSProperty> getProperty() {
		return property;
	}
	
	/**
	 * Sets the properties associated with the stimulus.
	 * @param property
	 */
	public void setProperty(ArrayList<SOSProperty> property) {
		this.property = property;
	}
	
	/**
	 * Returns the stimulus' description.
	 * @return
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the stimulus' description.
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Returns the stimulus' label.
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the stimulus' label.
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Constructor.
	 */
	public SOSStimulus() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSStimulus(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Stimulus' URI.
	 * @param property Stimulus' set of properties.
	 * @param description Stimulus' description.
	 * @param label Stimulus' label.
	 */
	public SOSStimulus(URI uri, ArrayList<SOSProperty> property, String description, String label) {
		super();
		this.uri = uri;
		this.property = property;
		this.description = description;
		this.label = label;
		this.isFilled = true;
	}



}
