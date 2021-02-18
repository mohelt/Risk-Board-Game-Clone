import java.awt.BorderLayout;
import javax.swing.JFrame;

public class UI {

	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 800;
	
	private JFrame frame = new JFrame();
	private MapPanel mapPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	
	UI (Board board) {
		mapPanel = new MapPanel(board);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setTitle("Risk");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(mapPanel, BorderLayout.NORTH);
		frame.add(infoPanel, BorderLayout.CENTER);
		frame.add(commandPanel,BorderLayout.SOUTH);
		frame.setResizable(true);
		frame.setVisible(true);
		return;
	}
	
	public String getCommand () {
		return commandPanel.getCommand();
	}

	public void displayMap () {
		mapPanel.refresh();
		return;
	}

	public void displayString (String string) {
		infoPanel.addText(string);
		return;
	}
	public void displayStringCountryNames() {
		infoPanel.addText("\r\n" + 
				"		\"Ontario\",\"Quebec\",\"NW Territory\",\"Alberta\",\"Greenland\",\"E United States\",\"W United States\",\"Central America\",\"Alaska\",\r\n" + 
				"		\"Great Britain\",\"W Europe\",\"S Europe\",\"Ukraine\",\"N Europe\",\"Iceland\",\"Scandinavia\",\r\n" + 
				"		\"Afghanistan\",\"India\",\"Middle East\",\"Japan\",\"Ural\",\"Yakutsk\",\"Kamchatka\",\"Siam\",\"Irkutsk\",\"Siberia\",\"Mongolia\",\"China\",\r\n" + 
				"		\"E Australia\",\"New Guinea\",\"W Australia\",\"Indonesia\",\r\n" + 
				"		\"Venezuela\",\"Peru\",\"Brazil\",\"Argentina\",\r\n" + 
				"		\"Congo\",\"N Africa\",\"S Africa\",\"Egypt\",\"E Africa\",\"Madagascar\""
				);
		
		return;
	}
}
