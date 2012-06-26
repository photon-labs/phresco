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
		<input type="button" class="btn btn-primary" name="configTemplate_add" id="configtempAdd" onclick="loadContent('configtempAdd', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.cnfigtmplte.add'/>"/>
		<input type="button" class="btn" id="del" disabled value="<s:text name='lbl.hdr.comp.delete'/>"/>
		<s:if test="hasActionMessages()">
			<div class="alert alert-success"  id="successmsg">
				<s:actionmessage />
			</div>
		</s:if>
		<s:if test="hasActionErrors()">
			<div class="alert alert-error"  id="errormsg">
				<s:actionerror />
			</div>
		</s:if>
	</div>
		
	<div class="table_div">
		<div class="fixed-table-container">
			<div class="fixed-table-container-inner">
				<table cellspacing="0" class="zebra-striped">
					<thead>
						<div class="header-background">
							<tr>
								<th class="first">
									<div class="th-inner tablehead">
										<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
									</div>
								</th>
								<th class="second">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.cmp.name" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.cmp.desc" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.comp.aplesto" theme="simple"/></div>
								</th>
							</tr>
						</div>
					</thead>
					
					<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Server</a>
							</td>
							<td>Server configuration settings</td>
							<td>Drupal</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Database</a>
							</td>
							<td >Database configuration settigns</td>
							<td>PHP</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Web Service</a>
							</td>
							<td >Web Service configuration settings</td>
							<td>Java, Node JS</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Email</a>
							</td>
							<td >Email configuration settings</td>
							<td>Php, Drupal</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>