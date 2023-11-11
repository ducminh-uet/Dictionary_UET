package screen;

import javafx.event.EventType;
import dictionary.tool.SQL;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import dictionary.tool.TranslateAPI;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import dictionary.tool.Sound;

import java.net.URISyntaxException;

import static com.sun.javafx.util.Utils.getScreen;


public class Main implements Initializable {
     //Tạo sự kiện tùy chỉnh


    private ExecutorService executor = Executors.newFixedThreadPool(1);
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

            String initWord = "Hello";
            String wordResult = TranslateAPI.translate("en", "vi", initWord);

            String a = "<html><body><h1>Hello</h1></body></html>";
            WebEngine webEngine = currentDetail.getEngine();
            relatedWords = FXCollections.observableArrayList(SQL.getAllWords());
            allWords.setItems((ObservableList<String>) relatedWords);
            allWords.setItems(FXCollections.observableList(SQL.getAllWords())); // Lấy danh sách từ
            allWords.setOnMouseClicked(event -> {
                String selectedWord = allWords.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    current.setText(selectedWord);
                    int index = allWords.getSelectionModel().getSelectedIndex();
                    if (index >= 0 && index < SQL.getAllDetails().size()) {
                        webEngine.loadContent(SQL.getAllDetails().get(index));
                    }

                }
            });
            searchField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    String keyword = searchField.getText().trim();
                    if (!keyword.isEmpty()) {
                        List<String> filteredWords = relatedWords.stream()
                                .filter(word -> word.toLowerCase().startsWith(keyword.toLowerCase()))
                                .collect(Collectors.toList());

                        if (!filteredWords.isEmpty()) {
                            String selectedWord = filteredWords.get(0);
                            current.setText(selectedWord);
                            int index = relatedWords.indexOf(selectedWord);
                            if (index >= 0 && index < SQL.getAllDetails().size()) {
                                webEngine.loadContent(SQL.getAllDetails().get(index));
                            }
                        }
                        else {
                            // Hiển thị cảnh báo "No data"
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("No data");
                            alert.setHeaderText(null);
                            alert.setContentText("Không có dữ liệu về từ bạn đang tìm kiếm!");
                            alert.showAndWait();
                        }
                    }
                    resultListView.setVisible(false); // Ẩn ListView
                }
            });



            volumeButton.setOnAction(e -> {
                String selectedWord = current.getText(); // Lấy từ hiện tại
                if (selectedWord != null) {
                    Sound.Speech(selectedWord); // Phát âm từ được chọn
                }
            });

            setDark(dark);
            dark.setOnAction(e -> {
                System.out.println("Haha");
                toggleButtonAction();
            });

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                //sử dụng bất đồng bộ
                executor.submit(() -> {
                    String keyword = newValue.trim().toLowerCase();
                    if (!keyword.isEmpty()) {
                        List<String> filteredWords = relatedWords.stream()
                                .filter(word -> word.toLowerCase().startsWith(keyword))
                                .collect(Collectors.toList());

                        // cập nhật
                        Platform.runLater(() -> {
                            resultListView.getItems().clear();
                            resultListView.getItems().addAll(filteredWords);
                            resultListView.setVisible(true);
                            resultListView.setOnMouseClicked(event -> {
                                String selectedWord = resultListView.getSelectionModel().getSelectedItem();
                                if (selectedWord != null) {
                                    current.setText(selectedWord);
                                    int index = relatedWords.indexOf(selectedWord); // Tìm chỉ số của từ trong relatedWords
                                    if (index >= 0 && index < SQL.getAllDetails().size()) {
                                        webEngine.loadContent(SQL.getAllDetails().get(index));
                                    }
                                    resultListView.setVisible(false); // Ẩn ListView
                                }
                            });
                        });
                    } else {
                        // cập nhật
                        Platform.runLater(() -> {
                            resultListView.getItems().clear();
                            resultListView.setVisible(false);
                        });
                    }
                });
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

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(gameItem, vocabItem,translateItem);
            mainMenu.getItems().setAll(menuItems);


            menu.setOnAction(event -> {
                if (mainMenu.isShowing()) {
                    mainMenu.hide();
                } else {
                    mainMenu.show(menu, Side.BOTTOM, 0, 0);
                }
            });

            addItem.setOnAction(e -> show("/com/example/dictionary_uet/AddWord.fxml"));

            editItem.setOnAction(e -> show("/com/example/dictionary_uet/EditWord.fxml"));

            deleteItem.setOnAction(e -> show("/com/example/dictionary_uet/DeleteWord.fxml"));

            translateItem.setOnAction(e -> {
                System.out.println("Hello");
                //show("/com/example/dictionary_uet/Translate.fxml");
                show2();
            });

            // tắt ExecutorService khi out
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                executor.shutdown();
            }));

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    //end initialize
    public Translate getMain() {
        return translateController;
    }

    public void setMain(Translate translateController) {
        this.translateController = translateController;
    }

    private Translate translateController;

    @FXML
    public void toggleButtonAction() {
        if (dark.isSelected()) {
            toggle_image.setImage(new Image("/image/toggle.png"));
        } else {
            toggle_image.setImage(new Image("/image/toggle2.png"));
        }

        boolean toggled = dark.isSelected();
        Translate.changeInterfaceColor(toggled);

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
    private void show2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/dictionary_uet/Translate.fxml"));
            AnchorPane component = loader.load();
            translateController = loader.getController(); // Gán controller đã load cho biến translateController
            setNode(component);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private WebView currentDetail;

    @FXML
    private ListView<String> resultListView,historySearch,allWords;

    @FXML
    private TextField searchField,current;

    @FXML
    private List<String> relatedWords;

    @FXML
    private Button arrowButton,volumeButton,plus,menu;

    @FXML
    MenuItem addItem,editItem,deleteItem,gameItem,vocabItem,translateItem;

    @FXML
    ContextMenu plusMenu,mainMenu;

    @FXML
    AnchorPane screen;

    @FXML
    private ToggleButton dark;

    @FXML
    private Tooltip history,edit,Menu,toggle;

    @FXML
    private ImageView toggle_image;

    public ToggleButton getDark() {
        return dark;
    }

    public void setDark(ToggleButton dark) {
        this.dark = dark;
    }


}