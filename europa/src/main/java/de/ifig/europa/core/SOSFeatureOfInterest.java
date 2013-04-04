package de.ifig.europa.core;

public class SOSFeatureOfInterest {

	private long id;
	private String identifier;
	private String name;
	private String label;	
	private SOSPoint defaultGeometry;
	
	public SOSFeatureOfInterest(long id, String identifier, String name,
			String label, SOSPoint defaultGeometry) {
		super();
		this.id = id;
		this.identifier = identifier;
		this.name = name;
		this.label = label;
		this.defaultGeometry = defaultGeometry;
	}
	
	
	public SOSFeatureOfInterest() {
		super();
		// TODO Auto-generated constructor stub
	}


	public long getId() {
		return id;
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
	
	
	
}
