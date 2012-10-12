//
//  ConfigurationReader.m
//  Phresco
//
//  Created by Administrator on 02/04/12.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "ConfigurationReader.h"

@implementation ConfigurationReader
@synthesize stories;

- (void)parseXMLFileAtURL:(NSString *)config environment:(NSString *)name
{	
	stories = [[NSMutableArray alloc] init];
	
    //you must then convert the path to a proper NSURL or it won't work
    //NSURL *xmlURL = [NSURL URLWithString:URL];
	
    // here, for some reason you have to use NSClassFromString when trying to alloc NSXMLParser, otherwise you will get an object not found error
    // this may be necessary only for the toolchain
    /* NSStringEncoding encoding = NSASCIIStringEncoding;
    NSError* error;
    
	NSString *str = [NSString stringWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"phresco-env-config" ofType:@"xml"] usedEncoding:&encoding error:&error];
    
    NSString *trimmedString = [str stringByTrimmingCharactersInSet:[NSCharacterSet newlineCharacterSet]];
    
    [Base64 initialize];
    
    NSData	*b64DecData = [Base64 decode:trimmedString];
    
    NSString* decryptedStr = [[NSString alloc] initWithData:b64DecData encoding:NSASCIIStringEncoding];
    
	NSLog(@"decrypted string = %@",decryptedStr);*/
    NSString *xmlPath = [[NSBundle mainBundle] pathForResource:@"phresco-env-config" ofType:@"xml"];
     NSData *xmlData = [NSData dataWithContentsOfFile:xmlPath];
    rssParser = [[NSXMLParser alloc] initWithData:xmlData];
	
    // Set self as the delegate of the parser so that it will receive the parser delegate methods callbacks.
    [rssParser setDelegate:self];
	
    // Depending on the XML document you're parsing, you may want to enable these features of NSXMLParser.
    [rssParser setShouldProcessNamespaces:NO];
    [rssParser setShouldReportNamespacePrefixes:NO];
    [rssParser setShouldResolveExternalEntities:NO];
	
    [rssParser parse];
	
}

- (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError {
	NSString * errorString = [NSString stringWithFormat:@"Unable to download story feed from web site (Error code %i )", [parseError code]];
	NSLog(@"error parsing XML: %@", errorString);
	
	UIAlertView * errorAlert = [[UIAlertView alloc] initWithTitle:@"Error loading content" message:errorString delegate:self cancelButtonTitle:@"OK" otherButtonTitles:nil];
	[errorAlert show];
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict{			
    //NSLog(@"found this element: %@", elementName);
	currentElement = [elementName copy];
    if(@"environment") {
		if([elementName isEqualToString:@"environment"]) {
			//NSString *relAtt = [attributeDict valueForKey:@"url"];
			thisURL  = [attributeDict valueForKey:@"default"];
            NSLog(@"environment: %@", thisURL);
        }
    }
	if ([elementName isEqualToString:@"WebService"]) {
		// clear out our story item caches...
        currentNode = @"WebService";
		item = [[NSMutableDictionary alloc] init];
		currentTitle = [[NSMutableString alloc] init];
		currentDate = [[NSMutableString alloc] init];
		currentSummary = [[NSMutableString alloc] init];
		currentLink = [[NSMutableString alloc] init];
        
        thisLength  = [attributeDict valueForKey:@"name"];
        NSLog(@"server: %@", thisLength);
	}
	
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName{     
	//NSLog(@"ended element: %@", elementName);
   
   /* envVar = @"mywebservice";
    if (([envVar isEqualToString:thisLength])&& ([thisURL isEqualToString:@"true"])){*/
         if(@"WebService") {
        if ([elementName isEqualToString:@"WebService"]){
            // save values to an item, then store that item into the array...
            
            [item setObject:currentTitle forKey:@"host"];
            [item setObject:currentLink forKey:@"port"];
            [item setObject:currentSummary forKey:@"protocol"];
            [item setObject:currentDate forKey:@"context"];
            [item setObject:thisLength forKey:@"name"];
            NSLog(@"currentDate: %@", currentDate);
            [stories addObject:[item copy]];
            NSLog(@"adding story: %@", currentTitle);
        }
        // }
    }
     
}

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)string{
	//NSLog(@"found characters: %@", string);
	// save the characters for the current item...
    if([currentNode isEqualToString:@"WebService"] ) {
	if ([currentElement isEqualToString:@"host"]) {
		[currentTitle appendString:string];
	} else if ([currentElement isEqualToString:@"port"]) {
		[currentLink appendString:string];
	} else if ([currentElement isEqualToString:@"protocol"]) {
		[currentSummary appendString:string];
	} else if ([currentElement isEqualToString:@"context"]) {
		[currentDate appendString:string];
    } else if ([currentElement isEqualToString:@"name"]) {
		[thisLength appendString:string];
    }
        
     }
	
}

- (void)parserDidEndDocument:(NSXMLParser *)parser {
	
	[activityIndicator stopAnimating];
	[activityIndicator removeFromSuperview];
	
	NSLog(@"all done!");
	NSLog(@"stories array has %d items", [stories count]);
	
}

@end
