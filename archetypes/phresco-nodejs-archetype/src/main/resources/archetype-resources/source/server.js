/*
 * ###
 * Archetype - phresco-nodejs-archetype
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */
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
