var utility = require('./utility');
var events = require('events')
emitter = new events.EventEmitter()

function PhrescoException(errorNo, errorMessage, res) {
    if (res != undefined && res != ''); {
        sendErrorResponse(res, errorNo, errorMessage)
    }
    throw new Error(errorNo + ':' + errorMessage)
}

exports.executeException = function(arg, errorNo, errorMessage, res, callback) {
    if (arg === 1) {
        PhrescoException(errorNo, errorMessage, res)
        callback()
    } else {
        function onTick() {
            try {
                PhrescoException(errorNo, errorMessage, res)
            } catch(err) {
                emitter.emit('myerror', err)
                return
            }
            callback()
        }
        process.nextTick(onTick)
    }
}

emitter.on('myerror', function(err) { console.log(err) })

function sendErrorResponse(res, errorNo, errorMessage) {
	var errorJson = {};
	errorJson.type = 'failure';
	errorJson.message = errorMessage;

	res.header("Access-Control-Allow-Origin", "*");
    res.writeHead(200, {"Content-Type": "application/json"});
    res.write(JSON.stringify(errorJson));
    res.end();
}