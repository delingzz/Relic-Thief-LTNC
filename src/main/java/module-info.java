module com.example.relicthief {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens com.example.relicthief to javafx.fxml;
    exports com.example.relicthief;
    exports Application;
    opens Application to javafx.fxml;
}