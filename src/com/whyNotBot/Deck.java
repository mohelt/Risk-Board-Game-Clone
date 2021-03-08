package com.whyNotBot;
import java.util.ArrayList;
//Team Members:
//Mohamed Eltayeb Student Number:19349633
//Cian O'Reilly Student Number:19394833
//Tom Higgins Student Number: 19343176


public class Deck {

	public ArrayList<Card> cards;
	
	Deck () {
		int cardId;
		cards = new ArrayList<Card>();
		for (cardId=0; cardId<GameData.NUM_COUNTRIES; cardId++) {
			cards.add(new Card(cardId, GameData.COUNTRY_NAMES[cardId]));
		}
		return;
	}
	
	public Card getCard () {
		int index = (int)(Math.random() * cards.size());
		Card card = cards.remove(index);
		return card;
	}
	
	public boolean isEmpty () {
		return cards.isEmpty();
	}
	
}
