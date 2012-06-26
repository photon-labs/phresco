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
		<input type="button" class="btn btn-primary" name="application_add" id="applicationAdd" onclick="loadContent('applicationAdd', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.apln.add'/>"/>
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
								<th class="first nameTd">
									<div class="th-inner">
										<input type="checkbox" value="" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
									</div>
								</th>
								<th class="second">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.cmp.name" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.hdr.cmp.desc" theme="simple"/></div>
								</th>
							</tr>
						</div>
					</thead>
								
					<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td class="nameTd">
								<a href="#" name="edit" id="" >Web Application</a>
							</td>
							<td>An application that is accessed over a network such as the Internet or an intranet</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td class="nameTd">
								<a href="#" name="edit" id="" >Mobile</a>
							</td>
							<td>A mobile application is software that runs on a handheld device (phone, tablet, iPod, etc.) than can connect to wifi or wireless carrier networks, and has an operating system that supports web or standalone software</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td class="nameTd">
								<a href="#" name="edit" id="" >Web Service</a>
							</td>
							<td>A Web service is a method of communication between two electronic devices over the web. The W3C defines a "Web service" as "a software system designed to support interoperable machine-to-machine interaction over a network"</td>
						</tr>
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" value="" onclick="checkboxEvent();">
							</td>
							<td class="nameTd">
								<a href="#" name="edit" id="" >Standalone</a>
							</td>
							<td>Standalone Application is a software that can work offline, i.e. does not necessarily require network connection to function</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>