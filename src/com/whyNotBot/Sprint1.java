package com.whyNotBot;

public class Sprint1 {

	public static void main (String args[]) {	   
		Board board = new Board();
		Cards cards = new Cards();
		new SplashScreen();
		UI ui = new UI(board);
		int playerId, countryId;
		String name;
		String userInput;
		boolean gameOver = false;
		int playerTurn;
		//player 1 turn is odd, player 2 turn is odd


		// display blank board
		ui.displayMap();

		// get player names
		for (playerId=0; playerId<GameData.NUM_PLAYERS; playerId++) {
			ui.displayString("Enter the name of player " + (playerId+1));
			name = ui.getCommand();
			ui.displayString("> " + name);
		}

		// add units
		countryId = 0;
		for (playerId=0; playerId<GameData.NUM_PLAYERS; playerId++) {
			for (int i=0; i<GameData.INIT_COUNTRIES_PLAYER; i++) {
				board.addUnits(countryId, playerId, 1);
				countryId++;
			}
		}
		for (; playerId<GameData.NUM_PLAYERS_PLUS_NEUTRALS; playerId++) {
			for (int i=0; i<GameData.INIT_COUNTRIES_NEUTRAL; i++) {
				board.addUnits(countryId, playerId, 1);
				countryId++;
			}
		}		

		// display map
		ui.displayMap();

		// Create two dice for game
		Die die1 = new Die(1);
		Die die2 = new Die(2);
		// roll both dice
		// Continue to roll until numbers are different
		do {
			die1.roll(6);
			die2.roll(6);
		} while (die1.rolledNum == die2.rolledNum);

		// Display that player with higher roll
		ui.displayString(die1.compareTo(die2));

		playerTurn =die1.getPlayerTurn(die2);

		do {
			ui.displayString("Type 'draw card' to draw a card");
			userInput = ui.getCommand();
			ui.displayString("> " + userInput);
		} while (!(userInput.equals("draw card")));

		ui.displayString("> " + "Drawing card...");
		ui.displayString("> " + "You drew " + cards.drawCard());
		while(gameOver == false) {
			switch(playerTurn % 2) {
			case 0:
				while(playerTurn % 2 == 0) {
					ui.displayString("> Player 2 turn choose a territory (shortname/longname)  to place 3 units on: ");
					ui.displayStringCountryNames();
					userInput = ui.getCommand();
					for(int i=0;i<GameData.NUM_COUNTRIES;i++) {
						if(userInput.equals(GameData.COUNTRY_NAMES[i]) ||userInput.equals(GameData.COUNTRY_NAMES_SHORT[i])) {
							ui.displayString("> " +userInput);
							if(board.getOccupier(i)== 1) {
								if(board.getPlayerArmiesNumber(1) >= 3) {
									ui.displayString("> Player 2 Added 3 Units to "+ GameData.COUNTRY_NAMES[i]);
									board.addUnits(i, 1, 3);
								}
								else {
									ui.displayString("> Cannot add, not enough armies");
								}
								ui.displayString("> Armies left: "+board.getPlayerArmies(1));
								ui.displayMap();
								playerTurn++;
							}else {
								ui.displayString("> Cannot add units to this country as it it not your territory! ");
							}
						}
					}
				}
				break;
			case 1:
				while(playerTurn % 2 == 1) {
					ui.displayString("> Player 1 turn choose a territory (shortname/longname) to place 3 units on: ");
					ui.displayStringCountryNames();
					userInput = ui.getCommand();
					for(int i=0;i<GameData.NUM_COUNTRIES;i++) {
						if(userInput.equals(GameData.COUNTRY_NAMES[i]) ||userInput.equals(GameData.COUNTRY_NAMES_SHORT[i])) {
							ui.displayString("> " +userInput);
							if(board.getOccupier(i)== 0) {
								if(board.getPlayerArmiesNumber(0) >= 3) {
									ui.displayString("> Player 1 Added 3 Units to "+ GameData.COUNTRY_NAMES[i]);
									board.addUnits(i, 0, 3);
								}
								else {
									ui.displayString("> Cannot add, not enough armies");
								}
								ui.displayString("> Armies left: " + board.getPlayerArmies(0));
								ui.displayMap();
								playerTurn++;
							}else {
								ui.displayString("> Cannot add units to this country as it it not your territory! ");
							}
						}
					}
				}
				break;
			default: ui.displayString("> Error: Unknown player turn ");
			}
		}
		return;
	}
}

