package de.ifgi.europa.core;

import java.net.*;
import java.util.ArrayList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.ifgi.europa.comm.JenaConnector;
import de.ifgi.europa.constants.Constants;


public class SOSProperty extends LODResource {

	private URI uri;	
	private ArrayList<SOSFeatureOfInterest> foi;
	private String description;
	private String label;
	
	public SOSProperty(URI uri, ArrayList<SOSFeatureOfInterest> foi,
			String description, String label) {
		super();
		this.uri = uri;
		this.foi = foi;
		this.description = description;
		this.label = label;
	}
	public SOSProperty() {
		super();
		// TODO Auto-generated constructor stub
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



	public void fill(){


	}



}
