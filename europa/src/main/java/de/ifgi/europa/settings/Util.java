///**
//   Copyright 2013 Jim Jones, Matthias Pfeil and Alber Sanchez
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
// */
//
//package de.ifgi.europa.settings;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//public abstract class Util {
//
//	/**
//	 * Test of the input string is a URI
//	 * @param uri URI to be tested
//	 * @return TRUE if the string is an URI
//	 */
//	public static boolean isURI(String uri){
//		boolean res = true;
//		try {
//			new URI(uri);
//		} catch (URISyntaxException e) {
//			res = false;
//		}
//		return res;
//	}
//	
//	
//	public static URI getURI(String uri){
//		if(Util.isURI(uri)){
//			try {
//				return new URI(uri);
//			} catch (URISyntaxException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}
//	
//
//	
//}
