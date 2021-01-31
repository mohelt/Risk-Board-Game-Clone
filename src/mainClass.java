//Group WhyNotBot
import java.lang.*;
import java.util.*;
import java.util.List;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

public class mainClass extends JPanel implements ActionListener {

	//constant variables to use

	static String playerOneName;
	static String playerTwoName;
	static boolean playerOneNameSet;
	static boolean nameSetUpDone= false;
	public static final int NUM_PLAYERS = 2;
	public static final int NUM_NEUTRALS = 4;
	public static final int NUM_PLAYERS_PLUS_NEUTRALS = NUM_PLAYERS + NUM_NEUTRALS;
	public static final int NUM_COUNTRIES = 42;
	public static final int INIT_COUNTRIES_PLAYER = 9;
	public static final int INIT_COUNTRIES_NEUTRAL = 6;
	public static final int INIT_UNITS_PLAYER = 36;
	public static final int INIT_UNITS_NEUTRAL = 24;
	public static final String[] COUNTRY_NAMES = {
			"Ontario","Quebec","NW Territory","Alberta","Greenland","E United States","W United States","Central America","Alaska",
			"Great Britain","W Europe","S Europe","Ukraine","N Europe","Iceland","Scandinavia",
			"Afghanistan","India","Middle East","Japan","Ural","Yakutsk","Kamchatka","Siam","Irkutsk","Siberia","Mongolia","China",
			"E Australia","New Guinea","W Australia","Indonesia",
			"Venezuela","Peru","Brazil","Argentina",
			"Congo","N Africa","S Africa","Egypt","E Africa","Madagascar"};  
	// for reference
	public static final int[][] ADJACENT = { 
			{4,1,5,6,3,2},    // 0
			{4,5,0},
			{4,0,3,8},
			{2,0,6,8},
			{14,1,0,2},
			{0,1,7,6}, 
			{3,0,5,7},
			{6,5,32},
			{2,3,22},
			{14,15,13,10},    
			{9,13,11,37},     // 10
			{13,12,18,39,10},
			{20,16,18,11,13,15},
			{15,12,11,10,9},
			{15,9,4},
			{12,13,14},
			{20,27,17,18,12}, 
			{16,27,23,18},
			{12,16,17,40,39,11},
			{26,22},
			{25,27,16,12},    // 20
			{22,24,25},        
			{8,19,26,24,21},
			{27,31,17},
			{21,22,26,25},
			{21,24,26,27,20},
			{24,22,19,27,25},
			{26,23,17,16,20,25},
			{29,30},          
			{28,30,31},
			{29,28,31},      // 30
			{23,29,30},
			{7,34,33},       
			{32,34,35},
			{32,37,35,33},
			{33,34},
			{37,40,38},      
			{10,11,39,40,36,34},
			{36,40,41},
			{11,18,40,37},
			{39,18,41,38,36,37},  //40
			{38,40}
	};
	public static final int NUM_CONTINENTS = 6;
	public static final String[] CONTINENT_NAMES = {"N America","Europe","Asia","Australia","S America","Africa"};  // for reference 
	public static final int[] CONTINENT_IDS = {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,2,2,3,3,3,3,4,4,4,4,5,5,5,5,5,5};
	public static final int[] CONTINENT_VALUES = {5,5,7,2,2,3};
	public static final int[][] COUNTRY_COORD = {
			{191,150},     // 0
			{255,161},
			{146,86},
			{123,144},
			{314,61},
			{205,235},
			{135,219},
			{140,299},
			{45,89},
			{370,199},
			{398,280},      // 10
			{465,270},
			{547,180},
			{460,200},
			{393,127},
			{463,122},
			{628,227},
			{679,332},
			{572,338},
			{861,213},
			{645,152},      // 20
			{763,70},
			{827,94},
			{751,360},
			{750,140},
			{695,108},
			{760,216},
			{735,277},
			{889,537},
			{850,429},
			{813,526},       // 30
			{771,454},
			{213,352},
			{221,426},
			{289,415},
			{233,523},
			{496,462},
			{440,393},
			{510,532},
			{499,354},
			{547,432},        // 40
			{586,545}
	};


