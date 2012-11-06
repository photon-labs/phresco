#import "../../../../../main/com/photon/phresco/util/MainActivity.js"
#import "../../../../../main/com/photon/phresco/util/UIElements.js"


function testTextPresent(testname){
	try{
		
		target.logElementTree();
		var scroll= target.frontMostApp().mainWindow();
		
		var scroll2 =scroll.scrollViews();
		
		var web= scroll2[0].webViews();
		
		var static=web[0].staticTexts();
	
		if(static[0].name()== Text){
			
			UIALogger.logPass(testname);
		
			}else{
				
				throw error;
				
				}
			
		
	}
	catch(error){
		UIALogger.logFail(testname);
		captureScreenshot(testname);
		UIALogger.logError(error);
		
		}
	}


testTextPresent("TextPresent");





