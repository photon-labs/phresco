<?php
/* Author by {phresco} QA Automation Team */
require_once 'phresco/tests/phpwebdriver/RequiredFunction.php';
include 'phresco/tests/basescreen.php';

class WordPressCommonFun extends RequiredFunction 
{
	private $properties;
	private $host;
	private $port;
	private $context;
	private $protocol;
	private $serverUrl;
	private $browser;
	private $screenShotsPath;
	
	protected function setUp()
	{
		$doc = new DOMDocument();
		
		$doc->load('test-classes/phresco/tests/phresco-env-config.xml');
		
		$configuration = $doc->getElementsByTagName("Server");
		
		$config = $doc->getElementsByTagName("Browser");
		$browser = $config->item(0)->nodeValue;
		
    	$this->webdriver = new WebDriver("localhost", 4444); 
		
       	$this->webdriver->connect($browser);
		
        $screenShotsPath = getcwd()."/surefire-reports/screenshots";
		
		if (!file_exists($screenShotsPath)) {
		
			mkdir($screenShotsPath);
		
		}
	}
	
	
	public function Title()
	{
		$doc = new DOMDocument();
		
		$doc->load('test-classes/phresco/tests/phresco-env-config.xml');
		
		$configuration = $doc->getElementsByTagName("Server");
		
		foreach( $configuration as $Server )
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
    	
        $serverUrl = $protocol . ':'.'//' . $host . ':' . $port . '/'. $context . '/';
		
		$this->webdriver->get($serverUrl);

	}
	function DoLogin(){
			$name;
			$password;
			$property = new WordPressCommonFun;
			$doc = new DOMDocument();
			$doc->load('test-classes/phresco/tests/WpSetting.xml');
			$users = $doc->getElementsByTagName("user");
		foreach( $users as $user )
		{
			$names = $user->getElementsByTagName("username");
			$name = $names->item(0)->nodeValue;
			
			$passwords = $user->getElementsByTagName("password");
			$password = $passwords->item(0)->nodeValue;
		}
		   $this->clickandLoad(CLICK_LOGIN_TEXT);
		   sleep(2);
		   $this->type(UNAME_XPATH,$name);
		   sleep(1);
		   $this->type(PASSWORD_XPATH,$password);
		   $this->clickandLoad(LOGIN);
		   
		try {
			$this->assertTrue($this->isTextPresent(LOGIN_CONFIRM));
		    } 
		catch (PHPUnit_Framework_AssertionFailedError $e) {
		 	$this->doCreateScreenShot(__FUNCTION__);
		}
		} 
}
?>

