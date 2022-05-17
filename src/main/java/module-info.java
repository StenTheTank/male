module com.example.oopprojekt.malemang {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.oopprojekt.malemang to javafx.fxml;
    exports com.example.oopprojekt.malemang;
}