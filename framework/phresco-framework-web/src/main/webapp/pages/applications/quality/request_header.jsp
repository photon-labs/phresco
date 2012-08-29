<%--
  ###
  Framework Web Archive
  
  Copyright (C) 1999 - 2012 Photon Infotech Inc.
  
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at
  
       http://www.apache.org/licenses/LICENSE-2.0
  
  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ###
  --%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!-- Header Popup starts -->
<div class="popup_Modal hideContent" id="headerPopup">
	<div class="modal-header">
		<h3>
			<s:text name="label.request.header"/>
		</h3>
		<a class="close" href="#" id="headerPopupClose">&times;</a>
	</div>

	<div class="modal-body">
		<div class="clearfix">
		    <label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.header.name"/></label>
		    <div class="input">
				<input type="text" placeholder="<s:text name="placeholder.header.name"/>" class="xlarge" name="headerName" />
		    </div>
		</div>
		<div class="clearfix">
		    <label for="xlInput" class="xlInput popup-label"><span class="red">* </span><s:text name="label.header.value"/></label>
		    <div class="input">
				<input type="text" placeholder="<s:text name="placeholder.header.value"/>" class="xlarge" name="headerValue"/>
		    </div>
		</div>
	</div>
	
	<div class="modal-footer">
		<div class="action popup-action">
			<div id="errMsgHeader" style="width:72%; text-align: left; float: left; color: red;"></div>
			<input type="button" class="btn primary" value="<s:text name="label.cancel"/>" id="headerPopupCancel">
			<input type="button" id="addHeaderActionBtn" class="btn primary" value="<s:text name="label.add"/>">
		</div>
	</div>
</div>
<!-- Header Popup ends -->