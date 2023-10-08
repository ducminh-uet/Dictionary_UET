package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

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
        List<String> relatedWords = Arrays.asList("Apple","Applicant", "Banana","Borrow", "Carry","Cock", "Date","Down", "Fig","Fan", "Grape","Good");

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String keyword = newValue.trim();

            if (!keyword.isEmpty()) {
                resultListView.getItems().clear();
                resultListView.getItems().addAll(relatedWords);
                resultListView.setVisible(true);
            } else {
                resultListView.getItems().clear();
                resultListView.setVisible(false);
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();
    }
}
