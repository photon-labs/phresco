$(document).ready(function(){
	$('#latestVersion').click(function() {
		$('#versionInfo').css("display", "none");
		$('#availableVersion').css("display", "block");
	});
	
	$('#updateYes').click(function() {
		var latestVer = $('#LatestVersion').html();
		aboutVersionDialog('block');
		$.ajax({
			url : 'updateVersion',
			data : {
				'latestVersion' : latestVer,
			},
			success : function(data) {
				$("#aboutDialog").html(data);
			}
		});
	});
	
	$('#closeAboutDialog, #okAboutDialog, #closeVersionDialog, #UpdateNo, #updatedVersionDialog, #updatedVersionOk').click(function() {
		aboutDialog('none');
	});
});

function aboutDialog(enableProp) {
	$(".wel_come").css("display", enableProp);
	$("#aboutDialog").css("display", enableProp);
}

function aboutVersionDialog(enableProp) {
	$(".wel_come").show().css("display", enableProp);
}
