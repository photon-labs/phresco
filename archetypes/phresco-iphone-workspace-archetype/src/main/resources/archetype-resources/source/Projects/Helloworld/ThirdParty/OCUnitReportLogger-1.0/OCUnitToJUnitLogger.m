//
//  OCUnitToJUnitLogger.h
//
//  Created by Scott Densmore on 6/5/10.
//  Copyright 2010 Scott Densmore. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <SenTestingKit/SenTestingKit.h>
#import "GDataXMLNode.h"

@interface OCUnitToJUnitLogger : NSObject
{
@private
    GDataXMLDocument *document;
    GDataXMLElement *suitesElement;
    GDataXMLElement *currentSuiteElement;
    GDataXMLElement *currentCaseElement;
    NSString *Path;
}

@property (retain) GDataXMLDocument *document;
@property (retain) GDataXMLElement *suitesElement;
@property (retain) GDataXMLElement *currentSuiteElement;
@property (retain) GDataXMLElement *currentCaseElement;
@property (retain) NSString *Path;

- (void)writeResultFile;

@end


static OCUnitToJUnitLogger *instance = nil;

static void __attribute__ ((constructor)) OCUnitToJUnitLoggerStart(void)
{
    instance = [OCUnitToJUnitLogger new];
   // [instance writeResultFile];
    
}

static void __attribute__ ((destructor)) OCUnitToJUnitLoggerStop(void)
{
    //[instance writeResultFile];
	[instance release];
}


@implementation OCUnitToJUnitLogger

@synthesize document;
@synthesize suitesElement;
@synthesize currentSuiteElement;
@synthesize currentCaseElement;

#pragma mark -
#pragma mark Init / Dealloc
- (id)init;
{
    NSFileManager *fileManager = [NSFileManager defaultManager];
    if([fileManager fileExistsAtPath:[[NSBundle mainBundle] pathForResource:@"Path" ofType:@"txt"]]) {
        Path =[NSString stringWithContentsOfFile:[[NSBundle mainBundle] pathForResource:@"Path" ofType:@"txt"] usedEncoding:nil error:nil];
        NSLog(@"dPath = %@",Path);
    } else {
        Path = @"";
    }
    
    if ((self = [super init]))
    {
        NSNotificationCenter *center = [NSNotificationCenter defaultCenter];
        [center addObserver:self selector:@selector(testSuiteStarted:) name:SenTestSuiteDidStartNotification object:nil];
        [center addObserver:self selector:@selector(testSuiteStopped:) name:SenTestSuiteDidStopNotification object:nil];
        [center addObserver:self selector:@selector(testCaseStarted:) name:SenTestCaseDidStartNotification object:nil];
        [center addObserver:self selector:@selector(testCaseStopped:) name:SenTestCaseDidStopNotification object:nil];
        [center addObserver:self selector:@selector(testCaseFailed:) name:SenTestCaseDidFailNotification object:nil];
        
        document = [[GDataXMLDocument alloc] init];
		[document initWithRootElement:[GDataXMLElement elementWithName:@"testsuites"]];
		self.suitesElement = [document rootElement];
    }
    return self;
}

- (void)dealloc;
{
    [[NSNotificationCenter defaultCenter] removeObserver:self];
    [document release];
	[suitesElement release];
	[currentSuiteElement release];
	[currentCaseElement release];
    [super dealloc];
}

#pragma -
#pragma mark Methods
- (void)writeResultFile;
{
    if (self.document)
	{		NSMutableString *strPath = [[NSMutableString alloc]initWithString:Path];
        [strPath appendFormat:@"OCUnitReports/Appunit.xml"];
        NSLog(@"strPath = %@",strPath);
		[[document XMLData] writeToFile:strPath atomically:NO];
        
	}
    
}

#pragma mark -
#pragma mark Notification Callbacks
- (void)testSuiteStarted:(NSNotification*)notification;
{
    SenTest *test = [notification test];
    self.currentSuiteElement = [GDataXMLElement elementWithName:@"testsuite"];
    [currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"name" stringValue:[test name]]];
}

- (void)testSuiteStopped:(NSNotification*)notification;
{
    SenTestSuiteRun *testSuiteRun = (SenTestSuiteRun *)[notification object];
	[instance writeResultFile];

    if (currentSuiteElement)
    {
        [currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"name" stringValue:[[testSuiteRun test] name]]];
		
		[currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"tests" stringValue:[NSString stringWithFormat:@"%d",[testSuiteRun testCaseCount]]]];
		
		[currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"errors" stringValue:[NSString stringWithFormat:@"%d",[testSuiteRun unexpectedExceptionCount]]]];
		
		[currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"failures" stringValue:[NSString stringWithFormat:@"%d", [testSuiteRun failureCount]]]];
        
		[currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"skipped" stringValue:@"0"]];
		
		[currentSuiteElement addAttribute:[GDataXMLNode attributeWithName:@"time" stringValue:[NSString stringWithFormat:@"%f",[testSuiteRun testDuration]]]];
		
        [suitesElement addChild:self.currentSuiteElement];
        
		self.currentSuiteElement = nil;
    }
}

- (void)testCaseStarted:(NSNotification*)notification;
{
    SenTest *test = [notification test];
    self.currentCaseElement = [GDataXMLElement elementWithName:@"testcase"];
    [currentCaseElement addAttribute:[GDataXMLNode attributeWithName:@"name" stringValue:[test name]]];
}

- (void)testCaseStopped:(NSNotification*)notification;
{
    SenTestCase *testCase = (SenTestCase *)[notification test];
    SenTestCaseRun *testCaseRun = (SenTestCaseRun *)[notification object];
	
	[currentCaseElement addAttribute:[GDataXMLNode attributeWithName:@"name" stringValue:NSStringFromSelector([testCase selector])]];
    
	[currentCaseElement addAttribute:[GDataXMLNode attributeWithName:@"classname" stringValue:NSStringFromClass([testCase class])]];
    
	[currentCaseElement addAttribute:[GDataXMLNode attributeWithName:@"time" stringValue:[NSString stringWithFormat:@"%f",[testCaseRun testDuration]]]];
	
    [currentSuiteElement addChild:self.currentCaseElement];
    self.currentCaseElement = nil;
}

- (void)testCaseFailed:(NSNotification*)notification;
{
    GDataXMLElement *failureElement = [GDataXMLElement elementWithName:@"failure"];
    
    SenTest *test = [notification test];
    
    SenTestCase *testCase = (SenTestCase *)[notification test];
    
    [failureElement addAttribute:[GDataXMLNode attributeWithName:@"name" stringValue:[test name]]];
    
    [failureElement addAttribute:[GDataXMLNode attributeWithName:@"classname" stringValue:NSStringFromClass([testCase class])]];
    
    [failureElement setStringValue:[[notification exception] description]];
    
    [currentCaseElement addChild:failureElement];
    
    
}

@end
