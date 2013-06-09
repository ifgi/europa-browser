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
 * This class represents a sensing http://www.w3.org/2005/Incubator/ssn/ssnx/ssn#Sensing
 * @author alber
 *
 */
public class SOSSensing  extends LODResource {

	 URI uri;
	 ArrayList<String> description;
	 String label;
	boolean isFilled = false;
	
	/**
	 * Establish if the sensing has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}
	
	/**
	 * Returns the sensing's URI.
	 */
	public URI getUri() {
		return uri;
	}

	/**
	 * Sets the sensing's URI.
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

	/**
	 * Constructor.
	 */
	public SOSSensing() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSSensing(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Sensing's URI.
	 * @param description Sensing's description.
	 * @param label Sensing's label.
	 */
	public SOSSensing(URI uri, ArrayList<String> description, String label) {
		super();
		this.uri = uri;
		this.description = description;
		this.label = label;
		this.isFilled = true;
	}

	/**
	 * Returns the sensing's description.
	 * @return
	 */
	public ArrayList<String> getDescription() {
		return description;
	}

	/**
	 * Sets the sensing's description.
	 * @param description
	 */
	public void setDescription(ArrayList<String> description) {
		this.description = description;
	}

	/**
	 * Returns the sensing's label.
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Sets the sensng's label.
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
}
