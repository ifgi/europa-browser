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
 * This class represents a GeoSparql point.
 * @author alber
 *
 */
public class SOSPoint  extends LODResource {

	 URI uri;
	 String asWKT;
	 String label;
	boolean isFilled = false;

	/**
	 * Establish if the point has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}	
	
	/**
	 * Returns the point's URL.
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Sets the point's URI.
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}
	
	/**
	 * Returns the point geometry express as GeoSPARQL's WKT.
	 * @return Point WKT representation
	 */
	public String getAsWKT() {
		return asWKT;
	}
	
	/**
	 * Sets the point's GeoSPARQL WKT representation.
	 * @param asWKT
	 */
	public void setAsWKT(String asWKT) {
		this.asWKT = asWKT;
	}
	
	/**
	 * Returns the point's label.
	 * @return
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Sets the point's label.
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Constructor.
	 */
	public SOSPoint() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSPoint(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param uri Point's URI.
	 * @param asWKT Point's geometry as WKT.
	 * @param label Point's label.
	 */
	public SOSPoint(URI uri, String asWKT, String label) {
		super();
		this.uri = uri;
		this.asWKT = asWKT;
		this.label = label;
		this.isFilled = true;
	}


}



