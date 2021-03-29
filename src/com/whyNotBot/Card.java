package com.whyNotBot;

public class Card {
	
	private int countryId;
	private String countryName;
	private int unitTypeID; // integer reference to unit type of card
	private String unitType; // string representation of unit type of card
	private int numUnits;

	Card (int inCountryId, String inCountryName) {   
		countryId = inCountryId;
		countryName = inCountryName;
		unitTypeID = chooseUnitType();
		switch (unitTypeID){
			case 0:
				unitType = "Infantry";
				numUnits = 1;
				break;
			case 1:
				unitType = "Cavalry";
				numUnits = 5;
				break;
			case 2:
				unitType = "Artillery";
				numUnits = 10;
				break;
		}
		return;
	}
	
	public int getCountryId () {
		return countryId;
	}
	
	public String getCountryName () {
		return countryName;
	}

	public int getUnitTypeID(){
		return unitTypeID;
	}

	public String getUnitType(){
		return unitType;
	}

	public int getNumUnits(){
		return numUnits;
	}

	private int chooseUnitType(){
		return (int)(Math.random() * 3);
	}
	
}