package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSStimulus  extends LODResource {

	URI uri;
	ArrayList<SOSProperty> property;
	URI description;
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
	public URI getDescription() {
		return description;
	}
	public void setDescription(URI description) {
		this.description = description;
	}
	
	
	public SOSStimulus() {
		super();
	}
	public SOSStimulus(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSStimulus(URI uri, ArrayList<SOSProperty> property, URI description) {
		super();
		this.uri = uri;
		this.property = property;
		this.description = description;
		this.isFilled = true;
	}



}
