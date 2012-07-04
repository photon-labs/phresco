/*
 * ###
 * Phresco Service Implemenation
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
package com.photon.phresco.service.impl;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


public class EHCacheManager {
	
	/**
     * The CacheManager provides us access to individual Cache instances
     */
    private static final CacheManager cacheManager = new CacheManager();
    
    /**
     * A cache that we're designating to hold Widget instances
     */
    private Ehcache widgetCache;
    

    public EHCacheManager()
    {
        // Load our widgets cache:
        widgetCache = cacheManager.getEhcache( "widgets" );
        
    }
    
    /**
     * Adds a new Widget to the Cache
     *
     * @param id        The ID of the widget
     * @param widget    The Widget itself
     */
    public void addWidget( String id, Widget widget )
    {
        // Create an EHCache Element to hold the widget
        Element element = new Element( id, widget );

        // Add the element to the cache
        widgetCache.put( element );
    }

    /**
     * Retrieves a Widget from the cache
     *
     * @param id        The ID of the Widget to retrieve
     * @return          The requested Widget or null if the Widget is not in the cache
     */
    public Widget getWidget( String id )
    {
        // Retrieve the element that contains the requested widget
        Element element = widgetCache.get( id );
        if( element != null )
        {
            // Get the value out of the element and cast it to a Widget
            return ( Widget )element.getValue();
        }

        // We don't have the object in the cache so return null
        return null;
    }
}
