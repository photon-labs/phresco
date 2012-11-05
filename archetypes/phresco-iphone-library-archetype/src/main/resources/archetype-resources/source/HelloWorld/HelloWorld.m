//
//  HelloWorld.m
//  HelloWorld
//
//  Created by SHIVAKUMAR_CH on 11/5/12.
//  Copyright (c) 2012 Photon. All rights reserved.
//

#import "HelloWorld.h"

@implementation HelloWorld

-(void) createHelloWorldLabel
{
    UILabel *label = [[UILabel alloc] initWithFrame:CGRectMake([UIScreen mainScreen].applicationFrame.size.width /2, [UIScreen mainScreen].applicationFrame.size.height/2, 100, 40)];
    [label setBackgroundColor:[UIColor clearColor]];
    [label setTextColor:[UIColor blackColor]];
    [label setText:@"Hello World"];
    [self.view addSubview:label];
}

@end
