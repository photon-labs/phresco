/*
 * ###
 * Framework Web Archive
 * %%
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * %%
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
    * o------------------------------------------------------------------------------o
    * | This file is part of the RGraph package - you can learn more at:             |
    * |                                                                              |
    * |                          http://www.rgraph.net                               |
    * |                                                                              |
    * | This package is licensed under the RGraph license. For all kinds of business |
    * | purposes there is a small one-time licensing fee to pay and for non          |
    * | commercial  purposes it is free to use. You can read the full license here:  |
    * |                                                                              |
    * |                      http://www.rgraph.net/LICENSE.txt                       |
    * o------------------------------------------------------------------------------o
    */
    
    if (typeof(RGraph) == 'undefined') RGraph = {};

    /**
    * The bar chart constructor
    * 
    * @param object canvas The canvas object
    * @param array  data   The chart data
    */
    RGraph.Funnel = function (id, data)
    {
        // Get the canvas and context objects
        this.id                = id;
        this.canvas            = document.getElementById(id);
        this.context           = this.canvas.getContext ? this.canvas.getContext("2d") : null;
        this.canvas.__object__ = this;
        this.type              = 'funnel';
        this.coords            = [];
        this.isRGraph          = true;


        /**
        * Compatibility with older browsers
        */
        RGraph.OldBrowserCompat(this.context);


        // Check for support
        if (!this.canvas) {
            alert('[FUNNEL] No canvas support');
            return;
        }

        /**
        * The funnel charts properties
        */
        this.properties = {
            'chart.strokestyle':           'black',
            'chart.gutter.left':           25,
            'chart.gutter.right':          25,
            'chart.gutter.top':            25,
            'chart.gutter.bottom':         25,
            'chart.labels':                null,
            'chart.labels.sticks':         false,
            'chart.title':                 '',
            'chart.title.background':       null,
            'chart.title.hpos':             null,
            'chart.title.vpos':            null,
            'chart.title.bold':             true,
            'chart.title.font':             null,
            'chart.colors':                ['red', 'green', 'gray', 'blue', 'black', 'gray'],
            'chart.text.size':             10,
            'chart.text.boxed':            true,
            'chart.text.halign':           'left',
            'chart.text.color':            'black',
            'chart.text.font':             'Arial',
            'chart.contextmenu':           null,
            'chart.shadow':                false,
            'chart.shadow.color':          '#666',
            'chart.shadow.blur':           3,
            'chart.shadow.offsetx':        3,
            'chart.shadow.offsety':        3,
            'chart.key':                    [],
            'chart.key.background':         'white',
            'chart.key.position':           'graph',
            'chart.key.halign':             'right',
            'chart.key.shadow':             false,
            'chart.key.shadow.color':       '#666',
            'chart.key.shadow.blur':        3,
            'chart.key.shadow.offsetx':     2,
            'chart.key.shadow.offsety':     2,
            'chart.key.position.gutter.boxed': true,
            'chart.key.position.x':         null,
            'chart.key.position.y':         null,
            'chart.key.color.shape':        'square',
            'chart.key.rounded':            true,
            'chart.key.linewidth':          1,
            'chart.key.colors':             null,
            'chart.tooltips':               null,
            'chart.tooltips.effect':        'fade',
            'chart.tooltips.css.class':     'RGraph_tooltip',
            'chart.highlight.stroke':       'black',
            'chart.highlight.fill':         'rgba(255,255,255,0.5)',
            'chart.tooltips.highlight':     true,
            'chart.annotatable':           false,
            'chart.annotate.color':        'black',
            'chart.zoom.factor':           1.5,
            'chart.zoom.fade.in':          true,
            'chart.zoom.fade.out':         true,
            'chart.zoom.factor':           1.5,
            'chart.zoom.fade.in':          true,
            'chart.zoom.fade.out':         true,
            'chart.zoom.hdir':             'right',
            'chart.zoom.vdir':             'down',
            'chart.zoom.frames':            25,
            'chart.zoom.delay':             16.666,
            'chart.zoom.shadow':            true,
            'chart.zoom.mode':              'canvas',
            'chart.zoom.thumbnail.width':   75,
            'chart.zoom.thumbnail.height':  75,
            'chart.zoom.thumbnail.fixed':   false,
            'chart.zoom.background':        true,
            'chart.zoom.action':            'zoom',
            'chart.resizable':              false,
            'chart.taper':                  true
        }

        // Store the data
        this.data = data;

        /**
        * Set the .getShape commonly named method
        */
        this.getShape = this.getSegment;
    }


    /**
    * A setter
    * 
    * @param name  string The name of the property to set
    * @param value mixed  The value of the property
    */
    RGraph.Funnel.prototype.Set = function (name, value)
    {
        this.properties[name.toLowerCase()] = value;
    }


    /**
    * A getter
    * 
    * @param name  string The name of the property to get
    */
    RGraph.Funnel.prototype.Get = function (name)
    {
        return this.properties[name.toLowerCase()];
    }


    /**
    * The function you call to draw the bar chart
    */
    RGraph.Funnel.prototype.Draw = function ()
    {
        /**
        * Fire the onbeforedraw event
        */
        RGraph.FireCustomEvent(this, 'onbeforedraw');

        /**
        * Clear all of this canvases event handlers (the ones installed by RGraph)
        */
        RGraph.ClearEventListeners(this.id);
        
        /**
        * This is new in May 2011 and facilitates indiviual gutter settings,
        * eg chart.gutter.left
        */
        this.gutterLeft   = this.Get('chart.gutter.left');
        this.gutterRight  = this.Get('chart.gutter.right');
        this.gutterTop    = this.Get('chart.gutter.top');
        this.gutterBottom = this.Get('chart.gutter.bottom');

        // This stops the coords array from growing
        this.coords = [];

        RGraph.DrawTitle(this.canvas, this.Get('chart.title'), this.gutterTop, null, this.Get('chart.title.size') ? this.Get('chart.title.size') : this.Get('chart.text.size') + 2);
        this.DrawFunnel();
        
        
        /**
        * Setup the context menu if required
        */
        if (this.Get('chart.contextmenu')) {
            RGraph.ShowContext(this);
        }
        
        /**
        * The tooltip handler
        */
        if (this.Get('chart.tooltips')) {
        
            RGraph.Register(this);

            var canvas_onclick_func = function (e)
            {
                RGraph.Redraw();

                var e       = RGraph.FixEventObject(e);
                var canvas  = e.target;
                var context = canvas.getContext('2d');
                var obj     = canvas.__object__;

                var mouseCoords = RGraph.getMouseXY(e);
                var coords      = obj.coords;
                var x           = mouseCoords[0];
                var y           = mouseCoords[1];
                var segment     = obj.getSegment(e);
                
                if (segment) {
                
                    var idx = segment[2];

                    // Is there a tooltip defined?
                    if (!obj.Get('chart.tooltips')[idx] && typeof(obj.Get('chart.tooltips')) != 'function') {
                        return;
                    }

                    context.beginPath();

                    RGraph.NoShadow(obj);

                    context.strokeStyle = obj.Get('chart.highlight.stroke');
                    context.fillStyle   = obj.Get('chart.highlight.fill');

                    context.moveTo(coords[idx][0], coords[idx][1]);
                    context.lineTo(coords[idx][2], coords[idx][3]);
                    context.lineTo(coords[idx][4], coords[idx][5]);
                    context.lineTo(coords[idx][6], coords[idx][7]);
                    context.closePath();

                    context.stroke();
                    context.fill();
                
                    /**
                    * Draw the key again for in-graph keys so they don't appear "under" the highlight
                    */
                    if (obj.Get('chart.key').length && obj.Get('chart.key.position') == 'graph') {
                        RGraph.DrawKey(obj, obj.Get('chart.key'), obj.Get('chart.colors'));
                    }
                    
                    /**
                    * Redraw the labels if necessary
                    */
                    if (obj.Get('chart.labels')) {
                        obj.DrawLabels();
                    }

                    /**
                    * Get the tooltip text
                    */
                    if (typeof(obj.Get('chart.tooltips')) == 'function') {
                        var text = obj.Get('chart.tooltips')(idx);
                    
                    } else if (typeof(obj.Get('chart.tooltips')) == 'object' && typeof(obj.Get('chart.tooltips')[idx]) == 'function') {
                        var text = obj.Get('chart.tooltips')[idx](idx);
                    
                    } else if (typeof(obj.Get('chart.tooltips')) == 'object') {
                        var text = obj.Get('chart.tooltips')[idx];

                    } else {
                        var text = '';
                    }

                    // Show the tooltip
                    RGraph.Tooltip(canvas, text, e.pageX, e.pageY, idx);
    
                    // Stop the event propagating
                    e.stopPropagation();
                }
            }
            this.canvas.addEventListener('click', canvas_onclick_func, false);
            RGraph.AddEventListener(this.id, 'click', canvas_onclick_func);
            
            /**
            * Onmousemove event handler
            */
            var canvas_onmousemove_func = function (e)
            {
                var e = RGraph.FixEventObject(e);

                var canvas = e.target;
                var context = canvas.getContext('2d');
                var obj     = canvas.__object__;
                var overFunnel = false;
                var coords = obj.coords;

                var mouseCoords = RGraph.getMouseXY(e);
                var x           = mouseCoords[0];
                var y           = mouseCoords[1];
                var segment     = obj.getSegment(e);

                if (segment) {
                
                    var idx = segment[2];
                    
                    // Is there a tooltip defined?
                    if (obj.Get('chart.tooltips')[idx] || typeof(obj.Get('chart.tooltips')) == 'function') {

                        overFunnel = true;
                        canvas.style.cursor = 'pointer';
        
                        // Stop the event propagating
                        e.stopPropagation();
                        
                    }
                }
                
                if (!overFunnel) {
                    canvas.style.cursor = 'default';
                    canvas.style.cursor = 'default';
                }
            }
            this.canvas.addEventListener('mousemove', canvas_onmousemove_func, false);
            RGraph.AddEventListener(this.id, 'mousemove', canvas_onmousemove_func);
        }


        /**
        * Draw the labels on the chart
        */
        this.DrawLabels();
        
        /**
        * If the canvas is annotatable, do install the event handlers
        */
        if (this.Get('chart.annotatable')) {
            RGraph.Annotate(this);
        }
        
        /**
        * This bit shows the mini zoom window if requested
        */
        if (this.Get('chart.zoom.mode') == 'thumbnail'|| this.Get('chart.zoom.mode') == 'area') {
            RGraph.ShowZoomWindow(this);
        }

        
        /**
        * This function enables resizing
        */
        if (this.Get('chart.resizable')) {
            RGraph.AllowResizing(this);
        }
        
        /**
        * Fire the RGraph ondraw event
        */
        RGraph.FireCustomEvent(this, 'ondraw');
    }


    /**
    * This function actually draws the chart
    */
    RGraph.Funnel.prototype.DrawFunnel = function ()
    {
        var context   = this.context;
        var canvas    = this.canvas;
        var width     = this.canvas.width - this.gutterLeft - this.gutterRight;
        var height    = this.canvas.height - this.gutterTop - this.gutterBottom;
        var total     = RGraph.array_max(this.data);
        var accheight = this.gutterTop;


        /**
        * Loop through each segment to draw
        */
        
        // Set a shadow if it's been requested
        if (this.Get('chart.shadow')) {
            context.shadowColor   = this.Get('chart.shadow.color');
            context.shadowBlur    = this.Get('chart.shadow.blur');
            context.shadowOffsetX = this.Get('chart.shadow.offsetx');
            context.shadowOffsetY = this.Get('chart.shadow.offsety');
        }

        for (i=0; i<this.data.length; ++i) {

            i = Number(i);

            var firstvalue = this.data[0];
            var firstwidth = (firstvalue / total) * width;
            var curvalue   = this.data[i];
            var curwidth   = (curvalue / total) * width;
            var curheight  = height / this.data.length;
            var halfCurWidth = (curwidth / 2);
            var nextvalue  = this.data[i + 1] ?  this.data[i + 1] : 0;
            var nextwidth  = this.data[i + 1] ? (nextvalue / total) * width : 0;
            var halfNextWidth = (nextwidth / 2);
            var center     = this.gutterLeft + (firstwidth / 2);

            /**
            * First segment
            */
            if (i == 0) {
                var x1 = center - halfCurWidth;
                var y1 = this.gutterTop;
                var x2 = center + halfCurWidth;
                var y2 = this.gutterTop;
                var x3 = center + halfNextWidth;
                var y3 = accheight + curheight;
                var x4 = center - halfNextWidth;
                var y4 = accheight + curheight;

            /**
            * Subsequent segments
            */
            } else if (this.Get('chart.taper') || i < (this.data.length - 1)) {
                var x1 = center - halfCurWidth;
                var y1 = accheight;
                var x2 = center + halfCurWidth;
                var y2 = accheight;
                var x3 = center + halfNextWidth;
                var y3 = accheight + curheight;
                var x4 = center - halfNextWidth;
                var y4 = accheight + curheight;
            }

            /**
            * Set the fill colour. If i is over 0 then don't use an offset
            */
            if (document.all && this.Get('chart.shadow')) {
                this.DrawIEShadow([x1, y1, x2, y2, x3, y3, x4, y4], i > 0 && this.Get('chart.shadow.offsety') < 0);
            }

            context.strokeStyle = this.Get('chart.strokestyle');
            context.fillStyle   = this.Get('chart.colors')[i];

            if (this.Get('chart.taper') || (i < this.data.length - 1) ) {

                context.beginPath();
                    context.moveTo(x1, y1);
                    context.lineTo(x2, y2);
                    context.lineTo(x3, y3);
                    context.lineTo(x4, y4);
                context.closePath();

                /**
                * Store the coordinates
                */
                this.coords.push([x1, y1, x2, y2, x3, y3, x4, y4]);
            }


            // The redrawing if the shadow is on will do the stroke
            if (!this.Get('chart.shadow')) {
                context.stroke();
            }

            context.fill();

            accheight += curheight;
        }

        /**
        * If the shadow is enabled, redraw every segment, in order to allow for shadows going upwards
        */
        if (this.Get('chart.shadow')) {
        
            RGraph.NoShadow(this);
        
            for (i=0; i<this.coords.length; ++i) {
            
                context.strokeStyle = this.Get('chart.strokestyle');
                context.fillStyle = this.Get('chart.colors')[i];
        
                context.beginPath();
                    context.moveTo(this.coords[i][0], this.coords[i][1]);
                    context.lineTo(this.coords[i][2], this.coords[i][3]);
                    context.lineTo(this.coords[i][4], this.coords[i][5]);
                    context.lineTo(this.coords[i][6], this.coords[i][7]);
                context.closePath();
                
                context.stroke();
                context.fill();
            }
        }
        
        /**
        * Lastly, draw the key if necessary
        */
        if (this.Get('chart.key') && this.Get('chart.key').length) {
            RGraph.DrawKey(this, this.Get('chart.key'), this.Get('chart.colors'));
        }
    }
    
    /**
    * Draws the labels
    */
    RGraph.Funnel.prototype.DrawLabels = function ()
    {
        /**
        * Draws the labels
        */
        if (this.Get('chart.labels') && this.Get('chart.labels').length > 0) {

            var context = this.context;
            var font    = this.Get('chart.text.font');
            var size    = this.Get('chart.text.size');
            var color   = this.Get('chart.text.color');
            var labels  = this.Get('chart.labels');
            var halign  = this.Get('chart.text.halign') == 'left' ? 'left' : 'center';
            var bgcolor = this.Get('chart.text.boxed') ? 'white' : null;

            if (typeof(this.Get('chart.labels.x')) == 'number') {
                var x = this.Get('chart.labels.x');
            } else {
                var x = halign == 'left' ? (this.gutterLeft - 15) : ((this.canvas.width - this.gutterLeft - this.gutterRight) / 2) + this.gutterLeft;
            }

            for (var j=0; j<this.coords.length; ++j) {  // MUST be "j"

                context.beginPath();
                
                // Set the color back to black
                context.strokeStyle = 'black';
                context.fillStyle = color;
                
                // Turn off any shadow
                RGraph.NoShadow(this);
                
                var label = labels[j];

                RGraph.Text(context,
                            font,
                            size,
                            x,
                            this.coords[j][1],
                            label,
                            'center',
                            halign,
                            this.Get('chart.text.boxed'),
                            null,
                            bgcolor);
                
                if (this.Get('chart.labels.sticks')) {
                    /**
                    * Measure the text
                    */
                    this.context.font = size + 'pt ' + font;
                    var labelWidth    = this.context.measureText(label).width;
    
                    /**
                    * Draw the horizontal indicator line
                    */
                    this.context.beginPath();
                        this.context.strokeStyle = 'gray';

                        this.context.moveTo(x + labelWidth + 10, AA(this, this.coords[j][1]));
                        this.context.lineTo(this.coords[j][0] - 10, AA(this, this.coords[j][1]));
                    this.context.stroke();
                }
            }



            /**
            * This draws the last labels if defined
            */
            var lastLabel = labels[j];

            if (lastLabel) {
                
                RGraph.Text(context, font, size,x,this.coords[j - 1][5],lastLabel,'center',halign,this.Get('chart.text.boxed'),null,bgcolor);

                if (this.Get('chart.labels.sticks')) {
                    /**
                    * Measure the text
                    */
                    this.context.font = size + 'pt ' + font;
                    var labelWidth    = this.context.measureText(lastLabel).width;
    
                    /**
                    * Draw the horizontal indicator line
                    */
                    this.context.beginPath();
                        this.context.strokeStyle = 'gray';
                        this.context.moveTo(x + labelWidth + 10, AA(this, this.coords[j - 1][7]));
                        this.context.lineTo(this.coords[j - 1][0] - 10, AA(this, this.coords[j - 1][7]));
                    this.context.stroke();
                }
            }
        }
    }


    /**
    * This function is used by MSIE only to manually draw the shadow
    * 
    * @param array coords The coords for the bar
    */
    RGraph.Funnel.prototype.DrawIEShadow = function (coords, noOffset)
    {
        var prevFillStyle = this.context.fillStyle;
        var offsetx = this.Get('chart.shadow.offsetx');
        var offsety = this.Get('chart.shadow.offsety');
        var context = this.context;

        context.lineWidth = 1;
        context.fillStyle = this.Get('chart.shadow.color');
        
        // Draw the shadow
        context.beginPath();
            context.moveTo(coords[0] + (noOffset ? 0 : offsetx), coords[1] + (noOffset ? 0 : offsety));
            context.lineTo(coords[2] + (noOffset ? 0 : offsetx), coords[3] + (noOffset ? 0 : offsety));
            context.lineTo(coords[4] + (noOffset ? 0 : offsetx), coords[5] + (noOffset ? 0 : offsety));
            context.lineTo(coords[6] + (noOffset ? 0 : offsetx), coords[7] + (noOffset ? 0 : offsety));
        context.closePath();
        
        context.fill();
        

        
        // Change the fillstyle back to what it was
        this.context.fillStyle = prevFillStyle;
    }


    /**
    * Gets the appropriate segment that has been highlighted
    */
    RGraph.Funnel.prototype.getSegment = function (e)
    {
        var canvas      = e.target;
        var obj         = canvas.__object__;
        var mouseCoords = RGraph.getMouseXY(e);
        var coords = obj.coords;
        var x = mouseCoords[0];
        var y = mouseCoords[1];
        
        

        for (i=0; i<coords.length; ++i) {
            if (
                   x > coords[i][0]
                && x < coords[i][2]
                && y > coords[i][1]
                && y < coords[i][5]
               ) {
               
                /**
                * Handle the right corner
                */
                if (x > coords[i][4]) {
                    var w1 = coords[i][2] - coords[i][4];
                    var h1 = coords[i][5] - coords[i][3];;
                    var a1 = Math.atan(h1 / w1) * 57.3; // DEGREES

                    var w2 = coords[i][2] - mouseCoords[0];
                    var h2 = mouseCoords[1] - coords[i][1];
                    var a2 = Math.atan(h2 / w2) * 57.3; // DEGREES

                    if (a2 > a1) {
                        continue;
                    }
                
                /**
                * Handle the left corner
                */
                } else if (x < coords[i][6]) {
                    var w1 = coords[i][6] - coords[i][0];
                    var h1 = coords[i][7] - coords[i][1];;
                    var a1 = Math.atan(h1 / w1) * 57.3; // DEGREES

                    var w2 = mouseCoords[0] - coords[i][0];
                    var h2 = mouseCoords[1] - coords[i][1];
                    var a2 = Math.atan(h2 / w2) * 57.3; // DEGREES
                
                    if (a2 > a1) {
                        continue;
                    }
                }
                
                return [obj, coords[i], i];
            }
        }
        
        return null;
    }