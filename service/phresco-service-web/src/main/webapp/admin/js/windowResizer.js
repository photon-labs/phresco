/*
 * ###
 * Service Web Archive
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
// JavaScript Document
$(document).ready(function() {
	$(window).bind("resize", resizeWindow);
	var heightDiff_MainWrpr = $(window).height()- $(".main_wrapper").height();
	var heightDiff_Wrpr = $(".main_wrapper").height()- $(".wrapper").height();
	var heightDiff_Content = $(".wrapper").height()- $(".container").height();
	
	function resizeWindow(e) {
		var newHeight_Window = $(window).height();
		$(".main_wrapper").css("height", newHeight_Window - heightDiff_MainWrpr);
		var newHeight_MainWrpr = $(".main_wrapper").height();
		$(".wrapper").css("height", newHeight_MainWrpr - heightDiff_Wrpr);
		
		var newHeight_content = $(".wrapper").height();
		$(".container").css("height", newHeight_content - heightDiff_Content);
		
		var newHeight_subcont = $(".container").height();
		$("#subcontainer").css("height", newHeight_subcont - 25);
		
		var newHeight_form = $("#subcontainer").height();
		$(".form-horizontal").css("height", newHeight_form - 5);
		
		var newHeight_contadder = $(".form-horizontal").height();
		$(".content_adder").css("height", newHeight_contadder - 70);
		
		var newWidth_button = $(".form-horizontal").width();
		$(".bottom_button").css("width", newWidth_button - 25);		
	}
});
