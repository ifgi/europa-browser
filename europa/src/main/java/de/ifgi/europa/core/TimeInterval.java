/**
   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package de.ifgi.europa.core;

/**
 * This class represents a time interval.
 * @author alber
 *
 */
public class TimeInterval {

	/**
	 * Returns the interval's initial date.
	 * @return
	 */
	public String getStartDate() {
		return minDate;
	}
	
	/**
	 * Sets the interval's initial date.
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.minDate = startDate;
	}
	
	/**
	 * Returns the interval's final date.
	 * @return
	 */
	public String getEndDate() {
		return maxDate;
	}
	
	/**
	 * Sets the interval's final date.
	 * @param endDate
	 */
	public void setEndDate(String endDate) {
		this.maxDate = endDate;
	}
	
	/**
	 * Constructor.
	 * @param startDate
	 * @param endDate
	 */
	public TimeInterval(String startDate, String endDate) {
		super();
		this.minDate = startDate;
		this.maxDate = endDate;
	}

	//TODO: Change these to private.
	public String minDate;
	public String maxDate;
	
}
