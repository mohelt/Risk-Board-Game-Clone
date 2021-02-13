
public class Sprint1 {

	public static void main (String args[]) {	   
		Board board = new Board();
		UI ui = new UI(board);
		int playerId, countryId;
		String name;
		
		// display blank board
		ui.displayMap();
		
		// get player names
		for (playerId=0; playerId<GameData.NUM_PLAYERS; playerId++) {
			ui.displayString("Enter the name of player " + (playerId+1));
			name = ui.getCommand();
			ui.displayString("> " + name);
		}
	
		// add units
		countryId = 0;
		for (playerId=0; playerId<GameData.NUM_PLAYERS; playerId++) {
			for (int i=0; i<GameData.INIT_COUNTRIES_PLAYER; i++) {
				board.addUnits(countryId, playerId, 1);
				countryId++;
			}
		}
		for (; playerId<GameData.NUM_PLAYERS_PLUS_NEUTRALS; playerId++) {
			for (int i=0; i<GameData.INIT_COUNTRIES_NEUTRAL; i++) {
				board.addUnits(countryId, playerId, 1);
				countryId++;
			}
		}		

		// display map
		ui.displayMap();

		// Create two dice for game
		Die die1 = new Die(1);
		Die die2 = new Die(2);
		// roll both dice
		// Continue to roll until numbers are different
		do {
			die1.roll(6);
			die2.roll(6);
		} while (die1.rolledNum == die2.rolledNum);

		// Display that player with higher roll
		ui.displayString(Integer.toString(die1.compareTo(die2)));

		return;
	}
}

