var utility = require('./lib/utility');
var configure = require('./lib/configure');
var services = require('./lib/services');
var http = require("http");
var currentEnv = process.argv[2];

var serverConfig = utility.getConfigByName(currentEnv, 'Server');

//console.info('serverConfig = ', serverConfig);

var app = require('express').createServer();
configure.appConfigure(app);
services.expose(app, serverConfig);

app.listen(serverConfig.port);

/* var serverUrl = serverConfig.protocol + '://' + serverConfig.host 
	+ ':' + serverConfig.port  + '/' + serverConfig.context;
	console.log('Server running at ' + serverUrl); */

var options = {
  host: serverConfig.host,
  port: serverConfig.port,
  path: '/' + serverConfig.context
};

http.get(options, function(res) {
  if (res.statusCode == 200) {
    //console.log("success");
	 var serverUrl = serverConfig.protocol + '://' + serverConfig.host 
	+ ':' + serverConfig.port  + '/' + serverConfig.context;
	console.log('Server running at ' + serverUrl);
  }
}).on('error', function(e) {
  console.log("Got error: " + e.message);
});
