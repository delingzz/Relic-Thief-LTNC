module RelicThief {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.media;
    opens Scene to javafx.fxml;

    exports Application;
    exports Scene;
    exports Logic;
    exports Entity;
    exports Item;
    exports Scene.Controller;
    opens Scene.Controller to javafx.fxml;
}