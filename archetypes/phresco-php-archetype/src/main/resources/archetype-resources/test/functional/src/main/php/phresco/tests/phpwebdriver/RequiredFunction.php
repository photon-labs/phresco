<?php
/*	Author by {phresco} QA Automation Team	*/

require_once 'phresco/tests/phpwebdriver/WebDriver.php';


class RequiredFunction  extends PHPUnit_Framework_TestCase {

	protected $waiting_time = 1;
	
	protected $max_waiting_time = 10;

	protected $requestURL;
	
	private $test;
  /**
	*
	* It is used to find the given text in page
	* It contains Minimum and Maximum waiting time to find the text
	* @param string	$text
	*
	*/
	
	public function isTextPresent( $text ) {
	
        $found = false;
        
		$i = 0;
        
		do {
            
			$html = $this->webdriver->getPageSource();
           
		    if( is_string( $html ) ) {
                
				$found = !(strpos( $html, $text ) === false);
            
			}
			
            if( !$found ) {
			
                sleep( $this->waiting_time );
				
                $i += $this->waiting_time;
            }
        
		} 
		
		while( !$found && $i <= $this->max_waiting_time );
        
		return $found;
	
	}
	
	/**
	*
	* It should wait for certain period of time to find the given element
	* It contains Minimum and Maximum waiting time
	* @param string $element_name 
	*
	*/
	
    public function getElement( $element_name,$testcases) {
	
        $i = 0;
		
		 if($testcases == null){
		
			$testcases = __FUNCTION__;
		} 
		
       do {
		
            try {
			
				$str = explode("//",$element_name );

				if($str[0] == ""){
				
					sleep( $this->waiting_time );
				
					$element = $this->webdriver->findElementBy(LocatorStrategy::xpath, $element_name );
						
				}
				
				else{
				
					$str = explode("=",$element_name);
					
					if($str[0] == "id"){
					
						sleep( $this->waiting_time );
					
						$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
							
					}
					
					elseif($str[0] == "name"){
					
						sleep( $this->waiting_time );
					
						$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
							
					}	
					
					elseif($str[0] == "link"){
					
						sleep( $this->waiting_time );
					
						$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
							
					}
					
					elseif($str[0] == "css"){
					
						sleep( $this->waiting_time );
						
						$element = $this->webdriver->findElementBy(LocatorStrategy::cssSelector, $element_name );
							
					}
					
					else{
					
						sleep( $this->waiting_time );
						
						$element = $this->webdriver->findElementBy(LocatorStrategy::linkText, $element_name );

					}
				
				}
				
			}            
			
			catch( NoSuchElementException $e ) {
			
                print_r( "\nWaiting for \"" . $element_name . "\" element to appear...\n" );
                
				sleep( $this->waiting_time );
                
				$i += $this->waiting_time;
				$this->CreateScreenShot($testcases);
            }
        }
		
		 while( !isset( $element ) && $i <= $this->max_waiting_time );
        
		if( !isset( $element ) )
		
           
		   $this->fail( "Element has not appeared after " . $this->max_waiting_time . " seconds." ); 
        
		return $element;
    
	}
	
	/**
	*
	* It is used to Capture screenshot
	*
	* It capture the screenshot whenever the failure occurs in the script
	*
	*/
	
	public function CreateScreenShot($file_name){ 
	
		$this->webdriver->getScreenshotAndSaveToFile(getcwd()."/"."/surefire-reports/screenshots"."/".$file_name.'.png');
	
	}  
	   
	/**
	*
	Find the field by using id or xpath of the field
	*
	Type the value in the required field
	*
	*/
	
