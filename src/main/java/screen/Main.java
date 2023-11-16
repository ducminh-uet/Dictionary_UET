package screen;

import javafx.animation.FadeTransition;
import javafx.event.EventType;
import dictionary.tool.SQL;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.media.AudioClip;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import dictionary.Dictionary;
import dictionary.DictionaryManagement;
import dictionary.Word;
import dictionary.tool.SQL;
import dictionary.tool.TranslateAPI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import dictionary.tool.Sound;
import javafx.util.Callback;

import java.net.URISyntaxException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Main implements Initializable {
    private List<String> searchHistory = new ArrayList<>();
    DictionaryManagement dictionaryManagement = new DictionaryManagement();
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private ObservableList<Word> wordList;
    private String existingWord;

    @FXML
    private ListView<Word> allWords;

    @FXML
    private WebView currentDetail;

    @FXML
    private ListView<String> resultListView, historySearch;

    @FXML
    private TextField searchField, current;

    @FXML
    private Button arrowButton, volumeButton, plus, menu;

    @FXML
    private MenuItem addItem, editItem, deleteItem, gameItem, vocabItem, translateItem;

    @FXML
    private ContextMenu plusMenu, mainMenu;

    @FXML
    private AnchorPane screen;

    @FXML
    private Tooltip history, edit, Menu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            String initWord = "Hello";
            String wordResult = TranslateAPI.translate("en", "vi", initWord);

            String a = "<html><body><h1>Hello</h1></body></html>";
            WebEngine webEngine = currentDetail.getEngine();

            wordList = FXCollections.observableList(SQL.getAllWords());
            allWords.setItems(wordList);

            allWords.setCellFactory(param -> new ListCell<Word>() {
                @Override
                protected void updateItem(Word item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getWord_target());
                    }
                }
            });

            allWords.setOnMouseClicked(event -> {
                Word selectedWord = allWords.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    current.setText(selectedWord.getWord_target());
                    webEngine.loadContent(selectedWord.getWord_explain());

                }
            });

            searchField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    String keyword = searchField.getText().trim();
                    if (!keyword.isEmpty()) {
                        searchHistory.add(0, keyword);
                        List<Word> filteredWords = wordList.stream()
                                .filter(word -> word.getWord_target().toLowerCase().startsWith(keyword.toLowerCase()))
                                .collect(Collectors.toList());

                        if (!filteredWords.isEmpty()) {
                            Word selectedWord = filteredWords.get(0);
                            current.setText(selectedWord.getWord_target());
                            resultListView.setVisible(false);
                            webEngine.loadContent(selectedWord.getWord_explain());
                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("No data");
                            alert.setHeaderText(null);
                            alert.setContentText("Không có dữ liệu về từ bạn đang tìm kiếm!");
                            alert.showAndWait();
                        }
                    }
                }
            });

            volumeButton.setOnAction(e -> {
                String selectedWord = current.getText();
                if (selectedWord != null) {
                    Sound.Speech(selectedWord);
                }
            });

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                executor.submit(() -> {
                    String keyword = newValue.trim().toLowerCase();
                    if (!keyword.isEmpty()) {
                        List<String> filteredWords = wordList.stream()
                                .filter(word -> word.getWord_target().toLowerCase().startsWith(keyword))
                                .map(Word::getWord_target)
                                .collect(Collectors.toList());

                        Platform.runLater(() -> {
                            resultListView.getItems().clear();
                            resultListView.getItems().addAll(filteredWords);
                            resultListView.setVisible(true);
                            resultListView.setOnMouseClicked(event -> {
                                String selectedWord = resultListView.getSelectionModel().getSelectedItem();
                                if (selectedWord != null) {
                                    searchHistory.add(0, selectedWord.toLowerCase());
                                    Word word = wordList.stream()
                                            .filter(w -> w.getWord_target().equalsIgnoreCase(selectedWord))
                                            .findFirst()
                                            .orElse(null);
                                    if (word != null) {
                                        current.setText(word.getWord_target());
                                        webEngine.loadContent(word.getWord_explain());
                                    }
                                    resultListView.setVisible(false);
                                }
                            });
                        });
                    } else {
                        Platform.runLater(() -> {
                            resultListView.getItems().clear();
                            resultListView.setVisible(false);
                        });
                    }
                });
            });

            historySearch.setVisible(false);

            // Hiển thị lịch sử tìm kiếm
            arrowButton.setOnAction(e -> {
                if (searchHistory.size() > 20) {
                    for (int i = 20; i < searchHistory.size(); i++) {
                        searchHistory.remove(searchHistory.get(i));
                    }
                }
                boolean showHistory = !historySearch.isVisible();
                resultListView.setVisible(false);
                historySearch.setVisible(showHistory);

                if (showHistory) {
                    // Hiển thị danh sách lịch sử tìm kiếm
                    ObservableList<String> historyItems = FXCollections.observableArrayList(searchHistory);
                    historySearch.setItems(historyItems);
                }
            });

            historySearch.setOnMouseClicked(e -> {
                String selectedWord = historySearch.getSelectionModel().getSelectedItem();
                if (selectedWord != null) {
                    Word word = wordList.stream()
                            .filter(w -> w.getWord_target().equalsIgnoreCase(selectedWord))
                            .findFirst()
                            .orElse(null);
                    if (word != null) {
                        current.setText(word.getWord_target());
                        webEngine.loadContent(word.getWord_explain());
                    }
                    historySearch.setVisible(false);
                }
            });

            plus.setOnAction(event -> {
                if (plusMenu.isShowing()) {
                    plusMenu.hide();
                } else {
                    plusMenu.show(plus, Side.BOTTOM, 0, 0);
                }
            });

            ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(gameItem, vocabItem, translateItem);
            mainMenu.getItems().setAll(menuItems);

            menu.setOnAction(event -> {
                if (mainMenu.isShowing()) {
                    mainMenu.hide();
                } else {
                    mainMenu.show(menu, Side.BOTTOM, 0, 0);
                }
            });

            addItem.setOnAction(e -> {
                handleButtonClick();
                showAddWordDialog();
            });

            editItem.setOnAction(e -> {
                handleButtonClick();
                showEditWordDialog();
            });

            deleteItem.setOnAction(e -> {
                handleButtonClick();
                showDeleteWordDialog();
            });

            translateItem.setOnAction(e -> {
                handleButtonClick();
                System.out.println("Hello");
                show("/com/example/dictionary_uet/Translate.fxml");

            });

            gameItem.setOnAction(e -> {
                handleButtonClick();
                System.out.println("Vao game");
                show("/game/screen/MenuController.fxml/");
            });

            toggle_image.setImage(new Image("/image/toggle.png"));
            notificationLabel.setVisible(true);
            //notificationLabel.setOpacity(0);

            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(500), notificationLabel);
            fadeInTransition.setFromValue(0);
            fadeInTransition.setToValue(1);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> hideLabel())
            ); //Delay 2s
            timeline.setDelay(Duration.millis(100)); // Delay 0.1 second before starting the timeline

            fadeInTransition.play();
            timeline.play();

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

    @FXML
    public void toggleButtonAction() {
        if (dark.isSelected()) {
            handleButtonClick();
            show("/com/example/dictionary_uet/Main_V_E.fxml");
            System.out.println("Chuyen ve V - E\nNut mau trang");

        }

    }
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
        String audioFile = getClass().getResource("/sound/button1_click.mp3").toString();
        AudioClip audioClip = new AudioClip(audioFile);
        // Play the audio
        audioClip.play();

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

    private void showAddWordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Thêm từ mới");

        // Tạo các controls trong Dialog
        TextField wordField = new TextField();
        TextField meaningField = new TextField();
        Label wordLabel = new Label("Từ:");
        Label meaningLabel = new Label("Nghĩa:");

        // Tạo layout cho Dialog
        GridPane grid = new GridPane();
        grid.add(wordLabel, 1, 1);
        grid.add(wordField, 2, 1);
        grid.add(meaningLabel, 1, 2);
        grid.add(meaningField, 2, 2);
        dialog.getDialogPane().setContent(grid);

        // Thêm các nút vào Dialog
        ButtonType addButton = new ButtonType("Thêm", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Hủy", ButtonType.CANCEL.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(addButton, cancelButton);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/Style.css").toExternalForm()
        );

        // Thiết lập cách xử lý khi nhấn nút Thêm
        dialog.setResultConverter(param -> {
            if (param == addButton) {
                String newWord = wordField.getText();
                String newMeaning = meaningField.getText();

                // Kiểm tra xem từ mới đã tồn tại trong danh sách chưa
                if (isWordExists(newWord, wordList)) {
                    // Hiển thị cảnh báo và xử lý thêm từ mới hoặc thay thế từ cũ
                    showReplaceWordDialog(newWord, existingWord, newMeaning);
                } else {
                    // Thêm từ mới vào danh sách
                    addWordToDictionary(newWord, newMeaning);
                    // Hiển thị thông báo thành công
                    showAlert(Alert.AlertType.INFORMATION, "Thêm từ", "Từ đã được thêm thành công!");
                }
            }
            return param;
        });

        // Hiển thị Dialog
        dialog.showAndWait();
    }

    @FXML
    private ToggleButton dark;

    @FXML
    private Tooltip toggle;

    @FXML
    private ImageView toggle_image;
    @FXML
    private Label notificationLabel;

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Ham addWord
     */
    private void addWordToDictionary(String word, String meaning) {
        Word newWord = new Word(word,meaning);
        wordList.add(newWord);
    }

    /**
     * Xet xem 1 chuoi string co trung voi tu nao khong.
     * Vi du nhap abc thi xem trong list co abc chua
     */
    private boolean isWordExists(String newWord, List<Word> wordList) {
        return wordList.stream()
                .anyMatch(word -> word.getWord_target().equalsIgnoreCase(newWord));
    }

    private void showReplaceWordDialog(String existingWord, String newWord, String newMeaning) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận thêm từ");
        alert.setHeaderText(null);
        alert.setContentText("Từ \"" + existingWord + "\" đã tồn tại trong từ điển. Bạn muốn thêm từ mới hay giữ nguyên từ cũ?");

        ButtonType replaceButton = new ButtonType("Thay thế từ cũ");
        ButtonType keepOldButton = new ButtonType("Giữ nguyên từ cũ", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(replaceButton, keepOldButton);


        alert.showAndWait().ifPresent(response -> {
            if (response == replaceButton) {
                // Thay thế từ cũ bằng từ mới
                replaceWord(existingWord, newWord, newMeaning);
            } else if (response == keepOldButton) {
                // Người dùng muốn giữ nguyên từ cũ
            }
        });
    }

    /**
     * Thay the tu. Chính xác là thay đổi nghĩa của từ ấy.
     */
    private void replaceWord(String existingWord, String newWord, String newMeaning) {
        // Tìm từ cũ trong danh sách allWords
        Word existingWordObject = wordList.stream()
                .filter(word -> word.getWord_target().equalsIgnoreCase(existingWord))
                .findFirst()
                .orElse(null);

        if (existingWordObject != null) {

            // Lấy vị trí của từ cũ trong danh sách
            int index = wordList.indexOf(existingWordObject);

            // Thay thế từ cũ bằng từ mới
            wordList.get(index).setWord_explain(newMeaning);

            // Cập nhật danh sách hiển thị
            allWords.setItems(FXCollections.observableList(wordList));

            // Hiển thị thông báo thành công
            showAlert(Alert.AlertType.INFORMATION, "Thay thế từ", "Từ đã được thay thế thành công!");
        }
    }
    private void showEditWordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa từ");

        // Tạo controls trong Dialog
        TextField wordToEditField = new TextField();
        Label wordLabel = new Label("Nhập từ muốn sửa:");

        // Tạo layout cho Dialog
        GridPane grid = new GridPane();
        grid.add(wordLabel, 1, 1);
        grid.add(wordToEditField, 2, 1);
        dialog.getDialogPane().setContent(grid);

        // Thêm các nút vào Dialog
        ButtonType editButton = new ButtonType("Sửa", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Hủy", ButtonType.CANCEL.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(editButton, cancelButton);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/Style.css").toExternalForm()
        );

        // Thiết lập cách xử lý khi nhấn nút Sửa
        dialog.setResultConverter(param -> {
            if (param == editButton) {
                String wordToEdit = wordToEditField.getText().trim();

                // Kiểm tra xem từ cần sửa có trong từ điển không
                Word word = getWordByTarget(wordToEdit);
                if (word != null) {
                    // Nếu có, hiển thị Dialog nhập nghĩa mới
                    showEditMeaningDialog(word);
                } else {
                    // Nếu không, hiển thị thông báo
                    showAlert(Alert.AlertType.WARNING, "Từ chưa có", "Từ này chưa có trong từ điển, bạn vui lòng thêm từ.");
                }
            }
            return param;
        });

        // Hiển thị Dialog
        dialog.showAndWait();
    }

    /**
     * Tìm từ trong cái list các từ
     */
    private Word getWordByTarget(String target) {
        // Tìm từ trong danh sách wordList
        return wordList.stream()
                .filter(word -> word.getWord_target().equalsIgnoreCase(target))
                .findFirst()
                .orElse(null);
    }

    private void showEditMeaningDialog(Word word) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Sửa nghĩa");

        // Tạo controls trong Dialog
        TextArea meaningTextArea = new TextArea();
        meaningTextArea.setText(word.getWord_explain());
        Label meaningLabel = new Label("Nhập nghĩa mới:");

        // Tạo layout cho Dialog
        GridPane grid = new GridPane();
        grid.add(meaningLabel, 1, 1);
        grid.add(meaningTextArea, 2, 1);
        dialog.getDialogPane().setContent(grid);

        // Thêm các nút vào Dialog
        ButtonType saveButton = new ButtonType("Lưu", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Hủy", ButtonType.CANCEL.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(saveButton, cancelButton);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/Style.css").toExternalForm()
        );
        // Thiết lập cách xử lý khi nhấn nút Lưu
        dialog.setResultConverter(param -> {
            if (param == saveButton) {
                // Lưu nghĩa mới vào danh sách
                String newMeaning = meaningTextArea.getText();
                word.setWord_explain(newMeaning);

                // Cập nhật danh sách hiển thị
                allWords.setItems(FXCollections.observableList(wordList));

                // Hiển thị thông báo thành công
                showAlert(Alert.AlertType.INFORMATION, "Sửa từ", "Từ đã được sửa thành công!");
            }
            return param;
        });

        // Hiển thị Dialog
        dialog.showAndWait();
    }
    private void showDeleteWordDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Xóa từ");

        // Tạo controls trong Dialog
        TextField wordToDeleteField = new TextField();
        Label wordLabel = new Label("Nhập từ muốn xóa:");

        // Tạo layout cho Dialog
        GridPane grid = new GridPane();
        grid.add(wordLabel, 1, 1);
        grid.add(wordToDeleteField, 2, 1);
        dialog.getDialogPane().setContent(grid);

        // Thêm các nút vào Dialog
        ButtonType deleteButton = new ButtonType("Xóa", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Hủy", ButtonType.CANCEL.getButtonData());
        dialog.getDialogPane().getButtonTypes().addAll(deleteButton, cancelButton);
        dialog.getDialogPane().getStylesheets().add(
                getClass().getResource("/Style.css").toExternalForm()
        );

        // Thiết lập cách xử lý khi nhấn nút Xóa
        dialog.setResultConverter(param -> {
            if (param == deleteButton) {
                String wordToDelete = wordToDeleteField.getText().trim();

                // Kiểm tra xem từ cần xóa có trong từ điển không
                Word word = getWordByTarget(wordToDelete);
                if (word != null) {
                    // Nếu có, tiến hành xóa từ
                    deleteWord(word);
                } else {
                    // Nếu không, hiển thị thông báo
                    showAlert(Alert.AlertType.WARNING, "Từ không có", "Từ này không có trong từ điển, không thể xóa.");
                }
            }
            return param;
        });

        // Hiển thị Dialog
        dialog.showAndWait();
    }

    /**
     * Xóa từ.
     * Cái hàm này sẽ nhận 1 từ vào để xóa, tức là mình sẽ tìm từ cần xóa ở hàm tìm từ rồi ném vào đây cho nó xóa.
     */
    private void deleteWord(Word word) {
        // Xóa từ khỏi danh sách wordList
        wordList.remove(word);

        // Cập nhật danh sách hiển thị
        allWords.setItems(FXCollections.observableList(wordList));

        // Hiển thị thông báo thành công
        showAlert(Alert.AlertType.INFORMATION, "Xóa từ", "Từ đã được xóa thành công!");
    }
}