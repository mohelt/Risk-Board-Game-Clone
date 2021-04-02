package com.whyNotBot;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class UI {

	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 800;
	private static String PROMPT = "> ";

	private JFrame frame = new JFrame();
	private MapPanel mapPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	private Parse parse = new Parse();
	private Board board;
	
	UI (Board inBoard) {
		board = inBoard;
		mapPanel = new MapPanel(board);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Risk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel, BorderLayout.NORTH);
		frame.add(infoPanel, BorderLayout.CENTER);
		frame.add(commandPanel,BorderLayout.SOUTH);
		frame.setResizable(true);
		frame.setVisible(true);
		return;
	}
	
	public int getCountryId () {
		return parse.getCountryId();
	}
	
	public int getNumUnits () {
		return parse.getNumUnits();
	}	
	
	public boolean isTurnEnded() {
		return parse.isTurnEnded();
	}
	
	public int getFromCountryId () {
		return parse.getFromCountryId();
	}
	
	public int getToCountryId () {
		return parse.getToCountryId();
	}
	
	private String makeLongName (Player player) {
		return player.getName() + " (" + mapPanel.getColorName(player.getId()) + ")";
	}
	
	public void displayMap () {
		mapPanel.refresh();
		return;
	}

	public void displayString (String string) {
		infoPanel.addText(string);
		return;
	}
	
	public void displayName (int playerId, String name) {
		displayString("Neutral player " + (playerId+1) + " is " + mapPanel.getColorName(playerId));
		return;		
	}

	public void displayCardDraw (Player player, Card card) {
		displayString(makeLongName(player) + " draws the " + card.getCountryName() + " card");
		return;
	}
	
	public void displayDice (Player player) {
		displayString(makeLongName(player) + " rolls " + player.getDice() );
		return;
	}
	
	public void displayRollWinner (Player player) {
		displayString(makeLongName(player)  + " wins roll and goes first");
		return;
	}
	
	public void displayReinforcements (Player player, int numUnits) {
		displayString(makeLongName(player) + " gets " + numUnits + " reinforcements.");
		return;
	}
	
	public void displayNumUnits (Player player) {
		String message = makeLongName(player) + " has " + player.getNumUnits() + " units";
		displayString(message);
		return;
	}
	
	public void displayBattle (Player attackPlayer, Player defencePlayer) {
		String message;
		message = makeLongName(attackPlayer) + "'s roll was " + attackPlayer.getDice() + " and ";
		message += makeLongName(defencePlayer) + "'s roll was " + defencePlayer.getDice();
		displayString(message);
		if (attackPlayer.getBattleLoss()==1) {
			message = makeLongName(attackPlayer) + " loses 1 unit and ";
		} else {
			message = makeLongName(attackPlayer) + " loses " + attackPlayer.getBattleLoss() + " units and ";			
		}
		if (defencePlayer.getBattleLoss()==1) {
			message += makeLongName(defencePlayer) + " loses 1 unit";	
		} else {
			message += makeLongName(defencePlayer) + " loses " + defencePlayer.getBattleLoss() + " units";		
		}
		displayString(message);
		return;
	}
	
	public void displayWinner (Player player) {
		displayString(makeLongName(player) + " wins the game!");
		return;
	}
		
	public String inputName (int playerId) {
		String response;
		displayString("Enter the name for player " + (playerId+1) + " (" + mapPanel.getColorName(playerId) + "):");
		response = commandPanel.getCommand();
		response.trim();
		displayString(PROMPT + response);
		return response;		
	}
		
	public void inputReinforcement (Player player) {
		String response, message;
		boolean responseOK = false;
		do {
			message = makeLongName(player) + ": REINFORCE: Enter a country to reinforce and the number of units";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.countryNumber(response);
			if (parse.isError()) {
				displayString("Error: Incorrect command");
			} else if (board.getOccupier(getCountryId()) != player.getId()) {
				displayString("Error: Cannot place the units in that country");
			} else if (parse.getNumUnits() > player.getNumUnits()) {
				displayString("Error: Not enough units");
			} else {
				responseOK = true;
			}
		} while (!responseOK);
		return;
	}

	public void inputPlacement (Player byPlayer, Player forPlayer) {
		String response, message;
		boolean responseOK = false;
		do {
			message = makeLongName(byPlayer) + ": REINFORCE: Enter a country occupied by " + makeLongName(forPlayer) + " to reinforce by 1 unit";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.country(response);
			if (parse.isError()) {
				displayString("Error: Not a country");
			} else if (board.getOccupier(getCountryId()) != forPlayer.getId()) {
				displayString("Error: Cannot place the units in that country");
			} else {
				responseOK = true;
			}
		} while (!responseOK);
		return;
	}

	public void inputBattle (Player player) {
		String response, message;
		boolean responseOK = false;
		do {
			message = makeLongName(player) + ": ATTACK: Enter country to attack from, country to attack and number of units to use, or enter skip";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.countryCountryNumber(response);
			if (parse.isError()) {
				displayString("Error: Incorrect command");
			} else if (parse.isTurnEnded()) {
				responseOK = true;
			} else if (board.getOccupier(getFromCountryId()) != player.getId()) {
				displayString("Error: Cannot invade from that country");
			} else if (board.getOccupier(getToCountryId()) == player.getId()) {
				displayString("Error: Cannot invade your own country");
			} else if (!board.isAdjacent(getFromCountryId(),parse.getToCountryId())) {
				displayString("Error: Countries not neighbours");				
			} else if (getNumUnits() >= board.getNumUnits(getFromCountryId())) {
				displayString("Error: Not enough units in the attacking country, must leave 1 behind");
			} else if (board.getNumUnits(getFromCountryId()) < GameData.ATTACK_MIN_IN_COUNTRY) {
				displayString("Error: Must have 2 or more units in the attacking country");
			} else if (parse.getNumUnits() > GameData.ATTACK_MAX) {
				displayString("Error: The maximum number of units that can used to attack is 3");
			} else {
				responseOK = true;
			}
		} while (!responseOK);
		return;
	}
	
	public void inputDefence (Player player, int countryId) {
		String response, message;
		boolean responseOK = false;
		do {
			message = makeLongName(player) + ": DEFEND: Enter number of units to defend with";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.number(response);
			if (parse.isError()) {
				displayString("Error: Incorrect command");
			} else if (getNumUnits() > GameData.DEFEND_MAX) {
				displayString("Error: Maximum of 2 defenders");
			} else if (getNumUnits() > board.getNumUnits(countryId)) {
				displayString("Error: Not enough units in the country");
			} else {
				responseOK = true;
			}
		} while (!responseOK);
		return;		
	}

	public void inputMoveIn (Player player, int attackCountryId) {
		String response, message;
		boolean responseOK = false;
		do {
			message = makeLongName(player) + ": MOVE IN: How many units do you wish to move in";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.number(response);
			if (parse.isError()) {
				displayString("Error: Incorrect command");
			} else if (getNumUnits() >= board.getNumUnits(attackCountryId)) {
				displayString("Error: Insufficient units in attacking country, note you must leave at least 1 behind");
			} else {
				responseOK = true;
			}
		} while (!responseOK);
		return;		
	}
	
	public void inputFortify (Player player) {
		String response, message;
		boolean responseOK = false;
		do {
			message = makeLongName(player) + ": FORTIFY: Enter country to move units from, country to fortify and number of units to move, or enter skip";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.countryCountryNumber(response);
			if (parse.isError()) {
				displayString("Error: Incorrect command");
			} else if (parse.isTurnEnded()) {
				responseOK = true;
			} else if (board.getOccupier(getFromCountryId()) != player.getId()) {
				displayString("Error: Cannot move from the origin country, you do not occupy it");
			} else if (board.getOccupier(getToCountryId()) != player.getId()) {
				displayString("Error: Cannot move to the destination country, you do not occupy it");
			} else if (!board.isConnected(getFromCountryId(),parse.getToCountryId())) {
				displayString("Error: Countries are not connected by your occupied territories");				
			} else if (getNumUnits() >= board.getNumUnits(getFromCountryId())) {
				displayString("Error: Not enough units in the origin country, note you must leave at least 1 behind");
			} else {
				responseOK = true;
			}
		} while (!responseOK);
		return;		
	}
	
	// check first if the player has 3 of the same cards and then call this function
	
	// iterate through player.obtainedCards to see if they have 3 the same types
	
	public void inputExchange (Player player) {
		String response, message;
		boolean responseOK = false;
		do {
			displayString("You have a set of cards.");
			message = makeLongName(player) + ": EXCHANGE: Trade 3 cards of the same type for reinforcements";
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			
			if (message.isEmpty()) { //if the player enters nothing
				displayString("Error: No Input");
			} else if (message.length() > 4) { //this input is too big
				displayString("Error: Invalid input");
			} else if (message.equalsIgnoreCase("III")) { //infantry
				
					if(player.getInfantryCards() >= 3) { //they have 3 or more infantry cards
						//function to remove the three infantry cards in the deck goes here:
						
						player.addUnits(player.calcCardsToArmiesTrade()); //adds the correct amount of armies
						player.removeSet(0); // remove set of infantry cards after trade
					}
				responseOK = true;
			} else if (message.equalsIgnoreCase("AAA")) { //artillery
				
				if(player.getArtilleryCards() >= 3) { //they have 3 or more infantry cards
					//function to remove the three artillery cards in the deck goes here:
					
					player.addUnits(player.calcCardsToArmiesTrade()); //adds the correct amount of armies
					player.removeSet(2); // remove set of Artillery cards after trade
				}
				responseOK = true;
			} else if (message.equalsIgnoreCase("CCC")) { //cavalry
				
				if(player.getCavalryCards() >= 3) { //they have 3 or more infantry cards
					//function to remove the three cavalry cards in the deck goes here:
					
					player.addUnits(player.calcCardsToArmiesTrade()); //adds the correct amount of armies
					player.removeSet(1); // remove set of Cavalry cards after trade
				}
				responseOK = true;
			} else if (message.equalsIgnoreCase("skip")) { //skip
				responseOK = true;
			} else {
				displayString("Error: Invalid input");
			}
		} while (!responseOK);
		return;		
	}
}


