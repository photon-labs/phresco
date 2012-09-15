<?php
/*Author by {phresco} QA Automation Team

*/
require_once 'WordPressCommonFun.php';

class Login_NewUsers extends WordPressCommonFun
{
	protected function Browser()
	{
		parent::setUp();
	}
	public function testLogin()
	{
		parent::Title();
		parent::DoLogin();

	}

}
?>