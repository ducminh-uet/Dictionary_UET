package game.screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class HistoryController {
    @FXML
    private TextArea historyTextArea;

    @FXML
    private Button returnToMenuButton;

    public void initialize() {
        // Load and display the score history when the controller is initialized.
        loadScoreHistory();
    }

    private void loadScoreHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src\\main\\java\\game\\history\\scores.txt"))) {
            StringBuilder historyText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                historyText.append(line).append("\n");
            }

            historyTextArea.setText(historyText.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void returnToMenu() throws IOException {
        // Show a confirmation alert before returning to the menu.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Return to Menu");
        alert.setHeaderText("Are you sure you want to return to the menu?");
        alert.setContentText("Your progress will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuController.fxml"));
             Parent menuRoot = loader.load();
             Scene menuScene = new Scene(menuRoot);
             Stage stage = (Stage) returnToMenuButton.getScene().getWindow();
             stage.setScene(menuScene);
             stage.show();
        }
    }
}
