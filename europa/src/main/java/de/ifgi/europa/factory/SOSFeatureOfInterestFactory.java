package de.ifgi.europa.factory;

import java.net.URI;

import de.ifgi.europa.constants.Constants;
import de.ifgi.europa.core.LODResource;

public class SOSFeatureOfInterestFactory extends LODResourceFactory {


	@Override
	protected LODResource create(URI uri){
		String query = Constants.SPARQL_Fill_FOI;
		query = query.replace("PARAM_URI", "<" + uri + ">");

		ResultSet res = JenaConnector.
		
		while (res.hasNext()) {
			QuerySolution soln = res.nextSolution();
			
			//SOSProperty tmpProperty = new SOSProperty();
			//ArrayList<SOSFeatureOfInterest> tmpFOIList = new ArrayList<SOSFeatureOfInterest>();
					
			this.setDescription(soln.get("?description").toString());
			this.setLabel(soln.get("?label").toString());						

			System.out.println(this.getDescription());
			System.out.println(this.getLabel());
		}
	}	
	
}
