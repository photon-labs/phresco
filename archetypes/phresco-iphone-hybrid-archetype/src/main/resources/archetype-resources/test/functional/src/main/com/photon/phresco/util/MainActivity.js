/*
 
 This is the Main class and it is common for all the Testcase
 It will execute firstly.
 Here we defined all the common functionalities. 
 
 */

#import "UIElements.js"


var application;
var target;
var mainwindow;
var j=10;


/*	This function will launch the application	*/


function launchingApplication(testname){
	
	target=UIATarget.localTarget();
	application=target.frontMostApp();
	mainwindow= application.mainWindow();	
	UIALogger.logMessage("Device Name: " + target.name() + "  OS Version:" + target.systemVersion());	
	
	
}


function captureScreenshot(name){

	target.captureScreenWithName(name); 
}


launchingApplication("Launching the application");


