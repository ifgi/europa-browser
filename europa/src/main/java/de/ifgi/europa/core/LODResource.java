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

import java.net.URI;

/**
 * This class represents a RDF resource.
 * @author alber
 *
 */
public abstract class LODResource {

	private URI uri;

	/**
	 * Returns the resource's URI
	 * @return
	 */
	public URI getUri() {
		return uri;
	}
	
	/**
	 * Sets the resource's URI
	 * @param uri
	 */
	public void setUri(URI uri) {
		this.uri = uri;
	}

}