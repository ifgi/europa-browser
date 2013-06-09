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

/**
 * This class represents a feature of interest - http://www.w3.org/2005/Incubator/ssn/ssnx/ssn#FeatureOfInterest
 * @author alber
 *
 */
public class SOSFeatureOfInterest  extends LODResource {

	long id;
	URI uri;
	String identifier;
	String name;
	String label;	
	SOSPoint defaultGeometry;
	boolean isFilled = false;
	
	/**
	 * Establish if the FOI has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}
	
	/**
	 * FOI's id.
	 * @return Id.
	 */
	public long getId() {
		return id;
	}
	
	/**
	 * Returns the FOI's URI.
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Sets the FOI's URI
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Sets an id for the FOI
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}
	
	/**
	 * Returns the FOI's identifier.
	 * @return Identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Sets an identifier for the FOI.
	 * @param identifier
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * Returns the FOI's name
	 * @return A name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the FOI's name.
	 * @param name A name.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns the FOI's label.
	 * @return A label.
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets a label for the FOI.
	 * @param label A label.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Returns the FOI's default geometry
	 * @return A string representation of the geometry. It could be WKT or GML.
	 */
	public SOSPoint getDefaultGeometry() {
		return defaultGeometry;
	}
	
	/**
	 * Sets the default geometry for the FOI.
	 * @param defaultGeometry A string representation of a geometry. Use WKT or GML.
	 */
	public void setDefaultGeometry(SOSPoint defaultGeometry) {
		this.defaultGeometry = defaultGeometry;
	}	

	/**
	 * Constructor.
	 */
	public SOSFeatureOfInterest() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri FOI's URI.
	 */
	public SOSFeatureOfInterest(URI uri) {
		super();
		this.uri = uri;
	}	
	
	/**
	 * Constructor.
	 * @param id FOI's id.
	 * @param uri FOI's URI.
	 * @param identifier FOI's identifier.
	 * @param name FOI's name.
	 * @param label FOI's label.
	 * @param defaultGeometry FOI's default geometry.
	 */
	public SOSFeatureOfInterest(long id, URI uri, String identifier,
			String name, String label, SOSPoint defaultGeometry) {
		super();
		this.id = id;
		this.uri = uri;
		this.identifier = identifier;
		this.name = name;
		this.label = label;
		this.defaultGeometry = defaultGeometry;
		this.isFilled = true;
	}

}
