<?php
/* Author by {phresco} QA Automation Team 
*/
require_once 'WordPressCommonFun.php';
require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
class Welcompage extends WordPressCommonFun
{
	protected function Browser()
	{
		parent::setUp();
	}
	public function testNewAC()
	{
		parent::Title();
		
		
	}
}
?>