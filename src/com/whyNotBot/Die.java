package com.whyNotBot;
import java.util.Random;
public class Die {
	int diceNum;
	int rolledNum;
	int playerTurn;

	//initialises the dice number
	public Die(int diceNum) {
		this.diceNum = diceNum;
	}

	public int roll(int bound) {
		Random randomNumber = new Random(); 
		// Create new instance of Random class
		rolledNum = randomNumber.nextInt(bound) + 1;
		// Generate random number and return as roll
		return rolledNum;
	}
	public int getPlayerTurn(Die die2) {
		//using the dice to assign player number
		if(rolledNum > die2.rolledNum){

			//if player one rolls bigger die number its his turn
			playerTurn=1;
		}
		else{
			//else its player twos turn
			playerTurn=2;
		}
		return playerTurn;
	}

	public String compareTo(Die die2){
		/*
        If die1 is greater return 1
        if die2 is greater return 2
		 */
		if(rolledNum > die2.rolledNum){
			//string to inform user who's turn it is
			return "Player One rolled a " + rolledNum +". " +"Player Two rolled a " + die2.rolledNum +". " +"Player One assigns armies first.";
		}

		else{
			//string to inform user who's turn it is
			return "Player One rolled a " + rolledNum +". " +"Player Two rolled a " + die2.rolledNum +". " +"Player Two assigns armies first.";
		}
	}
}
