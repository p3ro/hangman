package app;

import java.util.Random;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class Controller {

    //for menu scene
    @FXML
    private TextField idTextField;
    @FXML
    private Label messageLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Button playButton;
    @FXML
    private Button dictionaryButton;

    private final String REST_URL = "http://www.openlibrary.org/works/";

    //for game scene
    @FXML
    private Label wordLabel;

    @FXML
    private Button QButton;
    @FXML
    private Button WButton;
    @FXML
    private Button EButton;
    @FXML
    private Button RButton;
    @FXML
    private Button TButton;
    @FXML
    private Button YButton;
    @FXML
    private Button UButton;
    @FXML
    private Button IButton;
    @FXML
    private Button OButton;
    @FXML
    private Button PButton;
    @FXML
    private Button AButton;
    @FXML
    private Button SButton;
    @FXML
    private Button DButton;
    @FXML
    private Button FButton;
    @FXML
    private Button GButton;
    @FXML
    private Button HButton;
    @FXML
    private Button JButton;
    @FXML
    private Button KButton;
    @FXML
    private Button LButton;
    @FXML
    private Button ZButton;
    @FXML
    private Button XButton;
    @FXML
    private Button CButton;
    @FXML
    private Button VButton;
    @FXML
    private Button BButton;
    @FXML
    private Button NButton;
    @FXML
    private Button MButton;

    @FXML
    private TextField qTextField;
    @FXML
    private TextField wTextField;
    @FXML
    private TextField eTextField;
    @FXML
    private TextField rTextField;
    @FXML
    private TextField tTextField;
    @FXML
    private TextField yTextField;
    @FXML
    private TextField uTextField;
    @FXML
    private TextField iTextField;
    @FXML
    private TextField oTextField;
    @FXML
    private TextField pTextField;
    @FXML
    private TextField aTextField;
    @FXML
    private TextField sTextField;
    @FXML
    private TextField dTextField;
    @FXML
    private TextField fTextField;
    @FXML
    private TextField gTextField;
    @FXML
    private TextField hTextField;
    @FXML
    private TextField jTextField;
    @FXML
    private TextField kTextField;
    @FXML
    private TextField lTextField;
    @FXML
    private TextField zTextField;
    @FXML
    private TextField xTextField;
    @FXML
    private TextField cTextField;
    @FXML
    private TextField vTextField;
    @FXML
    private TextField bTextField;
    @FXML
    private TextField nTextField;
    @FXML
    private TextField mTextField;

    @FXML
    private ImageView hangmanImageView;

    @FXML
    private HBox hBox1; 
    @FXML
    private HBox hBox2;
    @FXML
    private HBox hBox3;

    @FXML
    private Label livesLabel;

    @FXML
    private Button menuButton;


    private List<String> dictionary = new ArrayList<String>();
    private String chosenWord;
    private boolean[] disabledButtons = new boolean[26];
    private String shownWord = new String("");
    private int lives;

    /*
    * called when the play button is pressed
    * picks a word from the dictionary correspoding to the given id (if it exists)
    * and initializes the game
    */
    @FXML
    protected void startGame(ActionEvent e) throws InterruptedException, IOException {
        String id = idTextField.getText().strip();;
        if (id.isEmpty()) {
            messageLabel.setText("No id given.");
            return;
        }
        chooseWordFromDic(id);
        changeToGameScene();
        setWordLabel();
        setLivesLabel();
        return;
    }

    /*
    * called when the create dictionary button is pressed
    * tries to create a dictionary from the given id's book description via the openlibrary api
    * if it succeeds it saves the dictionary in the multimedia folder
    */
    @FXML
    public void createDictionary(ActionEvent e) throws UndersizeException, UnbalancedException, IOException, InterruptedException, UndersizeException, UnbalancedException {
        String id = idTextField.getText().strip();
        if (id.isEmpty()) {
            messageLabel.setText("No id given.");
            return;
        }
        String response = fetchJSON(id);
        if (response != null) {
            Set<String> dictionary = initializeDictionary(response);
            if (dictionary != null) {
                validateDictionary(dictionary, id);
            }
        }
        return;
    }

    /*
    * called when the menu button is pressed stops the game and returns to the menu screen
    */
    @FXML
    protected void changeToMenuScene(ActionEvent e) {
        //disable game
        livesLabel.setDisable(true);
        menuButton.setDisable(true);
        wordLabel.setText(" ");
        wordLabel.setDisable(true);
        hangmanImageView.setDisable(true);
        QButton.setDisable(false);
        WButton.setDisable(false);
        EButton.setDisable(false);
        RButton.setDisable(false);
        TButton.setDisable(false);
        YButton.setDisable(false);
        UButton.setDisable(false);
        IButton.setDisable(false);
        OButton.setDisable(false);
        PButton.setDisable(false);
        AButton.setDisable(false);
        SButton.setDisable(false);
        DButton.setDisable(false);
        FButton.setDisable(false);
        GButton.setDisable(false);
        HButton.setDisable(false);
        JButton.setDisable(false);
        KButton.setDisable(false);
        LButton.setDisable(false);
        ZButton.setDisable(false);
        XButton.setDisable(false);
        CButton.setDisable(false);
        VButton.setDisable(false);
        BButton.setDisable(false);
        NButton.setDisable(false);
        MButton.setDisable(false);
        qTextField.setText("");
        wTextField.setText("");
        eTextField.setText("");
        rTextField.setText("");
        tTextField.setText("");
        yTextField.setText("");
        uTextField.setText("");
        iTextField.setText("");
        oTextField.setText("");
        pTextField.setText("");
        aTextField.setText("");
        sTextField.setText("");
        dTextField.setText("");
        fTextField.setText("");
        gTextField.setText("");
        hTextField.setText("");
        jTextField.setText("");
        kTextField.setText("");
        lTextField.setText("");
        zTextField.setText("");
        xTextField.setText("");
        cTextField.setText("");
        vTextField.setText("");
        bTextField.setText("");
        nTextField.setText("");
        mTextField.setText("");
        hBox1.setDisable(true);
        hBox2.setDisable(true);
        hBox3.setDisable(true);
        
        //enable the menu
        titleLabel.setVisible(true);
        idTextField.setDisable(false);
        idTextField.setVisible(true);
        playButton.setDisable(false);
        playButton.setVisible(true);
        dictionaryButton.setDisable(false);
        dictionaryButton.setVisible(true);
        messageLabel.setDisable(false);
        return;
    }

    @FXML
    protected void buttonPressed(ActionEvent e) {
        Button b = (Button)e.getSource();
        char letter = b.getText().charAt(0);
        System.out.println(letter);
        b.setDisable(true);
        disabledButtons[(int)letter-65] = true;
        boolean containsLetter = false;
        for (int i = 0; i < chosenWord.length(); i++) {
            if (chosenWord.charAt(i) == letter) {
                containsLetter = true;
                renewDictionary(new Pair<Character, Integer>(letter, i));
                updateShownWord(new Pair<Character, Integer>(letter, i));
                if(!shownWord.contains("_")) {
                    endGame(true);
                    return;
                }
            }
        }
        if(!containsLetter) {
            lives--;
            setLivesLabel();
            if (lives == 0) {
                endGame(false);
                return;
            }
            renewDictionary(new Pair<Character,Integer>(letter, -1));
        }
        setWordLabel();
        int[] probabilities = calculateProbabilities();
        setProbabilities(probabilities);
        return;
    }

    /*
    * used by the startGame and buttonPressed functions to change the wordLabel
    */
    private void setWordLabel() {
        System.out.println(shownWord);
        wordLabel.setText(shownWord);
        return;
    }

    /*
    * used by the startGame and buttonPressed functions to change the livesLabel
    */
    private void setLivesLabel() {
        livesLabel.setText("Lives: " + lives);
        return;
    }

    /*
    * used by the startGame function to change to the game screen
    */
    private void changeToGameScene() {
        //disable the menu
        titleLabel.setVisible(false);
        idTextField.setDisable(true);
        idTextField.setVisible(false);
        playButton.setDisable(true);
        playButton.setVisible(false);
        dictionaryButton.setDisable(true);
        dictionaryButton.setVisible(false);
        messageLabel.setText("");
        messageLabel.setDisable(true);
        
        //enable game
        livesLabel.setDisable(false);
        menuButton.setDisable(false);
        hBox1.setDisable(false);
        hBox2.setDisable(false);
        hBox3.setDisable(false);
        wordLabel.setDisable(false);
        hangmanImageView.setDisable(false);
        for (int i = 0; i < disabledButtons.length; i++) {
            disabledButtons[i] = false;
        }
        lives = 6;
        return;
    }

    /*
    * used by the startGame function to pick the word from the dictionary
    */
    private void chooseWordFromDic(String id) throws InterruptedException {
        try {
            String filename = new String("medialab/"+"hangman_"+id+".txt");
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
            messageLabel.setText("Couldn't find the dictionary with the given id.");
            e.printStackTrace();
        }
        chosenWord = getRandomWord();
        System.out.println(chosenWord);
        int wordSize = chosenWord.length();
        List<String> wordsToRemove = new ArrayList<String>();
        for (String word : dictionary) {
            if (word.length() != wordSize) {
                wordsToRemove.add(word);
            }
        }
        dictionary.removeAll(wordsToRemove);
        shownWord = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            shownWord += "_";
        }
        return;
    }    

    /*
    * used by the chooseWordFromDic function to get a random word from the validated dictionary
    */
    private String getRandomWord() {
        Random rand = new Random();
        return dictionary.get(rand.nextInt(dictionary.size()));
    }

    /*
    * thrown if the dictionary trying to be created doesn't have enough words
    */
    class UndersizeException extends Exception {
        public UndersizeException() throws InterruptedException  {
            messageLabel.setText("The dictionary doesn't have enough words.");
            return;
        }
    }

    /*
    * thrown if the dictionary trying to be created has too many small words
    */
    class UnbalancedException extends Exception {
        public UnbalancedException() throws InterruptedException {
            messageLabel.setText("The dictionary doesn't have enough words with at least 9 letters.");
            return;
        }
    }

    /*
    * used by the fetchJSON function to create the url for the api calls
    */ 
    private String formatURL(String id) {
        return REST_URL+id+".json";
    }

    /*
    * used by the createDictionary function to get the json file from the openlibrary api
    */
    private String fetchJSON(String id) {   
        try{
            URL searchURL = new URL(formatURL(id));
            HttpURLConnection con = (HttpURLConnection) searchURL.openConnection();
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                messageLabel.setText("No Internet Connection. / ID doesn't exist.");
                return null;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

    /*
    * used by the createDicitonary to get the description from the json file
    */
    private Set<String> initializeDictionary(String response) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(response);
            JSONObject resObj = (JSONObject)obj;
            resObj = (JSONObject)resObj.get("description");
            String description = (String)resObj.get("value");
            Set<String> dictionary = new HashSet<String>(Arrays.asList(description.replaceAll("[^\\p{L}\\p{Z}]"," ").split("\\s+")));
            System.out.println(dictionary);
            return dictionary;
        } catch (ParseException e) {
            messageLabel.setText("Can't create a dictionary from the given ID");
            System.out.println(e.toString());
            return null;
        }
    }

    /*
    * used by the createDictionary to create the dictionary from the description
    */
    private void validateDictionary(Set<String> dictionary, String id) throws UndersizeException, UnbalancedException, IOException, InterruptedException {
        int words = 0;
        int bigwords = 0;
        List<String> wordsToRemove = new ArrayList<String>();
        for (String word : dictionary) {
            if (word.length() < 6) 
                wordsToRemove.add(word);
            else {
                words++;
                if (word.length() >= 9) {
                    bigwords++;
                }
            }
        }
        dictionary.removeAll(wordsToRemove);
        if (words < 20) {
            throw new UndersizeException();
        }
        if ((double)bigwords/(double)words < 0.2) {
            throw new UnbalancedException();
        }
        else {
            try {
                String filename = new String("medialab/"+"hangman_"+id+".txt");
                File file = new File(filename);
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(filename);
                    for (String word : dictionary) {
                        writer.write(word.toUpperCase()+"\n");
                    }
                    writer.close();
                    messageLabel.setText("Dictionary with id: " + id + " already created.");
    
                    return;
                }
                else {
                    messageLabel.setText("Dictionary with id: " + id + " already already exists.");
    
                    return;
                }
            } catch (IOException e) {
                messageLabel.setText("Something went wrong while creating the dictionary file");

                e.printStackTrace();
            }
        }
    }

    private void renewDictionary(Pair<Character, Integer> revealedLetter) {
        List<String> wordsToRemove = new ArrayList<String>();
        char revealedChar = revealedLetter.getKey();
        int position = revealedLetter.getValue();
        for (String word: dictionary){
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
        return;
    }

    private int[] calculateProbabilities() {
        int[] probabilities = new int[26];
        for(int i = 0; i < 26; i++){
            char character = (char)(65 + i);
            int probability = 0;
            if (!disabledButtons[i]) {
                int wordsContainingChar = 0;
                for(String word: dictionary) {
                    if (word.indexOf(character) != -1) {
                        wordsContainingChar++;
                    }
                }
                probability = (int)((double)(wordsContainingChar)/(double)(dictionary.size()) * 100);
            } 
            else {
                probability = -1;
            }
            probabilities[i] = probability;
        }
        return probabilities;
    }

    private void updateShownWord(Pair<Character, Integer> revealedLetter) {
        char[] word = shownWord.toCharArray();
        System.out.println(word);
        word[revealedLetter.getValue()] = revealedLetter.getKey();
        System.out.println(word);
        shownWord = String.valueOf(word);
        System.out.println(shownWord);
        return;
    }

    private void endGame(boolean win) {
        //disable game
        livesLabel.setDisable(true);
        menuButton.setDisable(true);
        wordLabel.setText(" ");
        wordLabel.setDisable(true);
        hangmanImageView.setDisable(true);
        QButton.setDisable(false);
        WButton.setDisable(false);
        EButton.setDisable(false);
        RButton.setDisable(false);
        TButton.setDisable(false);
        YButton.setDisable(false);
        UButton.setDisable(false);
        IButton.setDisable(false);
        OButton.setDisable(false);
        PButton.setDisable(false);
        AButton.setDisable(false);
        SButton.setDisable(false);
        DButton.setDisable(false);
        FButton.setDisable(false);
        GButton.setDisable(false);
        HButton.setDisable(false);
        JButton.setDisable(false);
        KButton.setDisable(false);
        LButton.setDisable(false);
        ZButton.setDisable(false);
        XButton.setDisable(false);
        CButton.setDisable(false);
        VButton.setDisable(false);
        BButton.setDisable(false);
        NButton.setDisable(false);
        MButton.setDisable(false);
        qTextField.setText("");
        wTextField.setText("");
        eTextField.setText("");
        rTextField.setText("");
        tTextField.setText("");
        yTextField.setText("");
        uTextField.setText("");
        iTextField.setText("");
        oTextField.setText("");
        pTextField.setText("");
        aTextField.setText("");
        sTextField.setText("");
        dTextField.setText("");
        fTextField.setText("");
        gTextField.setText("");
        hTextField.setText("");
        jTextField.setText("");
        kTextField.setText("");
        lTextField.setText("");
        zTextField.setText("");
        xTextField.setText("");
        cTextField.setText("");
        vTextField.setText("");
        bTextField.setText("");
        nTextField.setText("");
        mTextField.setText("");
        hBox1.setDisable(true);
        hBox2.setDisable(true);
        hBox3.setDisable(true);
        
        //enable the menu
        titleLabel.setVisible(true);
        idTextField.setDisable(false);
        idTextField.setVisible(true);
        playButton.setDisable(false);
        playButton.setVisible(true);
        dictionaryButton.setDisable(false);
        dictionaryButton.setVisible(true);
        messageLabel.setDisable(false);
        if (win) {
            messageLabel.setText("You Won!");
        }
        else {
            messageLabel.setText("You Lost. The word was " + chosenWord);
        }
        return;
    }

    private void setProbabilities(int[] probabilities) {
        aTextField.setText(String.valueOf(probabilities[0]));
        bTextField.setText(String.valueOf(probabilities[1]));
        cTextField.setText(String.valueOf(probabilities[2]));
        dTextField.setText(String.valueOf(probabilities[3]));
        eTextField.setText(String.valueOf(probabilities[4]));
        fTextField.setText(String.valueOf(probabilities[5]));
        gTextField.setText(String.valueOf(probabilities[6]));
        hTextField.setText(String.valueOf(probabilities[7]));
        iTextField.setText(String.valueOf(probabilities[8]));
        jTextField.setText(String.valueOf(probabilities[9]));
        kTextField.setText(String.valueOf(probabilities[10]));
        lTextField.setText(String.valueOf(probabilities[11]));
        mTextField.setText(String.valueOf(probabilities[12]));
        nTextField.setText(String.valueOf(probabilities[13]));
        oTextField.setText(String.valueOf(probabilities[14]));
        pTextField.setText(String.valueOf(probabilities[15]));
        qTextField.setText(String.valueOf(probabilities[16]));
        rTextField.setText(String.valueOf(probabilities[17]));
        sTextField.setText(String.valueOf(probabilities[18]));
        tTextField.setText(String.valueOf(probabilities[19]));
        uTextField.setText(String.valueOf(probabilities[20]));
        vTextField.setText(String.valueOf(probabilities[21]));
        wTextField.setText(String.valueOf(probabilities[22]));
        xTextField.setText(String.valueOf(probabilities[23]));
        yTextField.setText(String.valueOf(probabilities[24]));
        zTextField.setText(String.valueOf(probabilities[25]));
    }
}