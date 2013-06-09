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

import java.net.*;
import java.util.ArrayList;

/**
 * This class represents a property http://www.w3.org/2005/Incubator/ssn/ssnx/ssn#Property
 * @author alber
 *
 */
public class SOSProperty extends LODResource {

	 URI uri;	
	 ArrayList<SOSFeatureOfInterest> foi;
	 String description;
	 String label;
	boolean isFilled = false;
	
	
	/**
	 * Establish if the property has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}
	
	/**
	 * Constructor.
	 */
	public SOSProperty() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSProperty(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Property's URI.
	 * @param foi Property's set of features of interests.
	 * @param description Property's description.
	 * @param label Property's label.
	 */
	public SOSProperty(URI uri, ArrayList<SOSFeatureOfInterest> foi,
			String description, String label) {
		super();
		this.uri = uri;
		this.foi = foi;
		this.description = description;
		this.label = label;
		this.isFilled = true;
	}
	
	/**
	 * Returns the property's URI.
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Set's the property's URI.
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Return the set of features of interest associated with the property.
	 * @return
	 */
	public ArrayList<SOSFeatureOfInterest> getFoi() {
		return foi;
	}
	
	/**
	 * Sets the set of features of interest associated with the property.
	 * @param foi
	 */
	public void setFoi(ArrayList<SOSFeatureOfInterest> foi) {
		this.foi = foi;
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
}
