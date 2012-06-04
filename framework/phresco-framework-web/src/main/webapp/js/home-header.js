// JavaScript Document
function showallNavHome(){
	$(".lefttopnav").fadeIn();
	$(".righttopnav").fadeIn();
	$(".rightbotnav").fadeIn();
	$(".leftbotnav").fadeIn();
}
function hideallNavHome(){
	$(".lefttopnav").fadeOut();
	$(".righttopnav").fadeOut();
	$(".rightbotnav").fadeOut();
	$(".leftbotnav").fadeOut();
}
	///////******************************/
	
function reset_currentcontainer_hm_tr(resetcont1, resetcont2){
	_resetcont1 = resetcont1;
	_resetcont2 = resetcont2;
	_resetcont1.animate({
							left:'160%',
							top:'-80%',
							width:'50%',
							height:'50%'
							},1000);
	_resetcont2.animate({
							left:'5%',
							top:'7%',
							width:'89%',
							height:'90%'
							},1000);
	
}

/**********//////////////checks the level of navigation
function getElementstate(_getElementstate){
	
	var _home 			="Home";
	var _spacenull		="";
	var _tec  			="Technology";
	var _what  			="What we ";
	var _provide		="Provide";
	var _become 		="Become a ";
	var _customer 		="Customer";
	var _our            ="Our ";
	var _exp  			="Expertise";
	var _about			="Applications";
	var _us 			="";
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
	var _application		="Applications";
	var _add			="Add ";	
	var _settings		="Settings";	
	
	var navLeftop1 = $("#lf_tp1");
	var navLeftop2 = $("#lf_tp2");
	var navleftbot1 = $("#lf_bt1");
	var navleftbot2 = $("#lf_bt2");
	var navrgtop1 = $("#rg_tp1");
	var navrgtop2 = $("#rg_tp2");
	var navrgbot1 = $("#rg_bt11");
	var navrgbot2 = $("#rg_bt12");
	
	var checkstate = navLeftop1.html()+navLeftop2.html()+navleftbot1.html()+navleftbot2.html();
		checkstate = checkstate+navrgtop1.html()+navrgtop2.html()+navrgbot1.html()+navrgbot2.html();
	
	switch(_getElementstate){
		
		
		case('sitemap_view'):
		
	
		navLeftop1.html(_about);
		navLeftop2.html(_us);
		navleftbot1.html(_join);
		navleftbot2.html(_us);
		navrgtop1.html(_our);
		navrgtop2.html(_exp);
		navrgbot1.html(_spacenull);
		navrgbot2.html(_dash);
		
			switch(checkstate){
		
		case('History in TechnologyHomeWhat we ProvideBecome a Customer'):
		var res_cont1= $(".inner_content_exp");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Our Expertise'):
		var res_cont1= $(".inner_content_consl");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About UsOur Expertise'):
	
		var res_cont1= $(".inner_content_history");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Management TeamLocationHistory in TechnologyHome'):
	
		var res_cont1= $(".inner_content_about");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('HomeContact UsBecome a CustomerInsight '):
	
		var res_cont1= $(".inner_content_dash");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('LocationApply for a JobHomeContact Us'):
	
		var res_cont1= $(".inner_content_join");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('About UsJoin Us'):
	
		var res_cont1= $(".inner_content_loaction");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join Us'):
	
		var res_cont1= $(".inner_content_applyforjob");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join UsDashboard'):
	
		var res_cont1= $(".inner_content_contact");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('Our ExpertiseDashboard'):
	
		var res_cont1= $(".inner_content_custom");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About Us'):
	
		var res_cont1= $(".inner_content_mng");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
	}
		break;
		
		
		case('home'):
		navLeftop1.html(_about);
		navLeftop2.html(_us);
		navleftbot1.html(_join);
		navleftbot2.html(_us);
		navrgtop1.html(_our);
		navrgtop2.html(_exp);
		navrgbot1.html(_spacenull);
		navrgbot2.html(_dash);
		
			switch(checkstate){
		
		case('History in TechnologyHomeWhat we ProvideBecome a Customer'):
		var res_cont1= $(".inner_content_exp");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Our Expertise'):
		var res_cont1= $(".inner_content_consl");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About UsOur Expertise'):
	
		var res_cont1= $(".inner_content_history");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Management TeamLocationHistory in TechnologyHome'):
	
		var res_cont1= $(".inner_content_about");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('HomeContact UsBecome a CustomerInsight '):
	
		var res_cont1= $(".inner_content_dash");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('LocationApply for a JobHomeContact Us'):
	
		var res_cont1= $(".inner_content_join");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('About UsJoin Us'):
	
		var res_cont1= $(".inner_content_loaction");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join Us'):
	
		var res_cont1= $(".inner_content_applyforjob");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join UsDashboard'):
	
		var res_cont1= $(".inner_content_contact");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('Our ExpertiseDashboard'):
	
		var res_cont1= $(".inner_content_custom");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About Us'):
	
		var res_cont1= $(".inner_content_mng");
		var res_cont2= $(".inner_content1");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
	}
		break;
		
		case('whoweare'):
		
		navLeftop1.html(_management);
		navLeftop2.html(_team);
		navleftbot1.html(_Location);
		navleftbot2.html(_spacenull);
		navrgtop1.html(_history);
		navrgtop2.html(_tec);
		navrgbot1.html(_home);
		navrgbot2.html(_spacenull);
		
			switch(checkstate){
		
		case('About UsJoin UsOur ExpertiseDashboard'):
		var res_cont1= $(".inner_content1");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('History in TechnologyHomeWhat we ProvideBecome a Customer'):
		var res_cont1= $(".inner_content_exp");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Our Expertise'):
		var res_cont1= $(".inner_content_consl");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About UsOur Expertise'):
	
		var res_cont1= $(".inner_content_history");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('HomeContact UsBecome a CustomerInsight '):
	
		var res_cont1= $(".inner_content_dash");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('LocationApply for a JobHomeContact Us'):
	
		var res_cont1= $(".inner_content_join");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('About UsJoin Us'):
	
		var res_cont1= $(".inner_content_loaction");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join Us'):
	
		var res_cont1= $(".inner_content_applyforjob");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join UsDashboard'):
	
		var res_cont1= $(".inner_content_contact");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('Our ExpertiseDashboard'):
	
		var res_cont1= $(".inner_content_custom");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About Us'):
	
		var res_cont1= $(".inner_content_mng");
		var res_cont2= $(".inner_content_about");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
	}
		break;
		
		case('whatwedo'):
		
		navLeftop1.html(_history);
		navLeftop2.html(_tec);
		navleftbot1.html(_home);
		navleftbot2.html(_spacenull);
		navrgtop1.html(_what);
		navrgtop2.html(_provide);
		navrgbot1.html(_become);
		navrgbot2.html(_customer);
		
		switch(checkstate){
		
		case('About UsJoin UsOur ExpertiseDashboard'):
		var res_cont1= $(".inner_content1");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Management TeamLocationHistory in TechnologyHome'):
		var res_cont1= $(".inner_content_about");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		
		case('Our Expertise'):
		var res_cont1= $(".inner_content_consl");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About UsOur Expertise'):
	
		var res_cont1= $(".inner_content_history");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('HomeContact UsBecome a CustomerInsight '):
	
		var res_cont1= $(".inner_content_dash");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('LocationApply for a JobHomeContact Us'):
	
		var res_cont1= $(".inner_content_join");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('About UsJoin Us'):
	
		var res_cont1= $(".inner_content_loaction");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join Us'):
	
		var res_cont1= $(".inner_content_applyforjob");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join UsDashboard'):
	
		var res_cont1= $(".inner_content_contact");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('Our ExpertiseDashboard'):
	
		var res_cont1= $(".inner_content_custom");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About Us'):
	
		var res_cont1= $(".inner_content_mng");
		var res_cont2= $(".inner_content_exp");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
	}
		break;
		
		case('carrers'):
		
		navLeftop1.html(_spacenull);
		navLeftop2.html(_Location);
		navleftbot1.html(_apply);
		navleftbot2.html(_ajob);
		navrgtop1.html(_spacenull);
		navrgtop2.html(_home);
		navrgbot1.html(_contact);
		navrgbot2.html(_us);
		
		switch(checkstate){
		
		case('About UsJoin UsOur ExpertiseDashboard'):
		var res_cont1= $(".inner_content1");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('History in TechnologyHomeWhat we ProvideBecome a Customer'):
		var res_cont1= $(".inner_content_exp");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Our Expertise'):
		var res_cont1= $(".inner_content_consl");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About UsOur Expertise'):
	
		var res_cont1= $(".inner_content_history");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('HomeContact UsBecome a CustomerInsight '):
	
		var res_cont1= $(".inner_content_dash");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About UsJoin Us'):
	
		var res_cont1= $(".inner_content_loaction");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join Us'):
	
		var res_cont1= $(".inner_content_applyforjob");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Join UsDashboard'):
	
		var res_cont1= $(".inner_content_contact");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('Our ExpertiseDashboard'):
	
		var res_cont1= $(".inner_content_custom");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
		case('About Us'):
	
		var res_cont1= $(".inner_content_mng");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		case('Management TeamLocationHistory in TechnologyHome'):
	
		var res_cont1= $(".inner_content_about");
		var res_cont2= $(".inner_content_join");
		res_cont2.css({'left' : '-160%', 'top' : '-80%'});
		reset_currentcontainer_hm_tr(res_cont1, res_cont2);
		break;
		
		
	}
		break;
case ('applications'):
    navLeftop1.html(_application);
    navLeftop2.html(_spacenull);
    navleftbot1.html(_about);
    navleftbot2.html(_spacenull);
    navrgtop1.html(_add);
    navrgtop2.html(_application);
    navrgbot1.html(_settings);
    navrgbot2.html(_spacenull);
    //document.getElementById('btnDiscover').style.display="block";
    //document.getElementById('btnAddNew').style.display="block";
    switch (checkstate) {

        case ('About UsJoin UsOur ExpertiseDashboard'):
            var res_cont1 = $(".inner_content1");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;

        case ('History in TechnologyHomeWhat we ProvideBecome a Customer'):
            var res_cont1 = $(".inner_content_exp");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;

        case ('Our Expertise'):
            var res_cont1 = $(".inner_content_consl");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;


        case ('About UsOur Expertise'):

            var res_cont1 = $(".inner_content_history");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;


        case ('HomeContact UsBecome a CustomerInsight '):

            var res_cont1 = $(".inner_content_dash");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;


        case ('About UsJoin Us'):

            var res_cont1 = $(".inner_content_loaction");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;

        case ('Join Us'):

            var res_cont1 = $(".inner_content_applyforjob");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;

        case ('Join UsDashboard'):

            var res_cont1 = $(".inner_content_contact");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;


        case ('Our ExpertiseDashboard'):

            var res_cont1 = $(".inner_content_custom");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;


        case ('About Us'):

            var res_cont1 = $(".inner_content_mng");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;

        case ('Management TeamLocationHistory in TechnologyHome'):

            var res_cont1 = $(".inner_content_about");
            var res_cont2 = $(".inner_content_appln");
            res_cont2.css({ 'left': '-160%', 'top': '-80%' });
            reset_currentcontainer_hm_tr(res_cont1, res_cont2);
            break;


    }
    break;		
		
		
	
	}
		
}





