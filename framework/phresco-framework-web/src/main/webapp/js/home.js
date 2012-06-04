// JavaScript Document
var leftnav_top = $(".lefttopnav");
var Leftnav_bot = $(".leftbotnav");
var Right_navtop =$(".righttopnav");
var Right_navbot =$(".rightbotnav");

function hideallNavHome(){
	$(".lefttopnav").fadeOut();
	$(".righttopnav").fadeOut();
	$(".rightbotnav").fadeOut();
	$(".leftbotnav").fadeOut();
}
///////////////////Nav Items/////////////////




/********rightNav*********/
function rightNav(thisclass, contentclass){
	thisclass.toggleClass("active");
	var contentbar= $(".right");
	var contentbarDiaplay = contentbar.css("width");
	if (contentbarDiaplay=="230px") {
		$("#search").prop("disabled", "disabled");
		contentbar.animate({
							width:'20px'
							},1000);
		$(".right_barcont").animate({
						width:'20px',
						marginRight:'-200px'
						},1000);
		
		//$("#scroller7").fadeIn(1000);
		$(".lnclose").fadeIn();
		if ((screen.width<=1024) && (screen.height<=768)) {
			$("#search").prop("disabled", "disabled");
			contentclass.animate({
				width:'97%'
			},1000);
		} else {
			contentclass.animate({
				width:'97%'
			},1000);
		}

		/*contentclass.animate({
							width:'81%'
							},1000);*/
	} else {
		$("#search").removeAttr("disabled");
		$(".lnclose").fadeOut();
		contentbar.animate({
						width:'230px'
						},1000);
		$(".right_barcont").animate({
						width:'200px',
						marginRight:'0px'
						},1000);
		
		if ((screen.width<=768)) {
		 contentclass.animate({
							width:'76%'
							},1000);
		 $(".bottom_nav").animate({
							left:'15%'
							},0);
		}
		else if ((screen.width<=800) && (screen.height<=600)) {
			
		 contentclass.animate({
							width:'70%'
							},1000);
		 $(".bottom_nav").animate({
							left:'15%'
							},0);
		}
		else if ((screen.width<=1024) && (screen.height<=768)) {
			
		 contentclass.animate({
							width:'77%'
							},1000);
		 $(".bottom_nav").animate({
							left:'15%'
							},0);
		}
		else {
			contentclass.animate({
							width:'80%'
							},1000);
		}
		
	}
}
/****************

/********Ourexp Move******/
function ourExp(flowevent1, flowevent2 ){
	inn_event =flowevent1;
	flowevent1.animate({
							left:'-160%',
							top:'80%',
							width:'50%',
							height:'50%'
							},1000);
	flowevent2.animate({
							left:'0%',
							top:'7%',
							width:'100%',
							height:'90%'
							},1500);
	$(".map_area img").animate({
														 width:"850px",
														 height:"476px",
														 top:"2%",
														 left:"14%"
														 });
								 $(".chennai, .bang, .Neayork, .sanjose, .somervile").css({
													
													display:"none"
													});
	$(".map_outer").css({
													
													marginTop:"-7%"
													});
	$(".chennaimap, .Bangmap, .newyorkmap, .sanjosemap, .somervelliemap").hide();
	
}
/**************move up*******/
function moveUp(flowevent1, flowevent2 ){
	
	flowevent1.animate({	left:'160%',
							top:'-80%',
							width:'50%',
							height:'50%'
							},1000
							);
	flowevent2.animate({
							
							left:'0%',
							top:'7%',
							width:'100%',
							height:'90%'
							},1500);
	$(".map_area img").animate({
														 width:"850px",
														 height:"476px",
														 top:"2%",
														 left:"14%"
														 });
								 $(".chennai, .bang, .Neayork, .sanjose, .somervile").css({
													
													display:"none"
													});
	
	$(".map_outer").css({
													
													marginTop:"-7%"
													});
	$(".chennaimap, .Bangmap, .newyorkmap, .sanjosemap, .somervelliemap").hide();
}

function fade(inn_event){
	inn_event.fadeOut();
	
}
/*************/

/**************move rightbot to lefttop*******/
function moveUp_left_top(flowevent1, flowevent2 ){
	
	flowevent1.animate({	left:'-160%',
							top:'-80%',
							width:'50%',
							height:'50%'
							},1000
							);
	flowevent2.animate({
							
							left:'0%',
							top:'7%',
							width:'100%',
							height:'90%'
							},1500);
	
	$(".map_area img").animate({
														 width:"850px",
														 height:"476px",
														 top:"2%",
														 left:"14%"
														 });
								 $(".chennai, .bang, .Neayork, .sanjose, .somervile").css({
													
													display:"none"
													});
	$(".map_outer").css({
													
													marginTop:"-7%"
													});
	$(".chennaimap, .Bangmap, .newyorkmap, .sanjosemap, .somervelliemap").hide();
}

/*************/

/**************move lefttop to rightbot*******/
function moveUp_right_bot(flowevent1, flowevent2 ){
	
	flowevent1.animate({	left:'160%',
							top:'80%',
							width:'50%',
							height:'50%'
							},1000
							);
	flowevent2.animate({
							
							left:'0%',
							top:'7%',
							width:'100%',
							height:'90%'
							},1500);
	$(".map_area img").animate({
														 width:"850px",
														 height:"476px",
														 top:"2%",
														 left:"14%"
														 });
								 $(".chennai, .bang, .Neayork, .sanjose, .somervile").css({
													
													display:"none"
													});
								 
	$(".map_outer").css({
													
													marginTop:"-7%"
													});
	$(".chennaimap, .Bangmap, .newyorkmap, .sanjosemap, .somervelliemap").hide();
	
}

/*************/



