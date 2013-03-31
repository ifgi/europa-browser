package de.ifig.europa.core;

import com.hp.hpl.jena.rdf.model.RDFNode;

public class TimeInterval {

	public RDFNode getStartDate() {
		return minDate;
	}
	public void setStartDate(RDFNode startDate) {
		this.minDate = startDate;
	}
	public RDFNode getEndDate() {
		return maxDate;
	}
	public void setEndDate(RDFNode endDate) {
		this.maxDate = endDate;
	}
	
	public TimeInterval(RDFNode startDate, RDFNode endDate) {
		super();
		this.minDate = startDate;
		this.maxDate = endDate;
	}

	public RDFNode minDate;
	public RDFNode maxDate;
	
}
