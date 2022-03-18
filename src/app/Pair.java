package app;

import java.util.Arrays;
import java.util.Comparator;

public class Pair {
    private char letter;
    private int probability;

    public Pair (char letter, int probability) {
        this.setLetter(letter);
        this.setProbability(probability);
    }

    public char getLetter() {
        return this.letter;
    }

    public void setLetter(char letter) {
        this.letter = letter;
        return;
    } 

    public int getProbability() {
        return this.probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
        return;
    } 

    public static void sortPair(Pair arr[], int n)
    {
        Arrays.sort(arr, new Comparator<Pair>() {
            @Override public int compare(Pair p1, Pair p2)
            {
                return p2.getProbability() - p1.getProbability();
            }
        });
    }
}