/*******Bottom Nav*****/
var oriantationval;
function updateOrientation(){
	
	var imtemlisterNext = $(this).next(".bottom_nav_lister").css("width");
	var imtemlisterNext_h = $(this).next(".bottom_nav_lister").css("height");
	var oriantationval = window.orientation;
	//var itemlisters= $(".bottom_nav_lister");
	if(oriantationval=="90" || oriantationval=="-90"){
		
		$(".bottom_nav_item").css("width", "90px");
		$(".bottom_nav_lister").css("height", "0px");
		$(".bottom_nav_lister").css("width", "90px");
		$(".bottom_nav_item").css("height", "25px");
		$(".bottom_nav_lister.active").css("height", "120px");
		/*itemlisters.animate({
				height:'0px'
			});*/
				$(this).css({'width' : '90px'});
				$(this).next(".bottom_nav_lister").animate({
				height:'120px'
		});
	}
	else{
		var imtemlisterNext = $(this).next(".bottom_nav_lister").css("width");
		var imtemlisterNext_h = $(this).next(".bottom_nav_lister").css("height");
		$(".bottom_nav_item").css("width", "25px");
		$(".bottom_nav_lister").css("height", "100px");
		$(".bottom_nav_lister").css("width", "0px");
		$(".bottom_nav_lister.active").css("width", "360px");
		$(".bottom_nav_item").css("height", "60px");
		if(imtemlisterNext=="0px"){
			/*itemlisters.animate({
								width:'0px'
								});*/
					$(this).css({'height' : '100px'});
					$(this).next(".bottom_nav_lister").animate({
								width:'360px'
			});
			
		}
	}
}

/***************/
function horizantal_acc(){
						$(".bottom_nav_item").click(function(event){
									$(".bottom_nav_item").removeClass("active");
									$(this).addClass("active");
									var itemlisters= $(".bottom_nav_lister");
									var imtemlisterNext = $(this).next(".bottom_nav_lister").css("width");
									var imtemlisterNext_h = $(this).next(".bottom_nav_lister").css("height");
									var _imtemlisterNextChild = $(this).next(".bottom_nav_lister").children(".bot_thumb");
									
									var oriantationval = window.orientation;
									if(oriantationval=="90" || oriantationval=="-90"){
										
										if(imtemlisterNext_h=="0px"){
												itemlisters.animate({
															height:'0px'
															});
												$(this).css({'width' : '90px'});
												$(this).next(".bottom_nav_lister").animate({
																height:'120px'
												});
											
											}
									
										}
									else{
										
								
									if(imtemlisterNext=="0px"){
									itemlisters.animate({
														width:'0px'
														});
									$(this).css({'height' : '60px'});
									$(this).next(".bottom_nav_lister").animate({
														width:'360px'
								});
	
									}
									
									}
	

	
							});	
						
						};

						
/************Animated nav**************/
function NavtopRightfn(){
	$(".Navtoprightevent").click(function(){
										  
		var _this = $(this);
		
		splitcontent(_this);////////////split the content of navigation
		
	});
}

function Navbotleftfn(){
	$(".Navbotleftevent").click(function(){
		var _this = $(this);
		
		splitcontent(_this);////////////split the content of navigation
	});
}

function NavbotRightfn(){
	$(".Navbotrighevent").click(function(){
		var _this = $(this);
		splitcontent(_this);////////////split the content of navigation
	});
}

function Navtopleftfn(){
	$(".Navtopleftevent").click(function(){
		var _this = $(this);
	
		splitcontent(_this);////////////split the content of navigation
	});
}


////////////////////fade items//////////
function fadeOurexpertiseItems(){
	$(".lefttopnav").fadeOut();
	$(".righttopnav").fadeOut();
	$(".rightbotnav").fadeOut();
}

function fadeInOurexpertiseItems(){
	$(".lefttopnav").fadeIn();
	$(".righttopnav").fadeIn();
	$(".rightbotnav").fadeIn();
}
function fadeDashboardItems(){
	$(".righttopnav").fadeOut();
	$(".rightbotnav").fadeOut();
}
function fadeDashboardItems(){
	$(".righttopnav").fadeOut();
	$(".rightbotnav").fadeOut();
}
function fadehistoryItems(){
	$(".lefttopnav").fadeOut();
	$(".righttopnav").fadeOut();
}
function fadeInhistoryItems(){
	$(".lefttopnav").fadeIn();
	$(".righttopnav").fadeIn();
}
function fademangItems(){
	$(".lefttopnav").fadeOut();
	$(".righttopnav").fadeOut();
	$(".leftbotnav").fadeOut();
}
function fadeInmangItems(){
	$(".lefttopnav").fadeIn();
	$(".righttopnav").fadeIn();
	$(".leftbotnav").fadeIn();
}
function fadelocationItems(){
	$(".lefttopnav").fadeOut();
	$(".leftbotnav").fadeOut();
}
function fadeInlocationItems(){
	$(".lefttopnav").fadeIn();
	$(".leftbotnav").fadeIn();
}

