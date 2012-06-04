//Event = YUI.event,
YUI.add("helloWidget", function(Y) {
    function HelloWidget(config) {
        HelloWidget.superclass.constructor.apply(this, arguments);
    }

    HelloWidget.NAME = "helloWidget";

    HelloWidget.ATTRS = {
        targetNode : {
            value : []
        },
    };

    Y.extend(HelloWidget, Y.Base.BaseWidget, {
        initializer: function() {
            /*
             * initializer is part of the lifecycle introduced by 
             * the Base class. It is invoked during construction,
             * and can be used to setup instance specific state or publish events which
             * require special configuration (if they don't need custom configuration, 
             * events are published lazily only if there are subscribers).
             *
             * It does not need to invoke the superclass initializer. 
             * init() will call initializer() for all classes in the hierarchy.
             */
             /* this.publish("myEvent", {
                defaultFn: this._defMyEventFn,
                bubbles:false
             }); */

        },

        destructor : function() {
            /*
             * destructor is part of the lifecycle introduced by 
             * the Widget class. It is invoked during destruction,
             * and can be used to cleanup instance specific state.
             *
             * Anything under the boundingBox will be cleaned up by the Widget base class
             * We only need to clean up nodes/events attached outside of the bounding Box
             *
             * It does not need to invoke the superclass destructor. 
             * destroy() will call initializer() for all classes in the hierarchy.
             */
        },

        renderUI : function() {
            /*
             * renderUI is part of the lifecycle introduced by the
             * Widget class. Widget's renderer method invokes:
             *
             *     renderUI()
             *     bindUI()
             *     syncUI()
             *
             * renderUI is intended to be used by the Widget subclass
             * to create or insert new elements into the DOM. 
             */       
            this.createContent(this.getTargetNode());
        },

        bindUI : function() {
            /*
             * bindUI is intended to be used by the Widget subclass 
             * to bind any event listeners which will drive the Widget UI.
             * 
             * It will generally bind event listeners for attribute change
             * events, to update the state of the rendered UI in response 
             * to attribute value changes, and also attach any DOM events,
             * to activate the UI.
             */

            // this.after("attrAChange", this._afterAttrAChange);
        },

        syncUI : function() {
            /*
             * syncUI is intended to be used by the Widget subclass to
             * update the UI to reflect the initial state of the widget,
             * after renderUI. From there, the event listeners we bound above
             * will take over.
             */

            // this._uiSetAttrA(this.get("attrA"));
        },

        captureData : function(jsonData) {
           this.createContent(this.getTargetNode(), jsonData);
            var target = this.get("targetNode");
            $(target).unmask();
           },

        createContent : function(targetNode) {
           if (true) {
                targetNode.empty();
                var apiRef = this.get("apiReference");
                var url = apiRef.get("wsURLWithoutContext");
                var config = apiRef._getConfigData();
				var hWorldText = 'Hello World';
                targetNode.appendChild(hWorldText);  
              } else {
                var loading = this.createElement('<label>Loading...</label>');
                targetNode.appendChild(loading);
            }

            this.bindUI();
        },
        registerPop:function(){
            $('#registerpopup').css("display", "block");
            $('.wel_come').css("display", "block");
            //$('body').css('overflow','hidden');
        },
		 onUpdateListener: function(jsonData) {
            this.captureData(jsonData);
        },

        hideWidgets : function (hideWidgets) {
            this.set("hideWidgets", hideWidgets);
        },		
        closeFunction:function(){
            popup('none');
        }
    });

    Y.namespace("Base").HelloWidget = HelloWidget;
}, "3.3.0", {
    requires:["widget", "node", "substitute"]
});
