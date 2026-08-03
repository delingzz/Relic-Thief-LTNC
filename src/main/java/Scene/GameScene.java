package Scene;

import Entity.Player;
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

public class GameScene {
    private Stage stage;
    private Pane root = new Pane();
    private Pane gamePane = new Pane();
    private Pane UiPane = new Pane();
    private Manager manager;

    private ImageView background = new ImageView();

    public GameScene(Stage stage) {
        this.stage = stage;
    }
    public void show() {

        ImageView background = new ImageView(
                new Image(getClass().getResource("/image/test.png").toExternalForm())
        );

        background.setFitWidth(SCREENWIDTH);
        background.setFitHeight(SCREENHEIGHT);

        gamePane.getChildren().add(background);

        root.getChildren().add(gamePane);

        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);

        input inputHandler = new input();
        scene.setOnKeyPressed(inputHandler::handleKeyPressed);
        scene.setOnKeyReleased(inputHandler::handleKeyReleased);

        manager = new Manager(stage, gamePane);
        manager.start();
        stage.setScene(scene);

    }
}