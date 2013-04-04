package de.ifgi.europa.core;

import java.net.*;
import java.util.ArrayList;


public class SOSProperty {

	private ArrayList<SOSFeatureOfInterest> foi;
	private ArrayList<URI> description;
	private String label;
	
	
	public ArrayList<SOSFeatureOfInterest> getFoi() {
		return foi;
	}
	public void setFoi(ArrayList<SOSFeatureOfInterest> foi) {
		this.foi = foi;
	}
	public ArrayList<URI> getDescription() {
		return description;
	}
	public void setDescription(ArrayList<URI> description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	
	public SOSProperty() {
		super();
	}

	public SOSProperty(ArrayList<SOSFeatureOfInterest> foi,
			ArrayList<URI> description, String label) {
		super();
		this.foi = foi;
		this.description = description;
		this.label = label;
	}











}
