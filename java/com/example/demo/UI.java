package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/demo/Main.fxml"));

        Scene scene = new Scene(root, 600, 400);
        TextField searchField = (TextField) root.lookup("#searchField");
        ListView<String> resultListView = (ListView<String>) root.lookup("#resultListView");
        List<String> relatedWords = Arrays.asList("Apple", "Applicant", "Banana", "Borrow", "Carry", "Cock", "Date", "Down", "Fig", "Fan", "Grape", "Good");

        ScrollPane scrollPane = (ScrollPane) root.lookup("#scrollPane");
        ListView<String> allWords = new ListView<>();
        allWords.getItems().addAll(relatedWords);
        scrollPane.setContent(allWords);


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String keyword = newValue.trim().toLowerCase(); //trim() : xóa khoảng trắng ở đầu và cuối chuỗi
                                                            //toLowerCase() : chuyển hoa -> thường

            if (!keyword.isEmpty()) {
                List<String> filteredWords = relatedWords.stream()  //Lọc sử dụng Java Stream
                        .filter(word -> word.toLowerCase().startsWith(keyword)) //Tạo bộ lọc "filter"
                        .collect(Collectors.toList()); //Thực thi "filter"


                resultListView.getItems().clear();
                resultListView.getItems().addAll(filteredWords);
                resultListView.setVisible(true);
            } else {
                resultListView.getItems().clear();
                resultListView.setVisible(false);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("English Dictionary");
        primaryStage.show();
    }
}
