package de.ifgi.europa.core;

import java.util.ArrayList;

public class ObservationSet {

	private ArrayList<String> subject;
	private ArrayList<String> predicate;
	private ArrayList<String> object;
	private ArrayList<String> datatype;

	public ArrayList<String> getSubject() {
		return subject;
	}


	public void setSubject(ArrayList<String> subject) {
		this.subject = subject;
	}


	public ArrayList<String> getPredicate() {
		return predicate;
	}


	public void setPredicate(ArrayList<String> predicate) {
		this.predicate = predicate;
	}


	public ArrayList<String> getObject() {
		return object;
	}


	public void setObject(ArrayList<String> object) {
		this.object = object;
	}


	public ArrayList<String> getDatatype() {
		return datatype;
	}


	public void setDatatype(ArrayList<String> datatype) {
		this.datatype = datatype;
	}


	public ObservationSet() {

	}


	public ObservationSet(ArrayList<String> subject,
			ArrayList<String> predicate, ArrayList<String> object,
			ArrayList<String> datatype) throws Exception {
		super();

		if(subject.size() == predicate.size() && predicate.size() == object.size() && object.size() == datatype.size()){
			this.subject = subject;
			this.predicate = predicate;
			this.object = object;
			this.datatype = datatype;
		}else{
			//TODO: Deal with exceptions
			throw new Exception();
		}
		
	}




}
