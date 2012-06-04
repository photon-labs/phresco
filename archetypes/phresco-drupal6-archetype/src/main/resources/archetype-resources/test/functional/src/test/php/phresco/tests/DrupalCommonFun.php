<?php /*
 * ###
 * Archetype - phresco-drupal6-archetype
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
class DrupalCommonFun extends PHPUnit_Extensions_SeleniumTestCase
{
	private $properties;
	private $hostvalue;
	private $portvalue;
	private $contextvalue;
	private $protocolvalue;
	private $serverUrl;
    private $Browser;

	protected function setUp()
	{
	
	    $doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/phresco-env-config.xml');
		$environment = $doc->getElementsByTagName("Server");
		foreach( $environment as $Server )
		{
		$contexts = $Server->getElementsByTagName("context");
			$contextvalue = $contexts->item(0)->nodeValue;
		
		$protocols = $Server->getElementsByTagName("protocol");
			$protocolvalue = $protocols->item(0)->nodeValue;
		
		$hosts = $Server->getElementsByTagName("host");
			$hostvalue = $hosts->item(0)->nodeValue;
		
		$ports = $Server->getElementsByTagName("port");
			$portvalue = $ports->item(0)->nodeValue;
		
		
		}
		
		   $Config = $doc->getElementsByTagName("Browser");
			$Browser = $Config->item(0)->nodeValue;
		
		$this->setBrowser('*' . $Browser);
		$serverUrl = $protocolvalue . ':'.'//' . $hostvalue . ':' . $portvalue . '/'. $contextvalue . '/';
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
		$hosts = $Server->getElementsByTagName("host");
			$hostvalue = $hosts->item(0)->nodeValue;
		
		$ports = $Server->getElementsByTagName("port");
			$portvalue = $ports->item(0)->nodeValue;
		
		$contexts = $Server->getElementsByTagName("context");
			$contextvalue = $contexts->item(0)->nodeValue;
		
		$protocols = $Server->getElementsByTagName("protocol");
			$protocolvalue = $protocols->item(0)->nodeValue;
		}
		$serverUrl = $protocolvalue . ':'.'//' . $hostvalue . ':' . $portvalue . '/'. $contextvalue . '/';
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
		$doc = new DOMDocument();
		$doc->load('src/test/php/phresco/tests/drupal6.xml');
         $property = new DrupalCommonFun;
		$users = $doc->getElementsByTagName("user");
		foreach( $users as $user )
		{
			$names = $user->getElementsByTagName("username");
			$name = $names->item(0)->nodeValue;
			
			$passwords = $user->getElementsByTagName("password");
			$password = $passwords->item(0)->nodeValue;
			
		}		$this->isTextPresent(DRU_LOGIN_TEXT);
		
		
		$property->waitForElementPresent('DRU_LOGIN_UNAME');
		$this->type(DRU_LOGIN_UNAME, $name);
		$property->waitForElementPresent('DRU_LOGIN_PASSWORD');
		$this->type(DRU_LOGIN_PASSWORD,$password);
		$this->clickAndWait(DRU_LOGIN_BUTTON);
		$property->waitForElementPresent('DRU_LOGIN_UNAME_PRESENT');
		$this->clickAndWait(DRU_LOGOUT);

		try {
			$this->assertTrue($this->isTextPresent(DRU_LOGIN_UNAME_PRESENT));
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

s