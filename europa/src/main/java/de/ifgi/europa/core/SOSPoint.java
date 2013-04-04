package de.ifgi.europa.core;

import java.net.URI;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.ifgi.europa.comm.Comm;

public class SOSPoint  extends LODResource {

	private URI uri;
	private String asWKT;
	private String label;


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
		// TODO Auto-generated constructor stub
	}

	public SOSPoint(URI uri, String asWKT, String label) {
		super();
		this.uri = uri;
		this.asWKT = asWKT;
		this.label = label;
	}

	public void fill(){


	}		

}



