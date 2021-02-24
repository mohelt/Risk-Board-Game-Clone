import java.util.ArrayList;

public class Deck {

	private ArrayList<Card> cards;
	
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
