package de.ifig.europa.core;

import java.util.Date;

public class SOSValue {

	String value;
	String unitOfMeasurements;
	Date samplingTime;
	String property;
	
	public SOSValue(String value, String unitOfMeasurements, Date samplingTime,
			String property) {
		super();
		this.value = value;
		this.unitOfMeasurements = unitOfMeasurements;
		this.samplingTime = samplingTime;
		this.property = property;
	}
	

	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getUnitOfMeasurements() {
		return unitOfMeasurements;
	}
	public void setUnitOfMeasurements(String unitOfMeasurements) {
		this.unitOfMeasurements = unitOfMeasurements;
	}
	public Date getSamplingTime() {
		return samplingTime;
	}
	public void setSamplingTime(Date samplingTime) {
		this.samplingTime = samplingTime;
	}
	


	
}
