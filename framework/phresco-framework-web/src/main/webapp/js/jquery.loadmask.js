/**
 * Copyright (c) 2009 Sergiy Kovalchuk (serg472@gmail.com)
 * 
 * Dual licensed under the MIT (http://www.opensource.org/licenses/mit-license.php)
 * and GPL (http://www.opensource.org/licenses/gpl-license.php) licenses.
 *  
 * Following code is based on Element.mask() implementation from ExtJS framework (http://extjs.com/)
 *
 */
;(function($){
	
	/**
	 * Displays loading mask over selected element(s). Accepts both single and multiple selectors.
	 *
	 * @param label Text message that will be displayed on top of the mask besides a spinner (optional). 
	 * 				If not provided only mask will be displayed without a label or a spinner.  	
	 * @param delay Delay in milliseconds before element is masked (optional). If unmask() is called 
	 *              before the delay times out, no mask is displayed. This can be used to prevent unnecessary 
	 *              mask display for quick processes.   	
	 */
	$.fn.mask = function(label, delay){
		$(this).each(function() {
			if(delay !== undefined && delay > 0) {
		        var element = $(this);
		        element.data("_mask_timeout", setTimeout(function() { $.maskElement(element, label)}, delay));
			} else {
				$.maskElement($(this), label);
			}
		});
	};
	
	/**
	 * Removes mask from the element(s). Accepts both single and multiple selectors.
	 */
	$.fn.unmask = function(){
		$(this).each(function() {
			$.unmaskElement($(this));
		});
	};
	
	/**
	 * Checks if a single element is masked. Returns false if mask is delayed or not displayed. 
	 */
	$.fn.isMasked = function(){
		return this.hasClass("masked");
	};

	$.maskElement = function(element, label){
	
		//if this element has delayed mask scheduled then remove it and display the new one
		if (element.data("_mask_timeout") !== undefined) {
			clearTimeout(element.data("_mask_timeout"));
			element.removeData("_mask_timeout");
		}

		if(element.isMasked()) {
			$.unmaskElement(element);
		}
		
		if(element.css("position") == "static") {
			element.addClass("masked-relative");
		}
		
		element.addClass("masked");
		
		//var maskDiv = $('<div class="loadmask"></div>');
		var maskDiv = $('');
		
		//auto height fix for IE
		if(navigator.userAgent.toLowerCase().indexOf("msie") > -1){
			maskDiv.height(element.height() + parseInt(element.css("padding-top")) + parseInt(element.css("padding-bottom")));
			maskDiv.width(element.width() + parseInt(element.css("padding-left")) + parseInt(element.css("padding-right")));
		}
		
		//fix for z-index bug with selects in IE6
		if(navigator.userAgent.toLowerCase().indexOf("msie 6") > -1){
			element.find("select").addClass("masked-hidden");
		}
		
		element.append(maskDiv);
		
		if(label !== undefined) {
			//var maskMsgDiv = $('<div id="progressbar"><div id="indicator"><span id="progressnum">' + label + '</span></div></div>');
			var maskMsgDiv = $('<div id="progressbar"><div id="indicator"><div id="indicatorInnerElem"><span id="progressnum">' + label + '</span></div></div></div>');
			//maskMsgDiv.append('<div>' + label + '</div>');
			element.append(maskMsgDiv);
			
			//calculate center position
			maskMsgDiv.css("top", 40 +"%");
			maskMsgDiv.css("left", 40 +"%");
			
			maskMsgDiv.show();
		}
		
	};
	
	$.unmaskElement = function(element){
		//if this element has delayed mask scheduled then remove it
		if (element.data("_mask_timeout") !== undefined) {
			clearTimeout(element.data("_mask_timeout"));
			element.removeData("_mask_timeout");
		}
		
		element.find(".loadmask-msg,.loadmask").remove();
		element.removeClass("masked");
		element.removeClass("masked-relative");
		element.find("select").removeClass("masked-hidden");
	};
 
})(jQuery);