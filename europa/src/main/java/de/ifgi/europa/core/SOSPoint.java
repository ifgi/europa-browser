package de.ifgi.europa.core;

public class SOSPoint {

	String asWKT;
	String label;
	
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
	
	public SOSPoint(String asWKT, String label) {
		super();
		this.asWKT = asWKT;
		this.label = label;
	}
	
	
}
