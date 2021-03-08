package com.whyNotBot;
//Team Members:
//Mohamed Eltayeb Student Number:19349633
//Cian O'Reilly Student Number:19394833
//Tom Higgins Student Number: 19343176


public class Card {
	
	private int countryId;
	private String countryName;

	Card (int inCountryId, String inCountryName) {   
		countryId = inCountryId;
		countryName = inCountryName;
		return;
	}
	
	public int getCountryId () {
		return countryId;
	}
	
	public String getCountryName () {
		return countryName;
	}
	
}