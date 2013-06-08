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

public class SOSProperty extends LODResource {

	 URI uri;	
	 ArrayList<SOSFeatureOfInterest> foi;
	 String description;
	 String label;
	boolean isFilled = false;
	
	
	
	
	public SOSProperty() {
		super();
	}
	public SOSProperty(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSProperty(URI uri, ArrayList<SOSFeatureOfInterest> foi,
			String description, String label) {
		super();
		this.uri = uri;
		this.foi = foi;
		this.description = description;
		this.label = label;
		this.isFilled = true;
	}

	
	


	
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public ArrayList<SOSFeatureOfInterest> getFoi() {
		return foi;
	}
	public void setFoi(ArrayList<SOSFeatureOfInterest> foi) {
		this.foi = foi;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}






}
