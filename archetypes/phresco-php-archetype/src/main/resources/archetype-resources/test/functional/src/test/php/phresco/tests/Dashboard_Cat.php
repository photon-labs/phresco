<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'PhpCommonFun.php';

class Dashboard_Cat extends PhpCommonFun
{
    protected function setUp(){ 
    	parent::setUp();
    }   
    public function testDashboard(){ 
    	parent::Browser();
	
    }
	public function tearDown(){
		parent::tearDown();
	}
	
}  
?>