function fadeapplyjobItems(){
	$(".lefttopnav").fadeOut();
	$(".leftbotnav").fadeOut();
	$(".rightbotnav").fadeOut();
}
function fadeInapplyjobItems(){
	$(".lefttopnav").fadeIn();
	$(".leftbotnav").fadeIn();
	$(".rightbotnav").fadeIn();
}
function fadecontactItems(){
	$(".leftbotnav").fadeOut();
	$(".rightbotnav").fadeOut();
}
function fadeIncontactItems(){
	$(".leftbotnav").fadeIn();
	$(".rightbotnav").fadeIn();
}
function fadebecomeItems(){
	$(".righttopnav").fadeOut();
	$(".rightbotnav").fadeOut();
}
function fadeInbecomeItems(){
	$(".righttopnav").fadeIn();
	$(".rightbotnav").fadeIn();
}
/**************Pick nav text and split**********/
function splitcontent(_this){
	var currentclassTp_Rg = _this.hasClass("Navtoprightevent");////////get class
	var currentclassTp_Lf = _this.hasClass("Navtopleftevent");////////get class
	var currentclassBt_Rg = _this.hasClass("Navbotrighevent");////////get class
	var currentclassBt_Lf = _this.hasClass("Navbotleftevent");////////get class
	var navselectiontp_rg ="top_right";
	var navselectiontp_lf ="top_left";
	var navselectionbt_lf ="bot_left";
	var navselectionbt_rg ="bot_right";
	
	if(currentclassTp_Rg){//////////check if top right nav
		
	var nav_str = _this.html();
		nav_str = nav_str.split('<span class="red" id="rg_tp1">').join("");
		nav_str = nav_str.split('<span class="wh" id="rg_tp2">').join("");
		nav_str = nav_str.split("</span>").join("");
		nav_str_event = navselectiontp_rg;
		
		changeNavcontent(nav_str, nav_str_event);//////////////changes the content of the navigation
		
	} else if(currentclassBt_Lf){//////////check if bottom left nav
		
		var nav_str = _this.html();
		nav_str = nav_str.split('<span class="red" id="lf_bt1">').join("");
		nav_str = nav_str.split('<span class="wh" id="lf_bt2">').join("");
		nav_str = nav_str.split("</span>").join("");
		nav_str_event = navselectionbt_lf;
		changeNavcontent(nav_str, nav_str_event);//////////////changes the content of the navigation
	}
	 else if(currentclassBt_Rg){//////////check if bottom left nav
		
		var nav_str = _this.html();
		nav_str = nav_str.split('<span class="red" id="rg_bt11">').join("");
		nav_str = nav_str.split('<span class="wh" id="rg_bt12">').join("");
		nav_str = nav_str.split("</span>").join("");
		nav_str_event = navselectionbt_rg;
		changeNavcontent(nav_str, nav_str_event);//////////////changes the content of the navigation
	}
	else if(currentclassTp_Lf){//////////check if bottom left nav
		
		var nav_str = _this.html();
		nav_str = nav_str.split('<span class="red" id="lf_tp1">').join("");
		nav_str = nav_str.split('<span class="wh" id="lf_tp2">').join("");
		nav_str = nav_str.split("</span>").join("");
		nav_str_event = navselectiontp_lf;
		changeNavcontent(nav_str, nav_str_event);//////////////changes the content of the navigation
	}
	
	
}
/**********/

