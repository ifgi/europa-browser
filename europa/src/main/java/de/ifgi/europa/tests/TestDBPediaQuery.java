package de.ifgi.europa.tests;

import com.hp.hpl.jena.graph.Factory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFactory;
import com.hp.hpl.jena.sparql.resultset.ResultSetRewindable;

import de.ifgi.europa.facade.Facade;

public class TestDBPediaQuery {

	public static void main(String[] args) {

//				ResultSet rs =	Facade.getInstance().getExternalData(51.0 , 7.0);
//		
//				while (rs.hasNext()) {
//					QuerySolution soln = rs.nextSolution();
//					System.out.println("Label: " + soln.get("?label") + " - Lat: " + soln.getLiteral("?lat").getValue().toString() + " - Long: " + soln.getLiteral("?long").getValue().toString() );
//					System.out.println("Population: " + soln.get("?population") + " - Lat: " + soln.getLiteral("?population").getValue().toString() + " - Long: " + soln.getLiteral("?population").getValue().toString() );
//		
//					
//				}
//		
				

		ResultSet rs2 =	Facade.getInstance().getNodeExternalData("http://dbpedia.org/resource/Rodenkirchen");

		
		
			while (rs2.hasNext()) {
				QuerySolution soln = rs2.nextSolution();
				
					System.out.println("Predicate => " + soln.get("?predicate") + " \n" +
									   "Object => " + soln.get("?object")+  " \n" +
									   "isLiteral? => " + soln.get("?object").isLiteral() + "\n");										
				}

			System.out.println("2.");
			ResultSetRewindable rsw = ResultSetFactory.makeRewindable(rs2);
			
			
			
			
		while (rsw.hasNext()) {
				QuerySolution soln = rs2.nextSolution();
				
					System.out.println("Predicate => " + soln.get("?predicate") + " \n" +
									   "Object => " + soln.get("?object")+  " \n" +
									   "isLiteral? => " + soln.get("?object").isLiteral() + "\n");										
				}
			}


	}








