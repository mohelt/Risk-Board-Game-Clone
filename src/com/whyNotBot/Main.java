package com.whyNotBot;

public class Main {

	public static void main (String args[]) {	

		//Instance variables

		//loads the splashscreen
		new SplashScreen();
		//loads the boards
		Board board = new Board();
		//loads the cards for drawing later
		Cards cards = new Cards();
		//loads the ui with the board
		UI ui = new UI(board);
		//variables to handle the player identification and country Id's
		int playerId, countryId;
		//handle the names of the players
		String name;
		//handles the user input and puts it into a string
		String userInput;
		//handles the card drawn and puts it into a string
		String cardDrawn;
		//checks to see if the game is over
		boolean gameOver = false;
		//checks if the country matches a name that can be accepted as an argument
		boolean matching_country = false;
		//variable in charge of whos turn it is
		int playerTurn;
		//player 1 turn is even, player 2 turn is odd

		// display blank board
		ui.displayMap();

		// gets player names
		for (playerId=0; playerId<GameData.NUM_PLAYERS; playerId++) {
			ui.displayString("Enter the name of player " + (playerId+1));
			name = ui.getCommand();
			ui.displayString("> " + name);
		}

		// add units for regular players
		countryId = 0;
		for (playerId=0; playerId<GameData.NUM_PLAYERS; playerId++) {
			for (int i=0; i<GameData.INIT_COUNTRIES_PLAYER; i++) {
				board.addUnits(countryId, playerId, 1);
				countryId++;
			}
		}
		// add units for neutral players
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
		do {
			die1.roll(6);
			die2.roll(6);
			// Continue to roll until numbers are different
		} while (die1.rolledNum == die2.rolledNum);

		// Display that player with higher roll
		ui.displayString(die1.compareTo(die2));

		//gets the player turn after rolling the dice
		playerTurn =die1.getPlayerTurn(die2);

		//lets users know what colours they are, which is a  requirement of the assignment
		ui.displayString("> Player One is Red, Player 2 is Blue");

		//while the user didnt input draw card, tell him to input draw card
		do {
			ui.displayString("Type 'draw card' to draw a card");

			//get the command
			userInput = ui.getCommand();
			ui.displayString("> " + userInput);
		} while (!(userInput.equals("draw card")));

		//variable to hold what card was drawn
		cardDrawn =cards.drawCard();

		//text to show card being drawn
		ui.displayString("> " + "Drawing card...");
		ui.displayString("> " + "You drew " + cardDrawn);

		//pop up image showing the card
		ui.displayCardDrawn("/image/countries/"+cardDrawn +".jpg");

		//let user know that armies will be assigned after users finish assigning armies
		ui.displayString("> " + " Neutral armies will be assigned after both users finish assigning armies");

		//loop to let the game be played, while game over is false game continues until all players asssign armies
		while(gameOver == false) {

			//switch statement to check whos player turn it is
			switch(playerTurn % 2) {

			//if its player ones turn
			case 0:
				while(playerTurn % 2 == 0) {
					
					//while its player ones turn tell him to place armies on a territory
					ui.displayString("> Player 2 turn choose a territory (shortname/longname)  to place 3 units on: ");
					
					//recieve the input and store it in user input
					userInput = ui.getCommand();
					
					//for loop which loops through all countries to see which one the user typed
					for(int i=0;i<GameData.NUM_COUNTRIES;i++) {
						
						//checks if the user input is equal to a short name/ long name
						if(userInput.equals(GameData.COUNTRY_NAMES[i]) ||userInput.equals(GameData.COUNTRY_NAMES_SHORT[i])) {
							
							//displays user input
							ui.displayString("> " +userInput);
							
							//checks to see if input matches a country
							matching_country = true;
							

							//checks to see if country is ones own territory
							if(board.getOccupier(i)== 1) {
								
								//checks to see if users armies are enough to add three
								if(board.getPlayerArmiesNumber(1) >= 3) {

									//adds armies to users territory
									ui.displayString("> Player 2 Added 3 Units to "+ GameData.COUNTRY_NAMES[i]);
									board.addUnits(i, 1, 3);
								}
								else {
									//else don't add because there aren't enough armies
									ui.displayString("> Cannot add, not enough armies");
									gameOver = true;
								}

								//display how many armies left
								ui.displayString("> Armies left: "+board.getPlayerArmies(1));
								
								//update the map
								ui.displayMap();
								
								//end players turn
								playerTurn++;
							}else {
								//else tell the user that this isnt his territory
								ui.displayString("> Cannot add units to this country as it it not your territory! ");
								ui.displayString("Your Territory is Europe (Blue)!");

							}
						}
					}
					//display an error if the user types an unknown value
					if(matching_country==false) {
						ui.displayString("---> '" + userInput + "' not recognised, please check spelling and ensure correct case.");
					}
					else {
						
						//else matching country is false
						matching_country=false;
					}
				}
				break;
				//if its player twos turn
			case 1:
				while(playerTurn % 2 == 1) {
					
					//while its player ones turn tell him to place armies on a territory
					ui.displayString("> Player 1 turn choose a territory (shortname/longname) to place 3 units on: ");
					
					//recieve the input and store it in user input
					userInput = ui.getCommand();
					
					//for loop which loops through all countries to see which one the user typed
					for(int i=0;i<GameData.NUM_COUNTRIES;i++) {

						//checks if the user input is equal to a short name/ long name
						if(userInput.equals(GameData.COUNTRY_NAMES[i]) ||userInput.equals(GameData.COUNTRY_NAMES_SHORT[i])) {
							
							//displays user input
							ui.displayString("> " +userInput);
							
							//checks to see if input matches a country
							matching_country = true;
							
							//checks to see if country is ones own territory
							if(board.getOccupier(i)== 0) {
								

								//checks to see if users armies are enough to add three
								if(board.getPlayerArmiesNumber(0) >= 3) {
									
									//adds armies to users territory
									ui.displayString("> Player 1 Added 3 Units to "+ GameData.COUNTRY_NAMES[i]);
									board.addUnits(i, 0, 3);
								}
								else {

									//else don't add because there aren't enough armies
									ui.displayString("> Cannot add, not enough armies");
									gameOver = true;
								}
								
								//display how many armies left
								ui.displayString("> Armies left: " + board.getPlayerArmies(0));
								
								//update the map
								ui.displayMap();
								
								//end players turn
								playerTurn++;
							}else {

								//else tell the user that this isnt his territory
								ui.displayString("> Cannot add units to this country as it it not your territory! ");
								ui.displayString("Your Territory is Nothern America (Red)!");

							}
						}
					}
					//display an error if the user types an unknown value
					if(matching_country==false) {
						ui.displayString("---> '" + userInput + "' not recognised, please check spelling and ensure correct case.");
					}
					else {

						//else matching country is false
						matching_country=false;
					}
				}
				break;
				//default value is that the player is unknown
			default: ui.displayString("> Error: Unknown player turn ");
			}
		}
		//initial country value after player one and player two territories
		countryId = 18;
		
		//assigning neutral armies
		ui.displayString("> Neutral armies assigned. ");
		
		//for playerid is first neutral player
		for (playerId =2; playerId<GameData.NUM_PLAYERS_PLUS_NEUTRALS; playerId++) {
			for (int i=0; i<GameData.INIT_COUNTRIES_NEUTRAL; i++) {
				
				//assign the armies for the neutral players
				board.addUnits(countryId, playerId, 4);
				countryId++;
			}
		}
		return;
	}
}

