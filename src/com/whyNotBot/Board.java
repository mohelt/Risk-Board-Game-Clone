package com.whyNotBot;
public class Board {
	
	private boolean[] occupied = new boolean [GameData.NUM_COUNTRIES];
	public int[] occupier = new int [GameData.NUM_COUNTRIES];
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
	public void addUnitsTakeOver(int countryId, int player, int addNumUnits) {
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
	
	public int getNumTerritories(int player) {
		int numTerritories =0;
		for(int i =0;i<GameData.NUM_COUNTRIES;i++) {
			if (occupier[i]==player){
				numTerritories++;
			}
		}
		return numTerritories;
	}
	
	public int numOfArmies(Player[] playerArray,int player) {
		int numOfArmies = 0;
		if(playerArray[player].getNumUnits() <9) {
			return 3
			 +ownsNorthAmerica(player)
			 +ownsEurope(player)
			 +ownsAsia(player)
			 +ownsAfrica(player)
			 +ownsSouthAmerica(player)
			 +ownsAustralia(player);
		}else {
		 numOfArmies = (this.getNumTerritories(player)/3) 
				 +ownsNorthAmerica(player)
				 +ownsEurope(player)
				 +ownsAsia(player)
				 +ownsAfrica(player)
				 +ownsSouthAmerica(player)
				 +ownsAustralia(player);
		}
		return numOfArmies;
	}
	public int ownsNorthAmerica(int player) {
		for(int i =0;i<=8;i++) {
			if (occupier[i]!=player){
				return 0;
			}
		}
		return 5;
	}
	public int ownsEurope(int player) {
		for(int i =9;i<=15;i++) {
			if (occupier[i]!=player){
				return 0;
			}
		}
		return 5;
	}
	public int ownsAsia(int player) {
		for(int i =16;i<=27;i++) {
			if (occupier[i]!=player){
				return 0;
			}
		}
		return 7;
	}
	public int ownsAustralia(int player) {
		for(int i =28;i<=31;i++) {
			if (occupier[i]!=player){
				return 0;
			}
		}
		return 2;
	}
	public int ownsSouthAmerica(int player) {
		for(int i =32;i<=35;i++) {
			if (occupier[i]!=player){
				return 0;
			}
		}
		return 2;
	}
	public int ownsAfrica(int player) {
		for(int i =36;i<=41;i++) {
			if (occupier[i]!=player){
				return 0;
			}
		}
		return 3;
	}
}
