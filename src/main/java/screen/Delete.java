package screen;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;


public class Delete implements Initializable {
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
            Logout();
            System.out.println("Haha");
        }) ;

    }


    @FXML
    private void Accept() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn thêm từ này ?");
        ButtonType ButtonTypeYes = new ButtonType("Có", ButtonBar.ButtonData.YES);
        ButtonType ButtonTypeNo = new ButtonType("Không", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().addAll(ButtonTypeYes, ButtonTypeNo);
        alert.getButtonTypes().remove(ButtonType.OK);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonTypeYes) {
            System.out.println("Thêm code sau :v");
        } else if (result.get() == ButtonTypeNo) {
            System.out.println("Tắt thông báo");
        }

        alert.show();
    }

    @FXML
    private void Logout() {
        // Sử dụng Platform.runLater để thực hiện công việc trên một tiến trình khác
        Platform.runLater(() -> {
            show("/com/example/dictionary_uet/Main.fxml/");
        });
    }


    private void setNode(Node node) {
        screen.getChildren().clear();
        screen.getChildren().add(node);
    }

    @FXML
    private void show(String path) {
        try {
            AnchorPane component = FXMLLoader.load(getClass().getResource(path));
            setNode(component);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private ListView<String> allWords;

    @FXML
    private Button logout;

    @FXML
    private  TextField searchField,current;

    @FXML
    private WebView currentDetail;

    @FXML
    private  AnchorPane screen;

    @FXML
    private Tooltip Logout;

}