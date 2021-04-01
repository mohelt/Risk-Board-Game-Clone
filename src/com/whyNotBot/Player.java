package com.whyNotBot;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	
	private int id;
	private String name;
	private int numUnits;
	private ArrayList<Integer> dice = new ArrayList<Integer>();
	private ArrayList<Card> obtainedCards = new ArrayList<Card>();
	private int numCards = 0, infantryCards = 0, cavalryCards = 0, artilleryCards = 0;
	private int battleLoss = 0;
	
	Player (int inId, String inName, int inNumUnits) {
		id = inId;
		name = inName;
		numUnits = inNumUnits;
		return;
	}
	
	public void rollDice (int numDice) {
		dice.clear();
		for (int j=0; j<numDice; j++) {
				dice.add(1 + (int)(Math.random() * 6));   
		}
		Collections.sort(dice, Collections.reverseOrder());
		return;
	}

	public void addUnits (int inNum) {
		numUnits = numUnits + inNum;
		return;
	}
	
	public void subtractUnits (int inNum) {
		numUnits = numUnits - inNum;
		return;
	}
	
	public int getId () {
		return id;
	}
	
	public String getName () {
		return name;
	}
	
	public int getNumUnits () {
		return numUnits;
	}

	public ArrayList<Integer> getDice () {
		return dice;
	}
	
	public int getDie (int dieId) {
		return dice.get(dieId);
	}
	
	public void resetBattleLoss () {
		battleLoss = 0;
		return;
	}
	
	public void addBattleLoss () {
		battleLoss++;
		return;
	}
	
	public int getBattleLoss () {
		return battleLoss;
	}

	// add obtained card from battle to players cards
	public void addCard(Card card){
		obtainedCards.add(card);
		numCards++;
		switch (card.getUnitTypeID()){
			case 0:
				infantryCards++;
				break;
			case 1:
				cavalryCards++;
				break;
			case 2:
				artilleryCards++;
				break;
		}
	}

	// check if player has more than three of the same card
	// return true if 3 or more of same card have been obtained
	// return false if not
	
	//Cian: I commented out the add units because I think it works differently in the game rules
	
	public boolean hasReinforcements(){
		if(infantryCards >= 3){
//			addUnits(infantryCards / 3); // increase units by 1 per 3 infantry cards acquired
			return true;
		}

		else if(cavalryCards >= 3){
//			addUnits(cavalryCards / 3 * 5); // increase units by 5 per 3 cavalry cards acquired
			return true;
		}

		else if(artilleryCards >= 3 ){
//			addUnits(artilleryCards / 3 * 10); // increase units by 10 per 3 artillery cards acquired
			return true;
		}
		else {
			return false;
		}
	}

	public int calcCardsToArmiesTrade() {
		int total_armies = 0;
		
		// I added cardSetsTradedIn variable in board.java 
		
		//Taken from https://www.ultraboardgames.com/risk/game-rules.php
		
//		At the beginning of subsequent turns, you may trade in matched sets of cards and take 
//		additional armies based on the total number of sets anyone has traded in so far. 
//
//		After the sixth set has been traded in, each additional set is worth 5 more armies. 
//		Example: If you trade in the seventh set, you get 20 armies; 
//		if you trade in the eighth, you get 25 armies, and so on.
//
//		"First" and "second" set, etc., refer to sets traded in by anyone during the game. 
//		Thus, if you trade in the 3rd set in the game, you receive 8 armies, 
//		even if it is the first set you have traded in.
//
//		Occupied territories: If any of the 3 cards you trade in shows the picture of a territory you occupy, 
//		you receive 2 extra armies. You must place both those armies on to that particular territory.
		
		return total_armies;
	}
	
	public int getInfantryCards(){return infantryCards;}
	public int getCavalryCards(){return cavalryCards;}
	public int getArtilleryCards(){return artilleryCards;}

	public Card getFirstCard(){
		return obtainedCards.get(0);
	}
	public Card getCard(int cardId){
		return obtainedCards.get(cardId);
	}

	public Card getLastCard(){
		return obtainedCards.get(obtainedCards.size() - 1);
	}
}
