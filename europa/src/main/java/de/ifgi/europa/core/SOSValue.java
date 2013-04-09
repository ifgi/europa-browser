package de.ifgi.europa.core;

import java.net.URI;

public class SOSValue  extends LODResource {

	URI uri;
	long id;
	double hasValue;
	SOSProperty forProperty;
	boolean isFilled = false;
	

	public SOSValue() {
		super();
	}
	public SOSValue(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSValue(long id, double hasValue, SOSProperty forProperty) {
		super();
		this.id = id;
		this.hasValue = hasValue;
		this.forProperty = forProperty;
		this.isFilled = true;
	}


	
	
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getHasValue() {
		return hasValue;
	}

	public void setHasValue(double hasValue) {
		this.hasValue = hasValue;
	}

	public SOSProperty getForProperty() {
		return forProperty;
	}

	public void setForProperty(SOSProperty forProperty) {
		this.forProperty = forProperty;
	}


}
