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