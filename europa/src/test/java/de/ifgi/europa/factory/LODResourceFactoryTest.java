package de.ifgi.europa.factory;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

import de.ifgi.europa.core.LODResource;
import de.ifgi.europa.core.SOSObservation;

public class LODResourceFactoryTest {

	@Test
	public void testCreate() {
		String path = "http://ifgi.uni-muenster.de/hydrolod#OBSERVATION_20";
		URI uri = null;
		try {
			uri = new URI(path);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LODResourceFactory lrf = new LODResourceFactory();
		assertNotNull(lrf);
		LODResource lr = lrf.create(uri);
		assertNotNull(lr);
		SOSObservation so = (SOSObservation)lr;
		assertNotNull(so);
	}

}
