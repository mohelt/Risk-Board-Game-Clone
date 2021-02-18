package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameDataTest {

	@Test
	void testConstants() {
		assertEquals(GameData.NUM_PLAYERS, 2);
		assertEquals(GameData.NUM_NEUTRALS, 4);
		assertEquals(GameData.NUM_COUNTRIES, 42);
		assertEquals(GameData.NUM_CARDS, 42);
		assertEquals(GameData.INIT_COUNTRIES_PLAYER, 9);
		assertEquals(GameData.INIT_COUNTRIES_NEUTRAL, 6);
		assertEquals(GameData.PLAYERS_NUM_ARMIES, 36);
		assertEquals(GameData.NEUTRALS_NUM_ARMIES, 24);		
	}
}
