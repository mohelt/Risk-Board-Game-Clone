package com.whyNotBot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// put your code here

public class WhyNotBot implements Bot {
	// The public API of WhyNotBot must not change
	// You cannot change any other classes
	// WhyNotBot may not alter the state of the board or the player objects
	// It may only inspect the state of the board and the player objects
	// So you can use player.getNumUnits() but you can't use player.addUnits(10000), for example

	private BoardAPI board;
	private PlayerAPI player;
	ArrayList<Territory> borderTerritories;
	private int personalId,enemyIdentification;
	WhyNotBot (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		personalId = getPersonalIdentification();
		enemyIdentification = getEnemyIdentification();
		// put your code here
		return;
	}

	private int getEnemyIdentification() {
		// TODO Auto-generated method stub
		if(getPersonalIdentification() == 0){
			return 1; 
		}
		return 0;
	}

	private int getPersonalIdentification() {
		return player.getId(); 
	}

	public String getName () {
		String command = "";
		// put your code here
		command = "WhyNotBot";
		return(command);
	}
	class Territory {
		public int indexNumber;
		public String nameOfTerritory;
		public int continentTerritory;
		public int[] adjacentTerritorys;

		public Territory(int ind) {
			indexNumber = ind;
			nameOfTerritory = GameData.COUNTRY_NAMES[indexNumber];
			adjacentTerritorys = GameData.ADJACENT[indexNumber];
			continentTerritory = GameData.CONTINENT_IDS[indexNumber];
		}

		public int numberOfUnits(){ return board.getNumUnits(indexNumber);}

		public Boolean hasOwnerTerritory(){return board.isOccupied(indexNumber);}

		public int ownerTerritory(){return board.getOccupier(indexNumber);}

	}
	private void getBorderingTerritories(int flag){
		for (int i=0; i<GameData.NUM_COUNTRIES; i++){
			if (board.getOccupier(i) == personalId){
				for (int j=0; j<GameData.ADJACENT[i].length; j++){
					if (flag == -1) {
						if (board.getOccupier(GameData.ADJACENT[i][j]) != personalId) {
							borderTerritories.add(new Territory(i));
						}
					} else {
						if (board.getOccupier(GameData.ADJACENT[i][j]) == enemyIdentification) {
							borderTerritories.add(new Territory(i));
						}
					}
				}
			}
		}
	}
	Comparator<Territory> compareTerritoryByNumUnits = new Comparator<Territory>(){
		public int compare(Territory a, Territory b){
			return new Integer(a.numberOfUnits()).compareTo(new Integer(b.numberOfUnits()));
		}
	};

	public String getReinforcement () {
		String command = "";
		String territory = "";
		borderTerritories = new ArrayList<Territory>();
		getBorderingTerritories(0);
		if(borderTerritories.size() != 0){
			Collections.sort(borderTerritories, compareTerritoryByNumUnits);
			territory = borderTerritories.get(0).nameOfTerritory;
		} else {
			getBorderingTerritories(-1);
			Collections.sort(borderTerritories, compareTerritoryByNumUnits);
			territory = borderTerritories.get(0).nameOfTerritory;
		}
		//replace spaces in the territory name
		territory = territory.replaceAll("\\s", "");
		command = territory + " 1";
		return(command);
	}

	public String getPlacement (int forPlayer) {
		String command = "";
		// put your code here
		command = GameData.COUNTRY_NAMES[(int)(Math.random() * GameData.NUM_COUNTRIES)];
		command = command.replaceAll("\\s", "");
		return(command);
	}

	public String getCardExchange () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}

	public String getBattle () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		command = "1";
		return(command);
	}

	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
		command = "0";
		return(command);
	}

	public String getFortify () {
		String command = "";
		// put code here
		command = "skip";
		return(command);
	}

}
