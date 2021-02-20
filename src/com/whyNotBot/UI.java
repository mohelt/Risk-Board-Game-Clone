package com.whyNotBot;
import java.awt.BorderLayout;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UI {
	//file taken from the answer of sprint 1
	private static final int FRAME_WIDTH = 1000;
	private static final int FRAME_HEIGHT = 800;

	private JFrame frame = new JFrame();
	private MapPanel mapPanel;	
	private InfoPanel infoPanel = new InfoPanel();
	private CommandPanel commandPanel = new CommandPanel();
	private JDialog dialog = new JDialog();
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

	public void displayCardDrawn (String string) {
		URL url = SplashScreen.class.getResource(string);
		ImageIcon icon =new ImageIcon(url);
		JLabel label = new JLabel(icon);
		dialog.add( label );
		dialog.pack();
		dialog.setVisible(true);
	}
}
