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
/** 
 * Base widget, All widgets should extends this Phresco widget and implement captureData and createContent methods.
 */
YUI.add("baseWidget", function(Y) {
    function BaseWidget(config) {
        BaseWidget.superclass.constructor.apply(this, arguments);
    }
 
    BaseWidget.NAME = "baseWidget";
    BaseWidget.ATTRS = {
        targetNode : {
            value : Y.Node.one(document.body)
        },
        config : {
            value : null
        },
        apiReference : {
            value : null
        },
        onSelectedListeners : {
            value : []
        },
        widgets: {
            value : []
        },
    };
    Y.extend(BaseWidget, Y.Widget, {
        
        initializer: function() {},
        
        destructor : function() {},
        
        captureData : function(jsonData) {},
        
        createContent : function(jsonSrc) {},
        
        getTargetNode : function() {
            var targetNodeName  =   this.get("targetNode");
            return Y.Node.one(targetNodeName);
        },
        setAppConfigData : function (configJSON) {
            this.set("config", configJSON);
        },
        getAppConfigData : function () {
            return this.get("config");
        },
        getAPIReference : function () {
            var ref = this.get("apiReference");
            return ref;
        },   
       _getValueFromSession : function (key, dflt) {
            if (!dflt)
                dflt = 0;
            var val = (this.getSessionValue(key) != null && this.getSessionValue(key) != undefined) ? this.getSessionValue(key) : dflt;
            return val;
       },
       _getParamValueFromSession : function (key, param) {
            var val = (this.getSessionValue(key) != null && this.getSessionValue(key) != undefined) ? this.getSessionValue(key) : 0;
            if (val ==0)
                return "";
            return "&" + param + "=" + val;
       },
       _isEmpty : function(str) {
            return (str == "" || str == null);
       },
        createElement : function (contentHTML) {
            var element = Y.Node.create(contentHTML);
            if (element == null) {
                element = Y.Node.create(this.getHTML4Content(contentHTML));
            }
            return element;
        },
        getHTML4Content : function (contentHTML5) { 
            var contentHTML4 = contentHTML5.replace('<nav', '<div');
            contentHTML4 = contentHTML4.replace('</nav', '</div');
            contentHTML4 = contentHTML4.replace('<header', '<div');
            contentHTML4 = contentHTML4.replace('</header', '</div');
            contentHTML4 = contentHTML4.replace('<section', '<div');
            contentHTML4 = contentHTML4.replace('</section', '</div');
            contentHTML4 = contentHTML4.replace('<aside', '<div');
            contentHTML4 = contentHTML4.replace('</aside', '</div');
            return contentHTML4;
        },
		loading : function(target){
			$(target).mask(" "); // purpose for loading image.
		},
         addSelectedListener : function(widget) {
            var listeners = this.get("onSelectedListeners");
            listeners = widget;
            this.set("widgets", widget);
            this.set("onSelectedListeners", listeners);
        },
		doLogout: function () {
            var widgetObj = this.obj;
            var apiRef = widgetObj.get("apiReference");
			console.info(this.data);
			apiRef.set("userId", 0);
			apiRef.set("userData", "");
			var listeners = widgetObj.get("onTestListener");
			//console.info(listeners);
            apiRef.logoutUser(listeners,this.data);
        },
        
        // Beyond this point is the MyWidget specific application and rendering logic
        /* Attribute state supporting methods (see attribute config above) */       
        _defAttrAVal : function() {
            // this.get("id") + "foo";
        },

        _setAttrA : function(attrVal, attrName) {
            // return attrVal.toUpperCase();
        },

        _getAttrA : function(attrVal, attrName) {
            // return attrVal.toUpperCase();
        },

        _validateAttrA : function(attrVal, attrName) {
            // return Lang.isString(attrVal);
        },

        /* Listeners, UI update methods */

        _afterAttrAChange : function(e) {
            /* Listens for changes in state, and asks for a UI update (controller). */

            // this._uiSetAttrA(e.newVal);
        },

        _uiSetAttrA : function(val) {
            /* Update the state of attrA in the UI (view) */

            // this._mynode.set("innerHTML", val);
        },

        _defMyEventFn : function(e) {
            // The default behavior for the "myEvent" event.
        }
    }); 

    Y.namespace("Base").BaseWidget = BaseWidget;
}, "3.3.0", {
    requires:["widget", "io", "json", "node", "substitute"]
});
