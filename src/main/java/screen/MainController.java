package screen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private AnchorPane mainContent;
    @FXML
    private AnchorPane searchPane;
    @FXML
    private AnchorPane translatePane;
    @FXML
    private AnchorPane bookmarkPane;
    @FXML
    private AnchorPane historyPane;
    @FXML
    private AnchorPane settingPane;

    @FXML
    private SearchController searchController;
    @FXML
    private BookmarkController bookmarkController;
    @FXML
    private HistoryController historyController;

    @FXML
    private Button searchButton;
    @FXML
    private Button translateButton;
    @FXML
    private Button bookmarkButton;
    @FXML
    private Button mainHistoryButton;
    @FXML
    private Button settingButton;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("search.fxml"));
                searchPane = loader.load();
                searchController = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("history.fxml"));
                historyPane = loader.load();
                historyController = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("bookmark.fxml"));
                bookmarkPane = loader.load();
                bookmarkController = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("translate.fxml"));
                translatePane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("setting.fxml"));
                settingPane = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            searchButton.getStyleClass().add("active");
            mainContent.getChildren().setAll(searchPane);
        }
    }
}