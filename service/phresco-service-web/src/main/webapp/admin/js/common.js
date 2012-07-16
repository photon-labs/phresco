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
function clickMenu(menu, tag, form) {
	menu.click(function() {
		showLoadingIcon(tag);
		inActivateAllMenu(menu);
		activateMenu($(this));
		var selectedMenu = $(this).attr("id");
		loadContent(selectedMenu, form, tag);
	});
}

function clickButton(button, tag) {
	button.click(function() {
		var selectedMenu = $(this).attr("id");
		loadContent(selectedMenu, '', tag);
	});
}

function clickButtonFileUpload(button, csvElementIds, tag) {
	button.click(function() {
		var selectedMenu = $(this).attr("id");
		formSubmitFileUpload(selectedMenu, csvElementIds, tag);
	});
}

function formSubmitFileUpload(pageUrl, csvElementIds, tag, progressText) {
	var formDataJson = formSerializeToJson($('form').serializeArray());
	showProgressBar(progressText);
	$.ajaxFileUpload ({
		url: pageUrl,
		secureuri: true,
		fileElementId: csvElementIds,
		dataType: 'json',
		data: formDataJson,   //{name:'logan', version:'2'}
		success: function (data, status) {
			hideProgressBar();
			if (status == 'success') {
				loadData(data, tag);
			}
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

function loadContent(pageUrl, form, tag) {
	showLoadingIcon(tag);
	if (form != undefined && !isBlank(form)) {
		var params = form.serialize();
	}
	$.ajax({
		url : pageUrl,
		data : params,
		type : "POST",
		success : function(data) {
			loadData(data, tag);
		}
	});
}

function clickSave(pageUrl, params, tag, progressText) {
	showProgressBar(progressText);
	$.ajax({
		url : pageUrl,
		data : params,
		type : "POST",
		success : function(data) {
			hideProgressBar();
			loadData(data, tag);
		}
	});
}

function validate(pageUrl, params, tag, progressText) {
	$.ajax({
		url : pageUrl + "Validate",
		data : params,
		type : "POST",
		success : function(data) {
			if (data.errorFound != undefined && data.errorFound == true) {
				findError(data);
			} else {
				clickSave(pageUrl, params, tag, progressText);
			}
		}
	});
}

function loadData(data, tag) {
	tag.empty();
	tag.html(data);
	accordion();
	setTimeOut();
}

function inActivateAllMenu(allLink) {
	allLink.attr("class", "inactive");
}

function activateMenu(selectedMenu) {
	selectedMenu.attr('class', "active");
}

function checkAllEvent(currentCheckbox) {
	var checkAll = $(currentCheckbox).prop('checked');
	$('.check').prop('checked', checkAll);
	buttonStatus(checkAll);
}

function checkboxEvent() {
	var chkboxStatus = $('.check').is(':checked');
	buttonStatus(chkboxStatus);
	if ($('.check').length == $(".check:checked").length) {
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

function setTimeOut() {
	setTimeout(function() {
		$('#successmsg').fadeOut("slow", function () {
			$('#successmsg').remove();
		});
	}, 2000);
	
	setTimeout(function() {
		$('#errormsg').fadeOut("slow", function () {
			$('#errormsg').remove();
		});
	}, 2000);
}

function accordion() {
	/** Accordian starts **/
	var showContent = 0;	
    $('.siteaccordion').removeClass('openreg').addClass('closereg');
    $('.mfbox').css('display','none');
    
    $('.siteaccordion').bind('click',function(e) {
        var _tempIndex = $('.siteaccordion').index(this);
        $('.siteaccordion').removeClass('openreg').addClass('closereg');
        $('.mfbox').each(function(e) {
            if ($(this).css('display')=='block'){
                $(this).slideUp('300');
            }
        });
        if ($('.mfbox').eq(_tempIndex).css('display')=='none') {
            $(this).removeClass('closereg').addClass('openreg');
            $('.mfbox').eq(_tempIndex).slideDown(300,function() {
                
            });
        }
    });
}

function showLoadingIcon(tag) {
	var src = "theme/photon/images/loading_blue.gif";
	var theme =localStorage["color"];
    if (theme == undefined || theme == "theme/photon/css/red.css") {
    	src = "theme/photon/images/loading_red.gif";
    }
 	tag.empty();
	tag.html("<img class='loadingIcon' src='"+ src +"' style='display: block'>");
}

function showProgressBar(progressText) {
	$(".bar").html(progressText);
	$(".modal-backdrop").show();
	$(".progress").show();
	setInterval(prog, 100);
}

function hideProgressBar() {
	$(".modal-backdrop").hide();
	$(".progress").hide();
}

/** It allows A-Z, a-z, 0-9, - and _ **/
function checkForSplChr(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
}

/** It allows A-Z, a-z, 0-9, - , _ and . **/
function checkForSplChrExceptDot(inputStr) {
	return inputStr.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
}

function changeTheme() {
  	if (localStorage["color"] != null) {
        $("link[title='phresco']").attr("href", localStorage["color"]);
    } else {
        $("link[title='phresco']").attr("href", "theme/photon/css/red.css");
    } 
}

function showWelcomeImage() {
	var theme = localStorage['color'];
	if (theme == "theme/photon/css/blue.css") {
		$("link[id='theme']").attr("href", localStorage["color"]);
		$('.headerlogoimg').attr("src","theme/photon/images/phresco_header_blue.png");
		$('.phtaccinno').attr("src","theme/photon/images/acc_inov_blue.png");
		$('.welcomeimg').attr("src","theme/photon/images/welcome_photon_blue.png");
	} else if (theme == null || theme == "theme/photon/css/red.css") {
		$("link[id='theme']").attr("href", "theme/photon/css/red.css");
		$('.headerlogoimg').attr("src","theme/photon/images/phresco_header_red.png");
		$('.phtaccinno').attr("src","theme/photon/images/acc_inov_red.png");
		$('.welcomeimg').attr("src","theme/photon/images/welcome_photon_red.png");

	}
}

function isBlank(str) {
    return (!str || /^\s*$/.test(str));
}