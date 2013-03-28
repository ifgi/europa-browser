package de.ifig.europa.comm;

import javax.swing.text.Position;

public class FeatureOfInterest {

	Position geometry;
	String description;
	String name;
	String identifier;
	String isReferencedBy;
	
	public Position getGeometry() {
		return geometry;
	}
	public void setGeometry(Position geometry) {
		this.geometry = geometry;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getIsReferencedBy() {
		return isReferencedBy;
	}
	public void setIsReferencedBy(String isReferencedBy) {
		this.isReferencedBy = isReferencedBy;
	}
	public FeatureOfInterest(Position geometry, String description,
			String name, String identifier, String isReferencedBy) {
		super();
		this.geometry = geometry;
		this.description = description;
		this.name = name;
		this.identifier = identifier;
		this.isReferencedBy = isReferencedBy;
	}

	
}