	private static final long serialVersionUID = 1L;

	//default constructor
	public mainClass() {

	}

	//paint components 
	public void paintComponent(Graphics g) {

		//set background color to white
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		if(nameSetUpDone ==false) {
			//iterating through array to correctly position nodes and country string names and colors
			for(int i =0; i<COUNTRY_COORD.length;i++) {

				//coloring the continents
				if(i<=8) {
					g.setColor(Color.yellow);
				}
				if(i>8 && i <=15) {
					g.setColor(Color.blue);
				}
				if(i>=16 && i <=27) {
					g.setColor(Color.green);

				}if(i>=28 && i <=31) {
					g.setColor(Color.black);
				}
				if(i>=32 && i <=35) {
					g.setColor(Color.orange);
				}
				if(i>=36 && i <=41) {
					g.setColor(Color.red);
				}

				//creating the circular nodes
				g.fillOval(COUNTRY_COORD[i][0],COUNTRY_COORD[i][1],20,20);	

				//setting the text color to black
				g.setColor(Color.black);

				//writing the country names
				g.drawString(COUNTRY_NAMES[i],(COUNTRY_COORD[i][0]-10),(COUNTRY_COORD[i][1]-10));

			}
		}else {
			
			// if nameSetUpDone is true i.e we are done setting up the names
			// then assign each player 9 territories and each of the 4 neutral players 6 territories
			
			for(int i =0; i<COUNTRY_COORD.length;i++) {
				//coloring the continents for each of the players
				if(i<=8) {
					g.setColor(Color.yellow);
				}
				if(i>8 && i <=17) {
					g.setColor(Color.blue);
				}
				if(i>17 && i <=23) {
					g.setColor(Color.green);

				}if(i>23 && i <=29) {
					g.setColor(Color.black);
				}
				if(i>29 && i <=35) {
					g.setColor(Color.orange);
				}
				if(i>35 && i <=41) {
					g.setColor(Color.red);
				}
				//creating the circular nodes
				g.fillOval(COUNTRY_COORD[i][0],COUNTRY_COORD[i][1],20,20);	

				//setting the text color to black
				g.setColor(Color.black);

				//writing the country names
				g.drawString(COUNTRY_NAMES[i],(COUNTRY_COORD[i][0]-10),(COUNTRY_COORD[i][1]-10));

				//writing the armies underneath the names of the countries
				g.drawString("1",(COUNTRY_COORD[i][0]+-5),(COUNTRY_COORD[i][1]+30));
			}
		}


		//making the lines dashed
		Graphics2D g2d = (Graphics2D) g;
		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g.setColor(Color.black);

		//diagonal lines intersecting nodes 
		g.drawLine(COUNTRY_COORD[29][0]+10,COUNTRY_COORD[29][1], COUNTRY_COORD[31][0]+10, COUNTRY_COORD[31][1]);
		g.drawLine(COUNTRY_COORD[29][0]+10,COUNTRY_COORD[29][1], COUNTRY_COORD[30][0]+10, COUNTRY_COORD[30][1]);
		g.drawLine(COUNTRY_COORD[30][0]+10,COUNTRY_COORD[30][1], COUNTRY_COORD[31][0]+10, COUNTRY_COORD[31][1]);
		g.drawLine(COUNTRY_COORD[30][0]+10,COUNTRY_COORD[30][1], COUNTRY_COORD[28][0]+10, COUNTRY_COORD[28][1]);
		g.drawLine(COUNTRY_COORD[29][0]+10,COUNTRY_COORD[29][1], COUNTRY_COORD[28][0]+10, COUNTRY_COORD[28][1]);
		g.drawLine(COUNTRY_COORD[23][0]+10,COUNTRY_COORD[23][1], COUNTRY_COORD[31][0]+10, COUNTRY_COORD[31][1]);
		g.drawLine(COUNTRY_COORD[23][0]+10,COUNTRY_COORD[23][1], COUNTRY_COORD[27][0]+10, COUNTRY_COORD[27][1]);
		g.drawLine(COUNTRY_COORD[23][0]+10,COUNTRY_COORD[23][1], COUNTRY_COORD[17][0]+10, COUNTRY_COORD[17][1]);
		g.drawLine(COUNTRY_COORD[27][0]+10,COUNTRY_COORD[27][1], COUNTRY_COORD[17][0]+10, COUNTRY_COORD[17][1]);
		g.drawLine(COUNTRY_COORD[17][0]+10,COUNTRY_COORD[17][1], COUNTRY_COORD[18][0]+10, COUNTRY_COORD[18][1]);
		g.drawLine(COUNTRY_COORD[16][0]+10,COUNTRY_COORD[16][1], COUNTRY_COORD[17][0]+10, COUNTRY_COORD[17][1]);
		g.drawLine(COUNTRY_COORD[27][0]+10,COUNTRY_COORD[27][1], COUNTRY_COORD[16][0]+10, COUNTRY_COORD[16][1]);
		g.drawLine(COUNTRY_COORD[16][0]+10,COUNTRY_COORD[16][1], COUNTRY_COORD[18][0]+10, COUNTRY_COORD[18][1]);
		g.drawLine(COUNTRY_COORD[12][0]+10,COUNTRY_COORD[12][1], COUNTRY_COORD[18][0]+10, COUNTRY_COORD[18][1]);
		g.drawLine(COUNTRY_COORD[16][0]+10,COUNTRY_COORD[16][1], COUNTRY_COORD[12][0]+10, COUNTRY_COORD[12][1]);
		g.drawLine(COUNTRY_COORD[16][0]+10,COUNTRY_COORD[16][1], COUNTRY_COORD[20][0]+10, COUNTRY_COORD[20][1]);
		g.drawLine(COUNTRY_COORD[20][0]+10,COUNTRY_COORD[20][1], COUNTRY_COORD[27][0]+10, COUNTRY_COORD[27][1]);
		g.drawLine(COUNTRY_COORD[12][0]+10,COUNTRY_COORD[12][1], COUNTRY_COORD[20][0]+10, COUNTRY_COORD[20][1]);
		g.drawLine(COUNTRY_COORD[41][0]+10,COUNTRY_COORD[41][1], COUNTRY_COORD[38][0]+10, COUNTRY_COORD[38][1]);
		g.drawLine(COUNTRY_COORD[41][0]+10,COUNTRY_COORD[41][1], COUNTRY_COORD[40][0]+10, COUNTRY_COORD[40][1]);
		g.drawLine(COUNTRY_COORD[38][0]+10,COUNTRY_COORD[38][1], COUNTRY_COORD[40][0]+10, COUNTRY_COORD[40][1]);
		g.drawLine(COUNTRY_COORD[38][0]+10,COUNTRY_COORD[38][1], COUNTRY_COORD[36][0]+10, COUNTRY_COORD[36][1]);
		g.drawLine(COUNTRY_COORD[40][0]+10,COUNTRY_COORD[40][1], COUNTRY_COORD[36][0]+10, COUNTRY_COORD[36][1]);
		g.drawLine(COUNTRY_COORD[37][0]+10,COUNTRY_COORD[37][1], COUNTRY_COORD[36][0]+10, COUNTRY_COORD[36][1]);
		g.drawLine(COUNTRY_COORD[37][0]+10,COUNTRY_COORD[37][1], COUNTRY_COORD[40][0]+10, COUNTRY_COORD[40][1]);
		g.drawLine(COUNTRY_COORD[37][0]+10,COUNTRY_COORD[37][1], COUNTRY_COORD[39][0]+10, COUNTRY_COORD[39][1]);
		g.drawLine(COUNTRY_COORD[39][0]+10,COUNTRY_COORD[39][1], COUNTRY_COORD[40][0]+10, COUNTRY_COORD[40][1]);
		g.drawLine(COUNTRY_COORD[39][0]+10,COUNTRY_COORD[39][1], COUNTRY_COORD[18][0]+10, COUNTRY_COORD[18][1]);
		g.drawLine(COUNTRY_COORD[40][0]+10,COUNTRY_COORD[40][1], COUNTRY_COORD[18][0]+10, COUNTRY_COORD[18][1]);
		g.drawLine(COUNTRY_COORD[32][0]+10,COUNTRY_COORD[32][1], COUNTRY_COORD[34][0]+10, COUNTRY_COORD[34][1]);
		g.drawLine(COUNTRY_COORD[32][0]+10,COUNTRY_COORD[32][1], COUNTRY_COORD[33][0]+10, COUNTRY_COORD[33][1]);
		g.drawLine(COUNTRY_COORD[34][0]+10,COUNTRY_COORD[34][1], COUNTRY_COORD[33][0]+10, COUNTRY_COORD[33][1]);
		g.drawLine(COUNTRY_COORD[33][0]+10,COUNTRY_COORD[33][1], COUNTRY_COORD[35][0]+10, COUNTRY_COORD[35][1]);
		g.drawLine(COUNTRY_COORD[34][0]+10,COUNTRY_COORD[34][1], COUNTRY_COORD[35][0]+10, COUNTRY_COORD[35][1]);
		g.drawLine(COUNTRY_COORD[32][0]+10,COUNTRY_COORD[32][1], COUNTRY_COORD[7][0]+10, COUNTRY_COORD[7][1]);
		g.drawLine(COUNTRY_COORD[7][0]+10,COUNTRY_COORD[7][1], COUNTRY_COORD[6][0]+10, COUNTRY_COORD[6][1]);
		g.drawLine(COUNTRY_COORD[7][0]+10,COUNTRY_COORD[7][1], COUNTRY_COORD[5][0]+10, COUNTRY_COORD[5][1]);
		g.drawLine(COUNTRY_COORD[6][0]+10,COUNTRY_COORD[6][1], COUNTRY_COORD[5][0]+10, COUNTRY_COORD[5][1]);
		g.drawLine(COUNTRY_COORD[5][0]+10,COUNTRY_COORD[5][1], COUNTRY_COORD[1][0]+10, COUNTRY_COORD[1][1]);
		g.drawLine(COUNTRY_COORD[1][0]+10,COUNTRY_COORD[1][1], COUNTRY_COORD[0][0]+10, COUNTRY_COORD[0][1]);
		g.drawLine(COUNTRY_COORD[0][0]+10,COUNTRY_COORD[0][1], COUNTRY_COORD[3][0]+10, COUNTRY_COORD[3][1]);
		g.drawLine(COUNTRY_COORD[5][0]+10,COUNTRY_COORD[5][1], COUNTRY_COORD[0][0]+10, COUNTRY_COORD[0][1]);
		g.drawLine(COUNTRY_COORD[6][0]+10,COUNTRY_COORD[6][1], COUNTRY_COORD[0][0]+10, COUNTRY_COORD[0][1]);
		g.drawLine(COUNTRY_COORD[6][0]+10,COUNTRY_COORD[6][1], COUNTRY_COORD[3][0]+10, COUNTRY_COORD[3][1]);
		g.drawLine(COUNTRY_COORD[8][0]+10,COUNTRY_COORD[8][1], COUNTRY_COORD[3][0]+10, COUNTRY_COORD[3][1]);
		g.drawLine(COUNTRY_COORD[2][0]+10,COUNTRY_COORD[2][1], COUNTRY_COORD[3][0]+10, COUNTRY_COORD[3][1]);
		g.drawLine(COUNTRY_COORD[8][0]+10,COUNTRY_COORD[8][1], COUNTRY_COORD[2][0]+10, COUNTRY_COORD[2][1]);
		g.drawLine(COUNTRY_COORD[0][0]+10,COUNTRY_COORD[0][1], COUNTRY_COORD[2][0]+10, COUNTRY_COORD[2][1]);
		g.drawLine(COUNTRY_COORD[2][0]+10,COUNTRY_COORD[2][1], COUNTRY_COORD[4][0]+10, COUNTRY_COORD[4][1]);
		g.drawLine(COUNTRY_COORD[1][0]+10,COUNTRY_COORD[1][1], COUNTRY_COORD[4][0]+10, COUNTRY_COORD[4][1]);
		g.drawLine(COUNTRY_COORD[4][0]+10,COUNTRY_COORD[4][1], COUNTRY_COORD[14][0]+10, COUNTRY_COORD[14][1]);
		g.drawLine(COUNTRY_COORD[4][0]+10,COUNTRY_COORD[4][1], COUNTRY_COORD[0][0]+10, COUNTRY_COORD[0][1]);
		g.drawLine(COUNTRY_COORD[10][0]+10,COUNTRY_COORD[10][1], COUNTRY_COORD[11][0]+10, COUNTRY_COORD[11][1]);
		g.drawLine(COUNTRY_COORD[10][0]+10,COUNTRY_COORD[10][1], COUNTRY_COORD[37][0]+10, COUNTRY_COORD[37][1]);
		g.drawLine(COUNTRY_COORD[37][0]+10,COUNTRY_COORD[37][1], COUNTRY_COORD[11][0]+10, COUNTRY_COORD[11][1]);
		g.drawLine(COUNTRY_COORD[11][0]+10,COUNTRY_COORD[11][1], COUNTRY_COORD[39][0]+10, COUNTRY_COORD[39][1]);
		g.drawLine(COUNTRY_COORD[11][0]+10,COUNTRY_COORD[11][1], COUNTRY_COORD[13][0]+10, COUNTRY_COORD[13][1]);
		g.drawLine(COUNTRY_COORD[13][0]+10,COUNTRY_COORD[13][1], COUNTRY_COORD[15][0]+10, COUNTRY_COORD[15][1]);
		g.drawLine(COUNTRY_COORD[15][0]+10,COUNTRY_COORD[15][1], COUNTRY_COORD[12][0]+10, COUNTRY_COORD[12][1]);
		g.drawLine(COUNTRY_COORD[14][0]+10,COUNTRY_COORD[14][1], COUNTRY_COORD[15][0]+10, COUNTRY_COORD[15][1]);
		g.drawLine(COUNTRY_COORD[15][0]+10,COUNTRY_COORD[15][1], COUNTRY_COORD[9][0]+10, COUNTRY_COORD[9][1]);
		g.drawLine(COUNTRY_COORD[14][0]+10,COUNTRY_COORD[14][1], COUNTRY_COORD[9][0]+10, COUNTRY_COORD[9][1]);
		g.drawLine(COUNTRY_COORD[11][0]+10,COUNTRY_COORD[11][1], COUNTRY_COORD[12][0]+10, COUNTRY_COORD[12][1]);
		g.drawLine(COUNTRY_COORD[10][0]+10,COUNTRY_COORD[10][1], COUNTRY_COORD[13][0]+10, COUNTRY_COORD[13][1]);
		g.drawLine(COUNTRY_COORD[10][0]+10,COUNTRY_COORD[10][1], COUNTRY_COORD[9][0]+10, COUNTRY_COORD[9][1]);
		g.drawLine(COUNTRY_COORD[9][0]+10,COUNTRY_COORD[9][1], COUNTRY_COORD[13][0]+10, COUNTRY_COORD[13][1]);
		g.drawLine(COUNTRY_COORD[12][0]+10,COUNTRY_COORD[12][1], COUNTRY_COORD[13][0]+10, COUNTRY_COORD[13][1]);
		g.drawLine(COUNTRY_COORD[27][0]+10,COUNTRY_COORD[27][1], COUNTRY_COORD[19][0]+10, COUNTRY_COORD[19][1]);
		g.drawLine(COUNTRY_COORD[27][0]+10,COUNTRY_COORD[27][1], COUNTRY_COORD[26][0]+10, COUNTRY_COORD[26][1]);
		g.drawLine(COUNTRY_COORD[19][0]+10,COUNTRY_COORD[19][1], COUNTRY_COORD[26][0]+10, COUNTRY_COORD[26][1]);
		g.drawLine(COUNTRY_COORD[24][0]+10,COUNTRY_COORD[24][1], COUNTRY_COORD[26][0]+10, COUNTRY_COORD[26][1]);
		g.drawLine(COUNTRY_COORD[25][0]+10,COUNTRY_COORD[25][1], COUNTRY_COORD[24][0]+10, COUNTRY_COORD[24][1]);
		g.drawLine(COUNTRY_COORD[26][0]+10,COUNTRY_COORD[26][1], COUNTRY_COORD[25][0]+10, COUNTRY_COORD[25][1]);
		g.drawLine(COUNTRY_COORD[25][0]+10,COUNTRY_COORD[25][1], COUNTRY_COORD[21][0]+10, COUNTRY_COORD[21][1]);
		g.drawLine(COUNTRY_COORD[21][0]+10,COUNTRY_COORD[21][1], COUNTRY_COORD[19][0]+10, COUNTRY_COORD[19][1]);
		g.drawLine(COUNTRY_COORD[22][0]+10,COUNTRY_COORD[22][1], COUNTRY_COORD[21][0]+10, COUNTRY_COORD[21][1]);
		g.drawLine(COUNTRY_COORD[19][0]+10,COUNTRY_COORD[19][1], COUNTRY_COORD[22][0]+10, COUNTRY_COORD[22][1]);
		g.drawLine(COUNTRY_COORD[24][0]+10,COUNTRY_COORD[24][1], COUNTRY_COORD[19][0]+10, COUNTRY_COORD[19][1]);
		g.drawLine(COUNTRY_COORD[26][0]+10,COUNTRY_COORD[26][1], COUNTRY_COORD[20][0]+10, COUNTRY_COORD[20][1]);
		g.drawLine(COUNTRY_COORD[24][0]+10,COUNTRY_COORD[24][1], COUNTRY_COORD[21][0]+10, COUNTRY_COORD[21][1]);
		g.drawLine(COUNTRY_COORD[18][0]+10,COUNTRY_COORD[18][1], COUNTRY_COORD[11][0]+10, COUNTRY_COORD[11][1]);
		g.drawLine(COUNTRY_COORD[21][0]+10,COUNTRY_COORD[21][1], 1000, COUNTRY_COORD[22][1]);
		g.drawLine(0,COUNTRY_COORD[22][1], COUNTRY_COORD[8][0]+10, COUNTRY_COORD[8][1]);

	}

