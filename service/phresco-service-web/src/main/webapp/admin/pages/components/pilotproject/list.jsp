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

<form class="customer_list">
	<div class="operation">
		<input type="button" class="btn btn-primary" name="pilotproj_add" id="pilotprojAdd" onclick="loadContent('pilotprojAdd', $('#subcontainer'));" value="<s:text name='lbl.header.comp.pltprjt.add'/>"/>
		<input type="button" class="btn" id="del" disabled value="<s:text name='lbl.header.comp.delete'/>"/>
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
									<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
								</div>
							</th>
							<th class="second">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.cmp.desc" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.tchngy" theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Eshop</a>
							</td>
							<td>Eshop</td>
							<td>Drupal</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Leave Management System</a>
							</td>
							<td>Leave Management System</td>
							<td>Drupal</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Forum</a>
							</td>
							<td>Discussion group</td>
							<td>Java</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>