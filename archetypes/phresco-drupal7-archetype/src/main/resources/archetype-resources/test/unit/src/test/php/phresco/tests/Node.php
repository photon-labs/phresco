<?php
/*	

Author by {phresco} QA Automation Team	

*/

require_once 'PHPUnit/Framework.php';
require_once 'DrupalBaseclass.php';


class Node extends DrupalBaseclass {
		
	 function testLastChanged(){
		$this->connect();
		$changed = node_last_changed(1);
		$this->assertEquals('1330420018', $changed);
	}  
		
	function testNodeTitle(){
		$this->connect();
		$changed = node_load(1);
		$title = node_page_title($changed);
		$this->assertEquals('Hello World', $title);
	} 
}
?>