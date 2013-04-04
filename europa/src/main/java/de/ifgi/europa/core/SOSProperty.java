package de.ifgi.europa.core;

import java.net.*;
import java.util.ArrayList;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.ifgi.europa.comm.Comm;
import de.ifgi.europa.constants.Constants;


public class SOSProperty extends LODResource {

	private URI uri;	
	private ArrayList<SOSFeatureOfInterest> foi;
	private String description;
	private String label;
	
	public SOSProperty(URI uri, ArrayList<SOSFeatureOfInterest> foi,
			String description, String label) {
		super();
		this.uri = uri;
		this.foi = foi;
		this.description = description;
		this.label = label;
	}
	public SOSProperty() {
		super();
		// TODO Auto-generated constructor stub
	}
	public URI getUri() {
		return uri;
	}
	public void setUri(URI uri) {
		this.uri = uri;
	}
	public ArrayList<SOSFeatureOfInterest> getFoi() {
		return foi;
	}
	public void setFoi(ArrayList<SOSFeatureOfInterest> foi) {
		this.foi = foi;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}



	public void fill(){
//		String query = Constants.SPARQL_Fill_Property;
//		query = query.replace("PARAM_URI", "<" + this.uri + ">");
//		ResultSet res = Comm.(query);
//		
//		while (res.hasNext()) {
//			QuerySolution soln = res.nextSolution();
//			
//			//SOSProperty tmpProperty = new SOSProperty();
//			//ArrayList<SOSFeatureOfInterest> tmpFOIList = new ArrayList<SOSFeatureOfInterest>();
//					
//			this.setDescription(soln.get("?description").toString());
//			this.setLabel(soln.get("?label").toString());						
//
//			System.out.println(this.getDescription());
//			System.out.println(this.getLabel());
//		}
//

	}



}
