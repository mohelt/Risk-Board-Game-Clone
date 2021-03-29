package com.whyNotBot;

public class Card {
	
	private int countryId;
	private String countryName;
	private int unitTypeID; // integer reference to unit type of card
	private String unitType; // string representation of unit type of card

	Card (int inCountryId, String inCountryName) {   
		countryId = inCountryId;
		countryName = inCountryName;
		unitTypeID = chooseUnitType();
		switch (unitTypeID){
			case 0:
				unitType = "Infantry";
				break;
			case 1:
				unitType = "Cavalry";
				break;
			case 2:
				unitType = "Artillary";
		}
		return;
	}
	
	public int getCountryId () {
		return countryId;
	}
	
	public String getCountryName () {
		return countryName;
	}

	private int chooseUnitType(){
		return (int)(Math.random() * 3);
	}
	
}