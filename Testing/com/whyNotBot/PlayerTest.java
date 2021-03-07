package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PlayerTest {
	
	Player p1 = new Player(1, "PlayerName", 5);
	
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
}
