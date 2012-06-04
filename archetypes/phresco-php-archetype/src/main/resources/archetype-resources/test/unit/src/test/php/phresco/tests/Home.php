<?php
/*	

Author by {phresco} QA Automation Team	

*/

require_once 'PHPUnit/Framework.php';
require_once str_replace('.','/','phresco/tests').'/basescreen.php';

class Home extends PHPUnit_Framework_TestCase {
    function testString() {
    	$basescreen = new basescreen();
        $this->assertEquals('HelloWorld', $basescreen->checkString());
    }
}
?>