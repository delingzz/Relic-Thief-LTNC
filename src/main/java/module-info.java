module RelicThief {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    opens Scene to javafx.fxml;

    exports Application;
    exports Scene;
    exports Logic;
    exports Entity;
    exports Item;
}