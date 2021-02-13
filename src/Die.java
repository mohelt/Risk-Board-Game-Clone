import java.util.Random;

public class Die {
    int diceNum;
    int rolledNum;

    public Die(int diceNum) {
        this.diceNum = diceNum;
    }

    public void roll(int bound) {
        Random randomNumber = new Random(); // Create new instance of Random class
        rolledNum = randomNumber.nextInt(bound) + 1; // Generate random number and return as roll
    }
}
