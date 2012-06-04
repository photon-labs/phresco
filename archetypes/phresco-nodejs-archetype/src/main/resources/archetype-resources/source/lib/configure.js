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
var express = require('express');
var path = require('path');
var root = path.join(__dirname, '../');

function appConfigure(app) {
    app.configure(function(){
        app.use(express.methodOverride());
        app.use(express.bodyParser());
        app.use(express.cookieParser());
        app.use(express.session({secret: "phresco" }));
        app.use(app.router);
        app.set('view engine', 'jade');
        app.set('views', root + 'views');
        app.use(express.static(root + '/public'));
        app.use(express.errorHandler({ dumpExceptions: true, showStack: true }));
        //app.use(form({ keepExtensions: true }));
        app.enable("jsonp callback", true);
    });
}

exports.appConfigure = appConfigure;

