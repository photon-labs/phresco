var utility = require('./lib/utility');
var configure = require('./lib/configure');
var services = require('./lib/services');
var http = require("http");
var currentEnv = process.argv[2];

var serverConfig = utility.getConfigByName(currentEnv, 'Server');
var serverConfig = utility.getConfigByName(currentEnv, 'Server');
require('dns').lookup(require('os').hostname(), function (err, address, fam) {
	var ipaddress = address;
	if (ipaddress == serverConfig.host || serverConfig.host == "localhost") {
		var app = require('express').createServer();
		configure.appConfigure(app);
		services.expose(app, serverConfig);
		app.listen(serverConfig.port);
		 var serverUrl = serverConfig.protocol + '://' + serverConfig.host 
		+ ':' + serverConfig.port  + '/' + serverConfig.context;
		console.log('Server running at ' + serverUrl);
	} else {
		  console.log("Server startup failed");
		  console.log("Invaldid IP Address is Configured");		
	}
})