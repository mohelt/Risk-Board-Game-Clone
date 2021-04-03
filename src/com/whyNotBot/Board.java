package com.whyNotBot;

public class Board {
	
	private boolean[] occupied = new boolean [GameData.NUM_COUNTRIES];
	private int[] occupier = new int [GameData.NUM_COUNTRIES];
	private int[] numUnits = new int [GameData.NUM_COUNTRIES];
	private int winnerId;
	public boolean invasionSuccess;
	public int cardSetsTradedIn = 0;
	
	Board() {
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			occupied[i] = false;
			occupier[i] = 0;
			numUnits[i] = 0;
		}
		return;
	}
	
	public void addUnits (int countryId, int player, int addNumUnits) {	
		// prerequisite: country must be unoccupied or already occupied by this player
		if (!occupied[countryId]) {
			occupied[countryId] = true;
			occupier[countryId] = player;
		}
		numUnits[countryId] = numUnits[countryId] + addNumUnits;
		return;
	}
	
	public void addUnits (Card card, Player player, int addNumUnits) {
		addUnits(card.getCountryId(), player.getId(), addNumUnits);
		return;
	}
	
	public void addUnits (int countryId, Player player, int addNumUnits) {
		addUnits(countryId, player.getId(), addNumUnits);
		return;
	}	

	public void subtractUnits (int countryId, int subNumUnits) {	
		numUnits[countryId] = numUnits[countryId] - subNumUnits;
		if (numUnits[countryId] == 0) {
			occupied[countryId] = false;
		}
		return;
	}
	
	public int calcReinforcements (Player player) {
		int playerId = player.getId();
		int numCountriesOccupied = 0, numUnits;
		boolean allOccupied;
		int[] countryIds;
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			if (occupier[i]==playerId) {
				numCountriesOccupied++;
			}
		}
		numUnits = (int) numCountriesOccupied / 3;
		if (numUnits < 3) {
			numUnits = 3;
		}
		for (int i=0; i<GameData.NUM_CONTINENTS; i++) {
			countryIds = GameData.CONTINENT_COUNTRIES[i];
			allOccupied = true;
			for (int j=0; (j<countryIds.length) && (allOccupied); j++) {
				if (occupier[countryIds[j]] != playerId) {
					allOccupied = false;
				}
			}
			if (allOccupied) {
				numUnits = numUnits + GameData.CONTINENT_VALUES[i];				
			}
		}
		return numUnits;
	}
	
	public void calcBattle (Player attackPlayer, Player defencePlayer, int attackCountryId, int defenceCountryId, int attackNumUnits, int defenceNumUnits) {
		int numUnitsRemaining = attackNumUnits;
		attackPlayer.resetBattleLoss();
		defencePlayer.resetBattleLoss();
		attackPlayer.rollDice(attackNumUnits);
		defencePlayer.rollDice(defenceNumUnits);
		if (attackPlayer.getDie(0) > defencePlayer.getDie(0)) {
			subtractUnits(defenceCountryId, 1);		
			defencePlayer.addBattleLoss();
		} else {
			subtractUnits(attackCountryId, 1);
			attackPlayer.addBattleLoss();
			numUnitsRemaining--;
		}
		if ( (attackNumUnits>=2) && (defenceNumUnits>=2) && isOccupied(defenceCountryId) ) {
			if (attackPlayer.getDie(1) > defencePlayer.getDie(1)) {
				subtractUnits(defenceCountryId, 1);				
				defencePlayer.addBattleLoss();
			} else {
				subtractUnits(attackCountryId, 1);
				attackPlayer.addBattleLoss();
			}
		}
		if (!isOccupied(defenceCountryId)) {
			subtractUnits(attackCountryId,numUnitsRemaining);
			addUnits(defenceCountryId,attackPlayer,numUnitsRemaining);
			invasionSuccess = true;
		}
		else {
			invasionSuccess = false;
		}
		return;
	}
	
	public boolean isAdjacent (int fromCountry, int toCountry) {
		boolean found = false;
		int[] neighbours = GameData.ADJACENT[fromCountry];
		for (int i=0; (i<neighbours.length) && (!found); i++) {
			found = (neighbours[i] == toCountry);
		}
		return found;
	}
	
	public boolean isConnected (int fromCountry, int toCountry, boolean[] countriesChecked) {
		int[] neighbours;
		int currentCountry;
		boolean found = false;
		if (occupier[fromCountry] == occupier[toCountry])  {
			if (isAdjacent(fromCountry,toCountry)) {
				found = true;
			}
			else {
				neighbours = GameData.ADJACENT[fromCountry];
				countriesChecked[fromCountry] = true;
				for (int i=0; (i<neighbours.length) && (!found); i++) {
					currentCountry = neighbours[i];
					if ( (occupier[currentCountry] == occupier[toCountry]) && (!countriesChecked[currentCountry]) ) {
						found = isConnected(currentCountry,toCountry,countriesChecked);
					}
				}
			}
		}		
		return found;
	}
	
	public boolean isConnected (int fromCountry, int toCountry) {
		boolean [] countriesChecked = new boolean[GameData.NUM_COUNTRIES];
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			countriesChecked[i] = false;
		}
		countriesChecked[fromCountry] = true;
		return isConnected (fromCountry, toCountry, countriesChecked);
	}
	
	public boolean isOccupied(int country) {
		return occupied[country];
	}
	
	public boolean isInvasionSuccess () {
		return invasionSuccess;
	}
	
	public boolean isGameOver () {
		boolean gameOver = true;
		int firstOccupier = occupier[0];
		for (int i=1; (i<GameData.NUM_COUNTRIES) && gameOver; i++) {
			if  (occupier[i] != firstOccupier) {
				gameOver = false;
			}
		}
		winnerId = firstOccupier;
		return gameOver;
	}
	
	public int getOccupier (int country) {
		return occupier[country];
	}
	
	public int getNumUnits (int country) {
		return numUnits[country];
	}
	
	public int getWinner () {
		return winnerId;
	}

	public boolean checkOccupier(Player player, int countryId) {
		return (occupier[countryId] == player.getId());
	}
}
