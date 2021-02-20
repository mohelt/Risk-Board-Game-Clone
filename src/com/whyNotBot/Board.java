package com.whyNotBot;

public class Board {
	
	private boolean[] occupied = new boolean [GameData.NUM_COUNTRIES];
	private int[] occupier = new int [GameData.NUM_COUNTRIES];
	private int[] numUnits = new int [GameData.NUM_COUNTRIES];
	private int[] playerArmies = new int[GameData.NUM_PLAYERS_PLUS_NEUTRALS];
	Board() {
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			occupied[i] = false;
			numUnits[i] = 0;
		}
		playerArmies[0]= GameData.PLAYERS_NUM_ARMIES + 9;
		playerArmies[1]= GameData.PLAYERS_NUM_ARMIES + 9;
		playerArmies[2]= GameData.NEUTRALS_NUM_ARMIES + 6;
		playerArmies[3]= GameData.NEUTRALS_NUM_ARMIES + 6;
		playerArmies[4]= GameData.NEUTRALS_NUM_ARMIES + 6;
		playerArmies[5]= GameData.NEUTRALS_NUM_ARMIES + 6;
		return;
	}
	
	public void addUnits (int country, int player, int addNumUnits) {	
		// prerequisite: country must be unoccupied or already occupied by this player
		if (!occupied[country]) {
			occupied[country] = true;
			occupier[country] = player;
		}
		numUnits[country] = numUnits[country] + addNumUnits;
		playerArmies[player]-= addNumUnits;
		return;
	}
	
	public boolean isOccupied(int country) {
		return occupied[country];
	}
	
	public int getOccupier (int country) {
		return occupier[country];
	}
	
	public int getNumUnits (int country) {
		return numUnits[country];
	}
	public String getPlayerArmies (int playerNumber) {
		return Integer.toString(playerArmies[playerNumber]);
	}
	public int getPlayerArmiesNumber (int playerNumber) {
		return playerArmies[playerNumber];
	}
}
