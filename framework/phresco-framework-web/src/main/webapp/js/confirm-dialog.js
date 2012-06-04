$(document).ready(function(){
	$('#cancel').click(function() {
	    dialog('none');
	});
	
	$('#close').click(function() {
	    dialog('none');
	});
	
	/*$('#ok').click(function() {
		alert("ok clicked...");
		dialog('none');
		$('#deleteObjects').submit();
	});*/
});

function onOk() {
	dialog('none');
	$('#deleteObjects').submit();
}

function dialog(enableProp) {
    $(".wel_come").show().css("display", enableProp);
    $(".confirm").show().css("display", enableProp);
}