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
$(document).ready(function() {
	$('#showSettings').click(function() {
	    showSettings();
	});
});

function showSettings() {
	$.ajax({
		url : 'showSettings',
       	data : {
			'showSettings' : $('#showSettings').prop("checked"),
       	},
       	type : "POST",
       	success : function(data) {
       		fillData('environments', data.settingsEnv);
       	}
   });
}

/** This method is to fill select box with data in ShowSettings **/
function fillData(element, data) {
	var from = $("#from").val();
	var mobile = $("#mobile").val();
	if (from == "generateBuild" && mobile !== "mobile") {
		if ((data != undefined || !isBlank(data)) && data != "") {
			var list = '<li class="build_type" value="Settings" class="optgrplbl" id="' + element + 'Group">Settings';
			$('#' + element).append(list);
			for (i in data) {
				var value = '<li class="settings_envrnmt"><input type="checkbox" id="environments" value="' + data[i] + '">' + '&nbsp;' + data[i] +'</li>';
				$('#' + element + 'Group').append(value);
			}
			$('#' + element).append('</li>');
		} else {
			$('#' + element + 'Group').remove();
		}
	} else {
		if ((data != undefined || !isBlank(data)) && data != "") {
			$('#' + element).append('<optgroup label="Settings" class="optgrplbl" id="' + element + 'Group">');
			for (i in data) {
				$('#' + element + 'Group').append($("<option></option>").attr("onClick", "selectEnvs()").attr("value", data[i]).text(data[i]));
			}
			$('#' + element).append('</optgroup>');
		} else {
			$('#' + element + 'Group').remove();
		}
	}
}

/** To check whether the selected environment has the appropriate configurations **/
function checkForConfig() {
	var envs = getSelectedEnvs();
	var params = "";
	if (!isBlank($('form').serialize())) {
		params = $('form').serialize() + "&";
	}
	params = params.concat("environments=");
	params = params.concat(envs);
	performAction('checkForConfiguration', params, '', true);
}

/** To check whether the selected environment has the appropriate configuration based on type **/
function checkForConfigType(type) {
	var envs = getSelectedEnvs();
	var params = "";
	if (!isBlank($('form').serialize())) {
		params = $('form').serialize() + "&";
	}
	params = params.concat("environments=");
	params = params.concat(envs);
	params = params.concat("&type=");
	params = params.concat(type);
	performAction('checkForConfigType', params, '', true);
}

/** To get the selected environments as comma delimited string **/
function getSelectedEnvs() {
	var envs = "";
	var mobile = $("#mobile").val();
	var from = $("#from").val();
	if (from == "generateBuild" && mobile !== "mobile") {
		$('input[id=environments]:checkbox:checked').each(function(index) {
			envs = envs + $(this).val() + ",";
		});
	} else {
		$('#environments option:selected').each(function(index) {
			envs = envs + $(this).text() + ",";
		});
	}
	return envs = envs.substring(0, envs.length - 1);
}


function showError(data) {
	$("#errMsg").html(data.envError);
}