	$("a[name='ModuleDesc']").hover(function() {
        // Get the current title
        var title = $(this).attr("title");

        // Store it in a temporary attribute
        $(this).attr("tmp_title", title);

        // Set the title to nothing so we don't see the tooltips
        $(this).attr("title","");
        
		/* Get the tooltip text */
		var thisId = $(this).attr("id");
		if (thisId != "") {
			$(".twipsy-inner").html(thisId);
			
			/* Left and top positioning of the tooltip */
			var tooltipLeft = $(this).width() + $(this).offset().left;
			var tooltipTop = $(this).offset().top - $(".main_wrapper").offset().top;
			$("#toolTipDiv").css({"top" : tooltipTop, "left" : tooltipLeft});
			
			/* To position the tooltip to the mouse overed element */
			var midOfRow = ($(this).height()) / 2; //calculating the row mid
			var tooltipHeight = $("#toolTipDiv").height(); // getting tool height
			
			// getting bottom from the tootip
			var bottom = ($(window).height() - ($(this).offset().top + midOfRow)); 
			if (bottom < tooltipHeight) { // check to find the bottom element
				// calculating the top for tooltip
				tooltipTop = tooltipTop - (tooltipHeight - ($(this).height())) - 10;
				$("#toolTipDiv").css({"top" : tooltipTop, "left" : tooltipLeft});
				$(".twipsy-arrow").css({"top" : tooltipHeight - 8});
			} else {
				$(".twipsy-arrow").css({"top" : "0px" });
			}
			$("#toolTipDiv").show();
		}
	},

	function() { // Fired when we leave the element
        // Retrieve the title from the temporary attribute
        var title = $(this).attr("tmp_title");

        // Return the title to what it was
        $(this).attr("title", title);
        $(".twipsy").hide();
    });

	/* To check whether the divice is ipad or not and apply JQuery scroll bar */
	if(!isiPad()){
		$(".theme_accordion_container").scrollbars();
	}
	
	$(document).ready(function() {
		/** To check the pilot project modules during next/previous actions **/
		var fromPage = $('#fromPage').val();
		if (fromPage == "") {
			if($("#selectedPilotProject").val() != "None") {
				isPilotSelected = true;
			} else {
				isPilotSelected = false;
			}
			getPilotProjectModules(isPilotSelected);
		}

		/** show version of the default modules **/
		$("input[type=radio]:checked").each(function(i) {
			var version = $(this).val();
			var moduleId = $(this).attr('name');
			$("p[id='" + moduleId + "version']").html(version);
		});
		
		/** Accordian starts **/
		var showContent = 0;
	    
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
	    /** Accordian ends **/
		
		changeStyle("features");
		
		$('#finish').click(function() {
			showProgessBar("Creating project...", 100);
			featureUpdate('save');
			return true;
		});

		$('#update').click(function() {
			showProgessBar("Updating project...", 100);
			featureUpdate('update');
			return true;
		});
	         
		$('#previous').click(function() {
		    $("input[type=checkbox]:disabled").each ( function() {
		        $(this).attr('disabled', false)
		    });
		   	var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("fromPage=");
			params = params.concat($('#fromPage').val());
			showLoadingIcon($("#tabDiv")); // Loading Icon
		    performAction('previous', params, $('#tabDiv'));
		});
	
		// Description popup js codes
		$("a[name='ModuleDesc']").click(function() {
			var description = $(this).attr("tmp_title");
			if (description != "") {
				$("#moduleDescription").empty();
				$("#moduleDescription").html(description);
				enableModuleDesc('block');
			}
		});
		
		$('#close').click(function() {
			enableModuleDesc('none');
		});
		
		$('#closeDesc').click(function() {
			enableModuleDesc('none');
		});
		
		// Check box change function
		$("input[type=checkbox]").change(function() {
			var checkboxChecked = $(this).is(":checked");
			var moduleId = $(this).val();
			if (!checkboxChecked) {
				var toUncheckVersion = $("p[id='" + moduleId + "version']").html();
				$("input[name='" + moduleId + "']").prop("checked", false);
				$("p[id='" + moduleId + "version']").empty();
				uncheckDependency(moduleId, $(this).attr('class'), toUncheckVersion);
				var name = $(this).attr('class');
				$("input:checkbox[name='" + name + "']").prop("checked", false);
			} else {
				$("input:radio[name='" + moduleId + "']:first").prop("checked", true);
				var version = $("input:radio[name='" + moduleId + "']:first").val();
				$("p[id='" + moduleId + "version']").html(version);
				checkDependency(moduleId, $(this).attr("class"), version);
			}
		});
		
		$('#cancel').click(function() {
			var params = "";
	    	if (!isBlank($('form').serialize())) {
	    		params = $('form').serialize() + "&";
	    	}
			params = params.concat("fromPage=");
			params = params.concat("edit");
			showLoadingIcon($("#container")); // Loading Icon
			performAction('applications', params, $('#container'));
		});
	});
	
	function featureUpdate(url){
		var doGetDisableItems = false;
		if(url == 'save'){
			doGetDisableItems = true;
		}
		var allModuleVals = "";
		var allJsVals = "";
		$('input:checkbox[name=selectedModules]:checked').each(function() {
			var isDisabled = $(this).prop("disabled");
			if(doGetDisableItems || !isDisabled) {
				allModuleVals = allModuleVals + $(this).val() + ",";
			}
		});

		$('input:checkbox[name=selectedJsLibs]:checked').each(function() {
			var isJsDisabled = $(this).prop("disabled");
			if(doGetDisableItems || !isJsDisabled) {
				allJsVals = allJsVals + $(this).val() + ","; 
			}
		}); 
		allModuleVals = allModuleVals.substring(0, allModuleVals.length-1);
		allJsVals = allJsVals.substring(0, allJsVals.length-1);

		var params = "";
		var projectCode = $('#projectCode').val();
		var techId = $('#technology').val(); 
		params = params.concat("projectCode=")
		params = params.concat(projectCode);
		params = params.concat("&selectedModules=");
		params = params.concat(allModuleVals);
		params = params.concat("&selectedJsLibs=");
		params = params.concat(allJsVals);
		params = params.concat("&techId=");
		params = params.concat(techId);
		params = params.concat("&fromPage=");
		params = params.concat("edit");
		params = params.concat("&configServerNames=");
		params = params.concat($("#configServerNames").val());
		params = params.concat("&configDbNames=");
		params = params.concat($("#configDbNames").val());
		performAction(url, params, $('#container'));
	}
	
	function getPilotProjectModules(isPilotSelected) {
        var params = "technology=";
		params = params.concat($("#technology").val());
		performAction('getPilotProjectModules', params, '', true);
	}
	
	function chkUnchkPilotModules(pilotModules, isCheck) {
		for (i in pilotModules) {
   			$("input:radio[name='" + pilotModules[i] + "']").attr('checked', isCheck);
   			$("input:radio[name='" + pilotModules[i] + "']").attr('disabled', isCheck);
		
   			$("input:checkbox[value='" + pilotModules[i] + "']").attr('checked', isCheck);
   			$("input:checkbox[value='" + pilotModules[i] + "']").attr('disabled', isCheck);
			
			if(isCheck) {
				var version = $("input:radio[name='" + pilotModules[i] + "']").val();
				$("p[id='" +  pilotModules[i] + "version']").html(version);
			} else {
				$("p[id='" +  pilotModules[i] + "version']").empty();
			}
    	}
		getDefaultModules();
	}
	
	function getDefaultModules() {
		var params = "technology=";
		params = params.concat($("#technology").val());
		performAction('fetchDefaultModules', params, '', true);
	}
	
	function enableModuleDesc(enableProp) {
    	$(".wel_come").css("display", enableProp);
    	$("#descriptionDialog").css("display", enableProp);
    	$("#descDialog").show().css("display", enableProp);
    	escPopup();
	}
	
	function removeItem(arr, val) {
		for(var i=0; i<arr.length; i++) {
			if(arr[i] == val) {
				arr.splice(i, 1);
				break;
			}
		}
	}
	
	//Radio button click function
	function selectCheckBox(moduleId, moduleType, currentElement) {
		// var version = $("input[name='" + moduleId + "']").val();
		var preVersion = $("p[id='" + moduleId + "version']").html(); 
		var version = currentElement.value;
		tickCheckbox(moduleId, version, true);
		checkDependency(moduleId, moduleType, version, preVersion);
	}
	
	function tickCheckbox(moduleId, version, flag) {
		$("input[id='" + moduleId + "checkBox']").prop("checked", flag);
		$("p[id='" + moduleId + "version']").empty();
		if (flag) {
			$("p[id='" + moduleId + "version']").html(version);
		}
	}
	
	var isCheck = false;
	function uncheckDependency(moduleId, moduleType, version) {
		dependencyCall(moduleId, moduleType, version, false);
		isCheck = false;
	}
	
	function checkDependency(moduleId, moduleType, version, preVersion) {
		dependencyCall(moduleId, moduleType, version, true,  preVersion);
		isCheck = true;
	}
	
	function dependencyCall(moduleId, moduleType, version, isChk, preVersion) {
        var params = "moduleId=";
		params = params.concat(moduleId);
		params = params.concat("&version=");
		params = params.concat(version);
		params = params.concat("&moduleType=");
		params = params.concat(moduleType);
		params = params.concat("&techId=");
		params = params.concat($("#technology").val());
		if(preVersion != undefined) {
			params = params.concat("&preVersion=");
			params = params.concat(preVersion);
		}
		performAction('checkDependency', params, '', true);
	}
	
	function successDependencyCall(data) {
		dependentChkOrUnchk(data.dependentIds, data.dependentVersions, isCheck);
        dependentChkOrUnchk(data.preDependentIds, data.preDependentVersions, false);
	}
	
	function dependentChkOrUnchk(ids, versions, isCheck) {
		for (i in ids) {
    		tickCheckbox(ids[i], versions[i], isCheck);
    		$("input:radio[name='" + ids[i] + "'][value='" + versions[i] + "']").attr('checked', isCheck);
    	}
	}
		
	function successEvent(pageUrl, data) {
		if(pageUrl == "getPilotProjectModules") {
			chkUnchkPilotModules(data.pilotModules, isPilotSelected);
		} else if(pageUrl == "checkDependency") {
			successDependencyCall(data);
		} else if(pageUrl == "fetchDefaultModules") {
			chkDefaultModules(data.defaultModules);
		}
	}
	
	/** To check the default modules **/
	function chkDefaultModules(defaultModules) {
		for (i in defaultModules) {
			$("input:radio[name='" + defaultModules[i] + "']").attr('checked', true);
			$("input:radio[name='" + defaultModules[i] + "']").attr('disabled', true);
	
			$("input:checkbox[value='" + defaultModules[i] + "']").attr('checked', true);
			$("input:checkbox[value='" + defaultModules[i] + "']").attr('disabled', true);
	
			var version = $("input:radio[name='" + defaultModules[i] + "']").val();
			$("p[id='" +  defaultModules[i] + "version']").html(version);
		}
	}
	
	/** Select all features starts **/
	function selectAllChkBoxClk(moduleType, crrentElement) {
		var isChecked = $(crrentElement).prop("checked");
		if(isChecked) {
			selectAll(moduleType);
		} else {
			unSelectAll(moduleType);
		}
	}

	function selectAll(moduleType) {
		$("."+moduleType).prop("checked", true);
		$("."+moduleType).each(function() {
			var name = $(this).val();
			$("input:radio[name='" + name + "']:first").prop("checked", true);
			var version = $("input:radio[name='" + name + "']:first").val();
			$("p[id='" + name + "version']").html(version);
		});
	}

	function unSelectAll(moduleType){
		$("."+moduleType).each(function() {
			if (!$(this).is(':disabled')) {
				$(this).prop("checked", false);
				var name = $(this).val();
				$("input:radio[name='" + name + "']:first").prop("checked", false);
				$("p[id='" + name + "version']").empty();
			}
		});
	}
	/** Select all features ends **/