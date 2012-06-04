<%@ taglib uri="/struts-tags" prefix="s" %>

<form class="customer_list">
	<div class="operation">
		<input type="button" class="btn btn-primary" name="configTemplate_add" id="configtempAdd" onclick="loadContent('configtempAdd', $('#subcontainer'));" value="<s:text name='lbl.header.comp.cnfigtmplte.add'/>"/>
		<input type="button" class="btn" id="del" disabled value="<s:text name='lbl.header.comp.delete'/>"/>
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
									<div class="th-inner tablehead"><s:label for="description" key="lbl.header.cmp.name" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.header.cmp.desc" theme="simple"/></div>
								</th>
								<th class="third">
									<div class="th-inner tablehead"><s:label for="description" key="lbl.header.comp.aplesto" theme="simple"/></div>
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