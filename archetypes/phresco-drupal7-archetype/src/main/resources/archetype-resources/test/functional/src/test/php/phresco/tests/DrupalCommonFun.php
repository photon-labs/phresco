<?php
/* Author by {phresco} QA Automation Team */

require_once 'PHPUnit/Framework.php';
include 'phresco/tests/basescreen.php';

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
class DrupalCommonFun extends PHPUnit_Extensions_SeleniumTestCase
{
	private $properties;
	private $host;
	private $port;
	private $context;
	private $protocol;
	private $serverUrl;

	protected function setUp()
	{
		 $doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/phresco-env-config.xml');
		$environment = $doc->getElementsByTagName("Server");
		foreach( $environment as $Server )
		{
			$protocols= $Server->getElementsByTagName("protocol");
			$protocol = $protocols->item(0)->nodeValue;
			
			$hosts = $Server->getElementsByTagName("host");
			$host = $hosts->item(0)->nodeValue;
			
			$ports = $Server->getElementsByTagName("port");
			$port = $ports->item(0)->nodeValue;
			
			$contexts = $Server->getElementsByTagName("context");
			$context = $contexts->item(0)->nodeValue;
		}
			$config = $doc->getElementsByTagName("Browser");
			$browser = $config->item(0)->nodeValue;
		
		$this->setBrowser('*' . $browser);
		$serverUrl = $protocol . ':'.'//' . $host . ':' . $port . '/'. $context . '/';
		echo $serverUrl;
		$this->setBrowserUrl($serverUrl);
		$screenShotsPath = getcwd()."//"."target\surefire-reports\screenshots";
		if (!file_exists($screenShotsPath)) {
			mkdir($screenShotsPath);
		}
	}
	public function Title()
	{
		$doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/phresco-env-config.xml');
		$environment = $doc->getElementsByTagName("Server");
		foreach( $environment as $Server )
		{
			$protocols= $Server->getElementsByTagName("protocol");
			$protocol = $protocols->item(0)->nodeValue;
			
			$hosts = $Server->getElementsByTagName("host");
			$host = $hosts->item(0)->nodeValue;
			
			$ports = $Server->getElementsByTagName("port");
			$port = $ports->item(0)->nodeValue;
			
			$contexts = $Server->getElementsByTagName("context");
			$context = $contexts->item(0)->nodeValue;
		}
		$serverUrl = $protocol .':'. '//' . $host . ':' . $port . '/'. $context . '/';
		$this->open($serverUrl);
		$this->waitForPageToLoad(WAIT_FOR_NEXT_PAGES);
		$this->windowMaximize();
		$this->windowFocus();
		sleep(WAIT_FOR_NEXT_LINE);

	}
	function DoLogin($testCaseName){
        if ($testCaseName == null) 
	   {
           $testCaseName = __FUNCTION__;
       }
		$name;
		$password;
		$property = new DrupalCommonFun;
		$doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/drupalsetting.xml');
		$users = $doc->getElementsByTagName("user");
		foreach( $users as $user )
		{
			$names = $user->getElementsByTagName("username");
			$name = $names->item(0)->nodeValue;
			
			$passwords = $user->getElementsByTagName("password");
			$password = $passwords->item(0)->nodeValue;
		}
		$this->isTextPresent(DRU_LOGIN_TEXT);
		$property->waitForElementPresent('DRU_LOGIN_UNAME');
		$this->type(DRU_LOGIN_UNAME, $name);
		$property->waitForElementPresent('DRU_LOGIN_PASSWORD');
		$this->type(DRU_LOGIN_PASSWORD,$password);
		$this->clickAndWait(DRU_LOGIN_BUTTON);
		$property->waitForElementPresent('DRU_LOGIN_UNAME_PRESENT');
		

		try {
			$this->assertTrue($this->isTextPresent(DRU_LOGIN_UNAME_PRESENT));
		}
		catch (PHPUnit_Framework_AssertionFailedError $e) {
			$this->doCreateScreenShot($testCaseName);
			$this->fail( "Failed asserting that &lt;boolean:false&gt; required is true." );
		}
		$this->clickAndWait(DRU_LOGOUT);
		$property->waitForElementPresent('DRU_LOGIN_TEXT');
	}
	
	function doCreateScreenShot($file_name)
	{
		$this->captureEntirePageScreenshot(getcwd()."//"."target\surefire-reports\screenshots"."//".$file_name.'.png');
	}

	public function waitForElementPresent($waitfor)
	{
			
		for ($second = 0;$second <=WAIT_FOR_SEC ;$second++) {
			if ($second >= WAIT_FOR_SEC){
			}
			try{
				if ($this->isElementPresent($waitfor))
				break;
			} catch (Exception $e) {}
			sleep(1);
		}
	}
	function tearDown()
	{
	 $this->stop();
	}

}
?>

