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

