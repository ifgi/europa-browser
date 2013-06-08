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

public class SOSFeatureOfInterest  extends LODResource {

	long id;
	URI uri;
	String identifier;
	String name;
	String label;	
	SOSPoint defaultGeometry;
	boolean isFilled = false;
	
	
	public boolean isFilled() {
		return isFilled;
	}
	public long getId() {
		return id;
	}
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public SOSPoint getDefaultGeometry() {
		return defaultGeometry;
	}
	public void setDefaultGeometry(SOSPoint defaultGeometry) {
		this.defaultGeometry = defaultGeometry;
	}	


	public SOSFeatureOfInterest() {
		super();
	}
	public SOSFeatureOfInterest(URI uri) {
		super();
		this.uri = uri;
	}	
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
