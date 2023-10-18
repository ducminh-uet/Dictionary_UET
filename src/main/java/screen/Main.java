package screen;

import dictionary.tool.SQL;
import dictionary.tool.Sound;
import dictionary.tool.Translate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Main implements Initializable {
    // Add your controller logic here

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Tạo 1 cái selected để tí cho nó nói.
        //Để là StringBuffer cho đỡ tốn bộ nhớ, đa luồng đỡ đơ.
        StringBuffer selected = new StringBuffer();
        try {
            String initWord = "Hello";
            String wordResult = Translate.translate("en", "vi", initWord);

            // Lấy relatedWord từ danh sách từ.
            relatedWords = FXCollections.observableArrayList(SQL.getAllWords());
            allWords.setItems((ObservableList<String>) relatedWords);
            allWords.setItems(FXCollections.observableList(SQL.getAllWords())); // Lấy danh sách từ

            allWords.setOnMouseClicked(event -> {
                String selectedWord = allWords.getSelectionModel().getSelectedItem();
                /*Đầu tiên là cái selected này nó sẽ không tự xóa cái trước đó.
                Nghĩa là anh em chọn từ A, chọn từ B, chọn từ C thì khi phát âm từ C nó sẽ ra
                ABC, nên khi chọn 1 từ thì xóa toàn bộ cái cũ đi, và cho cái từ mới vào.
                */
                selected.setLength(0);
                selected.append(selectedWord);
                if (selectedWord != null) {
                    current.setText(selectedWord);

                    int index = allWords.getSelectionModel().getSelectedIndex();
                    if (index >= 0 && index < SQL.getAllDetails().size()) {
                        currentDetail.setText(SQL.getAllDetails().get(index)); // Lấy nghĩa tương ứng
                    }
                }
            });

            volumeButton.setOnAction(e -> {
                //Phát âm cái selected(từ mình chọn ấy).
                Sound.Speech(selected.toString());
            });

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

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(gameItem, vocabItem);
            mainMenu.getItems().setAll(menuItems);


            menu.setOnAction(event -> {
                if (mainMenu.isShowing()) {
                    mainMenu.hide();
                } else {
                    mainMenu.show(menu, Side.BOTTOM, 0, 0);
                }
            });

            addItem.setOnAction(e -> show("/com/example/dictionary_uet/Main.fxml"));

            editItem.setOnAction(e -> show("/com/example/dictionary_uet/EditWord.fxml"));

            deleteItem.setOnAction(e -> show("/com/example/dictionary_uet/DeleteWord.fxml"));

            translateItem.setOnAction(e -> {
                System.out.println("Hello");
                show("/com/example/dictionary_uet/Translate.fxml");
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
    private TextArea currentDetail;

    @FXML
    private ListView<String> resultListView, historySearch, allWords;

    @FXML
    private TextField searchField, current;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private List<String> relatedWords;

    @FXML
    private Button arrowButton, volumeButton, plus, menu;

    @FXML
    MenuItem addItem, editItem, deleteItem, gameItem, vocabItem,translateItem;

    @FXML
    ContextMenu plusMenu, mainMenu;

    @FXML
    AnchorPane screen;
}