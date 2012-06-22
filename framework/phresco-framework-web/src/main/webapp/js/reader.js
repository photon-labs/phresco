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
// from auto close
var showSuccessComplete = true;
function readerHandler(data, projectCode, testType) {
	
	// from auto close
	if($.trim(data) == 'Test is not available for this project') {
		data = '<b>Test is not available for this project</b>';
		showSuccessComplete = false;
	}
	if($.trim(data) == '[INFO] ... tomcatProcess stopped. ReturnValue:1') { // When CI server starts it wont give EOF, forced it.
		data = 'EOF';
	}

   if ($.trim(data) == 'EOF') {
	   $("#warningmsg").hide();
	   $('#loadingDiv').hide();
	   $('#buildbtn').prop("disabled", false);
	   
	   // from auto close
//	   if(showSuccessComplete) {
//		   $("#build-output").append("Successfully Completed" + '<br>');
//	   }
	   $('#build-output').prop('scrollTop', $('#build-output').prop('scrollHeight'));
	   
	   if(testType == "build") {
		   refreshTable(projectCode);
	   }
	   return;
   }
   $("#build-output").append(data + '<br>');
   $('#build-output').prop('scrollTop', $('#build-output').prop('scrollHeight'));
   asyncHandler(projectCode, testType);
}

function asyncHandler(projectCode, testType) {
   $.ajax({
        url : 'pages/applications/reader.jsp',
        type : "POST",
        data : {
            'projectCode' : projectCode,
            'testType' : testType
        },
        success : function(data) { 
            readerHandler(data, projectCode, testType); 
        }
    });
}

function refreshTable(projectCode) {
    $.ajax({
         url : 'builds',
         data : {
             'projectCode' : projectCode
         },
         type : "POST",
         success : function(data) { 
             $('#build-body-container').html(data);
         }
     });
 }