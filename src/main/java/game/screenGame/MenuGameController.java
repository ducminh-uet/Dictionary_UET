package game.screenGame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuGameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void startToQuiz(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("QuizController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

