package Scene;

import Logic.Manager;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;

public class GameScene {
    private Stage stage;
    private Pane root = new Pane();
    private Pane gamePane = new Pane();
    private Pane UiPane = new Pane();

    private ImageView background = new ImageView();

    public GameScene(Stage stage) {
        this.stage = stage;
    }
    public void show() {
        background.setImage(new Image(
                getClass().getResource("/images/Map.jpg").toExternalForm()
        ));
        background.setFitWidth(3000);
        background.setFitHeight(2000);

        gamePane.getChildren().addAll(background);
        root.getChildren().addAll(gamePane,UiPane);

        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);

        stage.setScene(scene);
        Manager manager = new Manager(stage, gamePane);
        manager.start();
    }
}