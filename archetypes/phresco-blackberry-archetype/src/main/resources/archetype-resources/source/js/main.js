var serverUrl;
var req;
var serviceConnFlag = false;
function init() {
	var mObj = new configParse(ENV_CONFIG_JSON, "Production" , "Server");
	var serverConfig = mObj.parseString();
	
	var port = (serverConfig.port.length===0?"":':'+serverConfig.port);
	var context = (serverConfig.context.length===0?"":'/'+serverConfig.context);
	var additionalContext = (serverConfig.additional_context.length===0?"":+serverConfig.additional_context);
	serverUrl = serverConfig.protocol + '://' + serverConfig.host + port  + context;// + additionalContext;
	
	loadURL();
}

function loadURL() {
	// 	See if we are in coverage
	if (!blackberry.system.hasDataCoverage()) {
	   alert("Network not available");
	   blackberry.app.exit();
	}
	else{ 
	   
	   try {
			req = new XMLHttpRequest();
			req.open('GET', serverUrl, false);
			req.onreadystatechange = handleStateChange;
			req.send(null);
		} catch (err) {
			//alert("Error: " , err.message);
		}
		if(!serviceConnFlag){
			alert("Service not available");
			blackberry.app.exit();
		}
		else{
			//alert("You are on the " + blackberry.network + " Network");
			window.location.href=serverUrl;
			//window.open (serverUrl, "_self");
		}
	}
}

function handleStateChange() {
	//alert("req.readyState = " + req.readyState + "\nreq.status = " + req.status);
	if ((req.readyState == 4) && (req.status == 200 || req.status == 0)) {
		serviceConnFlag = true;
	}
}