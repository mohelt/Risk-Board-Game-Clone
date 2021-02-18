package com.whyNotBot;
import java.util.Random;

public class Cards {
	private String[] cardDeck = new String [GameData.NUM_CARDS];
	Cards() {
		for(int i =0;i<GameData.NUM_CARDS;i++) {
			if(i % 3 ==0) {
				cardDeck[i] = GameData.COUNTRY_NAMES[i] + " Artillary Card";
			}else if(i % 3 ==1) {
				cardDeck[i] = GameData.COUNTRY_NAMES[i] + " Calvalry Card";
			}else if(i % 3 ==2) {
				cardDeck[i] = GameData.COUNTRY_NAMES[i] + " Infantry Card";
			}
		}
	}
	
	public String drawCard() {
		  Random random = new Random();
		  //instance of random class
	      int upperBound = 42;
	        //generate random values from 0-41
	      int intRandom = random.nextInt(upperBound); 
	      return cardDeck[intRandom];
	}
}
