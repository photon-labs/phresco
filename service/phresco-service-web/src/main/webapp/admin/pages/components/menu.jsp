<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">
	$(document).ready(function() {
		clickMenu($("a[name='compTab']"), $("#subcontainer"));
		loadContent("featuresList", $("#subcontainer"));
		activateMenu($("#features"));
	});
</script>
	
<nav>
	<ul class="tabs">
		<li>
			<a href="#" class="active" name="compTab" id="featuresList"><s:label for="description" key="lbl.header.comp.featrs" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="archetypesList"><s:label for="description" key="lbl.header.comp.arhtyp" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="applntypesList"><s:label for="description" key="lbl.header.comp.aplntyp" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="configtempList"><s:label for="description" key="lbl.header.comp.cnfigtmplt" theme="simple"/></a>
		</li>
		<li>
			<a href="#" class="inactive" name="compTab" id="pilotprojList"><s:label for="description" key="lbl.header.comp.pltprjt" theme="simple"/></a>
		</li>
			
	</ul>
	<div class="control-group customer_name">
		<s:label for="input01" key="lbl.header.comp.customer" cssClass="control-label custom_label" theme="simple"/>
		<div class="controls customer_select_div">
			<select id="select01" class="customer_listbox">
				<option>- select -</option>
				<option>Walgreens</option>
				<option>NBO</option>
				<option>Cengage</option>
				<option>Horizon Blue</option>
				<option>Photon</option>
			</select>
		</div>
	</div>			
</nav>			

<section id="subcontainer" class="navTopBorder">

</section>

<style>	
	.customer_name {
		float: right;
		left: 71%;
		margin-bottom: 0;
		position: absolute;
		top: 10px;
		width: auto;
	}
		
	.customer_listbox {
		float: right;
		left: 97%;
		margin-bottom: 0;
		position: absolute;
		top: 0px;
		width: auto;
	}
</style>
