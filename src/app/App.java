package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {
 
    @Override
    public void start(Stage stage) {
        try {
            Parent Root = FXMLLoader.load(getClass().getResource("hangman.fxml"));
            Scene Scene = new Scene(Root);
            stage.setScene(Scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 

    public static void main(String[] args) {
        launch(args);
    }
}