function resethome(currentNavClick){

	getElementstate(currentNavClick);

	showallNavHome();
	

}

function resesitemap_view(currentNavClick_site){
	
	getElementstate(currentNavClick_site);

	showallNavHome();
	

}

/*function headernav(){
	$(".headerInnerTop li a").click(function(){
										 
										 $(".headerInnerTop li a").removeClass("active");
										 $(this).addClass("active");
										 
										 var currentNavClick = $(this).attr("id");		
		
										resethome(currentNavClick);////////////////////////home reset	
										if($(".site_map").css('display') =="block"){	
										$(".site_map").hide("scale", {}, 1000);
										}
										 
										 });
}*/

//////////////
function sitemap_view(){
						  
		if($(".site_map").css('display') =="none"){	
		
		$(".bg_text").addClass("bg_hide");
		$(".site_map").show("scale", {}, 1000);
		$(".inner_content1").animate({		
						left:'160%',
						top:'-80%',
						width:'50%',
						height:'50%'
						},1000
						);
		}

}
/*****************************************/

$(document).ready(function(){

setTimeout(function(){
	  $('#errmsg').fadeOut("slow", function () {
	  $('#errmsg').remove();
	      });
	 
	}, 2000);
	
	
setTimeout(function(){
	  $('#successmsg').fadeOut("slow", function () {
	  $('#successmsg').remove();
	      });
	 
	}, 2000);

$(".headerInnerTop ul li a").click(function(){
		$(".headerInnerTop li a").removeClass("active");
		$(this).addClass("active");		
		
		var currentNavClick = $(this).attr("id");		
		
		resethome(currentNavClick);////////////////////////home reset	
		
		if($(".site_map").css('display') =="block"){
			
		$(".site_map").hide("scale", {}, 1000);
		}
		
});
	
	
	$("#sitemap_view").click(function(){	
									  
									  
		var currentNavClick_site = $(this).attr("id");
		
		resesitemap_view(currentNavClick_site);	///////////////view site map navigate	
		
		sitemap_view()///////////////view site map container	
		
		
		
		var _navitem1=$(".lefttopnav").css("display");
		var _navitem2=$(".leftbotnav").css("display");
		var _navitem3=$(".righttopnav").css("display");
		var _navitem4=$(".rightbotnav").css("display");
		
		if(_navitem1=="block" || _navitem2=="block" || _navitem3=="block" || _navitem4=="block"){
			
			hideallNavHome();
			$(".lefttopnav").css("display", "none");
			$(".leftbotnav").css("display", "none");
			$(".righttopnav").css("display", "none");
			$(".rightbotnav").css("display", "none");
		}
		
		 });
	
});

