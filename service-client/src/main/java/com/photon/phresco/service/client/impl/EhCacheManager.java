package com.photon.phresco.service.client.impl;

import java.util.ArrayList;
import java.util.List;

import com.photon.phresco.model.ApplicationType;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


public class EhCacheManager {
	
	/**
     * The CacheManager provides us access to individual Cache instances
     */
    private static final CacheManager cacheManager = new CacheManager();
    
    private Ehcache appTypeCache;
    

    public EhCacheManager()
    {
        // Load cache:
    	appTypeCache = cacheManager.getEhcache( "apptypes" );
        
    }
    
    public void addAppInfo( String id, List<ApplicationType> appTypes )
    {
        // Create an EHCache Element 
        Element element = new Element( id, appTypes );

        // Add the element to the cache
        appTypeCache.put( element );
    }

    public List<ApplicationType> getAppInfo( String id ) {
        // Retrieve the element that contains the requested appType
        Element element = appTypeCache.get( id );
        if( element != null )
        {
            // Get the value out of the element and cast it to a appType
            return ( List<ApplicationType> )element.getValue();
        }

        // We don't have the object in the cache so return null
        return new ArrayList<ApplicationType>(1);
    }
}
