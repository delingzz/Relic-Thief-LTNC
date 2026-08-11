package Scene;

import Event.HoverEffect;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class PauseController {
    private GameScene gamescene;
    @FXML
    private ImageView restart;
    @FXML
    private ImageView cont;
    @FXML
    private ImageView exi;
    public void setGameScene(GameScene gameScene) {
        this.gamescene = gameScene;
    }
    @FXML
    public void initialize() {
        restart.setOnMouseClicked(event -> {
            gamescene.restartgame();
        });
        cont.setOnMouseClicked(event -> {
            gamescene.continuegame();
        });
        exi.setOnMouseClicked(event -> {
            gamescene.exitgame();
        });
        HoverEffect.addHoverEffect(exi);
        HoverEffect.addHoverEffect(cont);
        HoverEffect.addHoverEffect(restart);
    }
}