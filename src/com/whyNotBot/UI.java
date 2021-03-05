package com.whyNotBot;
import java.awt.BorderLayout;
import java.util.Collections;

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
			} else {
				if (!board.checkOccupier(forPlayer, parse.getCountryId())) {
					displayString("Error: Cannot place the units on that country");
					response = commandPanel.getCommand();
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
	public int countryFromCheck(int playerId,Player player) {
		int countryAttackingFrom=0;
		boolean checkOver = false;

		while(checkOver == false) {
			displayString(makeLongName(player) + "): Type a country to attack from");
			String response = commandPanel.getCommand();
			parse.countryId(response);
			countryAttackingFrom= parse.getCountryId();
			if(board.getOccupier(countryAttackingFrom)==playerId) {
				checkOver = true;
			}else {
				displayString(makeLongName(player) + "): This is not your country or you have spelt it wrong");
			}
		}
		return countryAttackingFrom;
	}
	public int countryToCheck(Player player) {
		boolean checkOver = false;
		int countryToAttack = 0;
		while(checkOver ==false) {
			displayString(makeLongName(player) + "): Type a country to attack");
			String response = commandPanel.getCommand();
			parse.countryId(response);
			countryToAttack = parse.getCountryId();
			if(parse.isError()) {
				displayString("Error: Not a country");
			}else {
				checkOver = true;
			}
		}
		return countryToAttack;

	}
	public int numUnitsCheckerAttack(Player player,int countryFrom) {
		displayString(makeLongName(player) + "): Type Number Of Units to Attack With ");
		String response = commandPanel.getCommand();
		int numUnitsAttackWith =  Integer.parseInt(response);
		while(numUnitsAttackWith>3 || !(board.getNumUnits(countryFrom)>=numUnitsAttackWith)) {
			displayString(makeLongName(player) + "): You can't attack with more than 3, and you must have the number of units on your territory");
			response = commandPanel.getCommand();
			numUnitsAttackWith =  Integer.parseInt(response);
		}
		return numUnitsAttackWith;
	}
	public int numUnitsCheckerDefence(Player player,int countryTo) {
		displayString( "DEFEND: Type Number Of Units to Defend With ");
		String response = commandPanel.getCommand();
		int numUnitsDefendWith =  Integer.parseInt(response);
		while(numUnitsDefendWith>2 || !(board.getNumUnits(countryTo)>=numUnitsDefendWith)) {
			displayString(makeLongName(player) + "): You can't defend with more than 2, and you must have the number of units on your territory");
			response = commandPanel.getCommand();
			numUnitsDefendWith =  Integer.parseInt(response);
		}
		return numUnitsDefendWith;
	}
	public void diceCompare(Player player,Player[] playerArray,int occupierPlayer,int attackingPlayerMax2,int defendingPlayerMax2,
			int attackingPlayerMax1, int defendingPlayerMax1,int countryToAttack,int numUnitsAttackWith,int countryAttackingFrom){
		if(player.getDice().size() >=1 && playerArray[occupierPlayer].getDice().size() >=1) {
			displayString(makeLongName(playerArray[occupierPlayer]) + ": "+attackingPlayerMax1+" vs "+defendingPlayerMax1);
			displayString(makeLongName(playerArray[occupierPlayer]) + ": "+attackingPlayerMax2+" vs "+defendingPlayerMax2);
			if(attackingPlayerMax1>defendingPlayerMax1 && attackingPlayerMax2>defendingPlayerMax2) {

				board.addUnits(countryToAttack, playerArray[occupierPlayer], -2);
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
				}
				displayString(makeLongName(playerArray[occupierPlayer]) + ": Lost 2 armies ");
				displayMap();

			}else if(attackingPlayerMax1<defendingPlayerMax1 && attackingPlayerMax2<defendingPlayerMax2) {
				board.addUnits(countryAttackingFrom, player, -2);
				displayString(makeLongName(player) + ": Lost 2 armies ");
				displayMap();

			}else if(attackingPlayerMax1<defendingPlayerMax1 && attackingPlayerMax2>defendingPlayerMax2) {

				board.addUnits(countryAttackingFrom, player, -1);
				board.addUnits(countryToAttack, playerArray[occupierPlayer], -1);
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
				}
				displayString(makeLongName(player) + ": Lost 1 army" + makeLongName(playerArray[occupierPlayer])+" lost 1 army");
				displayMap();

			}
			else if(attackingPlayerMax1>defendingPlayerMax1 && attackingPlayerMax2<defendingPlayerMax2) {
				board.addUnits(countryAttackingFrom, player, -1);
				board.addUnits(countryToAttack, playerArray[occupierPlayer], -1);
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
				}
				displayString(makeLongName(player) + ": Lost 1 army " + makeLongName(playerArray[occupierPlayer])+" lost 1 army");
				displayMap();

			}
			else if(attackingPlayerMax1==defendingPlayerMax1 && attackingPlayerMax2==defendingPlayerMax2) {

				board.addUnits(countryAttackingFrom, player, -2);
				displayString(makeLongName(player) + ": Lost 2 armies ");
				displayMap();

			}
			else if(attackingPlayerMax1==defendingPlayerMax1 && attackingPlayerMax2<defendingPlayerMax2) {

				board.addUnits(countryAttackingFrom, player, -2);
				displayString(makeLongName(player) + ": Lost 2 armies ");
				displayMap();

			}

			else if(attackingPlayerMax1==defendingPlayerMax1 && attackingPlayerMax2>defendingPlayerMax2)
			{
				board.addUnits(countryAttackingFrom, player, -1);
				board.addUnits(countryToAttack, playerArray[occupierPlayer], -1);
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
				}
				displayString(makeLongName(player) + ": Lost 1 army " + makeLongName(playerArray[occupierPlayer])+" lost 1 army");
				displayMap();

			}
			else if(attackingPlayerMax1<defendingPlayerMax1 && attackingPlayerMax2==defendingPlayerMax2) {

				board.addUnits(countryAttackingFrom, player, -2);
				displayString(makeLongName(player) + ": Lost 2 armies ");
				displayMap();
			}

			else if(attackingPlayerMax1>defendingPlayerMax1 && attackingPlayerMax2==defendingPlayerMax2) {

				board.addUnits(countryAttackingFrom, player, -1);
				board.addUnits(countryToAttack, playerArray[occupierPlayer], -1);
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
				}
				displayString(makeLongName(player) + ": Lost 1 army " + makeLongName(playerArray[occupierPlayer])+" lost 1 army");
				displayMap();

			}
		}
	}
	public void diceCompare(Player player,Player[] playerArray,int occupierPlayer,
			int attackingPlayerMax1, int defendingPlayerMax1,int countryToAttack,int numUnitsAttackWith,int countryAttackingFrom){
		if(attackingPlayerMax1<defendingPlayerMax1) {
			board.addUnits(countryAttackingFrom, player, -1);
			displayString(makeLongName(player) + ": Lost 1 armies ");
			if(board.getNumUnits(countryToAttack)<=0) {
				board.addUnits(countryToAttack, player,numUnitsAttackWith);
				board.occupier[countryToAttack]= player.getId();
			}
			displayMap();
		}
		else if(attackingPlayerMax1==defendingPlayerMax1) {
			board.addUnits(countryAttackingFrom, player, -1);
			displayString(makeLongName(player) + ": Lost 1 armies ");
			if(board.getNumUnits(countryToAttack)<=0) {
				board.addUnits(countryToAttack, player,numUnitsAttackWith);
				board.occupier[countryToAttack]= player.getId();
			}
			displayMap();
		}
		else if(attackingPlayerMax1>defendingPlayerMax1) {
			board.addUnits(countryToAttack, playerArray[occupierPlayer], -1);
			displayString(makeLongName(playerArray[occupierPlayer]) + ": Lost 1 armies ");
			if(board.getNumUnits(countryToAttack)<=0) {
				board.addUnits(countryToAttack, player,numUnitsAttackWith);
				board.occupier[countryToAttack]= player.getId();
			}
			displayMap();
		}
	}
	public void attackOrSkip(Player player,Player[] playerArray, int playerId) {
		boolean attackFinished = false;
		int numUnitsAttackWith = 0;
		int defenceArmiesNumber =0;
		displayString(makeLongName(player) + "): Type 'attack' to attack or 'skip' to skip your turn...");
		String command = commandPanel.getCommand();
		if(command.equals("skip") ||command.equals("skip ")) {
			return;
		}else if (command.equals("attack") ||command.equals("attack ")){
			while(attackFinished == false) {
				int countryAttackingFrom=countryFromCheck(playerId,player);
				int countryToAttack = countryToCheck(player);
				int occupierPlayer =board.getOccupier(countryToAttack);
				if(isAdjacent(countryAttackingFrom,countryToAttack)) {
					numUnitsAttackWith =numUnitsCheckerAttack(player,countryAttackingFrom);
					defenceArmiesNumber = numUnitsCheckerDefence(player,countryToAttack);
					player.rollDice(numUnitsAttackWith);
					playerArray[occupierPlayer].rollDice(defenceArmiesNumber);
					displayString(makeLongName(player) + "Rolled: "+printDie(player));
					displayString(makeLongName(playerArray[occupierPlayer]) + "Rolled: "+printDie(playerArray[occupierPlayer]));
					Integer attackingPlayerMax1 =Collections.max(player.getDice());
					Integer defendingPlayerMax1 =Collections.max(playerArray[occupierPlayer].getDice());
					player.getDice().remove(attackingPlayerMax1);
					playerArray[occupierPlayer].getDice().remove(defendingPlayerMax1);
					if(player.getDice().size() >=1 && playerArray[occupierPlayer].getDice().size() >=1) {
						Integer attackingPlayerMax2 =Collections.max(player.getDice());
						Integer defendingPlayerMax2 =Collections.max(playerArray[occupierPlayer].getDice());
						diceCompare( player,playerArray,occupierPlayer,attackingPlayerMax2,defendingPlayerMax2,attackingPlayerMax1,defendingPlayerMax1,countryToAttack,numUnitsAttackWith,countryAttackingFrom);
					}else {
						diceCompare( player,playerArray,occupierPlayer,attackingPlayerMax1,defendingPlayerMax1,countryToAttack,numUnitsAttackWith,countryAttackingFrom);	
					}
					displayString(makeLongName(player) + "): 'skip' or attack");
					command = commandPanel.getCommand();
					if(command.equals("skip") ||command.equals("skip ")) {
						attackFinished = true;
					}else if(command.equals("attack") ||command.equals("attack ")){
						attackOrSkip(player,playerArray,playerId);
					}
				}else {
					displayString(makeLongName(player) + "): ERROR, not adjacent countries");
				}
			}
		}
	}
	public boolean isAdjacent(int countryFrom,int countryTo) {
		boolean adjacentTo=false;
		for (int j=0; j<GameData.ADJACENT[countryFrom].length; j++) {
			if(GameData.ADJACENT[countryFrom][j] ==countryTo) {
				adjacentTo=true;
			}
		}
		return adjacentTo;
	}
	public String printDie(Player player) {
		return player.getDice().toString();
	}
}
