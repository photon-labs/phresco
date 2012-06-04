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
var path = require('path');
var root = path.join(__dirname, '../');

exports.expose = function(app, serverConfig) {
    var eshopRestApi = '/' + serverConfig.context + '/rest/api';
    var imagePath = '/' + serverConfig.context;
    var json = "";

    app.get('/' + serverConfig.context, function(req, res) {
        var html;
        try {
            var indexPath = root + 'views/index.html';
            html = fs.readFileSync(indexPath,'utf8'); 
        } catch (err) {
            console.log('Resource not found: ' + err);
        }

        res.writeHead(200, {"Content-Type": "text/html"});
        res.write(html);
        res.end();
    });

}