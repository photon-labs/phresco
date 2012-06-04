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