	public static void main(String[] args) {

		// title of frame
		JFrame frame = new JFrame("Risk");

		//Creating a text field
		JTextField textField = new JTextField(20);
		frame.add(textField, BorderLayout.SOUTH);

		JLabel welcome = new JLabel("");
		welcome.setFont (welcome.getFont ().deriveFont (20.0f));
		welcome.setText("Please Enter name for Player 1 in the text box at the bottom");
		frame.add(welcome,BorderLayout.NORTH);

		//action listener listens for enter key
		textField.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{

				//checks if player Ones name is set and sets player Twos name aswell
				if (!playerOneNameSet)
				{
					playerOneName = textField.getText();
					textField.setText("");
					welcome.setText("Please Enter name for Player 2 in the text box at the bottom");
					playerOneNameSet = true;
				}
				else
				{
					playerTwoName = textField.getText();
					textField.setText("");  
					
					//turning the nameSetUpDone to true as we are finished setting up the names
					nameSetUpDone = true;
					
					// repainting as we want to update the screen
			        frame.getContentPane().repaint();
					welcome.setText("Player One:" + playerOneName +" Player Two:" + playerTwoName + " Awaiting player one move");
					
				}
			}
		});



		// make sure it closes correctly
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//frame size in pixels
		final int FRAME_WIDTH = 1000;    
		final int FRAME_HEIGHT = 700;
		frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);

		// makes sure the frame is visible
		frame.setVisible(true);
		mainClass main = new mainClass();
		frame.add(main);


	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}


}
