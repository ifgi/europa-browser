package de.ifgi.europa.core;

import java.net.URI;


public class SOSPoint  extends LODResource {

	 URI uri;
	 String asWKT;
	 String label;
	boolean isFilled = false;


	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public String getAsWKT() {
		return asWKT;
	}
	public void setAsWKT(String asWKT) {
		this.asWKT = asWKT;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}

	public SOSPoint() {
		super();
	}
	public SOSPoint(URI uri) {
		super();
		this.uri = uri;
	}
	public SOSPoint(URI uri, String asWKT, String label) {
		super();
		this.uri = uri;
		this.asWKT = asWKT;
		this.label = label;
		this.isFilled = true;
	}


}



