package Scene;

import Event.HoverEffect;
import Logic.GameSession;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class GameLose {
    private GameScene gamescene;
    @FXML
    private ImageView restart;

    @FXML
    private ImageView exit;

    public void setGameScene(GameScene gameScene) {
        this.gamescene = gameScene;
    }

    @FXML
    public void initialize() {
        restart.setOnMouseClicked(e-> {
            gamescene.hidegamelose();
            gamescene.restartgame();
        });
        exit.setOnMouseClicked(e-> {
            gamescene.exitgame();
        });
        HoverEffect.addHoverEffect(exit);
        HoverEffect.addHoverEffect(restart);
    }
}
