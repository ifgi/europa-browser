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

import java.util.ArrayList;

/**
 * Abstraction used for decoupling Jena from other packages.
 * @deprecated
 * @author alber
 *
 */
public class Fact {

	private String subject;
	private ArrayList<String> predicate = new ArrayList<String>();
	private ArrayList<String> object = new ArrayList<String>();
	private ArrayList<String> datatype = new ArrayList<String>();

	/**
	 * Returns the number of predicates in this fact.
	 * @deprecated
	 * @return
	 */
	public int getLength() {
		return predicate.size();
	}
	
	/**
	 * Returns the subject of this fact.
	 * @deprecated
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	
	/**
	 * Returns the predicates of this fact.
	 * @deprecated
	 * @return
	 */
	public ArrayList<String> getPredicate() {
		return predicate;
	}
	
	/**
	 * Returns the objects of this fact.
	 * @deprecated
	 * @return
	 */
	public ArrayList<String> getObject() {
		return object;
	}
	
	/**
	 * Return the data types of the objects in this fact.
	 * @deprecated
	 * @return
	 */
	public ArrayList<String> getDatatype() {
		return datatype;
	}


	/**
	 * Constructor
	 * @deprecated
	 */
	public Fact() {}
	
	/**
	 * Constructor 
	 * @deprecated
	 * @param subject Subject of the fact
	 */
	public Fact(String subject) {
		this.subject = subject;
	}
	
	/**
	 * Constructor
	 * @deprecated
	 * @param subject Fact' subject.
	 * @param predicate Fact's predicates.
	 * @param object Fact's objects.
	 * @param datatype Object's datatypes
	 * @throws Exception
	 */
	public Fact(String subject, ArrayList<String> predicate,
			ArrayList<String> object, ArrayList<String> datatype) throws Exception {
		super();
		if(predicate.size() == object.size() && object.size() == datatype.size()){		
			this.subject = subject;
			this.predicate = predicate;
			this.object = object;
			this.datatype = datatype;
		}else{
			// TODO Auto-generated catch block
			throw new Exception();
		}
	}

	/**
	 * Adds a single predicate-object pair to the fact.
	 * @deprecated
	 * @param predicate
	 * @param object
	 * @param datatype
	 */
	public void add(String predicate, String object, String datatype){
		this.predicate.add(predicate);
		this.object.add(object);
		this.datatype.add(datatype);
	}

	/**
	 * Removes a predicate-object pair.
	 * @deprecated
	 * @param index
	 */
	public void remove(int index){
		if(index >= 0 && index < this.getLength()){
			this.predicate.remove(index);
			this.object.remove(index);
			this.datatype.remove(index);
		}
	}

	/**
	 * Returns a predicate given it's index.
	 * @deprecated
	 * @param index
	 * @return
	 */
	public String getPredicate(int index) {
		String res = null;
		if(index >= 0 && index < this.getLength()){
			res = this.predicate.get(index);
		}
		return res;
	}
	
	/**
	 * Returns an object given it's index.
	 * @deprecated
	 * @param index
	 * @return
	 */
	public String getObject(int index) {
		String res = null;
		if(index >= 0 && index < this.getLength()){
			res = object.get(index);
		}
		return res;
	}
	
	/**
	 * Returns an object's datatype given it's index.
	 * @deprecated
	 * @param index
	 * @return
	 */
	public String getDatatype(int index) {
		String res = null;
		if(index >= 0 && index < this.getLength()){
			res = datatype.get(index);
		}
		return res;
	}
	
	/**
	 * Returns the number of predicate-object pairs.
	 * @deprecated
	 * @return
	 */
	public int getSize(){
		return this.predicate.size();
	}

}
