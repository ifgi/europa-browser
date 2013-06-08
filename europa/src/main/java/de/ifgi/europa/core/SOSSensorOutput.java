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

public class SOSSensorOutput  extends LODResource {

	private URI uri;		


	 SOSSensor sensor;
	 String label;
	 SOSValue value;
	 String samplingTime;
	boolean isFilled = false;
	
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public SOSSensor getSensor() {
		return sensor;
	}
	public void setSensor(SOSSensor sensor) {
		this.sensor = sensor;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public SOSValue getValue() {
		return value;
	}
	public void setValue(SOSValue value) {
		this.value = value;
	}
	public String getSamplingTime() {
		return samplingTime;
	}
	public void setSamplingTime(String samplingTime) {
		this.samplingTime = samplingTime;
	}
	
	public SOSSensorOutput() {
		super();
	}
	public SOSSensorOutput(URI uri) {
		super();
		this.uri = uri;
	}
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
