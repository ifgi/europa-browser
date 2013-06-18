package de.ifgi.europa.tests;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

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
				

		ResultSet rs2 =	Facade.getInstance().getNodeExternalData("http://dbpedia.org/resource/Innenstadt,_Cologne");


		
			while (rs2.hasNext()) {
				QuerySolution soln = rs2.nextSolution();
				
				//System.out.println(soln.get("?predicate").isURIResource() + " " + soln.get("?predicate").toString() );

				
					System.out.println("Predicate => " + soln.get("?predicate") + " \n" +
									   "Object => " + soln.get("?object")+  " \n" +
									   "isLiteral? => " + soln.get("?object").isLiteral() + "\n");
					
					
	
				}
			}


	}








