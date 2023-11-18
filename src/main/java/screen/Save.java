package screen;

import dictionary.Word;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
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
    private Button logout;


    @FXML
    private AnchorPane screen;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
