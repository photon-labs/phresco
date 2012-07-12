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

<div class="dashboard" style="width: 100%;">
	<section id="dashboard-panel1" class="dashboard-panel" style="width: 31%; margin: 1px 0 1px 0;">
		<div class="list-wrapper">
			<div class="header-background">
				<div class="list-header">
					<aside class="left-list-header tabletitle">
					<s:label key="lbl.hdr.dash.archtyp"  theme="simple"/>
					</aside>
					<div id="list-header-toggle1"></div>
					<aside class="right-list-header">
						<img id="replaceimg1" src="images/minus_icon.png" alt="open" />
					</aside>
				</div>
			</div>
			
			<div class="list-comment list-slide1">
				<div class="table_div">
					<div class="fixed-table-container">
						<div class="header-background"></div>
						<div class="fixed-table-container-inner">
							<table cellspacing="0" class="zebra-striped">
								<thead>
									<tr>
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.archtyp.archtyp"  theme="simple"/>
											</div>
										</th>
									
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.archtyp.cus"  theme="simple"/>
											</div>
										</th>
										
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.archtyp.hits"  theme="simple"/>
											</div>
										</th>
									</tr>	
								</thead>
								
								<tr>
									<td>HTML 5</td>
									<td >Macys</td>
									<td>55</td>
								</tr>
								
								<tr>
									<td>JavaScript</td>
									<td >Phresco</td>
									<td>56</td>
								</tr>
								
								<tr>
									<td>HTML 5</td>
									<td >Macys</td>
									<td>55</td>
								</tr>
								
								<tr>
									<td>NodeJS</td>
									<td>Macys</td>
									<td>52</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>	
	</section>
	<section id="dashboard-panel2" class="dashboard-panel">
		<div class="list-wrapper">
			<div class="header-background">
				<div class="list-header">
					<aside class="left-list-header tabletitle"><s:label key="lbl.hdr.dash.featrs"  theme="simple"/></aside>
					<div id="list-header-toggle2"> </div>
					<aside class="right-list-header">
						<img id="replaceimg2" src="images/minus_icon.png" alt="open" />
					</aside>
				</div>
			</div>
			
			<div class="list-comment list-slide2">
				<div class="table_div">
					<div class="fixed-table-container">
						<div class="header-background"></div>
						<div class="fixed-table-container-inner">
							<table cellspacing="0" class="zebra-striped">
								<thead>
									<tr>
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.featrs.featrs"  theme="simple"/>
											</div>
										</th>
									
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.featrs.tech"  theme="simple"/>
											</div>
										</th>
										<th class="first">
											<div class="th-inner tablehead">
											<s:label key="lbl.hdr.dash.featrs.hits"  theme="simple"/>	
											</div>
										</th>
									</tr>	
								</thead>
								<tr>
									<td>Login</td>
									<td >Drupal</td>
									<td>55</td>
								</tr>
								
								<tr>
									<td>Log</td>
									<td>Php</td>
									<td>52</td>
								</tr>
								
								<tr>
									<td>List Files</td>
									<td >Java</td>
									<td>56</td>
								</tr>
								
								<tr>
									<td>Pagenation</td>
									<td >HTML 5</td>
									<td>55</td>
								</tr>	
							</table>
						</div>
					</div>
				</div>	
			</div>
		</div>	
	</section>
	<section id="dashboard-panel3" class="dashboard-panel">
		<div class="list-wrapper">
			<div class="header-background">
				<div class="list-header">
					<aside class="left-list-header tabletitle"><s:label key="lbl.hdr.dash.pltprjt"  theme="simple"/></aside>
					<div id="list-header-toggle3"> </div>
					<aside class="right-list-header">
						<img id="replaceimg3" src="images/minus_icon.png" alt="open" />
					</aside>
				</div>
			</div>
			<div class="list-comment list-slide3">
				<div class="table_div">
					<div class="fixed-table-container">
						<div class="header-background"></div>
						  <div class="fixed-table-container-inner">  
							<table cellspacing="0" class="zebra-striped">
								<thead>
									<tr>
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.plt.plprjt"  theme="simple"/>
											</div>
										</th>
									
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.plt.cus" cssClass="slideheading  subtablemenu" theme="simple"/>
											</div>
										</th>
										<th class="first">
											<div class="th-inner tablehead">
												<s:label key="lbl.hdr.dash.plt.hits" cssClass="slideheading  subtablemenu" theme="simple"/>
											</div>
										</th>
									</tr>	
								</thead>
								<tr>
									<td>Eshop</td>
									<td >Macys</td>
									<td>55</td>
								</tr>
								
								<tr>
									<td>IMS</td>
									<td>Horizon Blue</td>
									<td>52</td>
								</tr>
								
								<tr>
									<td>Inventory System</td>
									<td >Macsys</td>
									<td>56</td>
								</tr>
								<tr>
									<td>Eshop</td>
									<td >Macys</td>
									<td>55</td>
								</tr>
							</table>
						 </div> 
					</div>
				</div>
			</div>
		</div>	
	</section>
</div>

<script type="text/javascript">

	$(document).ready(function() {
		$('#replaceimg1').click(function() {
			$('.list-slide1').slideToggle();
			if ($("#list-header-toggle1").is(":hidden")) {
				$("#replaceimg1").attr('src', 'images/add_icon.png');
				$("#list-header-toggle1").show();
				$("#dashboard-panel1").css("height", "auto");
			} else {
				$("#replaceimg1").attr('src', 'images/minus_icon.png');
				$("#list-header-toggle1").hide();
				$("#dashboard-panel1").css("height", "97%");
			}
		});

		$('#replaceimg2').click(function() {
			$('.list-slide2').slideToggle();
			if ($('#list-header-toggle2').is(":hidden")) {
				$("#replaceimg2").attr('src', 'images/add_icon.png');
				$("#list-header-toggle2").show();
				$("#dashboard-panel2").css("height", "auto");
			} else {

				$("#replaceimg2").attr('src', 'images/minus_icon.png');
				$("#list-header-toggle2").hide();
				$("#dashboard-panel2").css("height", "97%");
			}
		});

		$('#replaceimg3').click(function() {
			$('.list-slide3').slideToggle();
			if ($('#list-header-toggle3').is(":hidden")) {
				$('#replaceimg3').attr('src', 'images/add_icon.png');
				$('#list-header-toggle3').show();
				$("#dashboard-panel3").css("height", "auto");
			} else {
				$('#replaceimg3').attr('src', 'images/minus_icon.png');
				$("#list-header-toggle3").hide();
				$("#dashboard-panel3").css("height", "97%");
			}
		});
	});
</script>