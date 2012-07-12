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
	$doc = new DOMDocument();

	$doc->load('test-classes/phresco/tests/PhpData.xml');

	$Search = $doc->getElementsByTagName("searchmodule");

	foreach( $Search as $searchmodule ){

		$Searchtopics = $searchmodule->getElementsByTagName("Searchtopic");
		$Searchtopic = $Searchtopics->item(0)->nodeValue;
	}
	$this->assertFalse($this->isTextPresent($Searchtopic));

    }
public function tearDown(){
parent::tearDown();
}

} 

?>