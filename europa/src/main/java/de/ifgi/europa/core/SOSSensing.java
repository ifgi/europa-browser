package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSSensing {

	private ArrayList<URI> description;
	private String label;

	public SOSSensing(ArrayList<URI> description, String label) {
		super();
		this.description = description;
		this.label = label;
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

	public SOSSensing() {
		super();
	}


}
package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;

public class SOSSensing  extends LODResource {

	private URI uri;
	private ArrayList<URI> description;
	private String label;

	
	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}



	public SOSSensing(URI uri, ArrayList<URI> description, String label) {
		super();
		this.uri = uri;
		this.description = description;
		this.label = label;
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

	public SOSSensing() {
		super();
	}

	public void fill(){
		
	}
	
}
