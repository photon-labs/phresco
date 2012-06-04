<?php
/* Author by {phresco} QA Automation Team 
*/
require_once 'DrupalCommonFun.php';
require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
class CreateNewAC extends DrupalCommonFun
{
	protected function Browser()
	{
		parent::setUp();
	}
	public function testNewAC()
	{
		parent::Title();
		 $name;
		$email;
		$property = new DrupalCommonFun;
		$doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/drupalsetting.xml');
		$users = $doc->getElementsByTagName("user");
		foreach( $users as $user )
		{
			$names = $user->getElementsByTagName("username");
			$name = $names->item(0)->nodeValue;
			
			$emails = $user->getElementsByTagName("email");
			$email = $emails->item(0)->nodeValue;
		}
		
		if ($this->isElementPresent(DRU_CREATE_AC_LINK))
		$this->clickAndWait(DRU_CREATE_AC_LINK);		
		
		$this->type(DRU_CREATE_AC_CLICK_UNAME,$name);
		
		$this->type(DRU_CREATE_AC_CLICK_EMAIL,$email);
		
		$this->clickAndWait(DRU_CREATE_AC_CLICK_SUBMIT);
		sleep(3);
	 try {
	 	$this->assertTrue($this->isTextPresent(DRU_CREATE_AC_WAIT_FOR_TEXT));
	 	sleep(WAIT_FOR_NEXT_LINE);
	 }
	 catch (Exception $e)
	 {
	 	parent::doCreateScreenShot(__FUNCTION__);
	 	array_push($this->verificationErrors, $e->toString());
	 }
	}
}
?>