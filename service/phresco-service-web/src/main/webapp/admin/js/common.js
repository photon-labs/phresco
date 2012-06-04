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
function clickMenu(menu, tag) {
	menu.click(function() {
		inActivateAllMenu(menu);
		activateMenu($(this));
		var selectedMenu = $(this).attr("id");
		loadContent(selectedMenu, tag);
	});
}

function clickButton(button, tag) {
	button.click(function() {
		var selectedMenu = $(this).attr("id");
		loadContent(selectedMenu, tag);
	});
}

function clickButtonFileUpload(button, csvElementIds, tag) {
	button.click(function() {
		var selectedMenu = $(this).attr("id");
		formSubmitFileUpload(selectedMenu, csvElementIds, tag);
	});
}

function formSubmitFileUpload(pageUrl, csvElementIds, tag) {
	var formDataJson = formSerializeToJson($('form').serializeArray());
	$.ajaxFileUpload ({
		url: pageUrl,
		secureuri: true,
		fileElementId: csvElementIds,
		dataType: 'json',
		data: formDataJson,   //{name:'logan', version:'2'}
		success: function (data, status) {
			loadData(data, tag);
		},
		error: function (data, status, e) {
		}
	});

}

function formSerializeToJson(a) {
	var o = {};
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}

function loadContent(pageUrl, tag) {
	$.ajax({
		url : pageUrl,
		data : $('form').serialize(),
		type : "POST",
		success : function(data) {
			loadData(data, tag);
		}
	});
}

function loadData(data, tag) {
	if(data.errorFound != undefined) {
		findError(data);
	} else {
		tag.empty();
		tag.html(data);
	}
}


function inActivateAllMenu(allLink) {
	allLink.attr("class", "inactive");
}

function activateMenu(selectedMenu) {
	selectedMenu.attr('class', "active");
}

function checkAllEvent(currentCheckbox) {
	var checkAll = $(currentCheckbox).prop('checked');
	$('input[name="check"]').prop('checked', checkAll);
	buttonStatus(checkAll);
}

function checkboxEvent() {
	var chkboxStatus = $("input[name='check']").is(':checked');
	buttonStatus(chkboxStatus);
	if ($("input[name='check']").length == $("input[name='check']:checked").length) {
		$('#checkAllAuto').prop('checked', true);
	} else {
		$('#checkAllAuto').prop('checked', false);
	}
}

function buttonStatus(checkAll) {
	$('#del').attr('disabled', !checkAll);
	if (checkAll) {
		$('#del').addClass('btn-primary');
	} else {
		$('#del').removeClass('btn-primary');
	}
}

function showError(tag, span, errmsg) {
	tag.addClass("error");
	span.text(errmsg);
}

function hideError(tag, span) {
	tag.removeClass("error");
	span.empty();
}