/************change navItem********/
function changeNavcontent(eventclick, navsel){
	var currentClick = navsel;
	var currentevent = eventclick;
	var navLeftop1 = $("#lf_tp1");
	var navLeftop2 = $("#lf_tp2");
	var navleftbot1 = $("#lf_bt1");
	var navleftbot2 = $("#lf_bt2");
	var navrgtop1 = $("#rg_tp1");
	var navrgtop2 = $("#rg_tp2");
	var navrgbot1 = $("#rg_bt11");
	var navrgbot2 = $("#rg_bt12");
	
	var navLeftop1Con = $("#lf_tp1").html();
	var navLeftop2Con = $("#lf_tp2").html();
	var navleftbot1Con = $("#lf_bt1").html();
	var navleftbot2Con = $("#lf_bt2").html();
	var navrgtop1Con = $("#rg_tp1").html();
	var navrgtop2Con = $("#rg_tp2").html();
	var navrgbot1Con = $("#rg_bt11").html();
	var navrgbot2Con = $("#rg_bt12").html();
	
	//////////////////navItems
	var _home 			="Home";
	var _spacenull		="";
	var _tec  			="Technology";
	var _what  			="What we ";
	var _provide		="Provide";
	var _become 		="Become a ";
	var _customer 		="Customer";
	var _our            ="Our ";
	var _exp  			="Expertise";
	var _about			="About ";
	var _us 			="Us";
	var _join 			="Join ";
	var _dash			="Dashboard";
	var _history 		="History in ";
	var _management 	="Management ";
	var _team 			="Team";
	var _Location 		="Location";
	var _apply 			="Apply for ";
	var _ajob 			="a Job"
	var _contact 		="Contact ";
	var _insight 		="Insight ";
	var _discover		="Discover";
	var _add			="Add";
	var _new			="New";
	var _dashboard		="Dashboard";

	switch(currentevent){
		
		case('Dashboard'):
			switch(currentClick){
					case('bot_right'):
						
						navLeftop1.html(_spacenull);
						navLeftop2.html(_home);
						navleftbot1.html(_contact);
						navleftbot2.html(_us);
						navrgtop1.html(_become);
						navrgtop2.html(_customer);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_insight);
						var flowevent1 = $(".inner_content_appln");
						var flowevent2 = $(".inner_content_dash");	
						flowevent2.css({'left' : '120%', 'top' : '80%'});
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						fadeInlocationItems();
						$(".headerInnerTop li a").removeClass("active");
						break;
						
						
							case('bot_left'):
							
							navLeftop1.html(_spacenull);
							navLeftop2.html(_home);
							navleftbot1.html(_contact);
							navleftbot2.html(_us);
							navrgtop1.html(_become);
							navrgtop2.html(_customer);
							navrgbot1.html(_spacenull);
							navrgbot2.html(_insight);
							var flowevent1 = $(".inner_content_appln");
							
							var flowevent2 = $(".inner_content_dash");	
							flowevent2.css({'left' : '-120%', 'top' : '80%'});
							fadeInbecomeItems();
							moveUp(flowevent1, flowevent2);/////////moves the content area
							$(".headerInnerTop li a").removeClass("active");
							break;
						
						case('top_left'):
						
						navLeftop1.html(_spacenull);
						navLeftop2.html(_home);
						navleftbot1.html(_contact);
						navleftbot2.html(_us);
						navrgtop1.html(_become);
						navrgtop2.html(_customer);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_insight);
						var flowevent1 = $(".inner_content_contact");
						var flowevent2 = $(".inner_content_join");	
						flowevent2.css({'left' : '-100%', 'top' : '-80%'});
						moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
						fadeIncontactItems();
						$(".headerInnerTop li a").removeClass("active");
						break;
				
						case('top_right'):
						navLeftop1.html(_spacenull);
						navLeftop2.html(_home);
						navleftbot1.html(_contact);
						navleftbot2.html(_us);
						navrgtop1.html(_become);
						navrgtop2.html(_customer);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_insight);
						var flowevent1 = $(".inner_content_contact");
						var flowevent2 = $(".inner_content_dash");	
						flowevent2.css({'left' : '100%', 'top' : '-80%'});
						fadeInapplyjobItems();
						ourExp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						break;
						
				}
		break;
		
		
		case('Contact Us'):
			switch(currentClick){
					case('bot_right'):
							navLeftop1.html(_join);
							navLeftop2.html(_us);
							navleftbot1.html(_spacenull);
							navleftbot2.html(_spacenull);
							navrgtop1.html(_spacenull);
							navrgtop2.html(_dash);
							navrgbot1.html(_spacenull);
							navrgbot2.html(_spacenull );
							var flowevent1 = $(".inner_content_join");
							var flowevent2 = $(".inner_content_contact");	
							flowevent2.css({'left' : '120%', 'top' : '80%'});
							moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
							fadecontactItems();
							$(".headerInnerTop li a").removeClass("active");
							break;
							
					case('bot_left'):
							
							navLeftop1.html(_join);
							navLeftop2.html(_us);
							navleftbot1.html(_spacenull);
							navleftbot2.html(_spacenull);
							navrgtop1.html(_spacenull);
							navrgtop2.html(_dash);
							navrgbot1.html(_spacenull);
							navrgbot2.html(_spacenull );
							var flowevent1 = $(".inner_content_dash");
							var flowevent2 = $(".inner_content_contact");	
							flowevent2.css({'left' : '-120%', 'top' : '80%'});
							fadecontactItems();
							moveUp(flowevent1, flowevent2);/////////moves the content area
							$(".headerInnerTop li a").removeClass("active");
							break;
						
				}
			break;
		
		
		case('Apply for a Job'):
			switch(currentClick){
					case('bot_right'):
						
						navLeftop1.html(_spacenull);
						navLeftop2.html(_Location);
						navleftbot1.html(_apply);
						navleftbot2.html(_ajob);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_home);
						navrgbot1.html(_contact);
						navrgbot2.html(_us);
						var flowevent1 = $(".inner_content_loaction");
						var flowevent2 = $(".inner_content_join");	
						flowevent2.css({'left' : '120%', 'top' : '80%'});
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						fadeInlocationItems();
						$(".headerInnerTop li a").removeClass("active");
						break;
						
						
					case('bot_left'):
							
							navLeftop1.html(_spacenull);
							navLeftop2.html(_spacenull);
							navleftbot1.html(_spacenull);
							navleftbot2.html(_spacenull);
							navrgtop1.html(_join);
							navrgtop2.html(_us);
							navrgbot1.html(_spacenull);
							navrgbot2.html(_spacenull);
							var flowevent1 = $(".inner_content_join");
							var flowevent2 = $(".inner_content_applyforjob");	
							flowevent2.css({'left' : '-120%', 'top' : '80%'});
							
							moveUp(flowevent1, flowevent2);/////////moves the content area
						    fadeapplyjobItems();
							$(".headerInnerTop li a").removeClass("active");
							break;
						
				}
		break;
		
		
		
		case('Join Us'):
			switch(currentClick){
					case('bot_right'):
						
						navLeftop1.html(_spacenull);
						navLeftop2.html(_Location);
						navleftbot1.html(_apply);
						navleftbot2.html(_ajob);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_home);
						navrgbot1.html(_contact);
						navrgbot2.html(_us);
						var flowevent1 = $(".inner_content_loaction");
						var flowevent2 = $(".inner_content_join");	
						flowevent2.css({'left' : '120%', 'top' : '80%'});
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						fadeInlocationItems();
						$(".headerInnerTop li a").removeClass("active");
						$("#carrers").addClass("active");
						break;
						
						
							case('bot_left'):
							
							navLeftop1.html(_spacenull);
							navLeftop2.html(_Location);
							navleftbot1.html(_apply);
							navleftbot2.html(_ajob);
							navrgtop1.html(_spacenull);
							navrgtop2.html(_home);
							navrgbot1.html(_contact);
							navrgbot2.html(_us);
							var flowevent1 = $(".inner_content1");
							var flowevent2 = $(".inner_content_join");	
							flowevent2.css({'left' : '-120%', 'top' : '80%'});
							
							moveUp(flowevent1, flowevent2);/////////moves the content area
							$(".headerInnerTop li a").removeClass("active");
							$("#carrers").addClass("active");
							break;
						
						case('top_left'):
						
						navLeftop1.html(_spacenull);
						navLeftop2.html(_Location);
						navleftbot1.html(_apply);
						navleftbot2.html(_ajob);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_home);
						navrgbot1.html(_contact);
						navrgbot2.html(_us);
						var flowevent1 = $(".inner_content_contact");
						var flowevent2 = $(".inner_content_join");	
						flowevent2.css({'left' : '-100%', 'top' : '-80%'});
						moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
						fadeIncontactItems();
						$(".headerInnerTop li a").removeClass("active");
						$("#carrers").addClass("active");
						break;
				
						case('top_right'):
						navLeftop1.html(_spacenull);
						navLeftop2.html(_Location);
						navleftbot1.html(_apply);
						navleftbot2.html(_ajob);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_home);
						navrgbot1.html(_contact);
						navrgbot2.html(_us);
						var flowevent1 = $(".inner_content_applyforjob");
						var flowevent2 = $(".inner_content_join");	
						flowevent2.css({'left' : '100%', 'top' : '-80%'});
						fadeInapplyjobItems();
						ourExp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#carrers").addClass("active");
						break;
						
				}
		break;
		
		
		case('Location'):
					switch(currentClick){
							case('bot_left'):
							fadelocationItems();
							navLeftop1.html(_spacenull);
							navLeftop2.html(_spacenull);
							navleftbot1.html(_spacenull);
							navleftbot2.html(_spacenull);
							navrgtop1.html(_about);
							navrgtop2.html(_us);
							navrgbot1.html(_join);
							navrgbot2.html(_us );
							var flowevent1 = $(".inner_content_about");
							var flowevent2 = $(".inner_content_loaction");	
							flowevent2.css({'left' : '-120%', 'top' : '80%'});
							
							moveUp(flowevent1, flowevent2);/////////moves the content area
							$(".headerInnerTop li a").removeClass("active");
							
								 
							break;
							
							case('top_left'):
								fadelocationItems();
								navLeftop1.html(_spacenull);
								navLeftop2.html(_spacenull);
								navleftbot1.html(_spacenull);
								navleftbot2.html(_spacenull);
								navrgtop1.html(_about);
								navrgtop2.html(_us);
								navrgbot1.html(_join);
								navrgbot2.html(_us );
								var flowevent1 = $(".inner_content_join");
								var flowevent2 = $(".inner_content_loaction");	
								flowevent2.css({'left' : '-100%', 'top' : '-80%'});
								moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
								fadelocationItems();
								$(".headerInnerTop li a").removeClass("active");
								break;
								
								 
								
						}
		break;
		
		
		case('Management Team'):
					switch(currentClick){
								case('top_left'):
							
								navLeftop1.html(_spacenull);
								navLeftop2.html(_spacenull);
								navleftbot1.html(_spacenull);
								navleftbot2.html(_spacenull);
								navrgtop1.html(_spacenull);
								navrgtop2.html(_spacenull);
								navrgbot1.html(_about);
								navrgbot2.html(_us);
								var flowevent1 = $(".inner_content_about");
								var flowevent2 = $(".inner_content_mng");	
								flowevent2.css({'left' : '-100%', 'top' : '-80%'});
								moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
								fademangItems();
								$(".headerInnerTop li a").removeClass("active");
								break;
						}
		break;
				
		
		case('History in Technology'):
				switch(currentClick){
				
						
						case('top_left'):
						fadeInOurexpertiseItems();
						navLeftop1.html(_spacenull);
						navLeftop2.html(_spacenull);
						navleftbot1.html(_about);
						navleftbot2.html(_us);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_spacenull);
						navrgbot1.html(_our);
						navrgbot2.html(_exp);
						var flowevent1 = $(".inner_content_exp");
						var flowevent2 = $(".inner_content_history");	
						flowevent2.css({'left' : '-100%', 'top' : '-80%'});
						moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
						fadehistoryItems();
						$(".headerInnerTop li a").removeClass("active");
						break;
						
						case('top_right'):
						navLeftop1.html(_spacenull);
						navLeftop2.html(_spacenull);
						navleftbot1.html(_about);
						navleftbot2.html(_us);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_spacenull);
						navrgbot1.html(_our);
						navrgbot2.html(_exp);
						var flowevent1 = $(".inner_content_about");
						var flowevent2 = $(".inner_content_history");	
						flowevent2.css({'left' : '100%', 'top' : '-80%'});
						fadehistoryItems();
						ourExp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						break;
				}
			
			break;
		
		case('About Us'):
		

				

				switch(currentClick){
					case('top_right'):
						fadeInlocationItems();
						navLeftop1.html(_management);
						navLeftop2.html(_team);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_Location);
						navrgtop1.html(_history);
						navrgtop2.html(_tec);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_home );
						var flowevent1 = $(".inner_content_loaction");
						var flowevent2 = $(".inner_content_about");	
						flowevent2.css({'left' : '120%', 'top' : '-80%'});
						ourExp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whoweare").addClass("active");
						break;
						
						
						case('bot_left'):
						fadeInOurexpertiseItems();
						navLeftop1.html(_management);
						navLeftop2.html(_team);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_Location);
						navrgtop1.html(_history);
						navrgtop2.html(_tec);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_home );
						var flowevent1 = $(".inner_content_history");
						var flowevent2 = $(".inner_content_about");	
						flowevent2.css({'left' : '-120%', 'top' : '80%'});
						
						moveUp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whoweare").addClass("active");
						break;
						
						case('top_left'):
						fadeInOurexpertiseItems();
						navLeftop1.html(_management);
						navLeftop2.html(_team);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_Location);
						navrgtop1.html(_history);
						navrgtop2.html(_tec);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_home);
						var flowevent1 = $(".inner_content1");
						var flowevent2 = $(".inner_content_about");	
						flowevent2.css({'left' : '-100%', 'top' : '-80%'});
						moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whoweare").addClass("active");
						break;
						
						case('bot_right'):
						fadeInmangItems();
						navLeftop1.html(_management);
						navLeftop2.html(_team);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_Location);
						navrgtop1.html(_history);
						navrgtop2.html(_tec);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_home);
						var flowevent1 = $(".inner_content_mng");
						var flowevent2 = $(".inner_content_about");	
						flowevent2.css({'left' : '120%', 'top' : '80%'});
						
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whoweare").addClass("active");
						break;
				}
			
			break;
			
		case('Our Expertise'):
		

				

				switch(currentClick){
					case('top_right'):
						navLeftop1.html(_history);
						navLeftop2.html(_tec);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_home);
						navrgtop1.html(_what);
						navrgtop2.html(_provide);
						navrgbot1.html(_become);
						navrgbot2.html(_customer);
						var flowevent1 = $(".inner_content1");
						var flowevent2 = $(".inner_content_exp");	
						flowevent2.css({'left' : '160%', 'top' : '-80%'});
						ourExp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whatwedo").addClass("active");
						break;
						
						
						case('bot_left'):
						fadeInOurexpertiseItems();
						navLeftop1.html(_history);
						navLeftop2.html(_tec);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_home);
						navrgtop1.html(_what);
						navrgtop2.html(_provide);
						navrgbot1.html(_become);
						navrgbot2.html(_customer);
						var flowevent1 = $(".inner_content_consl");
						var flowevent2 = $(".inner_content_exp");	
						flowevent2.css({'left' : '-100%', 'top' : '80%'});
						
						moveUp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whatwedo").addClass("active");
						break;
						
						case('top_left'):
						fadeInOurexpertiseItems();
						fadeInOurexpertiseItems();
						navLeftop1.html(_history);
						navLeftop2.html(_tec);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_home);
						navrgtop1.html(_what);
						navrgtop2.html(_provide);
						navrgbot1.html(_become);
						navrgbot2.html(_customer);
						var flowevent1 = $(".inner_content_custom");
						var flowevent2 = $(".inner_content_exp");	
						flowevent2.css({'left' : '-100%', 'top' : '-80%'});
						moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whatwedo").addClass("active");
						break;
						
						case('bot_right'):
						fadeInhistoryItems();
						navLeftop1.html(_history);
						navLeftop2.html(_tec);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_home);
						navrgtop1.html(_what);
						navrgtop2.html(_provide);
						navrgbot1.html(_become);
						navrgbot2.html(_customer);
						var flowevent1 = $(".inner_content_history");
						var flowevent2 = $(".inner_content_exp");	
						flowevent2.css({'left' : '100%', 'top' : '80%'});
						
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#whatwedo").addClass("active");
						break;
				}
			
			break;
			
			
		case('What we Provide'):
			navLeftop1.html(_spacenull);
			navLeftop2.html(_spacenull);
			navleftbot1.html(_our);
			navleftbot2.html(_exp);
			navrgtop1.html(_spacenull);
			navrgtop2.html(_spacenull);
			navrgbot1.html(_spacenull);
			navrgbot2.html(_spacenull);
			var flowevent1 = $(".inner_content_exp");
			var flowevent2 = $(".inner_content_consl");
			
			ourExp(flowevent1, flowevent2);/////////moves the content area
			
			
			fadeOurexpertiseItems();////fade nav Items
			$(".headerInnerTop li a").removeClass("active");
			break;
			
			
				
			case('Home'):
			switch(currentClick){
					case('top_left'):
						
						navLeftop1.html(_about);
						navLeftop2.html(_us);
						navleftbot1.html(_join);
						navleftbot2.html(_us);
						navrgtop1.html(_our);
						navrgtop2.html(_exp);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_dash);
						var flowevent1 = $(".inner_content_dash");
						var flowevent2 = $(".inner_content1");	
						flowevent2.css({'left' : '-100%', 'top' : '-80%'});
						moveUp_right_bot(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#home").addClass("active");
						
						break;
						
					case('top_right'):
						navLeftop1.html(_about);
						navLeftop2.html(_us);
						navleftbot1.html(_join);
						navleftbot2.html(_us);
						navrgtop1.html(_our);
						navrgtop2.html(_exp);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_dash);
						var flowevent1 = $(".inner_content_join");
						var flowevent2 = $(".inner_content1");	
						flowevent2.css({'left' : '100%', 'top' : '-80%'});
						ourExp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#home").addClass("active");
						break;
						
						
						case('bot_left'):
						fadeInOurexpertiseItems();
						navLeftop1.html(_about);
						navLeftop2.html(_us);
						navleftbot1.html(_join);
						navleftbot2.html(_us);
						navrgtop1.html(_our);
						navrgtop2.html(_exp);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_dash);
						var flowevent1 = $(".inner_content_exp");
						var flowevent2 = $(".inner_content1");	
						flowevent2.css({'left' : '-120%', 'top' : '80%'});
						moveUp(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#home").addClass("active");
						break;
						
						case('bot_right'):
						navLeftop1.html(_about);
						navLeftop2.html(_us);
						navleftbot1.html(_join);
						navleftbot2.html(_us);
						navrgtop1.html(_our);
						navrgtop2.html(_exp);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_dash);
						var flowevent1 = $(".inner_content_about");
						var flowevent2 = $(".inner_content1");	
						flowevent2.css({'left' : '100%', 'top' : '80%'});
						
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						$("#home").addClass("active");
						break;
				}
				break;
				
			case('Become a Customer'):
			switch(currentClick){
					case('bot_right'):
						
						navLeftop1.html(_our);
						navLeftop2.html(_exp);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_dash);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_spacenull);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_spacenull);
						var flowevent1 = $(".inner_content_exp");
						var flowevent2 = $(".inner_content_custom");	
						fadeDashboardItems();
						flowevent2.css({'left' : '120%', 'top' : '80%'});
						moveUp_left_top(flowevent1, flowevent2);/////////moves the content area
						$(".headerInnerTop li a").removeClass("active");
						
						break;
						
					case('top_right'):
						navLeftop1.html(_our);
						navLeftop2.html(_exp);
						navleftbot1.html(_spacenull);
						navleftbot2.html(_dash);
						navrgtop1.html(_spacenull);
						navrgtop2.html(_spacenull);
						navrgbot1.html(_spacenull);
						navrgbot2.html(_spacenull);
						var flowevent1 = $(".inner_content_dash");
						var flowevent2 = $(".inner_content_custom");	
						flowevent2.css({'left' : '100%', 'top' : '-80%'});
						ourExp(flowevent1, flowevent2);/////////moves the content area
						fadebecomeItems();
						$(".headerInnerTop li a").removeClass("active");
						break;
						
				}
			break;
			case('AddNew'):
				$(".overlay_container").show();
				$("#appln_add_new").show("slow");
			break;			
	}
	
	
	
}
/***********/
/////////////////////Nav Link

