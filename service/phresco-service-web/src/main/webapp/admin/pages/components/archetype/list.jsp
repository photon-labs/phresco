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
		<input type="button" id="archetypeAdd" class="btn btn-primary" name="archetype_add" onclick="loadContent('archetypeAdd', $('#subcontainer'));" value="<s:text name='lbl.header.comp.arhtyp.add'/>"/>
		<input type="button" id="del" class="btn" disabled value="<s:text name='lbl.header.comp.delete'/>"/>
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
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.cmp.name"  theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.cmp.desc"  theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.ver"  theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.apptype"  theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
					
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Drupal</a>
							</td>
							<td class="namelabel-width">Drupal</td>
							<td class="namelabel-width">7.0</td>
							<td>Web Application</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >PHP</a>
							</td>
							<td>PHP</td>
							<td>5.3</td>
							<td>Web Application</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Android</a>
							</td>
							<td>Android</td>
							<td>2.3.3</td>
							<td>Mobile</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Java</a>
							</td>
							<td>Java</td>
							<td>1.7</td>
							<td>Standalone</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Node JS</a>
							</td>
							<td>Node JS</td>
							<td>0.4.11</td>
							<td>Web Service</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >HTML5 Multichannel Widget</a>
							</td>
							<td
							>HTML5 Multichannel Widget</td>
							<td>5.0</td>
							<td>Web Application</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>
