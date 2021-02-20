package com.whyNotBot;
//Team Members:
//Mohamed Eltayeb Student Number:19349633
//Cian O'Reilly Student Number:19394833
//Tom Higgins Student Number: 19343176
import java.util.Random;

public class Cards {
	
	//instance variables
	
	//used to store the string names of the cards
	public String[] cardDeck = new String [GameData.NUM_CARDS];
	
	//used to store if a card has already been drawn
	public boolean[] cardDeckTaken = new boolean [GameData.NUM_CARDS];
	
	//new random variable used to assign a random card
    Random random = new Random();
    
    //generate random values from 0-41
	public int intRandom = random.nextInt(42); 
	
	Cards() {
		//makes all cards not drawn at the start
		for(int i =0;i<GameData.NUM_CARDS;i++) {
			cardDeckTaken[i] = false;
			}
		
		//assigns the variable names to the cardDeck array
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
		//recursively calls another card if a card has already been drawn
	      if( cardDeckTaken[intRandom] == true) {
		      return drawCard();
	      }else {
	    	  //makes a card value true, as in it has been drawn
	    	  cardDeckTaken[intRandom] = true;
	    	  
	    	  //returns random card
		      return cardDeck[intRandom];
	      }
	}
}
