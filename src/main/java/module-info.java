module com.example.dictionary_uet {
    requires javafx.controls;
    requires javafx.fxml;




    opens com.example.dictionary_uet to javafx.fxml;
    exports screen;
}