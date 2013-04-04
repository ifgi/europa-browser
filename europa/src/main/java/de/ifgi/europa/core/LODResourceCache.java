package de.ifgi.europa.core;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LODResourceCache {

	private static LODResourceCache INSTANCE = new LODResourceCache();
	private Map<String,Object> map = new HashMap<String,Object>();
	
	private LODResourceCache() {
 
	}

	public static LODResourceCache getInstance() {
        return INSTANCE;
    }
	
	public Object addObject(Object obj){
		Object res = null;
		if(obj instanceof LODResource){
			LODResource lres = (LODResource)obj;
			URI newObjUri = lres.getUri();
			if(map.containsKey(newObjUri)){
				res = map.get(newObjUri); 
			}
		}
		return res;
	}
	
}
