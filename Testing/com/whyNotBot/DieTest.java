package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DieTest {
	
	Die die1 = new Die(1);
	Die die2 = new Die(2);
	@Test
	void testDie() {
		// roll both dice
			die1.roll(6);
			die2.roll(6);
			assertEquals(die1.diceNum,1);
			assertEquals(die2.diceNum,2);
			
	}

	@Test
	void testRoll() {
		Die die1 = new Die(1);
			die1.roll(6);
			boolean testDieLessThan7 = die1.roll(6)<7;
			boolean testDieGreaterThan0 = die1.roll(6)>0;
			assertEquals(testDieLessThan7 ,true);
			assertEquals(testDieGreaterThan0 ,true);
	}

	@Test
	void testGetPlayerTurn() {
		die1.rolledNum = 6;
		die2.rolledNum = 5;
		die1.getPlayerTurn(die2);
		assertEquals(die1.playerTurn,1);

		die1.rolledNum = 4;
		die2.rolledNum = 6;
		die1.getPlayerTurn(die2);
		assertEquals(die1.playerTurn,2);
	}

	@Test
	void testCompareTo() {
		die1.rolledNum = 6;
		die2.rolledNum = 5;
		String string = "Player One rolled a " + die1.rolledNum +". " +"Player Two rolled a " + die2.rolledNum +". " +"Player One assigns armies first.";
        
		assertEquals(string,die1.compareTo(die2));

		die1.rolledNum = 4;
		die2.rolledNum = 6;
		String string2 = "Player One rolled a " + die1.rolledNum +". " +"Player Two rolled a " + die2.rolledNum +". " +"Player Two assigns armies first.";
        
		assertEquals(string2,die1.compareTo(die2));
	}

}
