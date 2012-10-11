#import "../../../../../main/com/photon/phresco/util/MainActivity.js"
#import "../../../../../main/com/photon/phresco/util/UIElements.js"

function helloWorld_Test(methodName){

var HelloWorld="Hello World"
try
    {  
		isTextPresent(HelloWorld);
		UIATarget.localTarget().logElementTree(); 
		UIALogger.logPass("logSuccess"); 
		
		
}
    catch(err)
    {
        UIALogger.logMessage("There is an error") ;
		captureScreenshot("HelloWorld");
		
    }
}

helloWorld_Test("HelloWorld_Test");
