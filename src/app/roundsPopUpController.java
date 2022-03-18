package app;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class roundsPopUpController {

    @FXML
    private VBox vBox;

    private List<Game> games;

    @FXML
    public void exit() {
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
    }

    public void setGames(List <Game> games) {
        this.games = new ArrayList<Game>(games);
        return;
    }

    public void addLabels() {
        Label l;
        String winner;
        if (games.isEmpty()) {
            l = new Label();
            l.setPrefHeight(80);
            l.setPrefWidth(399);
            l.alignmentProperty().set(Pos.CENTER);
            l.setText("No games played yet");
            vBox.getChildren().add(l);
            return;
        }
        for (Game game : games) {
            if (game.PlayerWon()) {
                winner = "Player";
            }
            else {
                winner = "Computer";
            }
            l = new Label();
            l.setPrefHeight(80);
            l.setPrefWidth(399);
            l.alignmentProperty().set(Pos.CENTER);
            l.setText("Word: " + game.getWord() + "          Tries: " + game.getTries() + "          Winner: " + winner);
            vBox.getChildren().add(l);
        }
        return;
    }
}
