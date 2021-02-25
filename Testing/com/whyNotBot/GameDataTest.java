package com.whyNotBot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class GameDataTest {
	String[] array= {
			"Ontario","Quebec","NW Territory","Alberta","Greenland","E United States","W United States","Central America","Alaska",
			"Great Britain","W Europe","S Europe","Ukraine","N Europe","Iceland","Scandinavia",
			"Afghanistan","India","Middle East","Japan","Ural","Yakutsk","Kamchatka","Siam","Irkutsk","Siberia","Mongolia","China",
			"E Australia","New Guinea","W Australia","Indonesia",
			"Venezuela","Peru","Brazil","Argentina",
			"Congo","N Africa","S Africa","Egypt","E Africa","Madagascar"}; 
	 String[] array2 = {
				"Ont","Que","NW Ter","Alb","Gre","E US","W US","C Amer","Ala",
				"GB","W E","S E","Ukr","N E","Ice","Scan",
				"Afg","Ind","M East","Jap","Ur","Yak","Kam","Si","Irk","Sib","Mong","Chin",
				"E Aus","New G","W Aus","Indon",
				"Ven","Per","Bra","Arg",
				"Con","N Af","S Af","Egy","E Af","Mad"};
	@Test
	void testConstants() {
		
		assertEquals(GameData.NUM_PLAYERS, 2);
		assertEquals(GameData.NUM_NEUTRALS, 4);
		assertEquals(GameData.NUM_COUNTRIES, 42);
		assertEquals(GameData.INIT_COUNTRIES_PLAYER, 9);
		assertEquals(GameData.INIT_COUNTRIES_NEUTRAL, 6);
		assertEquals(GameData.INIT_UNITS_PLAYER, 36);
		assertEquals(GameData.INIT_UNITS_NEUTRAL, 24);	
		 
		 for(int i =0; i<GameData.NUM_COUNTRIES;i++) {
			 assertEquals(GameData.COUNTRY_NAMES[i],array[i]);	
		 }
//		 for(int i =0; i<GameData.NUM_COUNTRIES;i++) {
//			 assertEquals(GameData.COUNTRY_NAMES_SHORT[i],array2[i]);	
//		 }
	}
}
