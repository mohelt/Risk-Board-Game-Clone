package com.whyNotBot;

public class Main {

	public static void main (String args[]) {	   
		Board board = new Board();
		UI ui = new UI(board);
		Player[] players = new Player[GameData.NUM_PLAYERS_PLUS_NEUTRALS];
		Player currPlayer;
		Card card;
		int playerId, countryId, numUnits, numCards;
		String name;
//		boolean gameover = false;
	
		
		ui.displayString("ENTER PLAYER NAMES");
		ui.displayMap();
		for (playerId=0; playerId<GameData.NUM_PLAYERS_PLUS_NEUTRALS; playerId++) {
			if (playerId < GameData.NUM_PLAYERS) {
				name = ui.inputName(playerId);
				numUnits = GameData.INIT_UNITS_PLAYER;
			} else {
				name = "Neutral " + (playerId - GameData.NUM_PLAYERS + 1);
				ui.displayName(playerId,name);
				numUnits = GameData.INIT_UNITS_NEUTRAL;
			}
			players[playerId] = new Player (playerId, name, numUnits);
		}
		
		ui.displayString("\nDRAW TERRITORY CARDS FOR STARTING COUNTRIES");
		Deck deck = new Deck();
		for (playerId=0; playerId<GameData.NUM_PLAYERS_PLUS_NEUTRALS; playerId++) {
			currPlayer = players[playerId];
			if (playerId < GameData.NUM_PLAYERS) {
				numCards = GameData.INIT_COUNTRIES_PLAYER;
			} else {
				numCards = GameData.INIT_COUNTRIES_NEUTRAL;
			}
			for (int i=0; i<numCards; i++) {
				card = deck.getCard();
				ui.displayCardDraw(currPlayer, card);
				currPlayer.subtractUnits(1);
				board.addUnits(card, currPlayer, 1);
			}
		}
		ui.displayMap();
		
		ui.displayString("\nROLL DICE TO SEE WHO REINFORCES THEIR COUNTRIES FIRST");
		do {
			for (int i=0; i<GameData.NUM_PLAYERS; i++) {
				players[i].rollDice(1);
				ui.displayDice(players[i]);
			}
		} while (players[0].getDie(0) == players[1].getDie(0)); 
		if (players[0].getDie(0) > players[1].getDie(0)) {
			playerId = 0;
		} else {
			playerId = 1;
		}
		currPlayer = players[playerId];
		ui.displayRollWinner(currPlayer);
		
		ui.displayString("\nREINFORCE INITIAL COUNTRIES");
		while (currPlayer.getNumUnits() > 0) {
			ui.inputPlacement(currPlayer, currPlayer);
			countryId = ui.getCountryId();
			currPlayer.subtractUnits(3);
			board.addUnits(countryId, currPlayer, 3);
			ui.displayMap();
			for (int i=GameData.NUM_PLAYERS; i<GameData.NUM_PLAYERS_PLUS_NEUTRALS; i++) {
				ui.inputPlacement(currPlayer, players[i]);
				countryId = ui.getCountryId();
				currPlayer.subtractUnits(1);
				board.addUnits(countryId, currPlayer, 1);	
				ui.displayMap();
			}
			playerId = (++playerId)%GameData.NUM_PLAYERS;
			currPlayer = players[playerId];
		}
		
		
		ui.displayString("\nROLL DICE TO SEE WHO TAKES THE FIRST TURN");
		
		
		
		
		
		
		
		
		
		// **Cian**-> My intentions were good but it prints all jumbled up 
		// Don't delete it yet, may still use it
		
//		if(gameover = true) {
//			ui.displayString("*****    *   *     * ***** 	     ****  *     * ***** ***   ");
//			ui.displayString("*       * *  * * * * *	 		*	 * *     * *	 *  *  ");
//			ui.displayString("*  *** ***** *  *  * *****		*	 *  *   *  ***** ***   ");
//			ui.displayString("*   *  *   * *     * *         	*	 *   * *   *	 *  *  ");
//			ui.displayString("*****  *   * *     * *****    	 ****  	  *    ***** *   * ");
//			
//			ui.displayString("THE WINNER IS ...........");
//			
//		}
//		
		
		return;
	}

}
