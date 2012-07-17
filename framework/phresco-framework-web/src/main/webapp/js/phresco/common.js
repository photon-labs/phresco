	// To set images based on theme in login and home page
    function showWelcomeImage(key) {
    	var theme = localStorage[key];
    	if (theme == "themes/photon/css/blue.css") {
    		$("link[id='theme']").attr("href", localStorage["color"]);
    		$('.welcomeimg').attr("src", "images/welcome-photon_blue.png");
    		$('.phtaccinno').attr("src", "images/acceleratinginovation_blue.png");
    		$('.logoimage').attr("src", "images/photon_phresco_logo_blue.png");
    		$('.headerlogoimg').attr("src", "images/phresco_header_blue.png");
    	} else if(theme == null || theme == undefined || theme == "undefined" || theme == "null" || theme == "themes/photon/css/red.css") {
    		$("link[id='theme']").attr("href", "themes/photon/css/red.css");
    		$('.welcomeimg').attr("src", "images/welcome_photon_red.png");
    		$('.phtaccinno').attr("src", "images/acc_inov_red.png");
    		$('.logoimage').attr("src", "images/photon_phresco_logo_red.png");
    		$('.headerlogoimg').attr("src", "images/phresco_header_red.png");
    	}
    }
	
	// Enables button
	function enableControl(tagControl, css) {
		tagControl.attr("class", css);
		tagControl.attr("disabled", false);
	}

	// Disables button
	function disableControl(tagControl, css) {
		tagControl.attr("class", css);
		tagControl.attr("disabled", true);
	}
	
	// Enables button
    function enableBtn(enableId) {
    	$('#'+ enableId).attr("class", "primary btn");
    	$('#'+ enableId).attr("disabled", false);
    }
    
    // Disables button
    function disableBtn(disableId) {
    	$('#'+ disableId).attr("class", "btn disabled");
    	$('#'+ disableId).attr("disabled", true);
    }
    
    // This function to enable the screen
    function enableScreen() {
        $(".wel_come").show().css("display", "none");
        $("#loadingIconDiv").empty();
    }
    
    // This function to disable the screen
    function disableScreen() {
        $(".wel_come").show().css("display", "block");
    }

    //show the progress bar
    function showProgessBar(dispData, time) {
    	$("#progressnum").empty();
    	$("#progressnum").html(dispData);
    	$("#progressbar").show().css("display", "block");
    	refreshIntervalId = setInterval(prog, time);
    	disableScreen();
    }
	
	// Loading icon    
    function showLoadingIcon(tagControl) {
		var src = "themes/photon/images/loading_blue.gif";
    	var theme =localStorage["color"];
        if (theme == undefined || theme == null || theme == "null" || theme == "" || theme == "undefined" || theme == "themes/photon/css/red.css") {
        	src = "themes/photon/images/loading_red.gif";
        }
     	tagControl.empty();
    	tagControl.html("<img class='loadingIcon' src='"+ src +"' style='display: block'>");
    }
    
    //hide the progress bar
    function hideProgessBar() {
        $("#progressbar").show().css("display", "none");
        enableScreen();
    }
    
    // Shows the parent page
    function showParentPage() {
    	enableScreen();
		$('.popup_div').show().css("display","none");
    	$('#validateProject').show().css("display","none");
		$('#build-outputOuter').show().css("display","none");
		$('#descDialog').show().css("display","none");
		$('#testCaseScreenShotPopUp').show().css("display","none");
  	    $('#testCaseErrOrFail').show().css("display","none");
	}
    
    // Shows pop-up
    function showPopup() {
    	disableScreen();
		$('.popup_div').show().css("display","block");
	}
    
    // Show progress 
    function showConsoleProgress(prop) {
        $(".wel_come").show().css("display",prop);
        $('#build-outputOuter').show().css("display",prop);
    }
    
    //Form submit
    function performAction(pageUrl, params, tagControl, callSuccessEvent) {
    	$.ajax({
            url : pageUrl,
            data : params,
            type : "POST",
            success : function(data) {
            	if(callSuccessEvent != undefined && !isBlank(callSuccessEvent)) {
            		successEvent(pageUrl, data);
            	} else if(data.validated == true) {
            		validationError(data);
            	} else if(tagControl != undefined && !isBlank(tagControl)) {
            		if (pageUrl == "applications" || pageUrl == "settings" || pageUrl == "forum") {
            			$(".intro_container").hide();
            	    	$(".errorOverlay").show().css("display", "none");
            		}
            		if((pageUrl == "save" || pageUrl == "update" || pageUrl == "delete" || pageUrl == "deleteConfigurations" || pageUrl == "deleteSettings" || pageUrl == "deleteBuild" || pageUrl == "CIBuildDelete")) {
            			hideProgessBar();
            		} 
            		
	                tagControl.empty();
	                tagControl.html(data);
	           	}
            }
        }); 
    }
    
    // This method is for popup form submit and Dynamic small subpage page load
    // is secondpopup avail can be used to display two popup's in a single popup div
    function popup(pageUrl, params, tagControl, callSuccessEvent, isSecondPopupAvail) {
    	var param = "";
    	if (!isBlank($('form').serialize())) {
    		param = "&";
    	}
    	if(params != undefined && !isBlank(params)) {
    		param = param + params;
    	}
        $.ajax({
            url : pageUrl,
            data : $('form').serialize() + param,
            success : function(data) {
            	if(tagControl != undefined && !isBlank(tagControl) && isSecondPopupAvail == undefined && isBlank(isSecondPopupAvail)) {
                	tagControl.empty();
                	tagControl.html(data);
                }
            	if (tagControl != undefined && !isBlank(tagControl) && isSecondPopupAvail != undefined && !isBlank(isSecondPopupAvail)) {
            		tagControl.append(data);
            	}
             	if(callSuccessEvent != undefined && !isBlank(callSuccessEvent)) {
             		successEvent(pageUrl, data);
            	}
            }
        });
    }
    
    // This method is for popup submit
    function readerHandlerSubmit(pageUrl, projectCode, testType, params, callSuccessEvent) {
    	var param = "";
    	if (!isBlank($('form').serialize())) {
    		param = "&";
    	}
    	if (params != undefined && !isBlank(params)) {
            param = param + params;
        }
        $.ajax({
            url : pageUrl,
            data : $('form').serialize() + param,
            type : "POST",
            success : function(data) {
            	$("#build-output").empty();
            	readerHandler(data, projectCode, testType, pageUrl);
            	if(callSuccessEvent != undefined && !isBlank(callSuccessEvent)) {
            		successEvent(pageUrl, data);
            	}
            }
        });
    }
    
    function getCurrentCSS() {
        var theme =localStorage["color"];
        if(theme == undefined || theme == null || theme == "null" || theme == "" || theme == "undefined" || theme == "themes/photon/css/red.css") {
        	$('.loadingIcon, .popupLoadingIcon').attr("src", "themes/photon/images/loading_red.gif");
        }
        else {
        	$('.loadingIcon, .popupLoadingIcon').attr("src", "themes/photon/images/loading_blue.gif");
        }
    }
    
    
    function bacgroundValidate(validateURL, projectCode) {
        $.ajax({
            url : validateURL,
            data : {
                'validateInBg' : "true",
                'projectCode' : projectCode,
            },
            type : "POST",
            success : function(data) {
            	var status = data.globalValidationStatus;
            	if (status == "ERROR") {
            		$("#validationSuccess_"+validateURL).css("display", "none");
            		$("#validationErr_"+validateURL).show();
            	} else {
            		$("#validationErr_"+validateURL).hide();
            		$("#validationSuccess_"+validateURL).css("display", "block");
            	}
            }
        });
    }
    
    function specialCharHandle(e) {
        var key = e.charCode || e.keyCode || 0;
        // Dont allow % and &
        keychar = String.fromCharCode(key);
        if (keychar == "%" || keychar == "&" ) {
            return false;
        } else {
            return true;
        }
    }
    
 	// To check for special character in the project name while pasting
    function checkForSplChr(name) {
    	newName = name.replace(/[^a-zA-Z 0-9\-\_]+/g, '');
    	return newName;
    }
    
    function isValidChar(event, obj) {
    	var k;
    	document.all ? k = event.keyCode : k = event.which;
    	var keychar = String.fromCharCode(k);
    	var iChars = "`~!@#$%^&*()+=[]\\\';,./{}|\":<>?";
    	if (iChars.indexOf(keychar) != -1) {
    		return false;
    	}
    	return true;
    } 
    
    function checkForContext(name) {
    	newName = name.replace(/[^a-zA-Z 0-9\-\_/.]+/g, '');
    	return newName;
    }
    
    function checkForCode(name) {
    	newName = name.replace(/[^a-zA-Z 0-9]+/g, '');
    	return newName;
    }
    
    function checkForVersion(versionNo) {
    	newVersionNo = versionNo.replace(/[^a-zA-Z 0-9\.\-\_]+/g, '');
    	return newVersionNo;
    }
    
 	// To check for special character in the port name while pasting
    function checkForNumber(portNo) {
    	newPortNo = portNo.replace(/[^0-9]+/g, '');
    	return newPortNo;
    }
    
    
    function checkForRevision(revision) {
    	newRevision = revision.replace(/[^0-9\.]+/g, '');
    	return newRevision;
    }
    
    function checkForClassName(mainClassName) {
    	mainClassName = mainClassName.replace(/[^a-zA-Z 0-9\.\_]+/g, '');
    	return mainClassName;
    }
    
    function checkForJarName(jarName) {
    	jarName = jarName.replace(/[^a-zA-Z\_]+/g, '');
    	return jarName;
    }
	
    // To remove empty space between the characters
 	function removeSpace(name) {
 		return name.replace(/\s/g, '');
 	}
    
 	function escPopup() {
        $(document).keydown(function(e) {
            // ESCAPE key pressed
           if (e.keyCode == 27) {
                //dialog('none');
        	   showParentPage();     
            }
        });
    }
 	
 	function escBlockPopup() {
        $(document).keydown(function(e) {
            // ESCAPE key pressed
           if (e.keyCode == 27) {
                dialog('none');
            }
        });
    }
 	
    function isBlank(str) {
        return (!str || /^\s*$/.test(str));
    }
    
    /** To fill the versions in the select box **/
	function fillVersions(element, data, from) {
		$('#' + element).empty();
    	if ((data != undefined || !isBlank(data)) && data != "") {
    		if ("getSQLFiles" == from) {
    			for (i in data) {
	    			var sep = new Array();
	    			sep = data[i].split("#SEP#");
	    			$('#' + element).append($("<option></option>").attr("value", sep[0] + "/" + sep[1]).text(sep[1]));
    			}
    		} else {
				for (i in data) {
					$('#' + element).append($("<option></option>").attr("value", data[i]).text(data[i]));
				}
    		}
			return true;
		}
	}
	
	function fillCheckBoxVersion(element, data) {
		$('#' + element).empty();
    	if ((data != undefined || !isBlank(data)) && data != "") {
			for (i in data) {
				var list = '<li class="list_style"><input type="checkbox" name="'+ element +'" value="' + data[i] + '">' + '&nbsp;' + data[i] + '</li>';
				$('#' + element).append(list);
			}
			return true;
		}
	}
	
    // Hide a popup msg after some time
    function showHidePopupMsg(tagControl, msg) {
    	tagControl.html(msg);
    	tagControl.show();
    	setTimeout(function(){
    		tagControl.fadeOut("slow", function () {
    		tagControl.hide();
    		      });	 
    		}, 2000);
    }
    
    // To check whether the url is valid or not
	function isValidUrl(url) {
	    if(/^(http|https|ftp):\/\/[a-z0-9]+([-.]{1}[a-z0-9]+)*.[a-z]{2,5}(:[0-9]{1,5})?(\/.*)?$/i.test(url)) {
	      return false;
	    } else {
	      return true;
	    }   
	}
	// To check whether the given string contains empty space or not
	function isContainSpace(name) {
		newName = name.replace(/[^a-zA-Z 0-9\_]+/g, '');
		return newName;
    }
			 
	
	function accordion() {
	    var showContent = 0;
	    $('.siteaccordion').removeClass('openreg').addClass('closereg');
	    $('.mfbox').css('display','none');
	    $('.mfbox').eq(showContent).css('display','block');
	    $('.siteaccordion').eq(showContent).attr("id", "siteaccordion_active");
	    
	    <!--show hide-->
	    //$('.siteaccordion').eq(showContent).removeClass('closereg').addClass('openreg');
	    //$('.mfbox').eq(showContent).css('display','block')
	    <!--show hide-->
	    
	    $('.siteaccordion').bind('click',function(e){
	        var _tempIndex = $('.siteaccordion').index(this);
	            $('.siteaccordion').removeClass('openreg').addClass('closereg');
	            $('.mfbox').each(function(e){
	                if($(this).css('display')=='block'){
	                    $(this).find('.scrollpanel').slideUp('300');
	                    $(this).slideUp('300');
	                }
	            })
	        if($('.mfbox').eq(_tempIndex).css('display')=='none'){
	            $(this).removeClass('closereg').addClass('openreg');
	            $('.mfbox').eq(_tempIndex).slideDown(300,function(){
	                $('.mfbox').eq(_tempIndex).find('.scrollpanel').slideDown('300');
	            });
	        }
	    });
	    
	    $('.siteaccordion').click(function(){ 
	    	$('#siteaccordion_active').attr("id", "");   	
	    });
	}
	
	function isAllCheckBoxCheked(tagControlId) {
		if (!$("tbody#" + tagControlId + " input[type=checkbox]:not(:checked)").length) {
			 return true;
		} else {
			return false;
		}
	}
	
	function isAtleastOneCheckBoxCheked(tagControlId) {
		if ($("tbody." + tagControlId + " input[type=checkbox]:checked").length > 0) {
			 return true;
		} else {
			return false;
		}
	}
