module com.example.relicthief {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.relicthief to javafx.fxml;
    exports com.example.relicthief;
}