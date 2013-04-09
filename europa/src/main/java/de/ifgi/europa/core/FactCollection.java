package de.ifgi.europa.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class FactCollection implements Iterable<FactCollection.FactCollectionIterator<Fact>> {

	private ArrayList<Fact> factCollection;

	
	public ArrayList<Fact> getFactCollection() {
		return factCollection;
	}
	public void setFactCollection(ArrayList<Fact> factCollection) {
		this.factCollection = factCollection;
	}
	
	
	public FactCollection() {

	}
	public FactCollection(ArrayList<Fact> factCollection) {
		super();
		this.factCollection = factCollection;
	}
	public FactCollection(ArrayList<String> subject,ArrayList<String> predicate, ArrayList<String> object,ArrayList<String> datatype) throws Exception {
		super();
		if(subject.size() == predicate.size() && predicate.size() == object.size() && object.size() == datatype.size()){
			this.factCollection = this.buildFacts(subject, predicate, object, datatype);
		}else{
			// TODO Auto-generated catch block
			throw new Exception();
		}

	}

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

	public void add(Fact f) {
		this.factCollection.add(f);
	}

	public void remove(int index){
		if(index >= 0 && index < this.Size()){
			this.factCollection.remove(index);
		}
	}

	public int Size(){
		int res = this.factCollection.size();
		return res;
	}

	public Fact get(int index){
		Fact res = null;
		if(index >= 0 && index < this.Size()){
			res = this.factCollection.get(index);
		}
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Iterator<FactCollection.FactCollectionIterator<Fact>> iterator() {
		return new FactCollectionIterator(this);
	}

	
	@SuppressWarnings("hiding")
	public class FactCollectionIterator<Fact> implements Iterator<Object> {

		private ArrayList<?> factCollection;
		private int position;

		public FactCollectionIterator(FactCollection fc) {
			this.factCollection = fc.getFactCollection();
		}

		public boolean hasNext() {
			if (position < factCollection.size()){
				return true;
			}else{
				return false;	
			}
		}

		public Object next() {
			Object obj = factCollection.get(position);
			position++;
			return obj;	
		}

		public void remove() {
			factCollection.remove(position);
		}
	}
	
	
}
