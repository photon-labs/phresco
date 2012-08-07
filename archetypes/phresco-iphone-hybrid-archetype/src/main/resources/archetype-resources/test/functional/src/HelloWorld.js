
try
    {
var target = UIATarget.localTarget();
var app = target.frontMostApp();
var window = app.mainWindow();
}
    catch(err)
    {
        UIALogger.logMessage("There is an error") ;
        if(UIALogger.logError())
        {
            UIATarget.localTarget().captureScreenWithName("Computer_test screenshots"); 
        }
    }
