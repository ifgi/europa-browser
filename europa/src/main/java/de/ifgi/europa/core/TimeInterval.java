package de.ifgi.europa.core;

public class TimeInterval {

	public String getStartDate() {
		return minDate;
	}
	public void setStartDate(String startDate) {
		this.minDate = startDate;
	}
	public String getEndDate() {
		return maxDate;
	}
	public void setEndDate(String endDate) {
		this.maxDate = endDate;
	}
	
	public TimeInterval(String startDate, String endDate) {
		super();
		this.minDate = startDate;
		this.maxDate = endDate;
	}

	public String minDate;
	public String maxDate;
	
}
