

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class UI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main/resources/com/example/testfxMain.fxml"));
        Scene scene = new Scene(root, 600, 400);

        Button arrowButton = (Button) root.lookup("#arrowButton");
        ListView<String> HistorySearcher = (ListView<String>) root.lookup("#HistorySearcher");

        arrowButton.setOnAction(event -> {
            if (HistorySearcher.isVisible()) {
                HistorySearcher.setVisible(false); // Ẩn ListView
            } else {
                HistorySearcher.setVisible(true); // Hiển thị ListView
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Example");
        primaryStage.show();
    }
}
