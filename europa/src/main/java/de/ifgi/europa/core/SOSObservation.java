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
import java.util.Date;
public class SOSObservation  extends LODResource {

	 URI uri;
	 Date startTime;
	 Date endTime;
	 SOSFeatureOfInterest featureOfInterest;
	 ArrayList<SOSProperty> property;
	 SOSSensor sensor;
	 SOSSensing sensing;
	 ArrayList<SOSSensorOutput> sensorOutput;
	 String label;
	boolean isFilled = false;
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public SOSFeatureOfInterest getFeatureOfInterest() {
		return featureOfInterest;
	}
	public void setFeatureOfInterest(SOSFeatureOfInterest featureOfInterest) {
		this.featureOfInterest = featureOfInterest;
	}
	public ArrayList<SOSProperty> getProperty() {
		return property;
	}
	public void setProperty(ArrayList<SOSProperty> property) {
		this.property = property;
	}
	public SOSSensor getSensor() {
		return sensor;
	}
	public void setSensor(SOSSensor sensor) {
		this.sensor = sensor;
	}
	public SOSSensing getSensing() {
		return sensing;
	}
	public void setSensing(SOSSensing sensing) {
		this.sensing = sensing;
	}
	public ArrayList<SOSSensorOutput> getSensorOutput() {
		return sensorOutput;
	}
	public void setSensorOutput(ArrayList<SOSSensorOutput> sensorOutput) {
		this.sensorOutput = sensorOutput;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public SOSObservation() {
		super();
	}
	public SOSObservation(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSObservation(URI uri, Date startTime, Date endTime,
			SOSFeatureOfInterest featureOfInterest,
			ArrayList<SOSProperty> property, SOSSensor sensor,
			SOSSensing sensing, ArrayList<SOSSensorOutput> sensorOutput,
			String label) {
		super();
		this.uri = uri;
		this.startTime = startTime;
		this.endTime = endTime;
		this.featureOfInterest = featureOfInterest;
		this.property = property;
		this.sensor = sensor;
		this.sensing = sensing;
		this.sensorOutput = sensorOutput;
		this.label = label;
		this.isFilled = true;
	}




}
