package app;

/**
 * Used to describe the final state of a Hangman game
 */
public class Game {
    private String word;
    private int tries;
    private boolean playerWon;

    /**
     * Class Constructor
     * Create a new Game Object with the given values
     * @param word the hidden word
     * @param tries how many tries the player took
     * @param playerWon if the value is true the player won, else the winner was the computer
     */
    public Game(String word, int tries, boolean playerWon) {
        this.setWord(word);
        this.setTries(tries);
        this.setWinner(playerWon);
        return;
    }

    /**
     * Getter function for word
     * @return String
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Setter function for word
     * @param word
     */
    public void setWord(String word) {
        this.word = word;
        return;
    } 

    /**
     * Getter function for tries
     * @return int
     */
    public int getTries() {
        return this.tries;
    }

    /**
     * Setter function for tries
     * @param tries
     */
    public void setTries(int tries) {
        this.tries = tries;
        return;
    }

    /**
     * Getter function for playerWon
     * @return boolean
     */
    public boolean PlayerWon() {
        return this.playerWon;
    }

    /**
     * Setter function for playerWon
     * @param playerWon
     */
    public void setWinner(boolean playerWon) {
        this.playerWon = playerWon;
        return;
    }
}
