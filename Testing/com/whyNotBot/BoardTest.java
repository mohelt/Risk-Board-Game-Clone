package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BoardTest {

	Board boardTest = new Board();
	Player player1 = new Player(1, "Player", 5);
	Player[] playerArray = new Player[1];
	Card card1 = new Card(1, "Ireland");
	int[] occupier = new int [GameData.NUM_COUNTRIES];


	@Test
	void testAddUnitsIntIntInt() {	
	    int initNumUnits = boardTest.getNumUnits(1);
	
	    boardTest.addUnits(1, 1, 1); // Invoke addUnits method
	    assertTrue(boardTest.isOccupied(1)); // Test that territory is now occupied
	  
	    // Test new number of units on territory and also that player has against initial units
	    assertEquals(boardTest.getNumUnits(1), initNumUnits + 1);
	}

	@Test
	void testAddUnitsCardPlayerInt() {
	    int initNumUnits = boardTest.getNumUnits(1);
	    
		boardTest.addUnits(card1, player1, 2); // Invoke addUnits method
		assertTrue(boardTest.isOccupied(1)); // Test that territory is now occupied
		
		// Test new number of units on territory and also that player has against initial units
		assertEquals(boardTest.getNumUnits(1),initNumUnits + 2);
	}

	@Test
	void testAddUnitsIntPlayerInt() {
	    int initNumUnits = boardTest.getNumUnits(1);
	    
		boardTest.addUnits(1, player1, 3);// Invoke addUnits method
		assertTrue(boardTest.isOccupied(1)); // Test that territory is now occupied
		
		// Test new number of units on territory and also that player has against initial units
		assertEquals(boardTest.getNumUnits(1),initNumUnits + 3);
	}

	@Test
	void testCheckOccupier() {
		assertFalse(boardTest.checkOccupier(player1, 1));
	}

	@Test
	void testIsOccupied() {
		assertFalse(boardTest.isOccupied(1));
	}

	@Test
	void testGetOccupier() {
		  assertEquals(boardTest.getOccupier(1), 0);
	}

	@Test
	void testGetNumUnits() {
		assertEquals(boardTest.getNumUnits(1), 0);
	}

	/*@Test
	void testGetNumTerritories() {
		assertEquals(boardTest.getNumTerritories(1), 0);
	}

//	@Test
//	void testNumOfArmies() {
//		assertEquals(boardTest.numOfArmies(playerArray, 1), 5);
//	}

	@Test
	void testOwnsNorthAmerica() {
		assertEquals(boardTest.ownsNorthAmerica(1), 0);
	}

	@Test
	void testOwnsEurope() {
		assertEquals(boardTest.ownsEurope(1), 0);
	}

	@Test
	void testOwnsAsia() {
		assertEquals(boardTest.ownsAsia(1), 0);
	}

	@Test
	void testOwnsAustralia() {
		assertEquals(boardTest.ownsAustralia(1), 0);
	}

	@Test
	void testOwnsSouthAmerica() {
		assertEquals(boardTest.ownsSouthAmerica(1), 0);
	}

	@Test
	void testOwnsAfrica() {
		assertEquals(boardTest.ownsAfrica(1), 0);
	}*/

}



