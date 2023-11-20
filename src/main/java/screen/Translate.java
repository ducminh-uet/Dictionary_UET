package screen;

import dictionary.tool.Sound;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import dictionary.tool.TranslateAPI;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Translate implements Initializable {
    private Thread translationThread = null; // Khởi tạo một Thread

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translate.textProperty().addListener((observable, oldValue, newText) -> {
            if (translationThread != null && translationThread.isAlive()) {
                translationThread.interrupt();
            }
            translationThread = new Thread(() -> {
                try {
                    String translatedText = TranslateAPI.translate("en", "vi", newText);
                    Platform.runLater(() -> {
                        translateDetail.setText(translatedText);
                    });
                } catch (IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
            translationThread.start();
        });
        // Ấn vào loa thì đọc cái mình muốn dịch.
        word.setOnMouseClicked(event -> {
            try {
                Sound.SpeechAPI(translate.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        // Ấn vào loa thì đọc cái đã dịch xong - giọng tiếng Việt ko có nữ.
        detail.setOnMouseClicked(event -> {
            try {
                Sound.SpeechVietNam(translateDetail.getText());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        logout.setOnMouseEntered(event -> {
            logout.setScaleX(1.2);
            logout.setScaleY(1.2);
        });

        logout.setOnMouseExited(event -> {
            logout.setScaleX(1.0);
            logout.setScaleY(1.0);
        });

        logout.setOnAction(e -> {
            handleButtonClick();
            Logout();
            System.out.println("Haha");
        });

        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(500), notificationLabel);
        fadeInTransition.setFromValue(0);
        fadeInTransition.setToValue(1);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2), event -> hideLabel())
        ); //Delay 2s
        timeline.setDelay(Duration.millis(100)); // Delay 0.1 second before starting the timeline

        fadeInTransition.play();
        timeline.play();

    }
    // end initialize

    private void hideLabel() {
        notificationLabel.setVisible(false);
        FadeTransition fadeOutTransition = new FadeTransition(Duration.millis(500), notificationLabel);
        fadeOutTransition.setFromValue(1);
        fadeOutTransition.setToValue(0);
        fadeOutTransition.setOnFinished(event -> notificationLabel.setVisible(false));
        fadeOutTransition.play();
    }

    public void handleButtonClick() {
        // Load the audio file
        String audioFile = getClass().getResource("/sound/button_click.mp3").toString();
        AudioClip audioClip = new AudioClip(audioFile);
        // Play the audio
        audioClip.play();

    }

    @FXML
    private void Logout() {
        show("/com/example/dictionary_uet/Main.fxml");
    }

    private void setNode(Node node) {
        screen.getChildren().clear();
        screen.getChildren().add(node);
    }

    private void show(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Button logout;

    @FXML
    private TextArea translate, translateDetail;

    @FXML
    private AnchorPane screen;

    @FXML
    private Tooltip Logout;

    @FXML
    private ImageView word, detail;

    @FXML
    private Label notificationLabel;

}