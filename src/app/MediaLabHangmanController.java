package app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MediaLabHangmanController {

    @FXML
    private Label ALabel;

    @FXML
    private Label BLabel;

    @FXML
    private Label CLabel;

    @FXML
    private Label DLabel;

    @FXML
    private Label ELabel;

    @FXML
    private Label FLabel;

    @FXML
    private Label GLabel;

    @FXML
    private Label HLabel;

    @FXML
    private Label ILabel;

    @FXML
    private Label JLabel;

    @FXML
    private Label KLabel;

    @FXML
    private Label LLabel;

    @FXML
    private Label MLabel;

    @FXML
    private Label NLabel;

    @FXML
    private Label OLabel;

    @FXML
    private Label PLabel;

    @FXML
    private Label QLabel;

    @FXML
    private Label RLabel;

    @FXML
    private Label SLabel;

    @FXML
    private Label TLabel;

    @FXML
    private Label ULabel;

    @FXML
    private Label VLabel;

    @FXML
    private Label WLabel;

    @FXML
    private Label XLabel;

    @FXML
    private Label YLabel;

    @FXML
    private Label ZLabel;

    @FXML
    private Label accuracyLabel;

    @FXML
    private Button button;

    @FXML
    private Menu detailsMenu;

    @FXML
    private ImageView hangmanImageView;

    @FXML
    private ChoiceBox<Character> letterChoiceBox;

    @FXML
    private Label livesLabel;

    @FXML
    private MenuItem load;

    @FXML
    private ChoiceBox<Integer> posChoiceBox;

    @FXML
    private Label scoreLabel;

    @FXML
    private MenuItem startMenu;

    @FXML
    private Label wordLabel;

    @FXML
    private Label wordsInDic;

    @FXML
    private Label messageLabel;

    @FXML
    private Button playAgainButton;

    private List<String> fullDictionary = new ArrayList<String>();
    private List<String> tempDictionary = new ArrayList<String>();
    private String chosenWord;
    private String shownWord = new String("");
    private int lives;
    private int score;
    private int correctGuesses;
    private int wrongGuesses;
    private Pair[] prob = new Pair[26];
    private List<Game> games = new ArrayList<Game>();

    @FXML
    void createDic(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("createPopUp.fxml"));
            Parent Root = loader.load();
            createPopUpController controller = loader.getController();
            controller.setMediaLabHangmmanController(this);
            Scene Scene = new Scene(Root);
            Stage stage = new Stage();
            stage.setScene(Scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Root.setOnMousePressed(pressEvent -> {
                Root.setOnMouseDragged(dragEvent -> {
                    stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            stage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return;
    }

    @FXML
    void dicStats(ActionEvent event) {
        int six = 0;
        int seven = 0;
        int ten = 0 ;
        for (String word : fullDictionary) {
            if (word.length() == 6) {
                six++;
            }
            else if (word.length() <= 9) {
                seven++;
            }
            else {
                ten++;
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("wordPercentPopUp.fxml"));
            Parent Root = loader.load();
            wordPercentPopUpController controller = loader.getController();
            controller.setLetters(six, seven, ten);
            Scene Scene = new Scene(Root);
            Stage stage = new Stage();
            stage.setScene(Scene);
            controller.setLabels();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Root.setOnMousePressed(pressEvent -> {
                Root.setOnMouseDragged(dragEvent -> {
                    stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            stage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @FXML
    void exit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void loadDic(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loadPopUp.fxml"));
            Parent Root = loader.load();
            loadPopUpController controller = loader.getController();
            controller.setMediaLabHangmmanController(this);
            Scene Scene = new Scene(Root);
            Stage stage = new Stage();
            stage.setScene(Scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Root.setOnMousePressed(pressEvent -> {
                Root.setOnMouseDragged(dragEvent -> {
                    stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            stage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @FXML
    void makeGuess(ActionEvent event) {
        int position = posChoiceBox.getValue() - 1;
        Character letter = letterChoiceBox.getValue();
        isCorrect(position, letter);
        return;
    }

    @FXML
    void posChanged() {
        if (!posChoiceBox.getItems().isEmpty()) {
            calculateProbabilities();
        }
        return;
    }

    @FXML
    void restart(ActionEvent event) {
        initializeGame();
        return;
    }

    @FXML
    void roundsStats(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("roundsPopUp.fxml"));
            Parent Root = loader.load();
            roundsPopUpController controller = loader.getController();
            controller.setGames(games);
            Scene Scene = new Scene(Root);
            Stage stage = new Stage();
            stage.setScene(Scene);
            controller.addLabels();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            Root.setOnMousePressed(pressEvent -> {
                Root.setOnMouseDragged(dragEvent -> {
                    stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            stage.showAndWait();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return;
    }

    @FXML
    void solve(ActionEvent event) {
        File file = new File("images/hangman_"+0+".png");
        Image hangman = new Image(file.toURI().toString());
        hangmanImageView.setImage(hangman);
        gameLost();
        return;
    }

    @FXML
    void playAgain(ActionEvent event) {
        messageLabel.setText("");
        initializeGame();
    }

    public void startGame(List <String> dictionary) {
        fullDictionary = dictionary;
        button.setDisable(false);
        posChoiceBox.setDisable(false);
        letterChoiceBox.setDisable(false);
        detailsMenu.setDisable(false);
        startMenu.setDisable(false);
        initializeGame();
        return;
    }

    private void initializeGame() {
        tempDictionary = new ArrayList<String>(fullDictionary);
        lives = 6;
        correctGuesses = 0;
        wrongGuesses = 0;
        score = 0;
        pickWord();
        setLivesLabel();        
        setHangmanImageView();
        setWordsInDicLabel();
        setScoreLabel();
        setAccuracyLabel();
        posChoiceBox.setDisable(false);
        letterChoiceBox.setDisable(false);
        button.setDisable(false);
        calculateProbabilities();
        playAgainButton.setVisible(false);
    }

    private void pickWord() {
        Random rand = new Random();
        chosenWord = fullDictionary.get(rand.nextInt(fullDictionary.size()));
        System.out.println(chosenWord);
        int wordSize = chosenWord.length();
        List<String> wordsToRemove = new ArrayList<String>();
        for (String word : tempDictionary) {
            if (word.length() != wordSize) {
                wordsToRemove.add(word);
            }
        }
        tempDictionary.removeAll(wordsToRemove);
        shownWord = "";
        for (int i = 0; i < chosenWord.length(); i++) {
            shownWord += "_";
        }
        setWordLabel();
        posChoiceBox.getItems().clear();
        for(Integer i = 0; i < chosenWord.length(); i++) {
            posChoiceBox.getItems().add(i+1);
        }
        posChoiceBox.setValue(posChoiceBox.getItems().get(0));
    }
    
    private void setWordLabel() {
        wordLabel.setText(shownWord);
        return;
    }

    private void setLivesLabel() {
        livesLabel.setText("Lives: " + lives);
        return;
    }

    private void setScoreLabel() {
        scoreLabel.setText("Score: " + score);
        return;
    }

    private void setWordsInDicLabel() {
        wordsInDic.setText("Words in Dictionary: " + tempDictionary.size());
        return;
    }

    private void setAccuracyLabel() {
        if (wrongGuesses == 0) {
            accuracyLabel.setText("Accuracy: 0");
        }
        else {
            accuracyLabel.setText("Accuracy: " + correctGuesses*100/(correctGuesses+wrongGuesses)+"%");
        }
        return;
    }

    private void setHangmanImageView() {
        File file = new File("images/hangman_"+lives+".png");
        Image hangman = new Image(file.toURI().toString());
        hangmanImageView.setImage(hangman);
    }

    private void calculateProbabilities() {
        Integer position = posChoiceBox.getValue() - 1;
        int[] probabilities = new int[26];
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = 0;
        }
        for (String word : tempDictionary) {
            int index = word.charAt(position) - 65;
            probabilities[index]++;
        }
        for (int i = 0; i < prob.length; i++) {
            int probability = (int)((double) probabilities[i]/(double)(tempDictionary.size()) * 100);
            prob[i] = new Pair((char)(65 + i), probability);
        }
        Pair.sortPair(prob, prob.length);
        setProbabilities(probabilities);
        return;
    }

    private void setProbabilities(int[] probabilities) {
        letterChoiceBox.getItems().clear();
        for (Pair p : prob) {
            letterChoiceBox.getItems().add(p.getLetter());
        }
        letterChoiceBox.setValue(letterChoiceBox.getItems().get(0));
        ALabel.setText("A: " + String.valueOf((int)((double) probabilities[0]/(double)(tempDictionary.size()) * 100))+"%");
        BLabel.setText("B: " +String.valueOf((int)((double) probabilities[1]/(double)(tempDictionary.size()) * 100))+"%");
        CLabel.setText("C: " +String.valueOf((int)((double) probabilities[2]/(double)(tempDictionary.size()) * 100))+"%");
        DLabel.setText("D: " +String.valueOf((int)((double) probabilities[3]/(double)(tempDictionary.size()) * 100))+"%");
        ELabel.setText("E: " +String.valueOf((int)((double) probabilities[4]/(double)(tempDictionary.size()) * 100))+"%");
        FLabel.setText("F: " +String.valueOf((int)((double) probabilities[5]/(double)(tempDictionary.size()) * 100))+"%");
        GLabel.setText("G: " +String.valueOf((int)((double) probabilities[6]/(double)(tempDictionary.size()) * 100))+"%");
        HLabel.setText("H: " +String.valueOf((int)((double) probabilities[7]/(double)(tempDictionary.size()) * 100))+"%");
        ILabel.setText("I: " +String.valueOf((int)((double) probabilities[8]/(double)(tempDictionary.size()) * 100))+"%");
        JLabel.setText("J: " +String.valueOf((int)((double) probabilities[9]/(double)(tempDictionary.size()) * 100))+"%");
        KLabel.setText("K: " +String.valueOf((int)((double) probabilities[10]/(double)(tempDictionary.size()) * 100))+"%");
        LLabel.setText("L: " +String.valueOf((int)((double) probabilities[11]/(double)(tempDictionary.size()) * 100))+"%");
        MLabel.setText("M: " +String.valueOf((int)((double) probabilities[12]/(double)(tempDictionary.size()) * 100))+"%");
        NLabel.setText("N: " +String.valueOf((int)((double) probabilities[13]/(double)(tempDictionary.size()) * 100))+"%");
        OLabel.setText("O: " +String.valueOf((int)((double) probabilities[14]/(double)(tempDictionary.size()) * 100))+"%");
        PLabel.setText("P: " +String.valueOf((int)((double) probabilities[15]/(double)(tempDictionary.size()) * 100))+"%");
        QLabel.setText("Q: " +String.valueOf((int)((double) probabilities[16]/(double)(tempDictionary.size()) * 100))+"%");
        RLabel.setText("R: " +String.valueOf((int)((double) probabilities[17]/(double)(tempDictionary.size()) * 100))+"%");
        SLabel.setText("S: " +String.valueOf((int)((double) probabilities[18]/(double)(tempDictionary.size()) * 100))+"%");
        TLabel.setText("T: " +String.valueOf((int)((double) probabilities[19]/(double)(tempDictionary.size()) * 100))+"%");
        ULabel.setText("U: " +String.valueOf((int)((double) probabilities[20]/(double)(tempDictionary.size()) * 100))+"%");
        VLabel.setText("V: " +String.valueOf((int)((double) probabilities[21]/(double)(tempDictionary.size()) * 100))+"%");
        WLabel.setText("W: " +String.valueOf((int)((double) probabilities[22]/(double)(tempDictionary.size()) * 100))+"%");
        XLabel.setText("X: " +String.valueOf((int)((double) probabilities[23]/(double)(tempDictionary.size()) * 100))+"%");
        YLabel.setText("Y: " +String.valueOf((int)((double) probabilities[24]/(double)(tempDictionary.size()) * 100))+"%");
        ZLabel.setText("Z: " +String.valueOf((int)((double) probabilities[25]/(double)(tempDictionary.size()) * 100))+"%");
        return;
    }

    private void isCorrect(int position, Character letter) {
        if(chosenWord.charAt(position) == letter) {
            correctGuesses++;
            char[] word = shownWord.toCharArray();
            word[position] = letter;
            shownWord = String.valueOf(word);
            setWordLabel();
            if (!shownWord.contains("_")) {
                gameWon();
                return;
            }
            posChoiceBox.getItems().removeAll(position+1);
            if (!posChoiceBox.getItems().isEmpty()){
                posChoiceBox.setValue(posChoiceBox.getItems().get(0));
            }
            for (Pair p : prob) {
                if (p.getLetter() == letter) {
                    if (p.getProbability() < 25) {
                        score += 30;
                    }
                    else if (p.getProbability() < 45) {
                        score += 15;
                    }
                    else if (p.getProbability() < 60) {
                        score += 10;
                    }
                    else {
                        score += 5;
                    }
                    break;
                }
            }
            setScoreLabel();
            setAccuracyLabel();
            renewDictionary(position,letter,true);
            calculateProbabilities();
        }
        else {
            score -= 15;
            wrongGuesses++;
            if (score < 0) {
                score = 0;
            }
            setScoreLabel();
            setAccuracyLabel();
            renewDictionary(position,letter,false);
            calculateProbabilities();
            if (--lives == 0) {
                setLivesLabel();
                setHangmanImageView();
                gameLost();
                return;
            }
            setLivesLabel();
            setHangmanImageView();
        }
        return;
    }

    private void renewDictionary(int position, Character letter, boolean correct) {
        List<String> wordsToRemove = new ArrayList<String>();
        for (String word: tempDictionary) {
            if (word.charAt(position) == letter && !correct) {
                wordsToRemove.add(word);
            }
            else if (word.charAt(position) != letter && correct) {
                wordsToRemove.add(word);
            }
        }
        tempDictionary.removeAll(wordsToRemove);
        setWordsInDicLabel();
        return;
    }

    private void gameWon() {
        Game game = new Game(chosenWord, chosenWord.length() + 6 - lives, true);
        if (games.size() == 5) {
            games.remove(0);
        }
        games.add(game);
        messageLabel.setText("You won :D");
        playAgainButton.setVisible(true);
        posChoiceBox.setDisable(true);
        letterChoiceBox.setDisable(true);
        button.setDisable(true);
        return;
    }

    private void gameLost() {
        int tries = 6 - lives;
        for (int i = 0; i < shownWord.length(); i++) {
            if(shownWord.charAt(i) != '_') {
                System.out.println("test");
                tries++;
            }
        }
        Game game = new Game(chosenWord, tries, false);
        if (games.size() == 5) {
            games.remove(0);
        }
        games.add(game);
        messageLabel.setText("The word was " + chosenWord + ". Better luck next time!");
        playAgainButton.setVisible(true);
        posChoiceBox.setDisable(true);
        letterChoiceBox.setDisable(true);
        button.setDisable(true);
        return; 
    }

    public void setMessageLabel(String msg) {
        messageLabel.setText(msg);
        return;
    }
}