/*
 * ###
 * Archetype - phresco-iphone-native-archetype
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
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
//
//  ConfigurationReader.h
//  Phresco
//
//  Created by Administrator on 02/04/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ConfigurationReader : NSObject {
    
    UIActivityIndicatorView * activityIndicator;
    
    NSXMLParser * rssParser;
	
	NSMutableArray * stories;
    
    NSString *thisURL;
    NSString *thisLength;
    NSString *envVar;
    NSString *currentNode;
	
	// a temporary item; added to the "stories" array one at a time, and cleared for the next one
	NSMutableDictionary * item;
	
	// it parses through the document, from top to bottom...
	// we collect and cache each sub-element value, and then save each item to our array.
	// we use these to track each current item, until it's ready to be added to the "stories" array
	NSString * currentElement;
	NSMutableString * currentTitle, * currentDate, * currentSummary, * currentLink;
}
@property (nonatomic,retain) NSMutableArray * stories;
- (void)parseXMLFileAtURL:(NSString *)config environment:(NSString *)name;

@end
