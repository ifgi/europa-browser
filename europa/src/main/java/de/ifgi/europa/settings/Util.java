package de.ifgi.europa.settings;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class Util {

	/**
	 * Test of the input string is a URI
	 * @param uri URI to be tested
	 * @return TRUE if the string is an URI
	 */
	public static boolean isURI(String uri){
		boolean res = true;
		try {
			new URI(uri);
		} catch (URISyntaxException e) {
			res = false;
		}
		return res;
	}
	
	
	public static URI getURI(String uri){
		if(Util.isURI(uri)){
			try {
				return new URI(uri);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	

	
}
