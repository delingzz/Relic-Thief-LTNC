package Scene.Controller;

import Event.HoverEffect;
import Scene.GameScene;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class GameWin {
    private GameScene gamescene;
    @FXML
    private ImageView exit;
    public void setGamescene(GameScene scene) {
        this.gamescene = scene;
    }
    public void initialize() {
        exit.setOnMouseClicked(e-> {
            gamescene.exitgame();
        });
        HoverEffect.addHoverEffect(exit);
    }
}