function navLinkslideUp(){
	$("#close_links").click(function(){
		$(".nav_slider").fadeOut();
		setTimeout("quickLinkslidedown()");
	});
}

function navLinkslidedown(){
	
		$(".nav_slider").slideDown();
	
}

function quickLinkslidedown(){
	
		$(".quick_links_inner").slideDown();
	
}

function quickLinkslideslideUp(){
	$("#quick_lnk").click(function(event){
								   
		if (document.all)
			window.event.cancelBubble = true;
		else
			event.stopPropagation();	
			
		$(".quick_links_inner").fadeOut();
		
		setTimeout("navLinkslidedown()", 500);
	});
}

///////Header Nav////////
var _home_con = $(".inner_content1");
var _exp_con  = $(".inner_content_exp");
var _consul_con  = $(".inner_content_consl");
var _consutom_con  = $(".inner_content_custom");
var _history_con  = $(".inner_content_history");
var _aboutus_con  = $(".inner_content_about");
var _manage_con  = $(".inner_content_mng");
var _location_con  = $(".inner_content_loaction");
var _join_con  = $(".inner_content_join");
var _applyjob_con  = $(".inner_content_applyforjob");
var _contact_con  = $(".inner_content_contact");
var _dash_con  = $(".inner_content_dash");
var _appln_con  = $(".inner_content_appln");





