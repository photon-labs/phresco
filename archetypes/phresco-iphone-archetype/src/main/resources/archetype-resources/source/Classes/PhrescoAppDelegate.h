//
//  iShopAppDelegate.h
//
//  Created by PHOTON on 15/04/11.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>



@class RootViewController;

@interface iShopAppDelegate : NSObject <UIApplicationDelegate, UITextViewDelegate> {
   
	UIWindow *window;

   	
    UIAlertView *alertView;
    
    RootViewController *viewController;
		
}

@property (nonatomic, retain) IBOutlet UIWindow *window;
@property (nonatomic, retain) IBOutlet RootViewController *viewController;

@property (nonatomic, retain) NSDate *date;	
@property (nonatomic, retain) UIAlertView *alertView;


@end

