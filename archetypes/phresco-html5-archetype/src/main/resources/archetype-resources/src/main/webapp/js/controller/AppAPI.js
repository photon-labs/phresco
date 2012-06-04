/*
 * ###
 * Archetype - phresco-html5-archetype
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
YUI.add("appAPI", function(Y) {
    function AppAPI(config) {
        AppAPI.superclass.constructor.apply(this, arguments);
    }

    AppAPI.NAME = "appAPI";
    
    var callbackData = 'callbackData';  

    AppAPI.ATTRS = {
        config : {
            value : null
        },
        
    };

    Y.extend(AppAPI, Y.Base, {
        
        initializer: function() {},
        destructor : function() {},
        getWSConfig : function () {
            var cfg = {
                method: 'GET',               
                headers: {
                    'Content-Type': 'application/json'
                },
                on: {
                    complete: this.jspWSConfig
                },
                arguments : {
                    complete: ''
                },
                sync: true,
                context : this
            };
            var url = "environment.jsp";
            Y.io(url, cfg);
        },
		
        /*getConfig : function (uiWidgetsToPopulate) {
            var responseHandler = this.populateResponseToWidgets;
            var appAPI = this;
            var wsURL = this.get("wsURL");
            $.ajax({
                type: 'GET',
                dataType: 'jsonp',
                data: "somedata=blahblah",                      
                jsonp: 'callback',
                url: wsURL + '/rest/api/config?callback=?',                     
                success: function(data) {
                    appAPI.set("config", data);             
                },
                error: function(msg) {    
                    console.info('Message = ', msg);
                }
            });
        },*/
       
        /***
         * Common response handler method. Push the response json data to the corresponding widgets. 
         */
        pushDataToWidget : function (id, data, widgetsToPopulate) {
            widgetsToPopulate = widgetsToPopulate.complete;
            var responseText = data.responseText;

            for (var i = 0; i < widgetsToPopulate.length; i++) {
                var responseData = Y.JSON.parse(responseText)
                widgetsToPopulate[i].partialRefresh(this._responseValidator(responseData));
            }
        },
        populateResponseToWidgets : function (responseData, callbackArgs) {
            widgetsToPopulate = callbackArgs.complete;
            for (var i = 0; i < widgetsToPopulate.length; i++) {
                if (responseData != null) {
                    widgetsToPopulate[i].captureData(responseData);
                } else {
                    widgetsToPopulate[i].captureData("");
                }
            }
        },

        onFailure : function(transactionid, response, arguments) {
          // transactionid : The transaction's ID.
          // response: The response object.  Only status and
          //           statusText are populated when the
          //           transaction is terminated due to abort
          //           or timeout.  The status will read
          //           0, and statusText will return "timeout"
          //           or "abort" depending on the mode of
          //           termination.
          // arguments: String "Transaction Failed".
        },
         jspWSConfig : function(id, data, callbackArgs) {
            var currentEnv = data.responseText;      
            this.set("currentEnv", currentEnv.environment);
            this.setWSConfig();
            
        },
        
		setWSConfig : function() {
			if (window.XMLHttpRequest)
			  {// code for IE7+, Firefox, Chrome, Opera, Safari
			  xmlhttp=new XMLHttpRequest();
			  }
			else
			  {// code for IE6, IE5
			  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
			  }
            var currentEnv = this.get("currentEnv");
            //var currentEnv = "Dev";
            			
			xmlhttp.open("GET","resources/phresco-env-config.xml", false);
			xmlhttp.send();
			xmlDoc = xmlhttp.responseXML;	
			
            var type = "WebService";
            var name = "";
            var configdata = this.getConfigByName(currentEnv, type, name);
           // console.info("configdata", configdata);    
            if(configdata != undefined){ // when this project have content   
			var host = configdata.host;
            var port = configdata.port;
            var protocol = configdata.protocol;
            var context = configdata.context;
            var username = configdata.username;
            var password = configdata.password;
			}

            //this.set("wsConfig", wsConfig);
            var urlWithoutContext = protocol + '://' + host + ':' + port;
            var url = protocol + '://' + host + ':' + port + '/' + context;            
            this.set("wsURL", url);
            this.set("wsURLWithoutContext", urlWithoutContext); 
        },

       

        // three param for envtype, configtype, configname. for example currentEnv = "development", type ="server",name ="myserver"
        getConfigByName : function (currentEnv, type, name) {
            var environments = xmlDoc.documentElement.getElementsByTagName("environment"); 
            for (var i = 0; i < environments.length; i++) {
                var envNode = environments[i];
                var env = envNode.getAttribute("name");
                var envDefault = envNode.getAttribute("default");

                if (currentEnv != undefined && currentEnv != "") {
                    if (currentEnv == env) {
                        return this.getConfigJson(envNode, type, name);
                    }
                } else if (envDefault == "true") {
                    return this.getConfigJson(envNode, type, name);
                }
            }
        },
        getConfigJson : function(envNode, type, name) {
            var nodes = envNode.childNodes;
            var json = {};

            for (var i = 0; i < nodes.length; i++) {
                var configNode = nodes[i];
                var configNodeName = configNode.nodeName;

                if (configNodeName == type && name != undefined && configNodeName != "#text") {
                    var configName = configNode.getAttribute("name");
                    if (configName == name) {
                        var xmlString = (new XMLSerializer()).serializeToString(configNode);
                        json = $.xml2json(xmlString);
                        return json;
                    } else if (name == "") {
                        var xmlString = (new XMLSerializer()).serializeToString(configNode);
                        json = $.xml2json(xmlString);
                        return json;
                    }
                } else if (configNodeName == type && configNodeName != "#text") {
                    var xmlString = (new XMLSerializer()).serializeToString(configNode);
                    json = $.xml2json(xmlString);
                    return json;
                }
            }

            return json;
        },
            

        _showLoaderForWidgets : function(widgetsToPopulate) {
            for(var i = 0; i<widgetsToPopulate.length; i++) {
                widgetsToPopulate[i].showLoader();
            }
        },
        _responseValidator : function(responseData) {
            if (responseData.Success != undefined
                    && responseData.Success.toUpperCase() == "FALSE") {
            }
            return responseData;
        },
        _getConfigData : function() {
            return this.get("config");
        },
        _getWebImageURL : function() {
            return this.get("config").web;
        },
        
    });
    
    Y.namespace("Base").AppAPI = AppAPI;
    
                    
}, "3.4.1", {
    requires:["base", "io", "json", "node", "substitute"]
});
