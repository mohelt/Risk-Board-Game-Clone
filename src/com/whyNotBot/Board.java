package com.whyNotBot;
public class Board {
	
	private boolean[] occupied = new boolean [GameData.NUM_COUNTRIES];
	private int[] occupier = new int [GameData.NUM_COUNTRIES];
	private int[] numUnits = new int [GameData.NUM_COUNTRIES];
	
	Board() {
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			occupied[i] = false ;
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
	
	public boolean checkOccupier (Player player, int countryId) {
		return (occupier[countryId] == player.getId());
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

}
