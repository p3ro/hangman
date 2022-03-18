package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loadPopUpController {

    @FXML
    private Label errorLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private Button loadDictionaryButton;

    private MediaLabHangmanController mediaLabHangmanController;


    @FXML
    public void exit() {
        Stage stage = (Stage) loadDictionaryButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void loadDictionary(ActionEvent event) {
        List <String> dictionary = new ArrayList<String>();
        try {
            String id = idTextField.getText();
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
            mediaLabHangmanController.setMessageLabel("Dictionary with id "+ id + " loaded");
            mediaLabHangmanController.startGame(dictionary);
            Stage stage = (Stage) loadDictionaryButton.getScene().getWindow();
            stage.close();
        } catch (FileNotFoundException e) {
            errorLabel.setText("Couldn't find the dictionary with the given id.");
            e.printStackTrace();
        }
    }

    public void setMediaLabHangmmanController(MediaLabHangmanController mediaLabHangmanController) {
        this.mediaLabHangmanController = mediaLabHangmanController;
     }
}
