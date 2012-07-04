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
// JavaScript Document
$(document).ready(function() {
	$(window).bind("resize", resizeWindow);
	var heightDiff_MainWrpr = $(window).height()- $(".main_wrapper").height();
	var heightDiff_Wrpr = $(".main_wrapper").height()- $(".wrapper").height();
	var heightDiff_Content = $(".wrapper").height()- $("#container").height();
	var heightDiff_tablediv = $("#container").height()- $(".table_div").height();
	var heightDiff_tabledatadiv = $(".table_div").height()- $(".table_data_div").height();
	
	

	function resizeWindow(e) {
		var newHeight_Window = $(window).height();
		$(".main_wrapper").css("height",newHeight_Window - heightDiff_MainWrpr);
		
		var newHeight_MainWrpr = $(".main_wrapper").height();
		$(".wrapper").css("height",newHeight_MainWrpr - heightDiff_Wrpr);
		
		var newHeight_content = $(".wrapper").height();
		$("#container").css("height",newHeight_content - heightDiff_Content);
		
		var newHeight_addinfodiv = $("#container").height();
		$(".appInfoTabDiv").css("height",newHeight_addinfodiv - 66);
		$(".scrollCiList").css("height",newHeight_addinfodiv - 110);
		$("#CiBuildsList").css("height",newHeight_addinfodiv - 120);
		
		/* Appinfo page */
		var newHeight_addformdiv = $(".appInfoTabDiv").height();
		$(".app_add_form").css("height",newHeight_addformdiv - 15);
		
		var newHeight_appinfo_div = $(".app_add_form").height();
		$(".appInfoScrollDiv").css("height", newHeight_appinfo_div - 35) ;
		
		/* Feature page */
		var newHeight_feature_div = $(".appInfoTabDiv").height();
		$(".app_features_form").css("height",newHeight_feature_div - 10);
		
		var newHeight_feature_div = $(".app_features_form").height();
		$(".featuresScrollDiv").css("height", newHeight_feature_div - 75);
		
		/* Configuration add page */
		var newHeight_config_div = $(".appInfoTabDiv").height();
		$(".configurations_add_form").css("height",newHeight_config_div - 12);
		
		var newHeight_config_div = $(".configurations_add_form").height();
		$(".config_div").css("height", newHeight_config_div - 55);
		
		/* Settings page */
		var newHeight_settingdiv = $("#container").height();
		$(".settings_add_form").css("height",newHeight_settingdiv - 40);
		
		var newHeight_sttngs_div = $(".settings_add_form").height();
		$(".settings_div").css("height", newHeight_sttngs_div - 30);
		
		/* Build page */
		var newHeight_appInfTabBuild = $(".appInfoTabDiv").height();
		$(".buildDiv").css("height",newHeight_appInfTabBuild - 15); 
		
		var newHeight_build_left_container = $(".buildDiv").height();
		$(".build_detail_div").css("height",newHeight_build_left_container - 23);
		$(".build_progress_div").css("height",newHeight_build_left_container - 23);
		
		var newHeight_tablediv = $("#container").height();
		$(".table_div").css("height",newHeight_tablediv - heightDiff_tablediv);
		
		/* To resize table list and scrollbar */
		
		var newHeight_newscrollfixedcon = $(".table_div").height();
		$(".fixed-table-container").css("height",newHeight_newscrollfixedcon - 30);
		
		var newHeight_newscrollfixedcon_inner = $(".fixed-table-container").height();
		$(".fixed-table-container-inner").css("height",newHeight_newscrollfixedcon_inner - 3);
		
		/*var newHeight_newscrollbar = $(".fixed-table-container-inner").height();
		$(".scroll-bar").css("height",newHeight_newscrollbar + 36);*/
		
		/*var newHeight_newscrollbardiv = $(".theme_accordion_container").height();
		$(".scroll-bar").css("height",newHeight_newscrollbardiv + 36);*/
		
		/* Quality tab unit and functional table list resize */
		var tblheight = (($("#subTabcontainer").height() - $("#form_test").height()));
		$('.responsiveTableDisplay').css("height", parseInt((tblheight/($("#subTabcontainer").height()))*100) +'%');
		var responsiveFixedTblhight = ((($('#tabularView').height() - 30) / $('#tabularView').height()) * 100);
		$('.responsiveFixedTableContainer').css("height", responsiveFixedTblhight+'%');
		
	}
});