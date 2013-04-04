package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSStimulus {

	private ArrayList<SOSProperty> property;
	private URI description;
	
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

	public SOSStimulus(ArrayList<SOSProperty> property, URI description) {
		super();
		this.property = property;
		this.description = description;
	} 

}
package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSStimulus  extends LODResource {

	private URI uri;
	private ArrayList<SOSProperty> property;
	private URI description;
	
	
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
	
	public SOSStimulus(URI uri, ArrayList<SOSProperty> property, URI description) {
		super();
		this.uri = uri;
		this.property = property;
		this.description = description;
	}

	public void fill(){
		
	}

}
