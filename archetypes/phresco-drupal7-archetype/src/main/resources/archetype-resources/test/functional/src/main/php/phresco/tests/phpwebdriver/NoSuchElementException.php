<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of NoSuchElementException
 *
 * @author kolec
 */
class NoSuchElementException extends WebDriverException {
    private $json_response;
    public function __construct($json_response) {
        parent::__construct("No such element exception", WebDriverResponseStatus::NoSuchElement, null);
        $this->json_response = $json_response;
    }
}
?>
