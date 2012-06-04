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
var fs = require('fs'); 
var querystring = require('querystring');
var url = require('url');
var xml = fs.readFileSync('public/resources/phresco-env-config.xml', 'utf8'); 

var xml2js = require('xml2js');
var parser = new xml2js.Parser();

exports.getConfigByName = function(currentEnv, type, name) {
    var json = {};

    parser.parseString(xml, function (err, result) {
        var envLength = result.environment.length;

        if (envLength > 1) {
            for (i = 0; i < envLength; i++) {
                var environment = result.environment[i];
                var env = environment["@"].name;
                var envDefault = environment["@"].default;

                if (currentEnv != undefined) {
                    if (currentEnv == env) {
                        json = getConfigJson(environment, type, name);
                    }
                } else if (envDefault == "true") {
                    json = getConfigJson(environment, type, name);
                }

            }
        } else {
            var environment = result.environment;
            var env = environment["@"].name;
            var envDefault = environment["@"].default;

            json = getConfigJson(environment, type, name);
        } 
    });

    return json;
}

function getConfigJson(environment, type, name) {
    var json = {};
    var configurations = environment[type];

    if (configurations.length > 1) {
        for (var i = 0; i < configurations.length; i++) {
            if (name != undefined) {
                var configName = configurations[i]["@"].name;
                if (configName == name) {
                    json = configurations[i];
                }
            } else {
                json = configurations[0];
            }
        }
    } else {
        json = configurations;
    }

    return json;
}

exports.sendJSONResponse = function(req, res, json) {
    var pquery = querystring.parse(url.parse(req.url).query);
    var callback = (pquery.callback ? pquery.callback : '');
    
    if (callback) {
        res.header('Content-Type', 'application/json');
        res.header('Charset', 'utf-8') 
        res.send(callback + '(' + json + ')');
    } else {
        res.header("Access-Control-Allow-Origin", "*");
        res.writeHead(200, {"Content-Type": "application/json"});
        res.write(json);
        res.end();
    }
}