/*****************************************/
//////////display talk us 
function talkUs_container(){
	$(".letustalk").click(function(event){
								   
		if (document.all)
			window.event.cancelBubble = true;
		else
			event.stopPropagation();
			
	var _thisheght =$(".talk_us").css("height");
	if(_thisheght=="0px"){
	$(".talk_us").animate({
							top:'-129px',
							height:"125px"
							},1000);
	}else{
		$(".talk_us").animate({
							top:'0px',
							height:"0px"
							},1000);
	
		setTimeout('talk_display()', 500);
		//talk_display();
		}
	
	});
	}
	
	//////////////////////display login container
	function dashboard(){
		$("#login").click(function(){
								   
							$(".wel_come").fadeIn();
							$(".dashboard").fadeIn();		   
		});	
	}
	
	function dashboard_close(){
		$("#close_login").click(function(){
								   
							$(".wel_come").fadeOut();
							$(".dashboard").fadeOut();		   
		});	
	}
	
	function helpinfo_close(){
		$("#cancel").click(function(){
								   
							$(".wel_come").fadeOut();
							$(".dashboard").fadeOut();		   
		});	
	}

	function helpinfo_dis(){
		$("#help_info").click(function(){
								   
		$(".wel_come").fadeIn();
		$(".sitemap_info").fadeIn();
												   
	});
	}
	
	function _helpinfo_close(){
		$("#close_info").click(function(){
								   
							$(".wel_come").fadeOut();
							$(".sitemap_info").fadeOut();		   
		});	
	}
		 
