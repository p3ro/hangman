package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class App extends Application {
 
    @Override
    public void start(Stage stage) {
        try {
            Parent Root = FXMLLoader.load(getClass().getResource("MediaLabHangman.fxml"));
            Scene Scene = new Scene(Root);
            stage.setScene(Scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            Root.setOnMousePressed(pressEvent -> {
                Root.setOnMouseDragged(dragEvent -> {
                    stage.setX(dragEvent.getScreenX() - pressEvent.getSceneX());
                    stage.setY(dragEvent.getScreenY() - pressEvent.getSceneY());
                });
            });
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    } 


    public static void main(String[] args) {
        launch(args);
    }
}
