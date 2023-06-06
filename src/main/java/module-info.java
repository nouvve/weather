module com.example.weather {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires json.simple;

    opens com.example.weather to javafx.fxml;
    exports com.example.weather;
}