///////////////////////////////////
function talk_display(){
	$(".talk_us").hide(5);
	}
function fade_btn(){
	
	$(".gohomebutfade").fadeOut(1000);
}
function fadein_btn(){
	
	$(".gohomebutfade").fadeIn(1000);
}
/////////////////////////

function hidePop(){
	$(".wel_come").click(function(){
								  //$(".wel_come").fadeOut();  welcome div element  fadeout
								  $(".site_map").fadeOut();
								  $(".dashboard").fadeOut();
								  $(".sitemap_info").fadeOut();
								  });
	}
/////////////Image zoom////////////////
/*function imagefade(){
		$(".botInner li").mouseover(function (){
											$(".innergry").fadeIn();
										  $(this).children("a").children(".innergry").fadeOut();
											  });
}*/
var count = 0;
var zoom;
var zoomin;
function imageEnlarge(){
	$(".botInner li img").mouseover(function (){
											  $(".productinfo").html();
											  var _infotext = $(this).attr("alt");
											  $(".productinfo").html(_infotext);
										   clearInterval(zoomin);
										   var _src =$(this).attr("src");
										   _src =_src.split(".");
										   _src =_src[0]+"enb.png";
										
										   $(this).attr("src",_src);
										   var _imgwidth = parseInt($(this).css("width"));
										   var _imgheight = parseInt($(this).css("height"));
										   
										   _imgwidth_new = 40*1.4;
										   //var _this =$(this);
										   //if(count=="0"){
										   //if(!$(this).is(":animated")){
											   
											$(this).stop().animate({
											width:_imgwidth_new+"px",
											height:_imgwidth_new+"px",
											marginTop:-10+"px",
											marginLeft:-10+"px"
											},500);
											
											//count++;
										  // }
										  // }else{
											   
										  // }
										   
										 /*zoom=setInterval(function(){
																  _imageEnlarge(_imgwidth, _imgheight, _this)
																  },30);*/
										   
										  
										  
										   
										   });
	
	
	$(".botInner li img").mouseout(function (){
											  $(".productinfo").html("");
											var _imgwidth = parseInt($(this).css("width"));
											var _imgheight = parseInt($(this).css("height"));
											_imgwidth_new = 40;
											var _srcout =$(this).attr("src");
										   _srcout =_srcout.split(".");
										   _srcout =_srcout[0].replace("enb", "");
										   _srcout =_srcout+".png";
											$(this).attr("src",_srcout);
											//var _this =$(this);
											//if(count=="1"){
											//if(!$(this).is(":animated")){
											$(this).stop().animate({
											width:_imgwidth_new+"px",
											height:_imgwidth_new+"px",
											marginTop:0+"px",
											marginLeft:0+"px"
											},500);
											//count=0;
											
											//}
											//}else{
											//$(this).stop().animate({
											///width:_imgwidth_new+"px"
											//},500);
											//count=0;
											//}
											 
											
										   /*clearInterval(zoom);
										   //zoomin=setTimeout('_resizeimg()',30);
										   var _imgwidth_res = parseInt($(this).css("width"));
										   var _imgheight_res = parseInt($(this).css("height"));
										   var _this =$(this);
										   zoomin=setInterval(function(){
																  _resizeimg(_imgwidth_res, _imgheight_res, _this)
																  },30);
										  */
										   });
	

}

