package game.screen;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ScoreScreenController {
    @FXML
    private Label scoreLabel;

    public void setScore(int score) {
        scoreLabel.setText("Score: " + score);
    }
}
