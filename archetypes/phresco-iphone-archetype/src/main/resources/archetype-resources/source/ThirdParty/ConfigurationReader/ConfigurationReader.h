//
//  ConfigurationReader.h
//  Phresco
//
//  Created by Administrator on 02/04/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface ConfigurationReader : NSObject<NSXMLParserDelegate> {
    
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
