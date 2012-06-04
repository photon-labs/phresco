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