package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UI extends Application {
    /**
     * Triển khai.
     */

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //---------------------------

        /*--------------------------


        ---------------------------*/
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Main.fxml"));


        Scene scene = new Scene(root, 600, 400);
        TextField searchField = (TextField) root.lookup("#searchField");
        ListView<String> resultListView = (ListView<String>) root.lookup("#resultListView");
        ListView<String> historySearch = (ListView<String>) root.lookup("#historySearch");


        List<String> relatedWords = Arrays.asList("Apple", "Applicant", "Banana", "Borrow", "Carry", "Cock", "Date", "Down", "Fig", "Fan", "Grape", "Good");

        ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPane");
        ListView<String> allWords = new ListView<>();
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
        Button arrowButton = (Button) root.lookup("#arrowButton");
        arrowButton.setOnAction(event -> {
            boolean showHistory = !historySearch.isVisible();
            resultListView.setVisible(false);
            historySearch.setVisible(showHistory);
        });

        // PLUS BUTTON
        Button plus = (Button) root.lookup("#Plus");
        ContextMenu plusMenu = new ContextMenu();

        //Thêm từ
//        Parent root1 = FXMLLoader.load(getClass().getResource("/com/example/demo/AddWord.fxml")); //Trang mới
//        Scene scene1 = new Scene(root1, 600, 400);
//        TextField addWord = new TextField("Nhập từ ần thêm");
//        addWord.setVisible(false);
//        TextArea addDetail = new TextArea("Nội dung");
//        addDetail.setVisible(false);
//        addItem.setOnAction(e -> {
//
//            addWord.setVisible(true);
//            addDetail.setVisible(true);
//        });
        //Thêm từ
        MenuItem addItem = new MenuItem("Thêm từ");
        //Sửa từ
        MenuItem editItem = new MenuItem("Sửa từ");
        //Xoá từ
        MenuItem deleteItem = new MenuItem("Xóa từ");
        plusMenu.getItems().addAll(addItem, editItem, deleteItem);

            addItem.setOnAction(e -> {
            // Tạo một đối tượng FXMLLoader để tải FXML mới.
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddWord.fxml"));

            // Tạo một đối tượng Parent để lưu trữ giao diện từ FXML mới.
            Parent addPage = null;
            try {
                addPage = loader.load();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Tạo một đối tượng Scene mới từ giao diện FXML mới.
            Scene scene1 = new Scene(addPage);

            // Tạo một Stage mới và đặt Scene của nó là trang FXML mới.
            Stage stage1 = new Stage();
            stage1.setScene(scene1);

            // Hiển thị trang FXML mới.
            stage1.show();
        });


        // Ẩn hiên
        plus.setOnAction(event -> {
            if (plusMenu.isShowing()) {
                plusMenu.hide();
            } else {
                plusMenu.show(plus, Side.BOTTOM, 0, 0);
            }
        });

        Button menu = (Button) root.lookup("#menu");
        ContextMenu mainMenu = new ContextMenu();
        MenuItem gameItem = new MenuItem("Game");
        MenuItem vocabItem = new MenuItem("Vocabulary");
        mainMenu.getItems().addAll(gameItem, vocabItem);

        menu.setOnAction(event -> {
            if (mainMenu.isShowing()) {
                mainMenu.hide();
            } else {
                mainMenu.show(menu, Side.BOTTOM, 0, 0);
            }
        });

        primaryStage.setScene(scene);
        scene.getStylesheets().add("Style.css");
        primaryStage.setTitle("E-V Dictionary");
        primaryStage.show();
    }
}




