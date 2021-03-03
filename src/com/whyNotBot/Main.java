package com.whyNotBot;

public class Main {

	public static void main (String args[]) {	   
		Board board = new Board();
		UI ui = new UI(board);
		Player[] players = new Player[GameData.NUM_PLAYERS_PLUS_NEUTRALS];
		Player currPlayer;
		Card card;
		int playerId, countryId, numUnits = 0, numCards;
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
		
		
		ui.displayString("\nSTART TURNS");
		
		///////////////////////////////////////////////////////////////////////////////
			// NEED TO CALCULATE THE NUMBER OF NEW ARMIES YOU CAN PLACE ON A TERRITORY
		
			// According to risk rules:
//		At the beginning of each turn, calculate how many new armies you can add to your territories based on:
//
//			The number of territories you occupy.
//			The value of the continents you control.
//			The value of the matched sets of RISK cards you trade in.
//			The specific territory pictured on a traded-in card.
//
//			Territories
//			At the beginning of every turn (including your first), count the number of territories you currently occupy, then divide the total by three (ignore any fraction).
//
//			The answer is the number of armies you receive. Place the new armies on any territory you already occupy.
//
//			Example: 11 territories = 3 armies, 14 territories = 4 armies, 17 territories = 5 armies. You will always receive at least 3 armies on a turn, even if you occupy fewer than 9 territories.
//
//
//			Continents
//			In addition, at the beginning of your turn, you will receive armies for each continent you control. (To control a continent, you must occupy all its territories at the start of your turn).
//
//			The number of extra armies is different for each continent:
//
//			Asia: 7
//			North America: 5
//			Europe: 5
//			Africa: 3
//			South America: 2
//			Australia: 2
		///////////////////////////////////////////////////////////////////////////////
		
		currPlayer.addUnits(3); //player gets 3 units to start
		ui.reinforcementsPlacement(currPlayer); // asks the player the country and the number of units they requested to reinforce with
		
		do {
			numUnits += ui.getReinforcementsPlacementUnits(); // the number of units they requested to reinforce the country with
			
		}while(currPlayer.getNumUnits() >= numUnits); //so that the user can't try to add more than they have
		
		countryId = ui.getCountryId(); //gets country id entered
		board.addUnits(countryId, currPlayer, numUnits); //adds units to board
		currPlayer.subtractUnits(numUnits); //subtract from current units
		ui.displayMap(); 
		
		
		
		
		// **Cian**-> This prints all jumbled up 
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
