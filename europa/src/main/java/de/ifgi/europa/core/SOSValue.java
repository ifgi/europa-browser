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

import java.net.URI;

public class SOSValue  extends LODResource {

	URI uri;
	long id;
	double value;
	SOSProperty property;
	boolean isFilled = false;
	
	/**
	 * Establish if the value has all of its attributes.
	 * @return True if all the attributes were filled, false otherwise. 
	 */
	public boolean isFilled() {
		return isFilled;
	}
	
	/**
	 * Constructor.
	 */
	public SOSValue() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param uri
	 */
	public SOSValue(URI uri) {
		super();
		this.uri = uri;
	}
	
	/**
	 * Constructor.
	 * @param id Value's id.
	 * @param value Value's value.
	 * @param property Measured property represented by the value.
	 */
	public SOSValue(long id, double value, SOSProperty property) {
		super();
		this.id = id;
		this.value = value;
		this.property = property;
		this.isFilled = true;
	}

	/**
	 * Returns the value's id.
	 * @return
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the value's id.
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the value.
	 * @return
	 */
	public double getHasValue() {
		//TODO: Check method's name
		return value;
	}

	/**
	 * Sets the value.
	 * @param hasValue
	 */
	public void setHasValue(double hasValue) {
		//TODO: Check method's name
		this.value = hasValue;
	}

	/**
	 * Returns the property associated to the value.
	 * @return
	 */
	public SOSProperty getForProperty() {
		//TODO: Check method's name
		return property;
	}

	/**
	 * Sets the property associated to the value.
	 * @param forProperty
	 */
	public void setForProperty(SOSProperty forProperty) {
		//TODO: Check method's name
		this.property = forProperty;
	}


}
