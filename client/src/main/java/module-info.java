module com.example.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.example.network;


    opens com.example.client to javafx.fxml;
    exports com.example.client;
}