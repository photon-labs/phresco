<%--
  ###
  Service Web Archive
  
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
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">
function findError(data) {
	if(data.nameError != undefined) {
		showError($("#nameControl"), $("#nameError"), data.nameError);
	} else {
		hideError($("#nameControl"), $("#nameError"));
	}
	
	if(data.urlError != undefined) {
		showError($("#urlControl"), $("#urlError"), data.urlError);
	} else {
		hideError($("#urlControl"), $("#urlError"));
	}
}

</script>

<form class="form-horizontal customer_list">
	<h4><s:label for="description" key="lbl.header.admin.url.tiltle" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<s:label for="input01" key="lbl.header.admin.name" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="Customer Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
		<s:label for="input01" key="lbl.header.admin.desc" cssClass="control-label labelbold" theme="simple"/>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text" >
				
			</div>
		</div>
		
		<div class="control-group" id="urlControl">
			<s:label for="input01" key="lbl.header.admin.glblurl.url" cssClass="control-label labelbold" theme="simple"/>
			<span class="mandatory">*</span>
			<div class="controls">
				<input id="input01" placeholder="URL" class="input-xlarge" type="text" name="url">
				<span class="help-inline" id="urlError"></span>
			</div>
		</div>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="globalurlSave" class="btn btn-primary" onclick="loadContent('globalurlSave', $('#subcontainer'));" value="<s:text name='lbl.header.comp.save'/>"/>
		<input type="button" id="globalurlCancel" class="btn btn-primary" onclick="loadContent('globalurlCancel', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cancel'/>"/>
	</div>
</form>