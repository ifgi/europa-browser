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
import java.util.HashSet;
import java.util.Iterator;


/**
 * Class for handlig sets of facts.
 * @deprecated
 * @author alber
 *
 */
public class FactCollection implements Iterable<FactCollection.FactCollectionIterator<Fact>> {

	private ArrayList<Fact> factCollection = new ArrayList<Fact>();

	
	/**
	 * Return a fact collection
	 * @deprecated
	 * @return
	 */
	public ArrayList<Fact> getFactCollection() {
		return factCollection;
	}
	
	/**
	 * Sets the fact collection
	 * @deprecated
	 * @param factCollection
	 */
	public void setFactCollection(ArrayList<Fact> factCollection) {
		this.factCollection = factCollection;
	}

	
	/**
	 * Constructor
	 * @deprecated
	 */
	public FactCollection() {

	}
	
	
	/**
	 * Constructor
	 * @deprecated
	 * @author Alber Sánchez
	 * @param factList
	 */
	public FactCollection(ArrayList<Fact> factList) {
		super();

		ArrayList<String> subject = new ArrayList<String>();
		ArrayList<String> predicate = new ArrayList<String>();
		ArrayList<String> object = new ArrayList<String>();
		ArrayList<String> datatype = new ArrayList<String>();
		for(int i=0;i < factList.size();i++){
			Fact f = factList.get(i);
			for(int j=0;j<f.getSize();j++){
				subject.add(f.getSubject());
				predicate.add(f.getPredicate(j));
				object.add(f.getObject(j));
				datatype.add(f.getDatatype(j));
			}
		}
		try {
			this.factCollection = this.buildFacts(subject, predicate, object, datatype);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Constructor 
	 * @deprecated
	 * @author Alber Sánchez
	 * @param subject
	 * @param predicate
	 * @param object
	 * @param datatype
	 * @throws Exception
	 */
	public FactCollection(ArrayList<String> subject,ArrayList<String> predicate, ArrayList<String> object,ArrayList<String> datatype) throws Exception {
		super();
		if(subject.size() == predicate.size() && predicate.size() == object.size() && object.size() == datatype.size()){
			this.factCollection = this.buildFacts(subject, predicate, object, datatype);
		}else{
			// TODO Auto-generated catch block
			throw new Exception();
		}

	}

	
	/**
	 * Collapses the duplicated facts into one
	 * @deprecated
	 * @author Alber Sánchez
	 * @param subjectClone
	 * @param predicateFactCollection
	 * @param objectClone
	 * @param datatypeClone
	 * @return
	 */
	private ArrayList<Fact> buildFacts(ArrayList<String> subject,ArrayList<String> predicate, ArrayList<String> object,ArrayList<String> datatype){
		ArrayList<Fact> res = new ArrayList<Fact>();
		if(subject.size() == predicate.size() && predicate.size() == object.size() && object.size() == datatype.size()){
			//Get the list of unique subjects
			ArrayList<String> uniqueSub = new ArrayList<String>();
			HashSet<String> hs = new HashSet<String>(subject);
			uniqueSub.addAll(hs);
			//Builds each Fact
			for(String sub : uniqueSub){
				Fact newFact = new Fact(sub);
				//Adds the remaining data to each Fact
				for(int i=subject.size()-1;i>=0;i--){
					if(subject.get(i).equals(sub)){
						newFact.add(predicate.get(i), object.get(i), datatype.get(i));
						subject.remove(i);
						predicate.remove(i);
						object.remove(i);
						datatype.remove(i);
					}
				}
				res.add(newFact);
			}
		}
		return res;
	}

	
	/**
	 * Adds a fact to the collection
	 * @deprecated
	 * @param f
	 */
	public void add(Fact f) {
		this.factCollection.add(f);
	}

	/**
	 * Removes a fact form the collection.
	 * @deprecated 
	 * @param index
	 */
	public void remove(int index){
		if(index >= 0 && index < this.Size()){
			this.factCollection.remove(index);
		}
	}

	/**
	 * Returns the number of facts in the collection
	 * @deprecated
	 * @return
	 */
	public int Size(){
		int res = this.factCollection.size();
		return res;
	}
	
	/**
	 * Returns a fact from the collection.
	 * @deprecated 
	 * @param index
	 * @return
	 */
	public Fact get(int index){
		Fact res = null;
		if(index >= 0 && index < this.Size()){
			res = this.factCollection.get(index);
		}
		return res;
	}

	/**
	 * Iteratior over the fact collection
	 * @deprecated
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator<FactCollection.FactCollectionIterator<Fact>> iterator() {
		return new FactCollectionIterator(this);
	}


	/**
	 * Iterator over the fact collection
	 * @deprecated
	 * @author alber
	 *
	 * @param <Fact>
	 */
	@SuppressWarnings("hiding")
	public class FactCollectionIterator<Fact> implements Iterator<Object> {

		private ArrayList<?> factCollection;
		private int position;

		/**
		 * Constructor
		 * @deprecated
		 * @param fc
		 */
		public FactCollectionIterator(FactCollection fc) {
			this.factCollection = fc.getFactCollection();
		}

		/**
		 * True of there is a next element 
		 * @deprecated
		 */
		public boolean hasNext() {
			if (position < factCollection.size()){
				return true;
			}else{
				return false;	
			}
		}

		/**
		 * Returns the next fact in the collection
		 * @deprecated
		 */
		public Object next() {
			Object obj = factCollection.get(position);
			position++;
			return obj;	
		}

		/**
		 * Removes a fact from the collection
		 * @deprecated
		 */
		public void remove() {
			factCollection.remove(position);
		}
	}


}
