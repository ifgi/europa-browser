package de.ifgi.europa.core;

import java.util.Date;

public class SOSSensorOutput {

	private SOSSensor sensor;
	private String label;
	private SOSValue value;
	private Date samplingTime;
	
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
	public Date getSamplingTime() {
		return samplingTime;
	}
	public void setSamplingTime(Date samplingTime) {
		this.samplingTime = samplingTime;
	}
	
	public SOSSensorOutput() {
		super();
	}

	public SOSSensorOutput(SOSSensor sensor, String label, SOSValue value,
			Date samplingTime) {
		super();
		this.sensor = sensor;
		this.label = label;
		this.value = value;
		this.samplingTime = samplingTime;
	}	
	
}
package de.ifgi.europa.core;

import java.net.URI;
import java.util.Date;

public class SOSSensorOutput  extends LODResource {

	private URI uri;		
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}

	private SOSSensor sensor;
	private String label;
	private SOSValue value;
	private Date samplingTime;
	
	
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
	public Date getSamplingTime() {
		return samplingTime;
	}
	public void setSamplingTime(Date samplingTime) {
		this.samplingTime = samplingTime;
	}
	
	public SOSSensorOutput() {
		super();
	}
	public SOSSensorOutput(URI uri, SOSSensor sensor, String label,
			SOSValue value, Date samplingTime) {
		super();
		this.uri = uri;
		this.sensor = sensor;
		this.label = label;
		this.value = value;
		this.samplingTime = samplingTime;
	}

	public void fill(){
		
	}
	
}