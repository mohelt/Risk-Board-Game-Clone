
public class Parse {
	
	private String[] countryCodes = new String[GameData.NUM_COUNTRIES];
	private boolean isError = false;
	private int countryId = 0;
	private boolean isDone = false;
	private int numUnits;
	
	Parse () {
		String name;
		for (int i=0; i<GameData.NUM_COUNTRIES; i++) {
			name = GameData.COUNTRY_NAMES[i];
			name = name.toLowerCase();
			name = name.replaceAll("\\s","");
			name = name.substring(0,4);
			countryCodes[i] = name;
		}
		return;
	}
	
	public void countryId (String string) {
		boolean found = false;
		string = string.replaceAll("\\s", "");
		if (string.length() >= 4) {
			string = string.toLowerCase();
			string = string.substring(0,4);
	 		for (int i=0; (i<GameData.NUM_COUNTRIES) && !found; i++) {
				if (string.equals(countryCodes[i])) {
					found = true;
					countryId = i;
				}
	 		}
		}
		isError = !found;
		return;
	}
	
	public boolean isError () {
		return isError;
	}
	
	public boolean isDone () {
		return isDone;
	}
	
	public int getCountryId () {
		return countryId;
	}
	
	public int getNumUnits () {
		return numUnits;
	}

}
