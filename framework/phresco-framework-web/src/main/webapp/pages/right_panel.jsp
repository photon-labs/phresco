<%@ taglib uri="/struts-tags" prefix="s"%>

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
						<input name="" type="text" id="search" title="search" disabled="disabled" onkeypress="javascript:filter();">
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
		$.getJSON("http://twitter.com/statuses/user_timeline/photoninfotech.json?callback=?", function(data) {
		for(i in data) {
			$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
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
		$('.tweeterContent').empty();
		$.getJSON("http://twitter.com/statuses/user_timeline/photoninfotech.json?callback=?", function(data) {
			for(i in data) {
				if(data[i].text.indexOf(value)!= -1){
					$('.tweeterContent').append('<div class="blog_twit"><div class="blog_twit_img"><img src="images/right1.png" border="0" alt="image"></div><div class="blog_twit_txt">'+data[i].text+'</div><div class="blog_twit_boder">'+parseTwitterDate(data[i].created_at)+'</div></div>');
				}
			}				
		});
	}
</script>