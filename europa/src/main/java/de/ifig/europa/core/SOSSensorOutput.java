package de.ifig.europa.core;

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
