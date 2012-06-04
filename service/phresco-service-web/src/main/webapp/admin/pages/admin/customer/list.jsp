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

<form name="cusListForm" class="customer_list">
	<div class="operation">
		<input type="button" id="customerAdd" class="btn btn-primary" name="customer_add" onclick="loadContent('customerAdd', $('#subcontainer'));" value="<s:text name='lbl.header.admin.cust.add'/>"/>
		<input type="button"  id="del"  class="btn del" class="btn btn-primary" disabled value="<s:text name='lbl.header.admin.delete'/>"/>
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
									<input type="checkbox" id="checkAllAuto" class="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
								</div>
							</th>
							<th class="second">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admim.cuslt.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admim.cuslt.desc" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.cuslt.vldupto" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><div class="th-inner"><s:label for="description" key="lbl.header.admin.cusrlt.linctype" theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td class="namelabel-width">
								<a href="#" name="edit" id="" >Best Buy</a>
							</td>
							<td >Shopping site</td>
							<td>2011-2012</td>
							<td>
								Gold
							</td>		
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Horizon Blue</a>
							</td>
							<td >Health Insurance</td>
							<td>2011-2012</td>
							<td>
								Platinum	
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Travel click</a>
							</td>
							<td >Hotel business management</td>
							<td>2011-2012</td>
							<td>
								Silver	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>	
