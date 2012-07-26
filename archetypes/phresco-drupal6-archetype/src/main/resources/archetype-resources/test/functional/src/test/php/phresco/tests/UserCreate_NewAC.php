<?php
/* Author by {phresco} QA Automation Team*/
require_once 'DrupalCommonFun.php';
class UserCreate_NewAC extends DrupalCommonFun
{
	protected function Browser()
	{
		parent::setUp();
	}
	public function testNewAC()
	{ 
	    $testCaseName = __FUNCTION__;
		parent::Title();
		 $name;
		$email;
		$property = new DrupalCommonFun;
		$doc = new DOMDocument();
		$doc->load('test-classes/phresco/tests/Drupal6Data.xml');
		$users = $doc->getElementsByTagName("user");
		foreach( $users as $user )
		{
			$names = $user->getElementsByTagName("username");
			$name = $names->item(0)->nodeValue;
			
			$emails = $user->getElementsByTagName("email");
			$email = $emails->item(0)->nodeValue;
		}
	       
		   $this->clickandLoad(DRU_CREATE_AC_LINK);		
		   $this->type(DRU_CREATE_AC_CLICK_UNAME,$name);
		   $this->getElement(DRU_CREATE_AC_CLICK_EMAIL,$testCaseName);
		   $this->type(DRU_CREATE_AC_CLICK_EMAIL,$email);
		   $this->clickandLoad(DRU_CREATE_AC_CLICK_SUBMIT);
		   
        try {
			$this->assertTrue($this->isTextPresent(DRU_GET_STRING));
		} 
		catch (PHPUnit_Framework_AssertionFailedError $e) {
		 	$this->doCreateScreenShot(__FUNCTION__);
			
		}
	}
}
?>