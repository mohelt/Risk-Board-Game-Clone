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
	private ArrayList<Territory> territories;
	public ArrayList<AttackMoves> possibleAttackingMoves;
	WhyNotBot (BoardAPI inBoard, PlayerAPI inPlayer) {
		board = inBoard;	
		player = inPlayer;
		personalId = getPersonalIdentification();
		enemyIdentification = getEnemyIdentification();
		territories = createTerritories();
		// put your code here
		return;
	}

	private ArrayList<Territory> createTerritories() {
		// TODO Auto-generated method stub
		ArrayList<Territory> allTerritories = new ArrayList<>();
		for(int i=0; i<GameData.NUM_COUNTRIES; i++){
			allTerritories.add(new Territory(i));
		}
		return allTerritories;
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
		int continent = 0, country = 0;
		
		continent = ourBestContinent();
		country = randomCountryInContinent(continent);
		
//		command = getRandomNeutral(forPlayer);
		command = GameData.COUNTRY_NAMES[country];
		command = command.replaceAll("\\s", "");
		return(command);
	}

	private String getRandomNeutral(int forPlayer) {
		ArrayList<Territory> ownedTerritories = new ArrayList<>();

		for(Territory i:territories){
			if(i.ownerTerritory() == forPlayer){
				ownedTerritories.add(i);
			}
		}
		return ownedTerritories.get((int)(Math.random() * ownedTerritories.size() -1)).nameOfTerritory;
	}

	public String getCardExchange () {
		String command = "";
		// put your code here
		command = "skip";
		return(command);
	}
	public class AttackMoves{
		int attackerUnits, defenderUnits, attackID, defendID;
		double probability = 0;

		public AttackMoves(int a, int d){
			attackID = a;
			defendID = d;
			attackerUnits = board.getNumUnits(a);
			defenderUnits = board.getNumUnits(d);
			probability = calculateProb(attackerUnits, defenderUnits);
		}

		private double calculateProb(int attackerUnits, int defenderUnits) {
			if(attackerUnits>(defenderUnits+5) && ((attackerUnits-10) <defenderUnits)) {
				return 0.6;	
			}
			else if(attackerUnits>(defenderUnits+10) &&((attackerUnits-15) <defenderUnits)) {
				return 0.75;	
			}
			else if(attackerUnits>(defenderUnits+15) &&((attackerUnits-20) <defenderUnits)) {
				return 0.9;
			}
			else if(attackerUnits>(defenderUnits+20) &&((attackerUnits-25) <defenderUnits)) {
				return 0.95;
			}else if (defenderUnits>(attackerUnits+5) && ((defenderUnits-10) <attackerUnits)) {
				return 0.4;
			}else if (defenderUnits>(attackerUnits+10) && ((defenderUnits-15) <attackerUnits)) {
				return 0.25;
			}
			else if (defenderUnits>(attackerUnits+15) && ((defenderUnits-20) <attackerUnits)) {
				return 0.1;
			}
			else if (defenderUnits>(attackerUnits+20) && ((defenderUnits-25) <attackerUnits)) {
				return 0.05;
			}
			else {
				return 0.5;
			}
		}
	}
	Comparator<AttackMoves> compareAttackByProbability = new Comparator<AttackMoves>() {
		@Override
		public int compare(AttackMoves a, AttackMoves b) {
			return new Double(a.probability).compareTo(b.probability);
		}
	};

	public String getBattle () {
		String command = "";
		// put your code here
		command = "skip";
		possibleAttackingMoves = new ArrayList<AttackMoves>();
		getAllPossibleAttacks();
		Collections.sort(possibleAttackingMoves, compareAttackByProbability);
		if (possibleAttackingMoves.size() > 0) {
			AttackMoves chosenAttackMove = possibleAttackingMoves.get(possibleAttackingMoves.size() - 1);
			AttackMoves lastAttackMove = chosenAttackMove;

			String attackerName = GameData.COUNTRY_NAMES[chosenAttackMove.attackID].replaceAll("\\s", "");
			String defenderName =  GameData.COUNTRY_NAMES[chosenAttackMove.defendID].replaceAll("\\s", "");
			int troopsToAttackWith;
			if (chosenAttackMove.attackerUnits > 3){
				troopsToAttackWith = 3;
				command = attackerName + " " + defenderName + " " + troopsToAttackWith;
			} else if (chosenAttackMove.attackerUnits > 1) {
				troopsToAttackWith = chosenAttackMove.attackerUnits-1;
				command = attackerName + " " + defenderName + " " + troopsToAttackWith;
			}else {
				command = "skip";
			}
		} else {
			command = "skip";
		}
		return(command);
	}

	private void getAllPossibleAttacks() {
		for (int i=0; i<GameData.NUM_COUNTRIES; i++){
			if (board.getOccupier(i) == personalId &&(board.getNumUnits(i)>1)){
				for (int j=0; j<GameData.ADJACENT[i].length; j++){
					if (board.getOccupier(GameData.ADJACENT[i][j]) != personalId){
						
						possibleAttackingMoves.add(new AttackMoves(i, GameData.ADJACENT[i][j]));
					}
				}
			}
		}		
	}

	public String getDefence (int countryId) {
		String command = "";
		// put your code here
		if (board.getNumUnits(countryId) >= 2){
			command += 2;
		} else {
			command += 1;
		}
		return(command);
	}

	public String getMoveIn (int attackCountryId) {
		String command = "";
		// put your code here
		int half =board.getNumUnits(attackCountryId)/2;
		command = String.valueOf(half);
		return(command);
	}

	public String getFortify () {
		String command = "";
		// put code here
		command = "skip";
		return(command);
	}

	public int ourBestContinent() {
		int bestContinent = 0; // 1.NA 2.Europe 3.Asia 4.Austraila 5.SA 6.Africa
		int botId = player.getId();
		int countriesInNA = 0, countriesInSA = 0, countriesInEurope = 0, countriesInAfrica = 0, countriesInAsia = 0, countriesInAustralia = 0;
<<<<<<< HEAD
		float percentOfNA = 0, percentOfSA = 0, percentOfEurope = 0, percentOfAfrica = 0, percentOfAsia = 0, percentOfAustralia = 0;

=======
		int percentOfNA = 0, percentOfSA = 0, percentOfEurope = 0, percentOfAfrica = 0, percentOfAsia = 0, percentOfAustralia = 0;
		
>>>>>>> d92bc8a93375b2eccdd148a447795057023314f0
		// scan through all countries
		for(int i=0;i<GameData.NUM_COUNTRIES;i++) {
			// if we own the country
			if(botId == board.getOccupier(i)) { //calculates how much countries we have in each continent
				if((i>=0) && (i<=8)) {countriesInNA++;}
				if((i>=9) && (i<=15)) {countriesInEurope++;}
				if((i>=16) && (i<=27)) {countriesInAsia++;}//////
				if((i>=28) && (i<=31)) {countriesInAustralia++;}
				if((i>=32) && (i<=35)) {countriesInSA++;}
				if((i>=36) && (i<=41)) {countriesInAfrica++;}
			}
		}
<<<<<<< HEAD
		percentOfNA = countriesInNA / 9;
		percentOfEurope = countriesInEurope / 7;
		percentOfAsia = countriesInAsia / 12;
		percentOfAustralia = countriesInAustralia / 4;
		percentOfSA = countriesInSA / 4;
		percentOfAfrica = countriesInAfrica / 6;

		highestPercent(percentOfNA, percentOfEurope, percentOfAsia, percentOfAustralia, percentOfSA, percentOfAfrica);


		return bestContinent;
	}

	private float highestPercent(float a, float b, float c,float d, float e, float f) {

=======
		percentOfNA = (countriesInNA / 9) * 100;
		percentOfEurope = (countriesInEurope / 7) * 100;
		percentOfAsia = (countriesInAsia / 12) * 100;
		percentOfAustralia = (countriesInAustralia / 4) * 100;
		percentOfSA = (countriesInSA / 4) * 100;
		percentOfAfrica = (countriesInAfrica / 6) * 100;
		
		int hPC = highestPercent(percentOfNA, percentOfEurope, percentOfAsia, percentOfAustralia, percentOfSA, percentOfAfrica);
		
		if(hPC == percentOfNA) {// 1.NA 2.Europe 3.Asia 4.Austraila 5.SA 6.Africa
			return 1;
		}else if(hPC == percentOfEurope) {
			return 2;
		}else if(hPC == percentOfAsia) {
			return 3;
		}else if(hPC == percentOfAustralia) {
			return 4;
		}else if(hPC == percentOfSA) {
			return 5;
		}else if(hPC == percentOfAfrica) {
			return 6;
		}
		
		return bestContinent;
	}
	
	public int randomCountryInContinent(int continent) {
		int ownedCountry;
		int firstCountryID = 0, lastCountryID = 41;
		int botId = player.getId(), i = 0;
		
		switch (continent) {// 1.NA 2.Europe 3.Asia 4.Austraila 5.SA 6.Africa
		case 1:
			firstCountryID = 0;
			lastCountryID = 8;
			break;
		case 2:
			firstCountryID = 9;
			lastCountryID = 15;
			break;
		case 3:
			firstCountryID = 16;
			lastCountryID = 27;
			break;
		case 4:
			firstCountryID = 28;
			lastCountryID = 31;
			break;
		case 5:
			firstCountryID = 32;
			lastCountryID = 35;
			break;
		case 6:
			firstCountryID = 36;
			lastCountryID = 41;
			break;
		default:
			break;
		}
		
		do {
			i = firstCountryID + (int)(Math.random() * ((lastCountryID - firstCountryID) + 1));
			ownedCountry = i;
		} while (!(botId == board.getOccupier(i)));
		
		return ownedCountry;
	}
	
private int highestPercent(int a, int b, int c,int d, int e, int f) {
		
>>>>>>> d92bc8a93375b2eccdd148a447795057023314f0
		if((a>=b) && (a>=c) && (a>=d) && (a>=e) && (a>=f)) {
			return a;
		}
		if((b>=a) && (b>=c) && (b>=d) && (b>=e) && (b>=f)) {
			return b;
		}
		if((c>=a) && (c>=b) && (c>=d) && (c>=e) && (c>=f)) {
			return c;
		}
		if((d>=a) && (d>=b) && (d>=c) && (d>=e) && (d>=f)) {
			return d;
		}
		if((e>=a) && (e>=b) && (e>=c) && (e>=d) && (e>=f)) {
			return e;
		}
		if((f>=a) && (f>=b) && (f>=c) && (f>=d) && (d>=e)) {
			return f;
		}
		return e;
	}

}
