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

package de.ifgi.europa.factory;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.ifgi.europa.core.LODResource;

public class LODResourceCache {

	private static LODResourceCache INSTANCE = new LODResourceCache();
	private Map<String,LODResource> map = new HashMap<String,LODResource>();

	private LODResourceCache() {

	}

	public static LODResourceCache getInstance() {
		return INSTANCE;
	}

	/**
	 * Adds an object to the cache
	 * @param lres Object to be added
	 * @author Alber Sanchez
	 */
	public void addObject(LODResource lres){
		URI uri = lres.getUri();
		map.put(uri.toString(), lres);
	}

	/**
	 * Checks if an object exists in the cache
	 * @param lres Object to be checked
	 * @return TRUE if the object exists in the cache, FALSE otherwise
	 * @author Alber Sanchez
	 */
	public boolean exists(URI uri){
		boolean res = true;
		if(map.get(uri.toString()) == null){
			res = false;
		}
		return res;
	}

	/**
	 * Removes an object from the cache
	 * @param lres Object to be removed
	 * @author Alber Sanchez
	 */
	public void remove(LODResource lres){
		URI uri = lres.getUri();
		map.remove(uri.toString());
	}

	/**
	 * @author Alber Sanchez
	 * @param uri
	 * @return
	 */
	public LODResource get(URI uri){
		LODResource res = map.get(uri.toString());
		return res;
	}
}
