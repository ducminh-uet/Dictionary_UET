package screen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        StringBuilder origin_htmlContent = new StringBuilder();
        origin_htmlContent.append("<C><F><I><N><Q>@concentrate /'kɔnsentreit/<br />*  tính từ<br />- tập trung<br />=to concentrate troops+ tập trung quân<br />=to concentrate one's attention+ tập trung sự chú ý<br />- (hoá học) cô (chất lỏng)</Q></N></I></F></C>");

        // Loại bỏ ký tự '@' từ chuỗi
        int index = origin_htmlContent.indexOf("@");
        while (index != -1) {
            origin_htmlContent.deleteCharAt(index);
            index = origin_htmlContent.indexOf("@");
        }
        String htmlContent = origin_htmlContent.toString();
        // Tạo WebView
        WebView webView = new WebView();
        webView.getEngine().loadContent(htmlContent);

        // Tạo Scene và hiển thị WebView
        Scene scene = new Scene(webView, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}