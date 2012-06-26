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
		
<form class="form-horizontal customer_list">
	<div class="operation">
		<input type="button" id="featuresAdd" class="btn btn-primary" name="features_add" onclick="loadContent('featuresAdd', $('#subcontainer'));" value="<s:text name='lbl.hdr.comp.featrs.add'/>"/>
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
	<div class="content_adder">
		<div class="control-group">
			<div class="external_features_wrapper">
				<div class="theme_accordion_container" id="coremodule_accordion_container">
					<section class="accordion_panel_wid">
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion">
									<span>
										Login form 
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.desc" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.comp.ver" theme="simple"/></th>
													</tr>
												</thead>
													
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Log In</a>
														</td>
														<td class="editFeatures_td4">Authentication</td>
														<td class="editFeatures_td4">version 2.0</td>
													</tr>
												</tbody>
											</table>
										</section>
									</div>
								</div>
							</section>  
						</div>
						
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion">
									<span>
										Blog
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th class="accordiantable"></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.desc"  theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.comp.ver"  theme="simple"/></th>
													</tr>
												</thead>
																									
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Blog</a>
														</td>
														<td class="editFeatures_td4">Journal</td>
														<td class="editFeatures_td4">version 2.0</td>
													</tr>
												</tbody>
											</table>
										</section>
									</div>
								</div>
							</section>  
						</div>
						
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion">
									<span>
										Help
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.desc"  theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.comp.ver" theme="simple"/></th>
													</tr>
												</thead>
																									
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Help</a>
														</td>
														<td class="editFeatures_td4">help</td>
														<td class="editFeatures_td4">version 2.0</td>
													</tr>
												</tbody>
											</table>
										</section>
									</div>
								</div>
							</section>   
						</div>
						
						<div class="accordion_panel_inner">
							<section class="lft_menus_container">
								<span class="siteaccordion">
									<span>
										Forum
									</span>
								</span>
								<div class="mfbox siteinnertooltiptxt">
									<div class="scrollpanel">
										<section class="scrollpanel_inner">
											<table class="download_tbl">
												<thead>
													<tr>
														<th></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.name" theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.cmp.desc"  theme="simple"/></th>
														<th class="accordiantable"><s:label for="description" key="lbl.hdr.comp.ver" theme="simple"/></th>
													</tr>
												</thead>
																									
												<tbody>
													<tr>
														<td class="editFeatures_td1">
															<input type="radio" name="" value="">
														</td>
														<td class="editFeatures_td2">
															<div class="accordalign"></div>
															<a href="#" name="ModuleDesc" class="" id="" >Forum</a>
														</td>
														<td class="editFeatures_td4">Discussion site</td>
														<td class="editFeatures_td4">version 2.0</td>
													</tr>
												</tbody>
											</table>
										</section>
									</div>
								</div>
							</section> 
						</div>
					</section>		
				</div>
			</div>
		</div>
	</div>	
</form>

