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
		var newHeight_tablediv = $("#container").height();
		$(".table_div").css("height",newHeight_tablediv - heightDiff_tablediv);
		var newHeight_tabledatadiv = $(".table_div").height();
		$(".table_data_div").css("height",newHeight_tabledatadiv - heightDiff_tabledatadiv);
	}
});
