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

	int num_of_units = 0;

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

	public String makeLongName (Player player) {
		return player.getName() + " (" + mapPanel.getColorName(player.getId()) + ") ";
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

	public void reinforcementsPlacement (Player player) {
		displayString(makeLongName(player)  + ": REINFORCE: Enter a country to reinforce");
		String countryName,numOfUnitsReinforce; 

		boolean placementOK = false;
		do {
			countryName = commandPanel.getCommand(); //no need to shorten it as the parse function handles it
			displayString(PROMPT + countryName);
			displayString(makeLongName(player)  + ": Enter the number of units to reinforce with.");
			numOfUnitsReinforce = commandPanel.getCommand();
			displayString(PROMPT + num_of_units );
			num_of_units =  Integer.parseInt(numOfUnitsReinforce);
			parse.countryId(countryName);
			if (parse.isError()) {
				displayString("Error: Not a country");
			} else {
				if (!board.checkOccupier(player, parse.getCountryId())) {
					displayString("Error: Cannot place the units on that country");
				} else {
					placementOK = true;
				}
			}
			if(num_of_units >player.getNumUnits()) {
				displayString("Error: You don't have that many units to reinforce with.");
				placementOK = false;
			}
			if(num_of_units <= 0) {
				displayString("Error: You don't have that many units to reinforce with.");
				placementOK = false;
			}

		} while (!placementOK);

		return;
	}

	public int getReinforcementsPlacementUnits () {
		return num_of_units;
	}

	public String inputName (int playerId) {
		String response;
		displayString("Enter the name for player " + (playerId+1) + " (" + mapPanel.getColorName(playerId) + "):");
		response = commandPanel.getCommand();
		response.trim();
		displayString(PROMPT + response);
		return response;		
	}

	public void inputPlacement (Player byPlayer, Player forPlayer) {
		String response, message;
		boolean placementOK = false;
		do {
			message = makeLongName(byPlayer) + ": Enter a country to reinforce with ";
			if (byPlayer.equals(forPlayer)) {
				message += "your own units";				
			} else {
				message += makeLongName(forPlayer) + "'s units";
			}
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.countryId(response);
			if (parse.isError()) {
				displayString("Error: Not a country");
				response = commandPanel.getCommand();
			} else {
				if (!board.checkOccupier(forPlayer, parse.getCountryId())) {
					displayString("Error: Cannot place the units on that country");
				} else {
					placementOK = true;
				}
			}
		} while (!placementOK);
		return;
	}

	public int getCountryId () {
		return parse.getCountryId();
	}
	public int attackOrSkip(Player player,int playerId) {
		boolean attackFinished = false;
		int numUnitsAttackWith = 0;
		int defenceArmiesNumber =0;
		displayString(makeLongName(player) + "): Type 'attack' to attack or 'skip' to skip your turn...");
		String command = commandPanel.getCommand();
		if(command.equals("skip") ||command.equals("skip ")) {
			playerId = (playerId +1) % 2;
			return playerId;
		}else if (command.equals("attack") ||command.equals("attack ")){
			while(attackFinished == false) {
			displayString(makeLongName(player) + "): Type a country to attack from");
			String response = commandPanel.getCommand();
			parse.countryId(response);
			if (parse.isError()) {
				displayString("Error: Not a country");
				response = commandPanel.getCommand();
			}
			int countryAttackingFrom= parse.getCountryId();
			displayString(makeLongName(player) + "): Type a country to attack");
			response = commandPanel.getCommand();
			parse.countryId(response);
			if (parse.isError()) {
				displayString("Error: Not a country");
				response = commandPanel.getCommand();
			}
			int countryToAttack = parse.getCountryId();
			displayString(makeLongName(player) + "): Type Number Of Units to Attack With ");
			response = commandPanel.getCommand();
			numUnitsAttackWith =  Integer.parseInt(response);
			if(isAdjacent(countryAttackingFrom,countryToAttack)) {
				displayString("DEFEND: Enter number of units to defend with");
				String defenceArmies = commandPanel.getCommand();
				defenceArmiesNumber =Integer.parseInt(defenceArmies);
				attackFinished = true;
				return playerId;
			}else {
				displayString(makeLongName(player) + "): ERROR, not adjacent countries");
			}
			}
		}
		return playerId;
	}
	public boolean isAdjacent(int countryFrom,int countryTo) {
		boolean containsCountryFrom=false;
		boolean containsCountryTo=false;
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
        	for (int j=0; j<GameData.ADJACENT[i].length; j++) {
        		if(GameData.ADJACENT[i][j] == countryFrom) {
        			containsCountryFrom = true;
        			int tempInt = i;
        			for (int x =0; x<GameData.ADJACENT[tempInt].length; x++) {
        				if(GameData.ADJACENT[tempInt][x] == countryTo) {
        					containsCountryTo = true;
        				}
        			}
        		}
        	}
		}
		return containsCountryFrom && containsCountryTo;
	}
}
