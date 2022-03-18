package app;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class wordPercentPopUpController {

    @FXML
    private Label sixLetterPer;

    @FXML
    private Label sevenLetterPer;

    @FXML
    private Label tenLetterPer;

    private String sixLetter;

    private String sevenLetter;

    private String tenLetter;


    @FXML
    public void exit() {
        Stage stage = (Stage) sevenLetterPer.getScene().getWindow();
        stage.close();
    }

    public void setLetters(int six, int seven, int ten) {
        this.sixLetter = String.valueOf(six);
        this.sevenLetter = String.valueOf(seven);
        this.tenLetter = String.valueOf(ten);
    }

    public void setLabels() {
        sixLetterPer.setText(sixLetter);
        sevenLetterPer.setText(sevenLetter);
        tenLetterPer.setText(tenLetter);
    }
}
