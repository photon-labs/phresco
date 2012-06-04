<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">
	clickMenu($("a[name='url_add']"),$("#subcontainer"));
</script>

<form class="customer_list">
	<div class="operation">
		<input type="button" id="globalurlAdd" class="btn btn-primary" name="url_add" onclick="loadContent('globalurlAdd', $('#subcontainer'));" value="<s:text name='lbl.header.admin.urllst.title'/>"/>
		<input type="button" id="del" class="btn" disabled value="<s:text name='lbl.header.admin.delete'/>"/>	
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
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.lurllst.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.urllst.desc" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.urllst.url" theme="simple"/></div>
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