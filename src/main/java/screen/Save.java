package screen;

import dictionary.Word;
import dictionary.tool.Sound;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Save implements Initializable {
    public Save() {

    }
    private String previousInterface;

    // Constructor
    public Save(String previousInterface) {
        this.previousInterface = previousInterface;
    }

    // Setter
    public void setPreviousInterface(String previousInterface) {
        this.previousInterface = previousInterface;
    }



    @FXML
    private Label notificationLabel;
    @FXML
    private ListView<Word> allWords;

    @FXML
    private WebView currentDetail;

    @FXML
    private ListView<String> resultListView, historySearch;

    @FXML
    private TextField searchField, current;

    @FXML
    private Button logout, volumeButton, out;


    @FXML
    private AnchorPane screen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Word> saveWordList = FXCollections.observableArrayList(InterfaceManager.getInstance().getSelected());
        allWords.setItems(saveWordList);

        boolean x = InterfaceManager.getInstance().isState();
        if (x) {
            allWords.setItems(saveWordList);
        }

        WebEngine webEngine = currentDetail.getEngine();
        allWords.setOnMouseClicked(event -> {
            Word selectedWord = allWords.getSelectionModel().getSelectedItem();
            if (selectedWord != null) {
                current.setText(selectedWord.getWord_target());
                webEngine.loadContent(selectedWord.getWord_explain());

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
            System.out.println("test");
            String a = InterfaceManager.getInstance().getPreviousInterface();
            System.out.println("a = " + a);
            if (a != null) {
                if (a.equals("V-E")) {
                    Logout1();
                } else {
                    Logout();
                }
            } else {
                System.out.println("NULL?");
            }

        });
        volumeButton.setOnAction(e -> {
            String selectedWord = current.getText();
            if (selectedWord != null) {
                Sound.Speech(selectedWord);
            }
        });
        out.setOnAction(e ->{
            Word removeWord = allWords.getSelectionModel().getSelectedItem();
            InterfaceManager.getInstance().removeSavedWord(removeWord);
            saveWordList.remove(removeWord);
            allWords.setItems(saveWordList);
        });
    }

    public void handleButtonClick() {
        // Load the audio file
        String audioFile = getClass().getResource("/sound/button_click.mp3").toString();
        AudioClip audioClip = new AudioClip(audioFile);
        // Play the audio
        audioClip.play();
    }

    @FXML
    public void state() {
        previousInterface = InterfaceManager.getInstance().getPreviousInterface();
    }

    @FXML
    private void Logout() {
        show("/com/example/dictionary_uet/Main.fxml");
    }

    @FXML
    private void Logout1() {
        show("/com/example/dictionary_uet/Main_V_E.fxml");
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

}
