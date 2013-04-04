package de.ifig.europa.core;

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
