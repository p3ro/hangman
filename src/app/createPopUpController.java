package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
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


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class createPopUpController {

    @FXML
    private Button createDictionaryButton;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField dIdTextField;

    private final String REST_URL = "http://www.openlibrary.org/works/";

    private MediaLabHangmanController mediaLabHangmanController;


    @FXML
    public void exit() {
        Stage stage = (Stage) createDictionaryButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void createDic(ActionEvent event) throws UndersizeException, UnbalancedException, IOException, InterruptedException {
        String id = idTextField.getText().strip();
        String dId = dIdTextField.getText().strip();
        if (id.isEmpty()) {
            errorLabel.setText("No open library id given.");
            return;
        }
        if (dId.isEmpty()) {
            errorLabel.setText("No dictionary id given.");
        }
        String response = fetchJSON(id);
        if (response != null) {
            Set<String> dictionary = initializeDictionary(response);
            if (dictionary != null) {
                validateDictionary(dictionary, dId);
            }
        }
        return;
    }

    private String fetchJSON(String id) {   
        try{
            URL searchURL = new URL(formatURL(id));
            HttpURLConnection con = (HttpURLConnection) searchURL.openConnection();
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                errorLabel.setText("No Internet Connection. / ID doesn't exist.");
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

    private String formatURL(String id) {
        return REST_URL+id+".json";
    }

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
        } catch (org.json.simple.parser.ParseException e) {
            errorLabel.setText("Can't create a dictionary from the given ID");
            System.out.println(e.toString());
            return null;
        }
    }

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
                    mediaLabHangmanController.setMessageLabel("Dictionary with id " + id + " created.");
                    Stage stage = (Stage) createDictionaryButton.getScene().getWindow();
                    stage.close();
                    return;
                }
                else {
                    errorLabel.setText("Dictionary with id: " + id + " already exists.");
    
                    return;
                }
            } catch (IOException e) {
                errorLabel.setText("Something went wrong while creating the dictionary file");

                e.printStackTrace();
            }
        }
    }

    class UndersizeException extends Exception {
        public UndersizeException() throws InterruptedException  {
            errorLabel.setText("The dictionary doesn't have enough words.");
            return;
        }
    }

    class UnbalancedException extends Exception {
        public UnbalancedException() throws InterruptedException {
            errorLabel.setText("The dictionary doesn't have enough words with at least 9 letters.");
            return;
        }
    }

    public void setMediaLabHangmmanController(MediaLabHangmanController mediaLabHangmanController) {
        this.mediaLabHangmanController = mediaLabHangmanController;
     }
}
