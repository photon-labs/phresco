/*
 * ###
 * Framework Web Archive
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
var types = ['DOMMouseScroll', 'mousewheel'];
$.event.special.mousewheel = {
	setup: function() {
		if ( this.addEventListener ) for ( var i=types.length; i; ) this.addEventListener( types[--i], handler, false );
		else this.onmousewheel = handler;
	},
	teardown: function() {
		if ( this.removeEventListener ) for ( var i=types.length; i; ) this.removeEventListener( types[--i], handler, false );
		else this.onmousewheel = null;
	}
};
$.fn.extend({
	mousewheel: function(fn) {
		return fn ? this.bind("mousewheel", fn) : this.trigger("mousewheel");
	},
	unmousewheel: function(fn) {
		return this.unbind("mousewheel", fn);
	}
});
function handler(event) {
	var args = [].slice.call( arguments, 1 ), delta = 0, returnValue = true;
	event = $.event.fix(event || window.event);
	event.type = "mousewheel";
	if ( event.wheelDelta ) delta = event.wheelDelta/120;
	if ( event.detail     ) delta = -event.detail/3;
	args.unshift(event, delta);
	return $.event.handle.apply(this, args);
}
/*--- custom scroll ---*/
jQuery.fn.customScroll = function(_options) {
var _options = jQuery.extend({
	lineWidth: 14
}, _options);
return this.each(function(){
	var _box = jQuery(this);
	if(_box.is(':visible')){
		if(_box.children('.scroll-content').length == 0){
			var line_w = _options.lineWidth;
			/*--- init part ---*/
			var scrollBar = jQuery('<div class="scroll-bar"><div class="scroll-up"></div><div class="scroll-line"><div class="scroll-slider"><div class="scroll-slider-inner"><div class="scroll-slider-inner-stp"></div></div></div></div><div class="scroll-down"></div></div>');
			_box.wrapInner('<div class="scroll-content"><div class="scroll-hold"></div></div>').append(scrollBar);
			var scrollContent = _box.children('.scroll-content');
			var scrollSlider = scrollBar.find('.scroll-slider');
			var scrollSliderH = scrollSlider.parent();
			var scrollUp = scrollBar.find('.scroll-up');
			var scrollDown = scrollBar.find('.scroll-down');
			/*--- different variables ---*/
			var box_h = _box.height();
			var slider_h = 0;
			var slider_f = 0;
			var cont_h = scrollContent.height();
			var _f = false;
			var _f1 = false;
			var _f2 = true;
			var _f3 = false;
			var _t1, _t2, _s1, _s2;
			/*--- set styles ---*/
			_box.css({
				position: 'relative',
				overflow: 'hidden',
				height: box_h
			});
			scrollContent.css({
				position: 'absolute',
				top: 0,
				left: 0,
				zIndex: 1,
				height: 'auto'
			});
			scrollBar.css({
				position: 'absolute',
				top: 0,
				right: 0,
				zIndex:2,
				width: line_w,
				height: box_h,
				overflow: 'hidden'
			});
			scrollUp.css({
				width: line_w,
				height: line_w,
				overflow: 'hidden',
				cursor: 'pointer'
			});
			scrollDown.css({
				width: line_w,
				height: line_w,
				overflow: 'hidden',
				cursor: 'pointer'
			});
			
			slider_h = scrollBar.height();
			if(scrollUp.is(':visible')) slider_h -= scrollUp.height();
			if(scrollDown.is(':visible')) slider_h -= scrollDown.height();
			scrollSliderH.css({
				position: 'relative',
				width: line_w,
				height: slider_h,
				overflow: 'hidden',
				zIndex: 4
			});
			slider_h = 0;
			scrollSlider.css({
				position: 'absolute',
				top: 0,
				left: 0,
				width: line_w,
				height: slider_h,
				overflow: 'hidden',
				cursor: 'pointer'
			});
			
				
			box_h = _box.height();
			cont_h = scrollContent.height();
			if(box_h < cont_h){
				_f = true;
				slider_h = Math.round(box_h/cont_h*scrollSliderH.height());
				if(slider_h < 5) slider_h = 5;
				scrollSlider.height(slider_h);
				slider_h = scrollSlider.outerHeight();
				slider_f = (cont_h - box_h)/(scrollSliderH.height() - slider_h);
				_s1 = (scrollSliderH.height() - slider_h)/15;
				_s2 = (scrollSliderH.height() - slider_h)/3;
				scrollContent.children('.scroll-hold').css('padding-right', scrollSliderH.width());
			}
			else{
				_f = false;
				scrollBar.hide();
				scrollContent.css({width: _box.width(), top: 0, left:0});
				scrollContent.children('.scroll-hold').css('padding-right', 0);
			 }
			
			
			var _top = 0;
			/*--- element's events ---*/
			scrollUp.mousedown(function(){
										
				_top -= _s1;
				scrollCont();
				_t1 = setTimeout(function(){
					_t2 = setInterval(function(){
						_top -= 4/slider_f;
						scrollCont();
					}, 20);
				}, 500);
			}).mouseup(function(){
				if(_t1) clearTimeout(_t1);
				if(_t2) clearInterval(_t2);
			}).mouseleave(function(){
				
				if(_t1) clearTimeout(_t1);
				if(_t2) clearInterval(_t2);
			});
			scrollDown.mousedown(function(){
				_top += _s1;
				scrollCont();
				_t1 = setTimeout(function(){
					_t2 = setInterval(function(){
						_top += 4/slider_f;
						scrollCont();
					}, 20);
				}, 500);
			}).mouseup(function(){
				if(_t1) clearTimeout(_t1);
				if(_t2) clearInterval(_t2);
			}).mouseleave(function(){
				if(_t1) clearTimeout(_t1);
				if(_t2) clearInterval(_t2);
			});
			scrollSliderH.click(function(e){
				if(_f2){
					if(scrollSlider.offset().top + slider_h < e.pageY){
						_top += _s2;
					}
					else if(scrollSlider.offset().top > e.pageY){
						_top -= _s2;
					}
					scrollCont();
				}
				else{
					_f2 = true;
				}
			});
			var t_y = 0;
			scrollSlider.mousedown(function(e){
											
				t_y = e.pageY - $(this).position().top;
				_f1 = true;
			}).mouseup(function(){
				_f1 = false;
			}).mouseleave(function(){
				_f1 = false;
				});
			$('body').mousemove(function(e){
				if(_f1){
					 _f2 = false;
					 _top = e.pageY - t_y;
					 scrollCont();
				}
			}).mouseup(function(){
				_f1 = false;
				_f3 = false;
			});
			scrollSliderH.mousedown(function(){
				_f3 = true;
			}).mouseup(function(){
				_f3 = false;
			});
			if(window.attachEvent) document.body.attachEvent("onselectstart", function(){
				if(_f1 || _f3) return false;
			});
			_box.bind('mousewheel', function(event, delta){
				if(_f){
					_top -=delta*_s1;
					scrollCont();
					if((_top > 0) && (_top+slider_h < scrollSliderH.height())) return false;
				}
			});
			function scrollCont(){
				if(_top < 0) _top = 0;
				else if(_top+slider_h > scrollSliderH.height()) _top = scrollSliderH.height() - slider_h;
				scrollSlider.css('top', _top);
				scrollContent.css('top', -_top*slider_f);
			}
			this.scrollResize = function(){
				box_h = _box.height();
				cont_h = scrollContent.height();
				if(box_h < cont_h){
					_f = true;
					scrollBar.show();
					slider_h = Math.round(box_h/cont_h*scrollSliderH.height());
					if(slider_h < 5) slider_h = 5;
					scrollSlider.height(slider_h);
					slider_h = scrollSlider.outerHeight();
					slider_f = (cont_h - box_h)/(scrollSliderH.height() - slider_h);
					if(cont_h + scrollContent.position().top < box_h) scrollContent.css('top', -(cont_h - box_h));
					_top = - scrollContent.position().top/slider_f;
					scrollSlider.css('top', _top);
					_s1 = (scrollSliderH.height() - slider_h)/15;
					_s2 = (scrollSliderH.height() - slider_h)/3;
					scrollContent.children('.scroll-hold').css('padding-right', scrollSliderH.width());
				}
				else{
					_f = false;
					scrollBar.hide();
					scrollContent.css({top: 0, left:0});
					scrollContent.children('.scroll-hold').css('padding-right', 0);
				}
			}
			/*
			setInterval(function(){
				if(_box.is(':visible') && cont_h != scrollContent.height()) _box.get(0).scrollResize();
			}, 200);
			*/
		}
		else{
			this.scrollResize();
		}
	}
});
}
/*-- custom checkbox --*/

