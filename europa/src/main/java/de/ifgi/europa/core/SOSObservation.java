package de.ifgi.europa.core;

import java.util.ArrayList;
import java.util.Date;

public class SOSObservation {

	private Date startTime;
	private Date endTime;
	private SOSFeatureOfInterest featureOfInterest;
	private ArrayList<SOSProperty> property;
	private SOSSensor sensor;
	private SOSSensing sensing;
	private ArrayList<SOSSensorOutput> sensorOutput;
	private String label;
	
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

	public SOSObservation(Date startTime, Date endTime,
			SOSFeatureOfInterest featureOfInterest,
			ArrayList<SOSProperty> property, SOSSensor sensor,
			SOSSensing sensing, ArrayList<SOSSensorOutput> sensorOutput,
			String label) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.featureOfInterest = featureOfInterest;
		this.property = property;
		this.sensor = sensor;
		this.sensing = sensing;
		this.sensorOutput = sensorOutput;
		this.label = label;
	}




}
package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
public class SOSObservation  extends LODResource {

	private URI uri;
	private Date startTime;
	private Date endTime;
	private SOSFeatureOfInterest featureOfInterest;
	private ArrayList<SOSProperty> property;
	private SOSSensor sensor;
	private SOSSensing sensing;
	private ArrayList<SOSSensorOutput> sensorOutput;
	private String label;
	
	
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
	}

	public void fill(){
		
	}


}
