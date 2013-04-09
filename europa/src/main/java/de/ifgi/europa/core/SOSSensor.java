package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSSensor  extends LODResource {

	 URI uri;
	 ArrayList<SOSSensing> sensing;
	 ArrayList<SOSStimulus> stimulus;
	 ArrayList<SOSProperty> property;
	 String label;
	 String description;
	 URI identifier;
	boolean isFilled = false;
	
	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public ArrayList<SOSSensing> getSensing() {
		return sensing;
	}

	public void setSensing(ArrayList<SOSSensing> sensing) {
		this.sensing = sensing;
	}

	public ArrayList<SOSStimulus> getStimulus() {
		return stimulus;
	}

	public void setStimulus(ArrayList<SOSStimulus> stimulus) {
		this.stimulus = stimulus;
	}

	public ArrayList<SOSProperty> getProperty() {
		return property;
	}

	public void setProperty(ArrayList<SOSProperty> property) {
		this.property = property;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public URI getIdentifier() {
		return identifier;
	}

	public void setIdentifier(URI identifier) {
		this.identifier = identifier;
	}

	public SOSSensor() {
		super();
	}
	public SOSSensor(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSSensor(URI uri, ArrayList<SOSSensing> sensing,
			ArrayList<SOSStimulus> stimulus, ArrayList<SOSProperty> property,
			String label, String description, URI identifier) {
		super();
		this.uri = uri;
		this.sensing = sensing;
		this.stimulus = stimulus;
		this.property = property;
		this.label = label;
		this.description = description;
		this.identifier = identifier;
		this.isFilled = true;
	}



	
}
