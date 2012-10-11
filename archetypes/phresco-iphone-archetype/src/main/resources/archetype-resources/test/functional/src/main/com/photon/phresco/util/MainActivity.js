/*
 
 This is the Main class and it is common for all the Testcase
 It will execute firstly.
 Here we defined all the common functionalities. 
 
 */

#import "UIElements.js"


var application;
var target;
var mainwindow;
var j=30;


/*	This function will launch the application	*/


function launchingApplication(testname){
	
	target=UIATarget.localTarget();
	application=target.frontMostApp();
	mainwindow= application.mainWindow();
	target.delay(2);
	UIALogger.logMessage("Device Name: " + target.name() + "  OS Version:" + target.systemVersion());
	UIATarget.localTarget().logElementTree();  
	
}




/*	
 	It will wait for text till the given time
	It will tap the text if it exists in given time
	It will Capture the screen if text not exists in given time
 */


function clickOnText(text){
	
	var textpresent = isTextPresent(text);
	textpresent.tap();
}




/*
 It will wait for button till the given time
 It till tap the button if it exists in given time
 It will Capture the screen if button not exists in given time
 */

function clickOnButton(button){
	
	var buttoncheck = isButtonExist(button);
	buttoncheck.tap();
}




/*
 It will wait for button till the given time
 It will Capture the screen if button not exists in given time
 */



function isButtonExist(button){
	
	if(button!=null){
		for(var i=0;i<=j;i++){
			//var image = mainwindow.images()[0];
			
			var buttoncheck= mainwindow.buttons()[button];
			if(buttoncheck.isVisible() && buttoncheck.isEnabled()){
				target.delay(1);
				return buttoncheck;
			}
			else{
				if(i==j){
					UIALogger.logMessage("Assertion failed  Excepcted value is:"+ buttoncheck.name()+"     Getting value is: "+ button);
					UIALogger.logMessage("---Button Not Exists---");
					throw error;
					
				}
				else{
					UIALogger.logMessage("---Waiting for Button to exist---");
					target.delay(2);
				}
			}
		}
	}
	
	else{
		
		UIALogger.logMessage("-----------Button Value is empty-------------");
	}
}




/*
	It will wait for text till the given time
	It will Capture the screen if text not exists in given time
*/


function isTextPresent(text){
	if (text!=null){
		for(var i=0;i<=j;i++){
			var textpresent = mainwindow.staticTexts()[text];
			if(textpresent.isVisible() && textpresent.isEnabled()){
				return textpresent;
				//break;
			}
			else{
				if(i==j){
					UIALogger.logMessage("---Expected text not exists---");
					throw error;
				}
				else{
					UIALogger.logMessage("---wait for text present---");
					waitForFewSeconds(1);
				}
			}
		}
	}
	else{
		UIALogger.logMessage("---Text not exists---");
	}	
}




/* 	whenever the testcases fail it will capture the screenshot 	*/


function captureScreenshot(name){

	target.captureScreenWithName(name); 
}




/* It will delay the process for few seconds */


function waitForFewSeconds(seconds){
	
	if(seconds!==null){
		target.delay(seconds);
	}
	else{
		UIALogger.logMessage("---Shouldnot be null---"+seconds);
		throw error;
	}	
}




/*
	Here the function will check the screen coordinates and click on particular element.

*/


function clickOnScreen(xpath,ypath){
	
	try{
		if(xpath!==null && ypath!==null){
			UIALogger.logMessage("---clickOnScreen---");
			waitForFewSeconds(2);
			target.tap({x:xpath,y:ypath});
			waitForFewSeconds(2);
		}
		else{
			UIALogger.logMessage(" X value should not be: "+xpath.value+" And "+"  Y value should not be: "+ypath.value);
			}
	}
	catch(exception){
		throw exception;
	}
}



function clickOnTableCell(CellValues){
	
	try{	
		if(CellValues!==null){
			var table= mainwindow.tableViews()[0];
			var cellValue= table.cells()[CellValues].tap();
		}
		else{
		UIALogger.logMessage("---Shouldnot be null---");
		}	
	}
	catch(exception){
	throw exception;
	}
}

/**

 Here the function will click on search field in screen and enters the product name in field and it will click on search button.
*/

function textFields(textField ,textFieldValue){
	
	var textfields = mainwindow.textFields(); 
	waitForFewSeconds(2);
	textfields[textField].setValue(textFieldValue); 
	
	}

function passwordTextFields(passwordTextField ,passwordTextFieldValue){
	
	var passwordfields = mainwindow.secureTextFields(); 
	waitForFewSeconds(2);
	passwordfields[passwordTextField].setValue(passwordTextFieldValue); 
	
	}





launchingApplication("---Launching the application---");