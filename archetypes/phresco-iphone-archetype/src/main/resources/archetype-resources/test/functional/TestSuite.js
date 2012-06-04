function test()
{
var app=UIATarget.localTarget();
var target = app.frontMostApp();
target.mainWindow();
}
UIALogger.logPass("Sample test");
test();