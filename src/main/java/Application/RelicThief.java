package Application;

import Logic.Manager;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Window;

import java.io.File;
import java.io.IOException;

import Scene.mainmenu;

public class RelicThief extends Application {

    public static final int SCREENWIDTH = 1080;
    public static final int SCREENHEIGHT =720;
    public static final int BUTTONWIDTH = 300;
    public static final int BUTTONHEIGHT = 80;

    @Override
    public void start(Stage stage) throws IOException {
        //Manager GameManager = new Manager(stage);
        //GameManager.showMainMenu();
        mainmenu menu = new mainmenu(stage);
        menu.show();
        stage.setTitle("RelicThief");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
