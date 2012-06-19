<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'PHPUnit/Autoload.php';
include 'phresco/tests/basescreen.php';
require_once 'phresco/tests/phpwebdriver/RequiredFunction.php';

class PhpCommonFun extends RequiredFunction
{
	private $host;
	private $port;
	private $context;
	private $protocol;
	private $serverUrl;
	private $browser;
	private $screenShotsPath;
	
    protected function setUp(){ 
	
		$doc = new DOMDocument();
		
		$doc->load('test-classes/phresco/tests/phresco-env-config.xml');
		
		$environment = $doc->getElementsByTagName("Server");
		
		$config = $doc->getElementsByTagName("Browser");
		$browser = $config->item(0)->nodeValue;
		
    	$this->webdriver = new WebDriver("localhost", 4444); 
		
       	$this->webdriver->connect($browser);
		
        $screenShotsPath = getcwd()."/surefire-reports/screenshots";
		
		if (!file_exists($screenShotsPath)) {
		
			mkdir($screenShotsPath);
		
		}
    
	}
    public function Browser(){  
	
		$doc = new DOMDocument();
		
		$doc->load('test-classes/phresco/tests/phresco-env-config.xml');
		
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
    	
        $serverUrl = $protocol . ':'.'//' . $host . ':' . $port . '/'. $context . '/';
		
		$this->webdriver->get($serverUrl);
		
    }

}	

?>