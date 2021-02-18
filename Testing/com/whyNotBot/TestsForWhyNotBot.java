package com.whyNotBot;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;

import org.junit.jupiter.api.Test;

class TestsForWhyNotBot {

	@Test
	void testingTheJTextFields() {
		// testing the textFields to see if they change when set like in the program
		JTextField textField = new JTextField("Player One Name: ");
		assertEquals(textField.getText(),"Player One Name: ");
		textField.setText("Awaiting........ ");  
		assertEquals(textField.getText(),"Awaiting........ ");
		
		
	}
	@Test
	void testingTheJLabel() {
		// testing the JLabel to see if they change when set like in the program
		
		JLabel welcome = new JLabel("");
		
		welcome.setText("Please Enter name for Player 1 in the text box at the bottom");
		assertEquals(welcome.getText(),"Please Enter name for Player 1 in the text box at the bottom");
		welcome.setText("Please Enter name for Player 2 in the text box at the bottom");
		assertEquals(welcome.getText(),"Please Enter name for Player 2 in the text box at the bottom");
	}

	@Test
	void testingTheJFrames() {
		// testing the JFrames to see if they become visible when set
		
		// title of frame
		JFrame frame = new JFrame("Risk");
		JFrame frameSplash=new JFrame("Splash Screen");
		frame.setVisible(true);
		assertEquals(frame.isVisible(),true);
		frameSplash.setVisible(true);
		assertEquals(frame.isVisible(),true);
	}
}

