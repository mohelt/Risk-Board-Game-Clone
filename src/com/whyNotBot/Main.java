package com.whyNotBot;

public class Main {

	public static void main (String args[]) {	   
		Board board = new Board();
		UI ui = new UI(board);
		Player[] players = new Player[GameData.NUM_PLAYERS_PLUS_NEUTRALS];
		Player currPlayer, otherPlayer, defencePlayer;
		Card card;
		int playerId, otherPlayerId, numUnits, numCards, attackUnits, defenceUnits, countryId, attackCountryId, defenceCountryId;
		String name;
		
		ui.displayString("ENTER PLAYER NAMES");
		ui.displayMap();
		for (playerId=0; playerId<GameData.NUM_PLAYERS_PLUS_NEUTRALS; playerId++) {
			if (playerId < GameData.NUM_PLAYERS) {
				name = ui.inputName(playerId);
			} else {
				name = "Neutral " + (playerId - GameData.NUM_PLAYERS + 1);
				ui.displayName(playerId,name);
			}
			players[playerId] = new Player (playerId, name, 0);
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
			for (int c=0; c<numCards; c++) {
				card = deck.getCard();
				ui.displayCardDraw(currPlayer, card);
				board.addUnits(card, currPlayer, 1);
			}
		}
		ui.displayMap();

		Deck deck2 = new Deck();
		
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
		/*for (int r=0; r<2*GameData.NUM_REINFORCE_ROUNDS; r++) {
			ui.displayReinforcements(currPlayer, 3);
			currPlayer.addUnits(3);
			do {
				ui.inputReinforcement(currPlayer);
				currPlayer.subtractUnits(ui.getNumUnits());
				board.addUnits(ui.getCountryId(), currPlayer, ui.getNumUnits());
				ui.displayMap();
			} while (currPlayer.getNumUnits() > 0);
			ui.displayMap();
			for (int p=GameData.NUM_PLAYERS; p<GameData.NUM_PLAYERS_PLUS_NEUTRALS; p++) {
				ui.inputPlacement(currPlayer, players[p]);
				countryId = ui.getCountryId();
				board.addUnits(countryId, players[p], 1);
				ui.displayMap();
			}
			playerId = (++playerId)%GameData.NUM_PLAYERS;
			currPlayer = players[playerId];
		}*/

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
		do {
			otherPlayerId = (playerId+1)%GameData.NUM_PLAYERS;
			otherPlayer = players[otherPlayerId];

			if(currPlayer.hasSetOfCards()){
				ui.inputExchange(currPlayer);
			}
			ui.displayAllCards(currPlayer);
			// 1. Reinforcements
			numUnits = board.calcReinforcements(currPlayer);
			currPlayer.addUnits(numUnits);
			ui.displayReinforcements(currPlayer, numUnits);
			do {
				ui.inputReinforcement(currPlayer);
				currPlayer.subtractUnits(ui.getNumUnits());
				board.addUnits(ui.getCountryId(),currPlayer,ui.getNumUnits());
				ui.displayMap();
			} while (currPlayer.getNumUnits() > 0);

			// 2. Combat
			do {
				ui.inputBattle(currPlayer);
				if (!ui.isTurnEnded()) {
					attackUnits = ui.getNumUnits();
					attackCountryId = ui.getFromCountryId();
					defenceCountryId = ui.getToCountryId();
					defencePlayer = players[board.getOccupier(defenceCountryId)];
					if (board.getNumUnits(defenceCountryId) > 1) {
						ui.inputDefence(otherPlayer,defenceCountryId);
						defenceUnits = ui.getNumUnits();
					} else {
						defenceUnits = 1;
					}
					board.calcBattle(currPlayer,defencePlayer,attackCountryId,defenceCountryId,attackUnits,defenceUnits);
					ui.displayBattle(currPlayer,defencePlayer);
					ui.displayMap();
					if ( board.isInvasionSuccess() && (board.getNumUnits(attackCountryId) > 1) ) {
						ui.inputMoveIn(currPlayer,attackCountryId);
						board.subtractUnits(attackCountryId, ui.getNumUnits());
						board.addUnits(defenceCountryId, currPlayer, ui.getNumUnits());
						ui.displayMap();
					}
				}

			} while (!ui.isTurnEnded() && !board.isGameOver());

			if(!board.isGameOver() && board.isInvasionSuccess()){
				// Functionality to give player territory card after winning battle
				Card chosenCard =deck2.getCard();
				deck.removeCard(chosenCard); // Remove chosen card from Deck
				currPlayer.addCard(chosenCard); // Give chosen card from deck to attacking player
				ui.displayCardAfterAttack(currPlayer,chosenCard);
			}

			// 3. Fortify
			if (!board.isGameOver()) {
				ui.inputFortify(currPlayer);
				if (!ui.isTurnEnded()) {
					board.subtractUnits(ui.getFromCountryId(), ui.getNumUnits());
					board.addUnits(ui.getToCountryId(), currPlayer, ui.getNumUnits());
					ui.displayMap();
				}
			}

			playerId = (playerId+1)%GameData.NUM_PLAYERS;
			currPlayer = players[playerId];			

		} while (!board.isGameOver());
		
		ui.displayWinner(players[board.getWinner()]);
		ui.displayString("GAME OVER");
		
		return;
	}

}
