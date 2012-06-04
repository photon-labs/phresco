<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'PHPUnit/Framework.php';
include 'phresco/tests/basescreen.php';
require_once 'PHPUnit/Extensions/SeleniumTestCase.php';

class PhpCommonFun extends PHPUnit_Extensions_SeleniumTestCase
{
	private $properties;
	private $host;
	private $port;
	private $context;
	private $protocol;
	private $serverUrl;
	private $screenShotsPath;
	
    protected function setUp(){ 
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
    public function Browser(){  
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
}
?>