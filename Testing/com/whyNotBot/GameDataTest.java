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

//public static final int NUM_NEUTRALS = 4;
//public static final int NUM_PLAYERS_PLUS_NEUTRALS = NUM_PLAYERS + NUM_NEUTRALS;
//public static final int NUM_COUNTRIES = 42;
//public static final int NUM_CARDS = 42;
//public static final int INIT_COUNTRIES_PLAYER = 9;
//public static final int INIT_COUNTRIES_NEUTRAL = 6;
//public static final int PLAYERS_NUM_ARMIES = 36;
//public static final int NEUTRALS_NUM_ARMIES = 24;