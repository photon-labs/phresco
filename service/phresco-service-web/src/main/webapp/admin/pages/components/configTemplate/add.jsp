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
	
	if(data.applyError != undefined) {
		showError($("#applyControl"), $("#applyError"), data.applyError);
	} else {
		hideError($("#applyControl"), $("#applyError"));
	}
}
</script>

<form name="configForm" class="form-horizontal customer_list">
	<h4 class="hdr"><s:label key="lbl.hdr.comp.cnfigtmplt.title" theme="simple"/></h4>	
	<div class="content_adder">
		<div class="control-group" id="nameControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.name'/>
			</label>
			<div class="controls">
				<input id="configname" placeholder="<s:text name='place.hldr.configTemp.add.name'/>" class="input-xlarge" type="text" name="name">
				<span class="help-inline" id="nameError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.desc'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.configTemp.add.desc'/>" class="input-xlarge" type="text">
			</div>
		</div>
		
		<div class="control-group" id="applyControl">
			<label class="control-label labelbold">
				<span class="mandatory">*</span>&nbsp;<s:text name='lbl.hdr.comp.appliesto'/>
			</label>
			<div class="controls">
				<select id="multiSelect" multiple="multiple" name="applies">
					<option value="PH">PHP</option>
					<option value="DR">Drupal</option>
					<option value="HT">HTML5 Multichannel Widget</option>
					<option value="JA">Java</option>
					<option value="NJ">Node JS</option>
				</select>
				<span class="help-inline applyerror" id="applyError"></span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label labelbold">
				<s:text name='lbl.hdr.comp.help'/>
			</label>
			<div class="controls">
				<input id="input01" placeholder="<s:text name='place.hldr.configTemp.add.help.text'/>" class="input-xlarge" type="text">
			</div>
		</div>						
			
		<fieldset class = "configFieldset">
			<legend class = "configLegend"><s:label key="lbl.hdr.comp.proptemplate" cssClass="labelbold" theme="simple"/></legend>
			<div class = "table_div">
				<div class="fixed-table-container prpt-header">
					<div class="fixed-table-container-inner">
						<table cellspacing="0" class="zebra-striped tablelegend">
							<div class = "header-background">
								<thead class = "fieldset-tableheader">
									<tr>
										<th class="second">
											<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.cnfigtmplt.key.title" theme="simple"/></div>
										</th>
										<th class="second">
											<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.cnfigtmplt.type.title" theme="simple"/></div>
										</th>
										<th class="third">
											<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.cnfigtmplt.psblvalue.title"  theme="simple"/></div>
										</th>
										<th class="third">
											<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.cnfigtmplt.mndtry.title"  theme="simple"/></div>
										</th>
										<th class="third">
											<div class="th-inner tablehead"><s:label key="lbl.hdr.comp.cnfigtmplt.mltpl.title" theme="simple"/></div>
										</th>
										<th class="third">
											<div class="th-inner">
												
											</div>
										</th>
									</tr>
								</thead>
							</div>
							<div id="input1" class="clonedInput">
								<tbody>
									<tr class="configdynamiadd">
										<td class="textwidth">
											<input type="text" id = "concate" value="" placeholder="" class="span2">
										</td>
										<td class="textwidth">
											<select id="select01" class = "select typewidth">
												<option>- select -</option>
												<option>String</option>
												<option>Integer</option>
												<option>Data</option>
												<option>Password</option>
											</select>
										</td>
										<td class="psblevalue">
											<input type="text" placeholder="" class="span3">
											<a data-toggle="modal" href="#myModal"><img class="addiconAlign imagealign" src="images/add_icon.png"/></a>
										</td>
										<td class="mandatoryfld">
											<input type="checkbox" value="option1" id="optionsCheckbox">
										</td>
										<td class="multiplefld">
											<input type="checkbox" value="option1" id="optionsCheckbox">
										</td>
										<td class="imagewidth">
											<a ><img class="add imagealign" src="images/add_icon.png" onclick="addconfig();"></a>
										</td>
									</tr>
								</tbody>
							</div>
						</table>
						
						<div id="myModal" class="modal hide fade">
							<div class="modal-header">
								<a class="close" data-dismiss="modal" >&times;</a>
								<h3><s:label key="lbl.hdr.comp.cnfigtmplt.popup.title" theme="simple"/></h3>
							</div>
							<div class="modal-body">
								<div class="control-group">
									<s:label key="lbl.hdr.comp.popup.enter" cssClass="control-label labelbold modallbl-color" theme="simple"/>
									<div class="controls">
										<input type="text" name="txtCombo" id="txtCombo" class="span3"/>
										<button type="button" value="" id="addValues" class="btn btn-primary popupadd">
											<s:label key="lbl.hdr.comp.popup.add" theme="simple"/>
										</button>
									</div>
								</div>
								
								<div class="control-group">
									<div class="controls">
										<select name="valuesCombo" id="valuesCombo" multiple="multiple">
										
										</select>
										<div class="popopimage">
											<img src="images/up_arrow.jpeg" title="Move up" id="up" class="imageupdown"><br>
											<img src="images/remove.jpeg" title="Remove" id="remove" class="imageremove"><br>
											<img src="images/down_arrow.png" title="Move down" id="down" class="imageupdown" >
										</div>
									</div>
								</div>
							</div>
							
							<div class="modal-footer">
								<a href="#" class="btn btn-primary" data-dismiss="modal"><s:label key="lbl.hdr.comp.cancel" theme="simple"/></a>
								<a href="#" class="btn btn-primary" data-dismiss="modal" ><s:label key="lbl.hdr.comp.ok" theme="simple"/></a>
							</div>
						</div>
					</div>	
				</div>
			</div>	
		</fieldset>
	</div>
	
	<div class="bottom_button">
		<input type="button" id="configtempSave" class="btn btn-primary" onclick="clickSave('configtempSave', $('#subcontainer'), 'Creating Config Template');" value="<s:text name='lbl.hdr.comp.save'/>"/>
		<input type="button" id="configtempCancel" class="btn btn-primary" onclick="loadContent('configtempCancel', '', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.cancel'/>"/>
	</div>
