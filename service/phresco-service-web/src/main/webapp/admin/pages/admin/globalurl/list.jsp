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
	clickMenu($("a[name='url_add']"),$("#subcontainer"));
</script>

<form class="customer_list">
	<div class="operation" id="operation">
		<input type="button" id="globalurlAdd" class="btn btn-primary" name="url_add" onclick="loadContent('globalurlAdd', '', $('#subcontainer'));" value="<s:text name='lbl.hdr.adm.urllst.title'/>"/>
		<input type="button" id="del" class="btn" disabled value="<s:text name='lbl.hdr.adm.delete'/>"/>
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
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.lurllst.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.urllst.desc" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label key="lbl.hdr.adm.urllst.url" theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td class="namelabel-width">
								<a href="#" name="edit" id="" >Login Service</a>
							</td>
							<td>Authentication Details</td>
							<td>http://172.16.28.1:3030/service</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="">forum</a>
							</td>
							<td>Group Discussion Board</td>
							<td>http://172.16.27.121:3030/service</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="">Twitter</a>
							</td>
							<td >Social Blog </td>
							<td>http://172.165.28.11:3030/service</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Gmailer</a>
							</td>
							<td >Mailing Service Provider</td>
							<td>http://172.161.28.1:3030/service</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>	