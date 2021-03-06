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
		boolean gameOver = false;
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
		while(gameOver==false) {
			while(board.getNumTerritories(playerId) == 0) {
				playerId++;
			}
			currPlayer = players[playerId];
			currPlayer.addUnits(board.numOfArmies(players, playerId));
			ui.displayString(ui.makeLongName(currPlayer)+": gets "+ board.numOfArmies(players, playerId) + " reinforcements.");
			while(currPlayer.getNumUnits()> 0) {
				ui.displayString(ui.makeLongName(currPlayer)+": Number Of Armies " + currPlayer.getNumUnits());
				ui.reinforcementsPlacement(currPlayer); // asks the player the country and the number of units they requested to reinforce with

				numUnits = 0;
				numUnits += ui.getReinforcementsPlacementUnits(); // the number of units they requested to reinforce the country with

				countryId = ui.getCountryId(); //gets country id entered
				board.addUnits(countryId, currPlayer, numUnits); //adds units to board
				currPlayer.subtractUnits(numUnits); //subtract from current units
				ui.displayMap();
			}
			if(playerId == 0 ||playerId == 1) {
				ui.attackOrSkip(currPlayer,players,playerId);
			}

			ui.fortify(players[playerId],playerId);
			playerId=(playerId +1) % 6;
			
			boolean[][] gameWinner = new boolean[2][GameData.NUM_COUNTRIES];
			for(int i=0;i<2;i++) {
				for(int j=0;j<GameData.NUM_COUNTRIES;j++) {
					gameWinner[i][j] = false;
				}
			}
			for(int i=0;i<2;i++) {
				for(int j=0;j<GameData.NUM_COUNTRIES;j++) {
					if(board.getOccupier(j) == i) {
						gameWinner[i][j] = true;
					}
				}
			}
			if(areAllTrue(gameWinner[0])) {
				ui.displayString("THE WINNER IS "+ players[0].getName());
				gameOver=true;
			}
			if(areAllTrue(gameWinner[1])) {
				ui.displayString("THE WINNER IS "+ players[1].getName());
				gameOver =true;
			}
			// Checks if the game is over
			for(int playerLoop = 0; playerLoop<GameData.NUM_PLAYERS; playerLoop++) {
				if(board.getNumTerritories(playerLoop) == 0) { // if this player has no territories 
					ui.gameOverMessage(players[(playerLoop + 1) % 2]); //the other player is the winner
					gameOver=true;
				}
			}
		}

		
		
		
		return;
	}
	public static boolean areAllTrue(boolean[] array)
	{
	    for(boolean b : array) {
	    	if(!b) {
	    		return false;
	    	}
	    }
	    return true;
	}
}
