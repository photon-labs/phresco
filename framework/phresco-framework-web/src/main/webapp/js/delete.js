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