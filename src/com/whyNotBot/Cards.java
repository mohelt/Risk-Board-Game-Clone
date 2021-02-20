package com.whyNotBot;
import java.util.Random;

public class Cards {
	public String[] cardDeck = new String [GameData.NUM_CARDS];
	public boolean[] cardDeckTaken = new boolean [GameData.NUM_CARDS];
    Random random = new Random();
    
    //generate random values from 0-41
	public int intRandom = random.nextInt(42); 
	
	Cards() {
		for(int i =0;i<GameData.NUM_CARDS;i++) {
			cardDeckTaken[i] = false;
			}
		for(int i =0;i<GameData.NUM_CARDS;i++) {
			if(i % 3 ==0) {
				cardDeck[i] = GameData.COUNTRY_NAMES[i] + " Artillery Card";
			}else if(i % 3 ==1) {
				cardDeck[i] = GameData.COUNTRY_NAMES[i] + " Cavalry Card";
			}else if(i % 3 ==2) {
				cardDeck[i] = GameData.COUNTRY_NAMES[i] + " Infantry Card";
			}
		}
	}
	
	public String drawCard() {
	      if( cardDeckTaken[intRandom] == true) {
		      return drawCard();
	      }else {
	    	  cardDeckTaken[intRandom] = true;
		      return cardDeck[intRandom];
	      }
	}
}
