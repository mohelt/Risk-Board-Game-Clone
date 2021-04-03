package com.whyNotBot;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	protected ArrayList<Card> cards;
	
	Deck () {
		int cardId;
		cards = new ArrayList<Card>();
		for (cardId=0; cardId<GameData.NUM_COUNTRIES; cardId++) {
			cards.add(new Card(cardId, GameData.COUNTRY_NAMES[cardId]));
		}
		Collections.shuffle(cards);
	}

	public Card getCard () {
		int index = (int)(Math.random() * cards.size());  
		Card card = cards.remove(index);
		return card;
	}

	public Card takeNextCard(){
		return cards.remove(0);
	}

	public void removeCard(Card card){
		cards.remove(card);
	}
	
	public boolean isEmpty () {
		return cards.isEmpty();
	}
	
}
