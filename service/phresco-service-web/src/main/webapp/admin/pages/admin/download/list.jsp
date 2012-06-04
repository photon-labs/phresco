<%@ taglib uri="/struts-tags" prefix="s" %>

<form class="customer_list">
	<div class="operation">
		<input type="button" id="downloadAdd" class="btn btn-primary" name="download_add" onclick="loadContent('downloadAdd', $('#subcontainer'));" value="<s:text name='lbl.header.admin.dwndllst.title'/>"/>
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
									<input type="checkbox" value="" id="checkAllAuto" class="checkAllAuto" name="checkAllAuto" onclick="checkAllEvent(this);">
								</div>
							</th>
							<th class="second">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.dwnldlst.name" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.dwnldlst.desc" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.dwnldlst.appltfrm" theme="simple"/></div>
							</th>
							<th class="third">
								<div class="th-inner tablehead"><s:label for="description" key="lbl.header.admin.dwnldlst.ver"  theme="simple"/></div>
							</th>
						</tr>
					</thead>
		
					<tbody>
						<tr>
							<td class="checkboxwidth">
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td class="namelabel-width">
								<a href="#" name="edit" id="" >Notepad</a>
							</td>
							<td >Editor</td>
							<td>WindowsXP/7</td>
							<td>
								1.0
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Eclipse</a>
							</td>
							<td >IDE</td>
							<td>Windows7</td>
							<td>
								2.1	
							</td>
						</tr>
						
						<tr>
							<td>
								<input type="checkbox" class="check" name="check" onclick="checkboxEvent();">
							</td>
							<td>
								<a href="#" name="edit" id="" >Apache Tomcat</a>
							</td>
							<td >Web Server</td>
							<td>Solaris</td>
							<td>
								2.0
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</form>