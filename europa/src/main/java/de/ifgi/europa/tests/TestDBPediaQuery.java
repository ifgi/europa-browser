package de.ifgi.europa.tests;

import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

import de.ifgi.europa.facade.Facade;

public class TestDBPediaQuery {

	public static void main(String[] args) {

		ResultSet rs =	Facade.getInstance().getExternalData(48.8142, 2.25583);

		while (rs.hasNext()) {
			QuerySolution soln = rs.nextSolution();
			System.out.println(soln.get("?label") + " - Lat: " + soln.getLiteral("?lat").getValue().toString() + " - Long: " + soln.getLiteral("?long").getValue().toString() );                                                
		}



	}
}
