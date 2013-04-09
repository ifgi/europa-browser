package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSSensing  extends LODResource {

	 URI uri;
	 ArrayList<URI> description;
	 String label;
	boolean isFilled = false;
	
	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}


	public SOSSensing() {
		super();
	}
	public SOSSensing(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSSensing(URI uri, ArrayList<URI> description, String label) {
		super();
		this.uri = uri;
		this.description = description;
		this.label = label;
		this.isFilled = true;
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



	
}
