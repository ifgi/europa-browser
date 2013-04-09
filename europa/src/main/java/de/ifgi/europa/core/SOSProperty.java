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
