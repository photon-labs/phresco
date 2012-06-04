$(document).ready(function(){
	$('#checkAllAuto').click(function(){
		  $(".check").each(function() {
					if ($(this).is(':disabled')) {
						
					} else{
						$(this).attr('checked', $('#checkAllAuto').is(':checked')); 
					}
			});
		  if ($('#checkAllAuto').is(':checked')){
			$('#deleteButton').removeClass("btn disabled");
			$('#deleteButton').addClass("btn primary");
			$('#deleteButton').attr("disabled", false);
		  } else {
			$('#deleteButton').removeClass("btn primary");
			$('#deleteButton').addClass("btn disabled");
			$('#deleteButton').attr("disabled", true);
		  }
	   }
	);

	$('.check').click(function() {
		if($('.check').is(':checked')){
		 		$('#deleteButton').removeClass("btn disabled");
				$('#deleteButton').addClass("btn primary");
				$('#deleteButton').attr("disabled", false);
		} else {
				$('#deleteButton').removeClass("btn primary");
				$('#deleteButton').addClass("btn disabled");
				$('#deleteButton').attr("disabled", true);
		}
		isAllChecked();
	});
});

function isAllChecked() {
	var $allCheck = $('.check');
	if ($allCheck.length == $allCheck.filter(':checked').length) {
		$('#checkAllAuto').attr('checked', true);
	} else {
		$('#checkAllAuto').attr('checked', false);
	}
}

//function dialog(enableProp) {
//    $(".wel_come").show().css("display", enableProp);
//    $(".confirm").show().css("display", enableProp);
//}