package org.codehaus.mojo.jslint;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */

import org.codehaus.plexus.util.DirectoryScanner;

/**
 * A FilteredDirectoryScanner is a DirectoryScanner that permits an additional expression to be applied to each file
 * that has the potential to be included.
 * 
 * @author Christopher Hunt
 */
public class FilteredDirectoryScanner
    extends DirectoryScanner
{
    /**
     * This interface describes a protocol to be implemented that applies a filter expression.
     */
    public interface Filter
    {
        /**
         * The condition to apply.
         * 
         * @param name the file path to apply the filter to.
         * @return true if the file path should be included by the scanner.
         */
        boolean apply( String name );
    };

    /**
     * The filter that will be applied when testing for included files.
     */
    private final Filter filter;

    /**
     * @param filter the filter to apply to each file that has the potential to be included.
     */
    public FilteredDirectoryScanner( Filter filter )
    {
        this.filter = filter;
    }

    @Override
    protected boolean isIncluded( String name )
    {
        boolean isIncluded = super.isIncluded( name );
        if ( isIncluded )
        {
            isIncluded = filter.apply( name );
        }
        return isIncluded;
    }

}