</form>

<script language="javascript">
$(document).ready(function() {
	$("#addValues").click(function() {
		var val = $("#txtCombo").val();
		$("#valuesCombo").append($("<option></option>").attr("value", val).text(val));
		$("#txtCombo").val("");
	});

	/* $('.add').live('click', function() {
		alert("sd");
		var appendRow =  '<tr class="configdynamiadd">' + $('.configdynamiadd').html() + '</tr>';
		$("tr:last").after(appendRow);			
		$("td:last").append('<img class = "del imagealign" src="images/minus_icon.png" onclick="removeTag(this);">');		
	});  */

	$('#remove').click(function() {
		$('#valuesCombo option:selected').each( function() {
			$(this).remove();
		});
	});

	//To move up the values
	$('#up').bind('click', function() {
		$('#valuesCombo option:selected').each( function() {
			var newPos = $('#valuesCombo  option').index(this) - 1;
			if (newPos > -1) {
				$('#valuesCombo  option').eq(newPos).before("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
				$(this).remove();
			}
		});
	});

	//To move down the values
	$('#down').bind('click', function() {
		var countOptions = $('#valuesCombo option').size();
		$('#valuesCombo option:selected').each( function() {
			var newPos = $('#valuesCombo  option').index(this) + 1;
			if (newPos < countOptions) {
				$('#valuesCombo  option').eq(newPos).after("<option value='"+$(this).val()+"' selected='selected'>"+$(this).text()+"</option>");
				$(this).remove();
			}
		});
	});
});
function addconfig(){
	var appendRow =  '<tr class="configdynamiadd">' + $('.configdynamiadd').html() + '</tr>';
	$("tr:last").after(appendRow);			
	$("td:last").append('<img class = "del imagealign" src="images/minus_icon.png" onclick="removeTag(this);">');		
}
 
function removeTag(currentTag) {
	$(currentTag).parent().parent().remove();
}
</script>