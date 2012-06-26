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

<script language="JavaScript" type="text/javascript">
<!--

var NS4 = (navigator.appName == "Netscape" && parseInt(navigator.appVersion) < 5);

function addOption(theSel, theText, theValue)
{
  var newOpt = new Option(theText, theValue);
  var selLength = theSel.length;
  theSel.options[selLength] = newOpt;
}

function deleteOption(theSel, theIndex)
{ 
  var selLength = theSel.length;
  if(selLength>0)
  {
    theSel.options[theIndex] = null;
  }
}

function moveOptions(theSelFrom, theSelTo){
  
  var selLength = theSelFrom.length;
  var selectedText = new Array();
  var selectedValues = new Array();
  var selectedCount = 0;
  
  var i;
  
  // Find the selected Options in reverse order
  // and delete them from the 'from' Select.
  for(i=selLength-1; i>=0; i--)
  {
    if(theSelFrom.options[i].selected)
    {
      selectedText[selectedCount] = theSelFrom.options[i].text;
      selectedValues[selectedCount] = theSelFrom.options[i].value;
      deleteOption(theSelFrom, i);
      selectedCount++;
    }
  }
  
  // Add the selected text/values in reverse order.
  // This will add the Options to the 'to' Select
  // in the same order as they were in the 'from' Select.
  for(i=selectedCount-1; i>=0; i--)
  {
    addOption(theSelTo, selectedText[i], selectedValues[i]);
  }
  
  if(NS4) history.go(0);
}

function moveAllOptions(theSelFrom, theSelTo){
	  
	  var selLength = theSelFrom.length;
	  var selectedText = new Array();
	  var selectedValues = new Array();
	  var selectedCount = 0;
	  
	  var i;
	  
	  // Find the selected Options in reverse order
	  // and delete them from the 'from' Select.
	  for(i=selLength-1; i>=0; i--)
	  {
	    if(theSelFrom.options[i])
	    {
	      selectedText[selectedCount] = theSelFrom.options[i].text;
	      selectedValues[selectedCount] = theSelFrom.options[i].value;
	      deleteOption(theSelFrom, i);
	      selectedCount++;
	    }
	  }
	  
	  // Add the selected text/values in reverse order.
	  // This will add the Options to the 'to' Select
	  // in the same order as they were in the 'from' Select.
	  for(i=selectedCount-1; i>=0; i--)
	  {
	    addOption(theSelTo, selectedText[i], selectedValues[i]);
	  }
	  
	  if(NS4) history.go(0);
	}
//-->
</script>

<form class="form-horizontal customer_list">
	<div class="operation" id="operation">
		<input type="button" id="featuresAdd" class="btn btn-primary" name="features_add" value="<s:text name='lbl.hdr.adm.usrlst'/>"/>
		<input type="button" id="del" class="btn" disabled value="<s:text name='lbl.hdr.adm.delete'/>"/>
	</div>
	
	<div class="table_div">
		<div class="fixed-table-container">
			<div class="header-background"> </div>
			<div class="fixed-table-container-inner">
				<table cellspacing="0" class="zebra-striped">
						<thead>
							<tr>
								<th class="first">
									<div class="th-inner">
										<input type="checkbox" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
									</div>
								</th>
								<th class="second">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.adm.usrlst.name" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.adm.usrlst.mail" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.adm.usrlst.status" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.adm.usrlst.asignrole" theme="simple"/></div>
								</th>
							</tr>
						</thead>
			
						<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check"  onclick="checkboxEvent();">
							</td>
							<td>
								<a>Smith</a>
							</td>
							<td class="emailalign">smith@gmail.com</td>
							<td>active</td>
							<td  class = "tablealign">
								<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Roles"></a>
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a>David</a>
							</td>
							<td>david.s@gmail.com</td>
							<td>active</td>
							<td  class = "tablealign">
								<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Roles"></a>
							</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a>John</a>
							</td>
							<td>john@ymail.com</td>
							<td>active</td>
							<td  class = "tablealign">
								<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Roles"></a>
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a>Peter</a>
							</td>
							<td>peter@yahoo.com</td>
							<td>active</td>
							<td  class = "tablealign">
								<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Roles"></a>
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a>Will Smith</a>
							</td>
							<td>willsmith@gmail.com</td>
							<td>active</td>
							<td  class = "tablealign">
								<a data-toggle="modal" href="#myModal"><input type="button" class="btn btn-primary addiconAlign" value="Roles"></a>
							</td>
						</tr>
					</tbody>
				</table>
				
								
				<div id="myModal" class="modal hide fade">
					<div class="modal-header">
					  <a class="close" data-dismiss="modal" >&times;</a>
					  <h3><s:label for="description" key="lbl.hdr.adm.usrlst.role.popup.title" theme="simple"/></h3>
					</div>
					<div class="modal-body">
						<div class="popupbody">
							<div class="popupusr"><s:label for="input01" key="lbl.hdr.adm.usrname" cssClass="popuplabel" theme="simple"/></div> <div class="popupusr-name">Smith</div>
						</div>
						<div class="pouproles">
							<div class="popuprls"><s:label for="input01" key="lbl.hdr.adm.availrole" cssClass="popuplabel" theme="simple"/></div> 
							<div class="popuprole-select"><s:label for="input01" key="lbl.hdr.adm.selrole" cssClass="popuplabel" theme="simple"/></div>
						</div>
						<div class="popuplist">
							<div class="popup-list">
								<select names="rolesAvailable" class="sample" id="rolesAvailable" multiple="multiple">
									<option value="Phresco">Phresco Admin</option>
									<option value="Customer">Customer Admin</option>
								</select> 
							</div>
							
							<div class="popup-button">
								<div class="btnalign"><input type="button" class="btn sample" value=">" onclick="moveOptions(this.form.rolesAvailable, this.form.rolesSelected);"/></div>
								<div class="btnalign"><input type="button" class="btn sample" value=">>" onclick="moveAllOptions(this.form.rolesAvailable, this.form.rolesSelected);"/></div>
								<div class="btnalign"><input type="button" class="btn sample" value="<" onclick="moveOptions(this.form.rolesSelected, this.form.rolesAvailable);"/></div>
								<div class="btnalign"><input type="button" class="btn sample" value="<<" onclick="moveAllOptions(this.form.rolesSelected, this.form.rolesAvailable);"/></div>
							</div>
 
							<div class="popupselect">
								<select name="rolesSelected" class="sample" id="rolesSelected" multiple="multiple">
								</select> 
							</div>
						</div>
						
						<script language="javascript">
							$(document).ready(function() {
								$("#addValues").click(function() {
									var val = $("#txtCombo").val();
									$("#valuesCombo").append($("<option></option>").attr("value", val).text(val));
									$("#txtCombo").val("");
								});
																											
								$('.add').click(function(){
									var appendRow =  '<tr class="configdynamiadd">' + $('.configdynamiadd').html() + '</tr>';
									appendRow = appendRow.replace('class="add" src="images/add_icon.png"', 'class = "del" src="images/minus_icon.png" onclick="removeTag(this);"');
									$("tr:last").after(appendRow);			
								}); 
								
								$('#remove').click(function(){
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
							function removeTag(currentTag) {
								$(currentTag).parent().parent().parent().remove();
							}
						</script>
					</div>
					<div class="modal-footer">
					  <a href="#" class="btn btn-primary" data-dismiss="modal"><s:label for="description" key="lbl.hdr.comp.cancel" theme="simple"/></a>
					  <a href="#" class="btn btn-primary" data-dismiss="modal" ><s:label for="description" key="lbl.hdr.comp.ok" theme="simple"/></a>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>