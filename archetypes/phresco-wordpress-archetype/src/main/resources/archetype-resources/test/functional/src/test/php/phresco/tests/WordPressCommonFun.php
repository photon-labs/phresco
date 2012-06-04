<?php /*
 * ###
 * Archetype - phresco-wordpress-archetype
 * 
 * Copyright (C) 1999 - 2012 Photon Infotech Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ###
 */ ?>
<?php
/* Author by {phresco} QA Automation Team */

require_once 'PHPUnit/Framework.php';
include 'phresco/tests/basescreen.php';

require_once 'PHPUnit/Extensions/SeleniumTestCase.php';
class WordPressCommonFun extends PHPUnit_Extensions_SeleniumTestCase
{
	private $properties;
	private $host;
	private $port;
	private $context;
	private $protocol;
	private $serverUrl;
	private $browser;

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
	    if($testCaseName==null )
		{
         $testCaseName = __FUNCTION__;
         }
		$name;
		$password;
		$property = new WordPressCommonFun;
		$doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/WpSetting.xml');
		$users = $doc->getElementsByTagName("user");
		foreach( $users as $user )
		{
			$names = $user->getElementsByTagName("username");
			$name = $names->item(0)->nodeValue;
			
			$passwords = $user->getElementsByTagName("password");
			$password = $passwords->item(0)->nodeValue;
		}
		
		$property->waitForElementPresent('CLICK_LOGIN_LINK');
		$this->clickAndWait(CLICK_LOGIN_LINK);
		$this->type(CLICK_UNAME, $name);
		$property->waitForElementPresent('CLICK_PASSWORD');
		$this->type(CLICK_PASSWORD,$password);
		$this->clickAndWait(CLICK_SUBMIT);
		
		try {
			$this->assertTrue($this->isElementPresent(WAIT_FOR_LINK));
		} 
		catch (PHPUnit_Framework_AssertionFailedError $e) {
			$this->doCreateScreenShot($testCaseName);
			$this->fail( "Failed asserting that &lt;boolean:false&gt; required is true." );

		}
		
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

