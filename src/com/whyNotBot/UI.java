import java.awt.BorderLayout;
import javax.swing.JFrame;

public class UI {

	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 800;
	private static String PROMPT = "> ";

	private JFrame frame = new JFrame();
	private MapPanel mapPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	private Parse parse = new Parse();
	private Board board;
	
	UI (Board inBoard) {
		board = inBoard;
		mapPanel = new MapPanel(board);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Risk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel, BorderLayout.NORTH);
		frame.add(infoPanel, BorderLayout.CENTER);
		frame.add(commandPanel,BorderLayout.SOUTH);
		frame.setResizable(false);
		frame.setVisible(true);
		return;
	}
	
	public String makeLongName (Player player) {
		return player.getName() + " (" + mapPanel.getColorName(player.getId()) + ") ";
	}
	
	public void displayMap () {
		mapPanel.refresh();
		return;
	}

	public void displayString (String string) {
		infoPanel.addText(string);
		return;
	}
	
	public void displayName (int playerId, String name) {
		displayString("Neutral player " + (playerId+1) + " is " + mapPanel.getColorName(playerId));
		return;		
	}

	public void displayCardDraw (Player player, Card card) {
		displayString(makeLongName(player) + " draws the " + card.getCountryName() + " card");
		return;
	}
	
	public void displayDice (Player player) {
		displayString(makeLongName(player) + " rolls " + player.getDice() );
		return;
	}
	
	public void displayRollWinner (Player player) {
		displayString(makeLongName(player)  + " wins roll and goes first");
		return;
	}
	
	public String inputName (int playerId) {
		String response;
		displayString("Enter the name for player " + (playerId+1) + " (" + mapPanel.getColorName(playerId) + "):");
		response = commandPanel.getCommand();
		response.trim();
		displayString(PROMPT + response);
		return response;		
	}
		
	public void inputPlacement (Player byPlayer, Player forPlayer) {
		String response, message;
		boolean placementOK = false;
		do {
			message = makeLongName(byPlayer) + ": Enter a country to reinforce with ";
			if (byPlayer.equals(forPlayer)) {
				message += "your own units";				
			} else {
				message += makeLongName(forPlayer) + "'s units";
			}
			displayString(message);
			response = commandPanel.getCommand();
			displayString(PROMPT + response);
			parse.countryId(response);
			if (parse.isError()) {
				displayString("Error: Not a country");
			} else {
				if (!board.checkOccupier(forPlayer, parse.getCountryId())) {
					displayString("Error: Cannot place the units on that country");
				} else {
					placementOK = true;
				}
			}
		} while (!placementOK);
		return;
	}
	
	public int getCountryId () {
		return parse.getCountryId();
	}
	
}
