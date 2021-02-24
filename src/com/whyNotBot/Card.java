
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