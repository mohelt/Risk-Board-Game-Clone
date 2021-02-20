package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardsTest {
	Cards card = new Cards();
	@Test
	void testForStringNames() {
		for(int i =0;i<GameData.NUM_CARDS;i++) {
			if(i % 3 ==0) {
				assertEquals(card.cardDeck[i],GameData.COUNTRY_NAMES[i] + " Artillery Card");
			}else if(i % 3 ==1) {
				assertEquals(card.cardDeck[i],GameData.COUNTRY_NAMES[i] + " Cavalry Card");
			}else if(i % 3 ==2) {
				assertEquals(card.cardDeck[i],GameData.COUNTRY_NAMES[i] + " Infantry Card");
			}
		}
	}
	@Test
	void testDrawCard() {
		boolean testGreaterThan = card.intRandom > -1;
		boolean testLessThan = card.intRandom < 42;
		assertEquals(testGreaterThan,true);

		assertEquals(testLessThan,true);
	}

}
