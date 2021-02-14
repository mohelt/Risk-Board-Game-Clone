import java.util.Random;
public class Die {
    int diceNum;
    int rolledNum;
    int playerTurn;
    
    
    public Die(int diceNum) {
        this.diceNum = diceNum;
    }

    public void roll(int bound) {
        Random randomNumber = new Random(); // Create new instance of Random class
        rolledNum = randomNumber.nextInt(bound) + 1; // Generate random number and return as roll
    }
    public int getPlayerTurn(Die die2) {
    	if(rolledNum > die2.rolledNum){
    		playerTurn=1;
         }
        else{
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
            return "Player One rolled a " + rolledNum +". " +"Player Two rolled a " + die2.rolledNum +". " +"Player One assigns armies first.";
        }

        else{
            return "Player One rolled a " + rolledNum +". " +"Player Two rolled a " + die2.rolledNum +". " +"Player Two assigns armies first.";
        }
    }
}