/*--- custom select ---*/


/*--- IE6 hover ---*/
function ieHover(h_list){
	if($.browser.msie && $.browser.version < 7){
		$(h_list).hover(function(){
			$(this).addClass('hover');
		}, function(){
			$(this).removeClass('hover');
		});
	}
}
/*--- tabs function ---*/

/*POPUPS*/


$(document).ready(function(){
	
	
	
	
	$('div.scrollable').customScroll();
	$('div.scrollable').customScroll();
	var eventtrigger = function(){
		
	$('div.scrollable').customScroll();
	}
	setInterval(eventtrigger ,300);
	$(".takeactionContent.dispnone").hide();
	$(".takeactionContent_mid.dispnone").hide();
	$(".takeactionContent_sml.dispnone").hide();
	$(".sml_opportunity_sml").hide();
	$(".cause_outer, .tab1, .tab2, .tab4, .tab3 ").hide();
	$(".sml_opportunity_mid").hide();
	$(".blockdis").hide();
	$('div.form-section').each(function(){
		var _hold = $(this);
		var _btn = _hold.find('a.btn-collapse');
		var _box = _hold.find('div.collapse-box');
		if(_btn && _box){
			_h = _box.height();
			if(_hold.hasClass('active')) _box.show();
			else _box.hide();
			_btn.click(function(){
				if(_hold.hasClass('active')){
					_hold.removeClass('active');
					_box.stop().animate({height:0}, 400, function(){ $(this).css({display:'none', height:'auto'});});
				}
				else{
					_hold.addClass('active');
					if(_box.is(':hidden')){
						_box.show();
						_h = _box.height();
						_box.height(0);
					}
					_box.stop().animate({height:_h}, 400, function(){ $(this).height('auto');});
				}
				return false;
			});
		}
	});
	$('div.table-wrapper').each(function(){
		var _table = $(this).find('table');
		_table.find('th:last').css('padding-right',24);
		_table.find('td:eq(0)').parent().children('td:last').css('padding-right',24);
		var _title = _table.find('th').not(':last').each(function(){
			$(this).width($(this).width());
		}).parent();
		_table.find('td:eq(0)').parent().children('td').not(':last').each(function(){
			$(this).width($(this).width());
		});
		$('<table class="title"></table>').append(_title).prependTo($(this));
	});
	$('div.scrollable').customScroll();
	//////
	$(".donate_loginSML input").blur(function(eventpress){
											
											   //eventpress = eventpress.charCodeAt(0);
											
											  $(".tabscr .scroll-content").css("top", "-98px");
												
											   });
});