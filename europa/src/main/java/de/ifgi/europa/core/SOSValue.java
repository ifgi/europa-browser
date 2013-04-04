package de.ifgi.europa.core;

import java.util.Date;

public class SOSValue {

	long id;
	double hasValue;
		


	
}
package de.ifgi.europa.core;

public class SOSValue  extends LODResource {

	private long id;
	private double hasValue;
	private SOSProperty forProperty;

	public SOSValue(long id, double hasValue, SOSProperty forProperty) {
		super();
		this.id = id;
		this.hasValue = hasValue;
		this.forProperty = forProperty;
	}

	public SOSValue() {
		super();
		// TODO Auto-generated constructor stub
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

	public void fill(){
		
	}
}
