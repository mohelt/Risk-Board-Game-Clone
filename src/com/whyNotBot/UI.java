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
		String countryName,numOfUnitsReinforce; 

		boolean placementOK = false;
		do {
			displayString(makeLongName(player)  + ": REINFORCE: Enter a country to reinforce");
			countryName = commandPanel.getCommand(); //no need to shorten it as the parse function handles it
			displayString(PROMPT + countryName);
			displayString(makeLongName(player)  + ": Enter the number of units to reinforce with.");
			numOfUnitsReinforce = commandPanel.getCommand();
			displayString(PROMPT + numOfUnitsReinforce);
			num_of_units =  Integer.parseInt(numOfUnitsReinforce);
			parse.countryId(countryName);
			if (parse.isError()) {
				displayString("Error: Not a country");
			} else {
				if (!board.checkOccupier(player, parse.getCountryId())) {
					displayString("Error: Cannot place the units on that country");
					displayString(makeLongName(player)  + ": REINFORCE: Enter a country to reinforce.");
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
	//checks if the country you are attacking from is owned by the user
	public int countryFromCheck(int playerId,Player player) {
		int countryAttackingFrom=0;
		boolean checkOver = false;

		while(checkOver == false) {
			displayString(makeLongName(player) + ": Type a country to attack from");
			String response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.countryId(response);
			countryAttackingFrom= parse.getCountryId();
			if(board.getOccupier(countryAttackingFrom)==playerId) {
				checkOver = true;
			}else {
				displayString(makeLongName(player) + ": This is not your country or you have spelt it wrong");
			}
		}
		return countryAttackingFrom;
	}
	//checks if the country you are attacking is owned by the user
	public int countryToCheck(Player player) {
		boolean checkOver = false;
		int countryToAttack = 0;
		while(checkOver ==false) {
			displayString(makeLongName(player) + ": Type a country to attack");
			String response = commandPanel.getCommand();
			displayString(PROMPT + response);
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
	//checks if the country has enough units to attack and that one unit is left behind to stay on the territory
	public int numUnitsCheckerAttack(Player player,int countryFrom) {
		displayString(makeLongName(player) + ": Type Number Of Units to Attack With ");
		String response;
		do {
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
		} while((response.equals("")) || (response.length() > 2)); //so user can't input nothing or a string too long
		int numUnitsAttackWith =  Integer.parseInt(response);
		while(numUnitsAttackWith>3 || !(board.getNumUnits(countryFrom)>=(numUnitsAttackWith)+1)) {
			displayString(makeLongName(player) + ": You can't attack with more than 3,you must leave 1 army behind and you must have the number of units on your territory");
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			numUnitsAttackWith =  Integer.parseInt(response);
		}
		return numUnitsAttackWith;
	}
	//checks if the country has enough units to defend
	public int numUnitsCheckerDefence(Player player,int countryTo) {
		displayString( "DEFEND: Type Number Of Units to Defend With ");
		String response;
		do {
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
		} while((response.equals("")) || (response.length() > 2)); //so user can't input nothing or a string too long
		int numUnitsDefendWith =  Integer.parseInt(response);
		while(numUnitsDefendWith>2 || !(board.getNumUnits(countryTo)>=numUnitsDefendWith)) {
			displayString(makeLongName(player) + ": You can't defend with more than 2, and you must have the number of units on your territory");
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			numUnitsDefendWith =  Integer.parseInt(response);
		}
		return numUnitsDefendWith;
	}
	//first dice compare function if there is a 3 v 2 or 2v2 dice rolls
	public void diceCompare(Player player,Player[] playerArray,int occupierPlayer,int attackingPlayerMax2,int defendingPlayerMax2,
			int attackingPlayerMax1, int defendingPlayerMax1,int countryToAttack,int numUnitsAttackWith,int countryAttackingFrom){
		//if you arent attacking your own country
		if(occupierPlayer !=player.getId()) {

			//if the user specified 2 or more dice for attack and 2 dice for defence
			if(player.getDice().size() >=1 && playerArray[occupierPlayer].getDice().size() >=1) {

				//display the dice rolls
				displayString(makeLongName(playerArray[occupierPlayer]) + ": "+attackingPlayerMax1+" vs "+defendingPlayerMax1);
				displayString(makeLongName(playerArray[occupierPlayer]) + ": "+attackingPlayerMax2+" vs "+defendingPlayerMax2);

				//the different conditions for who is the winner of the dice rolls
				if(attackingPlayerMax1>defendingPlayerMax1 && attackingPlayerMax2>defendingPlayerMax2) {

					board.addUnits(countryToAttack, playerArray[occupierPlayer], -2);
					if(board.getNumUnits(countryToAttack)<=0) {
						board.addUnits(countryToAttack, player,numUnitsAttackWith);
						board.occupier[countryToAttack]= player.getId();
						board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
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
						board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
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
						board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
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
						board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
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
						board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
					}
					displayString(makeLongName(player) + ": Lost 1 army " + makeLongName(playerArray[occupierPlayer])+" lost 1 army");
					displayMap();

				}
			}
		}else {
			displayString(makeLongName(playerArray[occupierPlayer]) + ": Cannot attack own country ");
		}
	}
	public void diceCompare(Player player,Player[] playerArray,int occupierPlayer,
			int attackingPlayerMax1, int defendingPlayerMax1,int countryToAttack,int numUnitsAttackWith,int countryAttackingFrom){

		//if the dice rolls are a 2v1 or a 1v1
		//if the user isnt attacking himself
		if(occupierPlayer !=player.getId()) {
			displayString(makeLongName(playerArray[occupierPlayer]) + ": "+attackingPlayerMax1+" vs "+defendingPlayerMax1);

			//handles the different conditions for the winners of the dice roll
			if(attackingPlayerMax1<defendingPlayerMax1) {
				board.addUnits(countryAttackingFrom, player, -1);
				displayString(makeLongName(player) + ": Lost 1 armies ");
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
					board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
				}
				displayMap();
			}
			else if(attackingPlayerMax1==defendingPlayerMax1) {
				board.addUnits(countryAttackingFrom, player, -1);
				displayString(makeLongName(player) + ": Lost 1 armies ");
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
					board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
				}
				displayMap();
			}
			else if(attackingPlayerMax1>defendingPlayerMax1) {
				board.addUnits(countryToAttack, playerArray[occupierPlayer], -1);
				displayString(makeLongName(playerArray[occupierPlayer]) + ": Lost 1 armies ");
				if(board.getNumUnits(countryToAttack)<=0) {
					board.addUnits(countryToAttack, player,numUnitsAttackWith);
					board.occupier[countryToAttack]= player.getId();
					board.addUnits(countryAttackingFrom, player,-numUnitsAttackWith);
				}
				displayMap();
			}
		}
		else {
			displayString(makeLongName(playerArray[occupierPlayer]) + ": Cannot attack own country ");
		}
	}
	public boolean attackOrSkip(Player player,Player[] playerArray, int playerId) {
		boolean attackFinished = false;
		int numUnitsAttackWith = 0;
		int defenceArmiesNumber =0;
		displayString(makeLongName(player) + ": Type 'attack' to attack or 'skip' to skip your turn...");
		String command = commandPanel.getCommand();
		displayString(PROMPT + command);
		if(command.equals("skip") ||command.equals("skip ") ||command.equals("s")) {
			return true;
		}else if (command.equals("attack") ||command.equals("attack ")){
			displayString(PROMPT + command);
			//while the attack isn't finished
			while(attackFinished == false) {

				//get the country the user is attacking
				int countryAttackingFrom=countryFromCheck(playerId,player);
				//get the country to attack
				int countryToAttack = countryToCheck(player);
				//get the player who we are attacking
				int occupierPlayer =board.getOccupier(countryToAttack);


				if ((board.getNumUnits(countryAttackingFrom)) < 2) {
					displayString("You dont have enough units on this country to make an attack!");
					attackOrSkip(player, playerArray, playerId);
					break;
				}
				//if the country is adjacent to another one then you can attack else no
				else if(isAdjacent(countryAttackingFrom,countryToAttack)) {

					//check the number of unit to attack with
					numUnitsAttackWith =numUnitsCheckerAttack(player,countryAttackingFrom);
					//check the number of unit to defend with
					defenceArmiesNumber = numUnitsCheckerDefence(player,countryToAttack);

					//roll the dice
					player.rollDice(numUnitsAttackWith);
					playerArray[occupierPlayer].rollDice(defenceArmiesNumber);

					//display the roll results
					displayString(makeLongName(player) + "Rolled: "+printDie(player));
					displayString(makeLongName(playerArray[occupierPlayer]) + "Rolled: "+printDie(playerArray[occupierPlayer]));

					//get the two highest numbers from the dice
					Integer attackingPlayerMax1 =Collections.max(player.getDice());
					Integer defendingPlayerMax1 =Collections.max(playerArray[occupierPlayer].getDice());

					//remove the highest rolls from both dice
					player.getDice().remove(attackingPlayerMax1);
					playerArray[occupierPlayer].getDice().remove(defendingPlayerMax1);
					//if the player asked form 3v2 or 2v2
					if(player.getDice().size() >=1 && playerArray[occupierPlayer].getDice().size() >=1) {
						//get the next highest rolls from the dice
						Integer attackingPlayerMax2 =Collections.max(player.getDice());
						Integer defendingPlayerMax2 =Collections.max(playerArray[occupierPlayer].getDice());
						//compare the dice and see who the winner is
						diceCompare( player,playerArray,occupierPlayer,attackingPlayerMax2,defendingPlayerMax2,attackingPlayerMax1,defendingPlayerMax1,countryToAttack,numUnitsAttackWith,countryAttackingFrom);
					}else {
						//compare the dice and see who the winner is
						diceCompare( player,playerArray,occupierPlayer,attackingPlayerMax1,defendingPlayerMax1,countryToAttack,numUnitsAttackWith,countryAttackingFrom);	
					}
					displayString(makeLongName(player) + ": 'end turn' or 'continue'");
					command = commandPanel.getCommand();
					displayString(PROMPT + command);
					if(command.equals("end turn")||command.equals("end turn ") ||command.equals("endturn")||command.equals("endturn ") ||command.equals("end")) {
						attackFinished = true;
						return attackFinished;
					}else if(command.equals("continue") ||command.equals("continue ") ||command.equals("con")){
						attackFinished = attackOrSkip(player,playerArray,playerId);
					}
				}else {
					displayString(makeLongName(player) + ": ERROR, not adjacent countries");
				}
			}
		}
		return attackFinished;

	}
	//function to allow users to fortify their position
	public boolean fortify(Player player,int playerId) {
		displayString(makeLongName(player) + "): Type 'fortify' to fortify or 'skip' to skip");
		String command = commandPanel.getCommand();
		while(!(command.equals("fortify") || command.equals("skip"))){
			displayString(makeLongName(player) + "): Please type 'fortify' to fortify or 'skip' to skip");
			command = commandPanel.getCommand();
		}
		//if the user enters skip skip the call
		if(command.equals("skip")) {
			return true;

			//if the user types in fortify
		}else if(command.equals("fortify")) {
			boolean checkOver =false;
			while(checkOver == false){
				//ask from where would he like to move units
				displayString(makeLongName(player) + "): What country would you like to move units from");

				String country = commandPanel.getCommand();
				parse.countryId(country);
				displayString(makeLongName(player) + "):"+ country);
				int countryInt = parse.getCountryId();

				//if its not a country return an error
				if(parse.isError()) {
					displayString("Error: Not a country");
				}else {
					//if the user own the territory
					if(playerId ==board.getOccupier(countryInt)) {

						//ask where he would like to move the territory
						displayString(makeLongName(player) + "): What country would you like to move units to");
						String countryTo = commandPanel.getCommand();
						displayString(makeLongName(player) + "):"+ countryTo);
						parse.countryId(countryTo);
						int countryInt2 = parse.getCountryId();
						if(parse.isError()) {
							displayString("Error: Not a country");
						}else {
							// if the user owns the territory he would like to move to
							if(playerId ==board.getOccupier(countryInt2)) {
								if(isAdjacent(countryInt,countryInt2)) {

									//ask how many units he would like to move
									displayString(makeLongName(player) + "Please Type a number of armies to move");
									String response;
									do {
										response = commandPanel.getCommand();
										displayString(PROMPT + response);
									} while((response.equals("")) || (response.length() > 2)); //so user can't input nothing or a string too long
									int numUnits =  Integer.parseInt(response);

									//if the user has the number of units to move and still has one unit left to keep on the original territory
									if(numUnits<=(board.getNumUnits(countryInt)-1)) {
										//subtract the number of units from the country he chose to move from
										board.addUnits(countryInt, player, -numUnits);
										//add the number of units to the country he chose to move to
										board.addUnits(countryInt2, player, numUnits);
										//update the map
										displayMap();
										//the loop is over
										checkOver = true;
										//ask if he would like to do it again
										displayString(makeLongName(player) + "):type 'fortify' to fortify again or 'skip' if you would like to skip ");
										String command2 = commandPanel.getCommand();
										while(!(command2.equals("fortify") || command2.equals("skip"))){
											displayString(makeLongName(player) + "): Please type 'fortify' to fortify or 'skip' to skip");
											command2 = commandPanel.getCommand();
										}
										//if he want to fortify again
										if(command2.equals("fortify")) {
											checkOver =fortify(player,playerId);
										}
										//else skip
										if(command2.equals("skip")) {
											return true;
										}
									}
									else {
										displayString(makeLongName(player) + "): You dont have that many units, you must have atleast one army to hold a territory");
									}
								}else {
									displayString(makeLongName(player) + "): Countries must be adjacent");
								}
							}else{
								displayString(makeLongName(player) + "): Not your country");
							}
						}
					}
				}
			}
		}

		return false;
	}

	//function checks if the user has typed in a country which is adjacent to the other country
	public boolean isAdjacent(int countryFrom,int countryTo) {
		boolean adjacentTo=false;
		for (int j=0; j<GameData.ADJACENT[countryFrom].length; j++) {
			if(GameData.ADJACENT[countryFrom][j] ==countryTo) {
				adjacentTo=true;
			}
		}
		return adjacentTo;
	}


	public void gameOverMessage(Player player){

		displayString(player + " is the winner!!!!!");
		displayString("\nGAME OVER!");

	}


	public String printDie(Player player) {
		return player.getDice().toString();
	}
}
