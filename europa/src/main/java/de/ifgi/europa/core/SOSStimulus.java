package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSStimulus  extends LODResource {

	URI uri;
	ArrayList<SOSProperty> property;
	String description;
	String label;

	boolean isFilled = false;
	
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public ArrayList<SOSProperty> getProperty() {
		return property;
	}
	public void setProperty(ArrayList<SOSProperty> property) {
		this.property = property;
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
	
	public SOSStimulus() {
		super();
	}
	public SOSStimulus(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSStimulus(URI uri, ArrayList<SOSProperty> property, String description, String label) {
		super();
		this.uri = uri;
		this.property = property;
		this.description = description;
		this.label = label;
		this.isFilled = true;
	}



}
