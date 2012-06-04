<%@ taglib uri="/struts-tags" prefix="s" %>
<div class="dashboard">
	<section class="dashboard-panel" style="1px solid red">
		<div class="list-wrapper">
			<div class="list-header">
				<aside class="left-list-header tabletitle">
				<s:label for="description" key="lbl.header.dshbd.archtyp"  theme="simple"/>
				</aside>
				<div id="list-header-toggle1"></div>
				<aside class="right-list-header">
					<img id="replaceimg1" src="images/minus_icon.png" alt="open" />
				</aside>
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
												<s:label for="description" key="lbl.header.dshbd.archtyp.archtyp"  theme="simple"/>
											</div>
										</th>
									
										<th class="first">
											<div class="th-inner tablehead">
												<s:label for="description" key="lbl.header.dshbd.archtyp.cus"  theme="simple"/>
											</div>
										</th>
										
										<th class="first">
											<div class="th-inner tablehead">
												<s:label for="description" key="lbl.header.dshbd.archtyp.hits"  theme="simple"/>
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
	<section class="dashboard-panel" style="1px solid red">
		<div class="list-wrapper">
			<div class="list-header">
				<aside class="left-list-header tabletitle"><s:label for="description" key="lbl.header.dshbd.featrs"  theme="simple"/></aside>
				<div id="list-header-toggle2"> </div>
				<aside class="right-list-header">
					<img id="replaceimg2" src="images/minus_icon.png" alt="open" />
				</aside>
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
												<s:label for="description" key="lbl.header.dshbd.featrs.featrs"  theme="simple"/>
											</div>
										</th>
									
										<th class="first">
											<div class="th-inner tablehead">
												<s:label for="description" key="lbl.header.dshbd.featrs.tchngy"  theme="simple"/>
											</div>
										</th>
										<th class="first">
											<div class="th-inner tablehead">
											<s:label for="description" key="lbl.header.dshbd.featrs.hits"  theme="simple"/>	
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
	<section class="dashboard-panel" style="1px solid red">
		<div class="list-wrapper">
			<div class="list-header">
				<aside class="left-list-header tabletitle"><s:label for="description" key="lbl.header.dshbd.pltprjt"  theme="simple"/></aside>
				<div id="list-header-toggle3"> </div>
				<aside class="right-list-header">
					<img id="replaceimg3" src="images/minus_icon.png" alt="open" />
				</aside>
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
												<s:label for="description" key="lbl.header.dshbd.plt.plprjt"  theme="simple"/>
											</div>
										</th>
									
										<th class="first">
											<div class="th-inner tablehead">
												<s:label for="description" key="lbl.header.dshbd.plt.cus" cssClass="slideheading  subtablemenu" theme="simple"/>
											</div>
										</th>
										<th class="first">
											<div class="th-inner tablehead">
												<s:label for="description" key="lbl.header.dshbd.plt.hits" cssClass="slideheading  subtablemenu" theme="simple"/>
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
		$('#replaceimg1').click(function () {
			$('.list-slide1').slideToggle();
			if ($("#list-header-toggle1").is(":hidden")) {
				 $("#replaceimg1").attr('src','images/add_icon.png');
	            $("#list-header-toggle1").show();
	        } else {
	            
			    $("#replaceimg1").attr('src','images/minus_icon.png');
	            $("#list-header-toggle1").hide();
	        }
		});	
			
		$('#replaceimg2').click(function () {
			$('.list-slide2').slideToggle();
			if($('#list-header-toggle2').is(":hidden")) {
				$("#replaceimg2").attr('src','images/add_icon.png');
	            $("#list-header-toggle2").show();
	        } else {
	            
			    $("#replaceimg2").attr('src','images/minus_icon.png');
	            $("#list-header-toggle2").hide();
			}
		});
		
		$('#replaceimg3').click(function() {
			$('.list-slide3').slideToggle();
			if($('#list-header-toggle3').is(":hidden")) {
				$('#replaceimg3').attr('src','images/add_icon.png');
				$('#list-header-toggle3').show();
			} else {
				$('#replaceimg3').attr('src','images/minus_icon.png');
				 $("#list-header-toggle3").hide();
			}
		});
	});
</script>