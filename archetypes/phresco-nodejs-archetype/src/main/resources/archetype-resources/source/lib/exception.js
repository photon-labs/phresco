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