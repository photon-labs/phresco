<%@page import="com.photon.phresco.framework.PhrescoFrameworkFactory"%>
<%@page import="com.photon.phresco.framework.FrameworkConfiguration"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<% 
FrameworkConfiguration frameworkConfig = PhrescoFrameworkFactory.getFrameworkConfig();
String path = frameworkConfig.getServerPath() + "/tweets"; 
%>

<aside id="sidebar">
	<section>
		<div class="right">
			<div class="right_navbar active">
				<div class="barclose">
					<div class="lnclose"><s:text name="label.latest"/>&nbsp;<s:text name="label.news"/></div>
				</div>
			</div>
			<div class="right_barcont">
				<div class="searchsidebar">
					<div class="newstext">
						<s:text name="label.latest"/> <span><s:text name="label.news"/></span>
					</div>
					<div class="topsearchinput">
						<input name="" type="text" id="search" title="search" disabled="disabled" onkeyup="javascript:filter();">
					</div>
					<div class="linetopsearch"></div>
				</div>
				<div id="tweets" class="sc7 scrollable dymanic paddedtop">
					<div class="tweeterContent"></div>
				</div>
			</div>
			<br clear="left">
		</div>
	</section>
</aside>

<script type="text/javascript">
	
	$(document).ready(function() {
		$.ajax({
		    url: '<%=path%>', 
		    type: 'GET',
		    dataType: 'jsonp',
		    jsonp: 'callback',
		    success: function(data){
		        for(i in data) {
					$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
				}
		    }
		});
	});
	
	function parseTwitterDate($stamp)
	{		
		var date = $stamp;	
		return date.substr(0,16);
	}
	
	function filter(){
		var value = document.getElementById('search').value;
		$.ajax({
		    url: '<%=path%>', 
		    type: 'GET',
		    dataType: 'jsonp',
		    jsonp: 'callback',
		    success: function(data){
		    	$('.tweeterContent').empty();
				var matchPos1 = -1;
				for(i in data) {
					var string1 = data[i].text.toUpperCase();
					var myRegExp = value.toUpperCase();
					if(myRegExp.length !=0){
						matchPos1 = string1.search(myRegExp);
						if(matchPos1!= -1){
							$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');	
						}
					}else{
						$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
					}
				}
		    }
		});
	}
</script>