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
clickButton($("#permissionSave"), $("#subcontainer"));
clickButton($("#permissionCancel"), $("#subcontainer"));
</script>

<form class="form-horizontal customer_list">
	<div class="operation">
			
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
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.rles.asgnprm.name" theme="simple"/></div>
							</th>
							<th class="third" style="padding-left:19px;">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.rles.asgnprm.dvlpr" theme="simple"/></div>
							</th>
							<th class="third" style="padding-left:16px;">
								<div class="th-inner tablehead" ><s:label key="lbl.hdr.adm.rles.asgnprm.phscoadmn" theme="simple"/></div>
							</th>
							<th class="third" style="padding-left:21px;">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.rles.asgnprm.tstng" theme="simple"/></div>
							</th>
							<th class="third" style="padding-left:16px;">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.rles.asgnprm.frmwrk" theme="simple"/></div>
							</th>
							<th class="third" style="padding-left:23px;">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.rles.asgnprm.mcyadmn" theme="simple"/></div>
							</th>
							<th class="third" style="padding-left:15px;">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.rles.asgnprm.phtnemp" theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
						<tr>
							<td style = "width:5%">
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td style = "width:16%">
								Kumar
							</td>
							<td class = "tablealign" style = "width:12%">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign" style = "width:14%">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign" style = "width:10%">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign" style = "width:12%">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign" style = "width:15%">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign" style = "width:17%">
								<input type="checkbox" class="" name="" value="">
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								Ganesh
							</td>
							<td class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								Siva
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								Guna
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								Sabari
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								James
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
							<td  class = "tablealign">
								<input type="checkbox" class="" name="" value="">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="bottom_button">
			<input type="button" id="permissionSave" value="Save" class="btn btn-primary">
		<input type="button" id="permissionCancel" value="Cancel" class="btn btn-primary">
		</div>
	</div>
</form>