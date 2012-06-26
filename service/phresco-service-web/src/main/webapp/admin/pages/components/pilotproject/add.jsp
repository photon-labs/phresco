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
	
	if(data.fileError != undefined) {
		showError($("#fileControl"), $("#fileError"), data.fileError);
		} else {
			hideError($("#fileControl"), $("#fileError"));
		}
}
</script>

<form class="form-horizontal customer_list">
	<h4 class="hdr"><s:label for="description" key="lbl.hdr.comp.pltprjt.title" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<label for="input01" class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.name'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="Customer Name" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label for="input01" class="control-label labelbold">
				<s:text name='lbl.hdr.comp.desc'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="Description" class="input-xlarge" type="text">
			</div>
		</div>
		
		<div class="control-group" id="fileControl">
			<label for="input01" class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.projsrc'/>
			</label>
			<div class="controls">
				<input class="input-xlarge" type="file" id="projArc" name="projArc">
				<span class="help-inline" id="fileError"></span>
			</div>
		</div>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="pilotprojSave" class="btn btn-primary" onclick="formSubmitFileUpload('pilotprojSave', 'projArc', $('#subcontainer'), 'Creating Pilotproject');" value="<s:text name='lbl.hdr.comp.save'/>"/>
		<input type="button" id="pilotprojCancel" class="btn btn-primary" onclick="loadContent('pilotprojCancel', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.cancel'/>"/>
	</div>
</form>