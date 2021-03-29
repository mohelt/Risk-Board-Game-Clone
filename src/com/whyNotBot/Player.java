package com.whyNotBot;

import java.util.ArrayList;
import java.util.Collections;

public class Player {
	
	private int id;
	private String name;
	private int numUnits;
	private ArrayList<Integer> dice = new ArrayList<Integer>();
	private ArrayList<Card> obtainedCards = new ArrayList<Card>();
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
	public void addCard(int cardId){
		obtainedCards.add(new Card(cardId, GameData.COUNTRY_NAMES[cardId]));
	}
}
