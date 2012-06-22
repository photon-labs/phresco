package com.photon.phresco;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class HelloWorld {

	public static void main(String[] args) {
 
	    Frame f = new Frame("Phresco Sample");
	    f.addWindowListener
	          (new WindowAdapter() {
	              public void windowClosing(WindowEvent e) {
	                 System.exit(0);
	                 }
	              }
	    );  
	    f.add(new Label("         HI, This is Hello World Application ...!!!       "));
	    f.setSize(250,150);
	    f.setVisible(true);
	}
}