package Scene;

import Entity.Player;
import Item.Item;
import Logic.Manager;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Event.input;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import static java.lang.System.in;
import Item.Inventory;
import Scene.Camera;

public class GameScene {
    private Stage stage;
    private Pane root = new Pane();
    private Pane gamePane = new Pane();
    private Pane UiPane = new Pane();
    private Manager manager;

    private ImageView bomicon;
    private ImageView foodicon;
    private ImageView speedicon;
    private ImageView keyicon;

    public GameScene(Stage stage) {
        this.stage = stage;
    }

    public void Hotbar() {
        ImageView hotbar = new ImageView(
                new Image(getClass().getResource("/image/HotBarr.png").toExternalForm())
        );
        bomicon = new ImageView(new Image(getClass().getResource("/image/bomicon.png").toExternalForm()));
        foodicon = new ImageView(new Image(getClass().getResource("/image/foodicon.png").toExternalForm()));
        speedicon = new ImageView(new Image(getClass().getResource("/image/speedicon.png").toExternalForm()));
        keyicon = new ImageView(new Image(getClass().getResource("/image/keyicon.png").toExternalForm()));

        Inventory inventory = manager.getInventory();
        bomicon.setFitWidth(40);
        bomicon.setFitHeight(40);
        bomicon.setLayoutX(
                280+60);

        bomicon.setLayoutY(
                654
        );

        keyicon.setFitWidth(40);
        keyicon.setFitHeight(40);
        keyicon.setLayoutX(
                280+60*2
        );

        keyicon.setLayoutY(
                654
        );

        foodicon.setFitWidth(40);
        foodicon.setFitHeight(40);
        foodicon.setLayoutX(
               280+60*3
        );

        foodicon.setLayoutY(
                654
        );

        speedicon.setFitWidth(40);
        speedicon.setFitHeight(40);
        speedicon.setLayoutX(
               280+60*4
        );

        speedicon.setLayoutY(
                654
        );

        hotbar.setFitWidth(SCREENWIDTH / 2);
        hotbar.setFitHeight(SCREENHEIGHT / 10 -15);
        hotbar.setLayoutX(
                (SCREENWIDTH - hotbar.getFitWidth()) / 2
        );

        hotbar.setLayoutY(
                SCREENHEIGHT - hotbar.getFitHeight() - 20
        );

        UiPane.getChildren().addAll(hotbar,bomicon,speedicon,keyicon,foodicon);


    }
    
    public void show() {

        root.getChildren().add(gamePane);
        root.getChildren().add(UiPane);

        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);

        input inputHandler = new input();
        scene.setOnKeyPressed(inputHandler::handleKeyPressed);
        scene.setOnKeyReleased(inputHandler::handleKeyReleased);
        manager = new Manager(stage, gamePane,this);
        Hotbar();
        stage.setScene(scene);
        manager.start();
    }
    public void updateHotbar() {

        Inventory inventory = manager.getInventory();

        if (inventory.dembom()) {
            bomicon.setOpacity(1.0);
        } else {
            bomicon.setOpacity(0.3);
        }

        if (inventory.demfood()) {
            foodicon.setOpacity(1.0);
        } else {
            foodicon.setOpacity(0.3);
        }

        if (inventory.demspeed()) {
            speedicon.setOpacity(1.0);
        } else {
            speedicon.setOpacity(0.3);
        }

        if (inventory.havekeyk()) {
            keyicon.setOpacity(1.0);
        } else {
            keyicon.setOpacity(0.3);
        }
    }
}