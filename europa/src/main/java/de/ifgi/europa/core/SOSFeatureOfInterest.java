package de.ifgi.europa.core;

import java.net.URI;

public class SOSFeatureOfInterest  extends LODResource {

	long id;
	URI uri;
	String identifier;
	String name;
	String label;	
	SOSPoint defaultGeometry;
	
	public long getId() {
		return id;
	}
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public SOSPoint getDefaultGeometry() {
		return defaultGeometry;
	}
	public void setDefaultGeometry(SOSPoint defaultGeometry) {
		this.defaultGeometry = defaultGeometry;
	}	
	
	public SOSFeatureOfInterest(long id, URI uri, String identifier,
			String name, String label, SOSPoint defaultGeometry) {
		super();
		this.id = id;
		this.uri = uri;
		this.identifier = identifier;
		this.name = name;
		this.label = label;
		this.defaultGeometry = defaultGeometry;
	}
	
	public SOSFeatureOfInterest() {
		super();
	}	

	public void fill(){
		
	}
}
