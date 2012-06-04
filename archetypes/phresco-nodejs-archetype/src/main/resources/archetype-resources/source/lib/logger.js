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
var events = require('events');
var util = require('util');
var fs = require('fs');

function update_log(data) {

  var path = "access_log";
  var now = new Date();
  var dateAndTime = now.toUTCString();
  stream = fs.createWriteStream(path, {
    'flags': 'a+',
    'encoding': 'utf8',
    'mode': 0644
  });

  stream.write(dateAndTime + " ", 'utf8');
  //stream.write(request.connection.remoteAddress + ": ", 'utf8')
  //stream.write(request.method + " ", 'utf8')
  //stream.write(request.url + "\n", 'utf8');
  stream.write(data);
  stream.end();
}

exports.update_log = update_log;

// The Thing That Emits Event
Logger = function() {
  	events.EventEmitter.call(this);
	this.log = function(data){
		//update_log(data);
		this.emit('log', data);
	}
};

util.inherits(Logger, events.EventEmitter);

// The thing that listens to, and handles, those events
Listener = function(){
  	this.logHandler =  function(data){
      	console.log("** log event handled");
		console.log(data);
  	}
};

// The thing that drives the two.
var logger = new Logger();
var listener = new Listener(logger);
logger.on('log', listener.logHandler);

logger.log('Exception handled');

logger.log('My Exception handled');

logger.log('New Exception handled');