	public function type( $element_name, $type_value ) {
		
		$str = explode("//",$element_name );

		if($str[0] == ""){
		
			$element = $this->webdriver->findElementBy(LocatorStrategy::xpath, $element_name );
		
				if( isset( $element ) ) {
			
					$element->sendKeys( array($type_value) );
				
				}
		}
		
		else{
		
			$str = explode("=",$element_name);
			
			if($str[0] == "id"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
		
					if( isset( $element ) ) {
		
						$element->sendKeys( array($type_value) );
					
					}
			}
			
			elseif($str[0] == "name"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
		
					if( isset( $element ) ) {
		
						$element->sendKeys( array($type_value) );
					
					}
			}	
			
			elseif($str[0] == "link"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
		
					if( isset( $element ) ) {
		
						$element->sendKeys( array($type_value) );
					
					}
			}
			
			elseif($str[0] == "css"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::cssSelector, $element_name );
		
					if( isset( $element ) ) {
		
						$element->sendKeys( array($type_value) );
					
					}
			}
			
			else{
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::name, $element_name );
			
					if( isset( $element ) ) {
		
						$element->sendKeys( array($type_value) );
					
					}
					
			}
		
		}		
		
	}
	
	/**
	*
	* It is used to click the given linkText in page and load the content in that link
	*
	* It contains Minimum and Maximum waiting time to find the text
	*
	*/
	
	public function clickandLoad($element_name) {
		
		$splitvar = explode("//",$element_name );

		if($splitvar[0] == ""){
		
			$element = $this->webdriver->findElementBy(LocatorStrategy::xpath, $element_name);
			
			$this->assertNotNull($element);
			
			$element->click();
		}
	
		else{
		
			$str = explode("=",$element_name);
			
			if($str[0] == "id"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
		
					$this->assertNotNull($element);
			
			$element->click();
			
			}
			
			elseif($str[0] == "name"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
		
				$this->assertNotNull($element);
			
				$element->click();
			
			}
			
			elseif($str[0] == "link"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::id, $element_name );
		
				$this->assertNotNull($element);
			
				$element->click();
			
			}
			
			elseif($str[0] == "css"){
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::cssSelector, $element_name );
		
				$this->assertNotNull($element);
			
				$element->click();
			
			}
			
			else{
			
				$element = $this->webdriver->findElementBy(LocatorStrategy::linkText, $element_name );
		
				$this->assertNotNull($element);
			
				$element->click();
			
			}
			
		}
		
	}
	
	/**
	*
	* It is used to click submit button
	*
	*/
	
	public function submit($element_name,$testcases) {	
	
		$element = $this->getElement( $element_name,$testcases);
			
			if( isset( $element ) ) {
				
				$element->submit();
			   
			}
	}
	
	/**
	*
	* It is used to Capture screenshot
	*
	* It capture the screenshot whenever the failure occurs in the script
	*
	*/
	
	public function doCreateScreenShot($file_name){ 
	
		$this->webdriver->getScreenshotAndSaveToFile(getcwd()."/"."/surefire-reports/screenshots"."/".$file_name.'.png');
		   
		$this->fail( "Failed asserting that &lt;boolean:false&gt; required is true." );
	
	}  
	
	/**
	*
	* It is used to close the window whenever the testcases completes the flow
	*
	*/
	
	public function tearDown(){ 
	
		$this->webdriver->close();
    
	}
	
	 public function focusFrame($frameId){
        $this->webdriver->focusFrame($frameId);
    }
	
	/**
	*
	* It is used to clear the text field
	*
	*/
	
	public function clear( $element_name,$testcases ) {
	
        $element = $this->getElement( $element_name,$testcases );
		
        if( $element ) {
		
            $element->clear();
        }
    }
	
	/**
	*
	* It is used to Select the option in the drop down value
	*
	*/
	
	public function select( $element_name, $option_text,$testcases ) {
	
        $element = $this->getElement( $element_name,$testcases );
		
        $option = $element->findOptionElementByText( $option_text );
		
        $option->click();
    }
	
	/**
	*
	* It is used to accept the pop-up alert not for the pop-up "window".It's equal to clicking "OK" button
	*
	*/
	
	public function acceptAlert() {
	
	$this->webdriver->acceptAlert();
	
	}
	
	/**
	*
	* It is used to discard the pop-up "alert" not for the pop-up "window".It's equal to clicking "Cancel" button
	*
	*/
	
	public function discardAlert() {
	
	$this->webdriver->dismissAlert();
	
	}
	
	/**
	*
	* It is used to Close the browser window
	*
	*/
	
	public function closeWindow(){
	
	$this->webdriver->closeWindow();
	}
	
}
?>