function checkAndUnCheckAllModules(allModuleId, SingleModuleId){
	$("."+ SingleModuleId).each(function() {
		if (!($(this).is(':disabled'))) {
			$(this).attr('checked', $('#' + allModuleId).is(':checked'));
		}
	});
}

function checkAndUnCheckModules(divElem, allModuleId) {
	var totalModule = ($('#'+ divElem + ' input[type=checkbox]').size()) - 1;
	var selectedModule = $('#'+ divElem + ' input[type=checkbox]:checked').size();
	if($('#' + allModuleId).is(':checked')){
		selectedModule = selectedModule - 1;
	}
	if(selectedModule == totalModule){
		$('#' + allModuleId).attr('checked', true); 
	}
	if(selectedModule < totalModule){
		$('#' + allModuleId).attr('checked', false); 
	}
}

function ShowHideNewApplication()
{
	//document.getElementById("appln_add_new").style.display="none";
	//document.getElementById("appln_add_new").style.display="none";
	// document.getElementById("overlay_container").style.display="block";
	//document.getElementById("appln_add_new").style.display="table";
	//$(".wel_come").fadeIn();
	//document.getElementById("appln_add_new").style.display="block";
}

function ShowHideQualityDiv()
{
	document.getElementById("builds_div").style.display="none";
	document.getElementById("build_detail_div").style.display="block";
}

/** To check whether the device is iPad or not **/
function isiPad() {
    return (
        (navigator.platform.indexOf("iPhone") != -1) ||
        (navigator.platform.indexOf("iPad") != -1)
    );
}