import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.util.Pair;

public class Controller {
    
    private List<String> dictionary = new ArrayList<String>();
    private String chosenWord;


    public Controller(String id) {
        try {
            String filename = new String("./medialab/"+"hangman_"+id+".txt");
            File file = new File(filename);
            Scanner dictionaryReader = new Scanner(file);
            String entry;
            while (dictionaryReader.hasNextLine()) {
                entry = dictionaryReader.nextLine().strip();
                if (entry != "") {
                    dictionary.add(entry);
                }
            }
            dictionaryReader.close();
            System.out.println(dictionary);
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong while trying to open the dictionary file");
            e.printStackTrace();
        }
        chosenWord = getRandomWord(dictionary);
        System.out.println(chosenWord);
        int wordSize = chosenWord.length();
        List<String> wordsToRemove = new ArrayList<String>();
        for (String word : dictionary) {
            if (word.length() != wordSize) {
                wordsToRemove.add(word);
            }
        }
        dictionary.removeAll(wordsToRemove);
    
    }

    private String getRandomWord(List<String> dictionary) {
        Random rand = new Random();
        return dictionary.get(rand.nextInt(dictionary.size()));
    }

    protected void renewDictionary(Pair<Character, Integer>revealedLetter) {
        List<String> wordsToRemove = new ArrayList<String>();
        for (String word: dictionary){
            char revealedChar = revealedLetter.getKey();
            int position = revealedLetter.getValue();
            if (position != -1) {
                if (revealedChar != word.charAt(position)) {
                    wordsToRemove.add(word);
                }
            }
            else if (word.indexOf(revealedChar) != -1) {
                wordsToRemove.add(word);
            }
        }
        dictionary.removeAll(wordsToRemove);
        System.out.println(dictionary);
    }
}