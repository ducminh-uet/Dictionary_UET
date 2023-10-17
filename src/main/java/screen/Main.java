package screen;

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
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Main implements Initializable {
    // Add your controller logic here

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize your controller
        relatedWords = Arrays.asList("Apple", "Applicant", "Banana", "Borrow", "Carry", "Cock", "Date", "Down", "Fig", "Fan", "Grape", "Good");
        allWords = new ListView<>();
        allWords.getItems().addAll(relatedWords);
        scrollPane.setContent(allWords);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String keyword = newValue.trim().toLowerCase(); //trim() : xóa khoảng trắng ở đầu và cuối chuỗi
            //toLowerCase() : chuyển hoa -> thường

            if (!keyword.isEmpty()) {
                List<String> filteredWords = relatedWords.stream() //Lọc sử dụng JavaStream
                        .filter(word -> word.toLowerCase().startsWith(keyword)) //Tạo bộ lọc
                        .collect(Collectors.toList());  //Lọc

                resultListView.getItems().clear();
                resultListView.getItems().addAll(filteredWords);
                resultListView.setVisible(true);
            } else {
                resultListView.getItems().clear();
                resultListView.setVisible(false);
            }
        });

        historySearch.setVisible(false); //Ẩn mặc định

        arrowButton.setOnAction(e -> {
            boolean showHistory = !historySearch.isVisible();
            resultListView.setVisible(false);
            historySearch.setVisible(showHistory);
        });

        plus.setOnAction(event -> {
            if (plusMenu.isShowing()) {
                plusMenu.hide();
            } else {
                plusMenu.show(plus, Side.BOTTOM, 0, 0);
            }
        });

        mainMenu.getItems().addAll(gameItem, vocabItem);

        menu.setOnAction(event -> {
            if (mainMenu.isShowing()) {
                mainMenu.hide();
            } else {
                mainMenu.show(menu, Side.BOTTOM, 0, 0);
            }
        });

        addItem.setOnAction(e -> {
            show("/com/example/dictionary_uet/AddWord.fxml");
        });

        editItem.setOnAction(e -> {
            show("/com/example/dictionary_uet/EditWord.fxml");
        });

        deleteItem.setOnAction(e -> {
            show("/com/example/dictionary_uet/DeleteWord.fxml");
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
    private ListView<String> resultListView,historySearch,allWords;

    @FXML
    private TextField searchField;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private List<String> relatedWords;

    @FXML
    private Button arrowButton,plus,menu;

    @FXML
    MenuItem addItem,editItem,deleteItem,gameItem,vocabItem;

    @FXML
    ContextMenu plusMenu,mainMenu;

    @FXML
    AnchorPane screen;
}