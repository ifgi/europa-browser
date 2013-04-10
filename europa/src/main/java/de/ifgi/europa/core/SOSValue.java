package de.ifgi.europa.core;

import java.net.URI;

public class SOSValue  extends LODResource {

	URI uri;
	long id;
	double value;
	SOSProperty property;
	boolean isFilled = false;
	

	public SOSValue() {
		super();
	}
	public SOSValue(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSValue(long id, double value, SOSProperty property) {
		super();
		this.id = id;
		this.value = value;
		this.property = property;
		this.isFilled = true;
	}


	
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getHasValue() {
		return value;
	}

	public void setHasValue(double hasValue) {
		this.value = hasValue;
	}

	public SOSProperty getForProperty() {
		return property;
	}

	public void setForProperty(SOSProperty forProperty) {
		this.property = forProperty;
	}


}
