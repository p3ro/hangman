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
import org.json.simple.parser.ParseException;

public class DictionaryCreator {
    private final String REST_URL = "http://www.openlibrary.org/works/";
    class UndersizeException extends Exception {
        public UndersizeException()  {
            System.out.println("The dictionary doesn't have enough words.");
        }
    }

    class UnbalancedException extends Exception {
        public UnbalancedException() {
            System.out.println("The dictionary doesn't have enough words with at least 9 letters.");
        }
    }

    private String formatURL(String id) {
        return REST_URL+id+".json";
    }

    public void createDictionary(String id) throws DictionaryCreator.UndersizeException, DictionaryCreator.UnbalancedException, IOException {
        String response = fetchJSON(id);
        if (response != null) {
            Set<String> dictionary = initializeDictionary(response);
            if (dictionary != null) {
                validateDictionary(dictionary, id);
            }
        }
    }

    private String fetchJSON(String id) {   
        try{
            URL searchURL = new URL(formatURL(id));
            HttpURLConnection con = (HttpURLConnection) searchURL.openConnection();
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Something went wrong!");
            }
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
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
            System.out.println(e.toString());
            return null;
        }
    }

    private void validateDictionary(Set<String> dictionary, String id) throws UndersizeException, DictionaryCreator.UnbalancedException, IOException {
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
                String filename = new String("./medialab/"+"hangman_"+id+".txt");
                File file = new File(filename);
                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(filename);
                    for (String word : dictionary) {
                        writer.write(word.toUpperCase()+"\n");
                    }
                    writer.close();
                    System.out.println("File with id: " + id + " created.");
                    return;
                }
                else {
                    System.out.println("File with id: " + id + " already exists.");
                    return;
                }
            } catch (IOException e) {
                System.out.println("Something went wrong while creating the dictionary file");
                e.printStackTrace();
            }
        }
    }
}