/*function _imageEnlarge(_imgwidth_res, _imgheight_res, _this){
											_imgwidth_new_res=_imgwidth_res+count;
										   _imgheight_new_res=_imgheight_res+count;
										  
										  if(count<10){
											  
										   _this.css("width",+_imgwidth_new_res+"px");
										   _this.css("height",+_imgheight_new_res+"px");
										   count++;
										   $("h1").html(count);
										   }
										   
										   
}

function _resizeimg(_imgwidth, _imgheight, _this){
	
		_imgwidth_new=_imgwidth-count;
		_imgheight_new=_imgheight-count;
		
	if(count>0){
	
		_this.css("width",+_imgwidth_new+"px");
		_this.css("height",+_imgheight_new+"px");
		count--;
		$("h1").html(count);
	}
}
*/
/***********************/
settime = 500;

	
function viewsitemap(){
	/*$(".sitemmappop").hover(function(){
									 clearNavTimer();
									 
									 },function(){
										 
										 $(".sitemmappop").hide();
										 
												  });*/
	
	$("div.sitemmappop").mouseover(function(){
											
										   clearNavTimer();
										   
										   });
	
		$("div.sitemmappop").mouseleave(function(){
										 
										settime = setTimeout(function (){
	 									 $(".sitemmappop").hide();
										 }, 500);
										
										  })
		
		
	$("#sitemap_view").mouseover(function(){
									  
									  
									  $(".sitemmappop").show();
									  
									  }).mouseout(function(){
										 clearNavTimer();
										 settime = setTimeout(function (){
	 									 $(".sitemmappop").hide();
										 }, 500);
										 
												   });
}
function hidesitemap(){
	 $(".sitemmappop").hide();
}


/////////

function clearNavTimer(){ 
		       clearTimeout(settime);
				settime=null;
				
				
	}
	
	
$(document).ready(function(){

	var contentHeight = $("#container").height();
	$(".right_navbar").click(function(){
		var thisclass= 	$(".right_navbar");		
		var contentclass= 	$(".wrapper");	
		
		if ((screen.width<=1024) && (screen.height<=768)) {
				$(".bottom_nav").css("left", "23%");
				$("#container").css("height", contentHeight);
		} else if ((screen.width<=1280) && (screen.height<=800)) {
				$(".bottom_nav").css("left", "23%");
		}
		rightNav(thisclass, contentclass);									  
	});		

	horizantal_acc();/////////bottom accordion
	
	NavtopRightfn();/////////////Right top Nav
	
	Navbotleftfn(); /////////////left bottom Nav
	
	NavbotRightfn();///////////////right bottom Nav
	
	Navtopleftfn();///////////////right bottom Nav
	
	navLinkslideUp();//////////Link slideUP
	
	quickLinkslideslideUp();//////////Link slidedown
	
	//headernav();////////////// Header
	
	//displayHome();//////////////////show home
	
	
	updateOrientation();//////////////////onOriantation change the accord width
	
	
	dashboard();/////////diaplay login form
	
	talkUs_container();///////////talk us
	
	
	dashboard_close();/////////////Login page close
	
	
	helpinfo_close();///////////close Login
	
	helpinfo_dis();///////////help info display
	
	_helpinfo_close();//////// help info close

	hidePop();///////////hide thick box and content
	
	imageEnlarge();//////////enlarge image
	
	//imagefade();/////////image show
	
	
	viewsitemap();////////sitemapview
	
	
	setInterval("fade_btn()", 1000);
	setInterval("fade_btn()", 1000);
	setInterval("fadein_btn()", 2000);
	
	
	
	
	
	$(".nav_btn").click(function(){
		navbtn = $(this).attr("id");
		tabheader = $(".tab_header"); 
		//leftpos = parseInt(tabheader.css("left"));
		item_index = parseInt(tabheader.attr("item-index"));
		total_tabs = tabheader.children("li").size();
		nav_item_index_grid = 0;
		
		//This is optional one
		if(navbtn == "prev_btn" && item_index <= 1){  // final  prev button
			
			nav_item_index_grid = item_index + 1 ; // for first item
		}else if(navbtn == "next_btn" && item_index >= total_tabs){ // final next button
			
			nav_item_index_grid = item_index - 1 ; // for last item
		}
		
		if(navbtn == "prev_btn" && item_index > 1){
			if(item_index > 6){
				posleft = (item_index-1 - 6) * -108;
				tabheader.animate({
					left: posleft+'px'
					},1000);	
			}
			nav_item_index_grid = item_index;
			item_index = item_index - 1;	
			tabheader.attr("item-index",item_index)
		}else if(navbtn == "next_btn" && item_index < total_tabs){
			nav_item_index_grid = item_index;
			item_index = item_index + 1
			if(item_index > 6){
				posleft = (item_index - 6) * -108;				
				tabheader.animate({
					left: posleft+'px'
					},1000);			
			}
			tabheader.attr("item-index",item_index)			
		}
		if(item_index == 1){
			
			$("#prev_btn").css("display", "none");
		}else{
			
			$("#prev_btn").css("display", "block");
		}
		if(item_index == 6){
			
			$("#next_btn").css("display", "none");
		}else{
			
			$("#next_btn").css("display", "block");
		}
		$(".tabs").removeClass("selected");
		$(".tabs.apptab"+item_index).addClass("selected");
		$(".tab_body").attr("class","tab_body apptab"+item_index);
		$("#grid"+item_index).show();
		$("#grid"+nav_item_index_grid).hide();
	});
	
	$("#cancel_btn").click(function(){
		$(".overlay_container").hide();
		$("#appln_add_new").hide();
		$(".wel_come").hide();
	});
	
	$(".overlay_container").click(function(){
		$(".overlay_container").hide();
		$("#appln_add_new").hide();	
		
	});
	
	
});




$(document).click(function(){
	
	
	
		/*$(".talk_us").animate({
							top:'0px',
							height:"0px"
							},1000);
		
		//setTimeout('talk_display()', 800);*/
		

});