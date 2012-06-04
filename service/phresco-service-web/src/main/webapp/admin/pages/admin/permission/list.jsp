<%@ taglib uri="/struts-tags" prefix="s" %>

<form class="form-horizontal customer_list">
	<div class="operation">
		
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
									<input type="checkbox" id="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
								</div>
							</th>
							<th class="second">
								<div class="th-inner tablehead">
								<s:label for="description" key="lbl.header.admin.prmlst.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.prmlst.prms" theme="simple"/></div>
							</th>
							
					</thead>
		
					<tbody>
					
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td class="namelabel-width">
								View
							</td>
							<td>
								Access to view
							</td>
							
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								Update
							</td>
							<td>
								Access to edit/delete
							</td>
							
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								Create
							</td>
							<td>
								Access to add/edit/delete
							</td>
							
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>