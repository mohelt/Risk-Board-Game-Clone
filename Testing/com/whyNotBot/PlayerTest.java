package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {
	
	Player p1 = new Player(1, "PlayerName", 5);
	Board board = new Board();
	@Test
	void testPlayer() {
		assertEquals(p1.getId(), 1);
		assertEquals(p1.getName(), "PlayerName");
		assertEquals(p1.getNumUnits(), 5);
	}

	@Test
	void testAddUnits() {
		p1.addUnits(3);
		assertEquals(p1.getNumUnits(), 8);
	}

	@Test
	void testSubtractUnits() {
		p1.subtractUnits(3);
		assertEquals(p1.getNumUnits(), 2);
	}

	@Test
	void testGetId() {
		assertEquals(p1.getId(), 1);
	}

	@Test
	void testGetName() {
		assertEquals(p1.getName(),"PlayerName");
	}

	@Test
	void testGetNumUnits() {
		assertEquals(p1.getNumUnits(), 5);
	}
	
	@Test
	void testGetInfantryCards() {
		assertEquals(p1.getInfantryCards(), 0);
	}
	
	@Test
	void testGetCavalryCards() {
		assertEquals(p1.getCavalryCards(), 0);
	}
	
	@Test
	void testGetArtilleryCards() {
		assertEquals(p1.getArtilleryCards(), 0);
	}
	
	@Test
	void testHasSetOfCards() {
		assertFalse(p1.hasSetOfCards());
	}
	@Test
	void testCalcCardsToArmiesTrade() {
		board.cardSetsTradedIn = 1;
		assertEquals(p1.calcCardsToArmiesTrade(), 4);
		board.cardSetsTradedIn = 2;
		assertEquals(p1.calcCardsToArmiesTrade(), 6);
		board.cardSetsTradedIn = 3;
		assertEquals(p1.calcCardsToArmiesTrade(), 8);
		board.cardSetsTradedIn = 4;
		assertEquals(p1.calcCardsToArmiesTrade(), 10);
		board.cardSetsTradedIn = 5;
		assertEquals(p1.calcCardsToArmiesTrade(), 12);
		board.cardSetsTradedIn = 6;
		assertEquals(p1.calcCardsToArmiesTrade(), 15);
		board.cardSetsTradedIn = 7;
		assertEquals(p1.calcCardsToArmiesTrade(), 20);
		board.cardSetsTradedIn = 8;
		assertEquals(p1.calcCardsToArmiesTrade(), 25);

	}
}
