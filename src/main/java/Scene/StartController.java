package Scene;

import Event.HoverEffect;
import Logic.GameSession;
import Logic.Manager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class StartController {
    private GameScene gamescene;
    public ImageView restart;
    public ImageView continu;
    private mainmenu menu;

    public void setGameScene(GameScene gameScene) {
        this.gamescene = gameScene;
    }
    public void setMainmenu(mainmenu menu) {
        this.menu = menu;
    }

    public void initialize() {
        restart.setOnMouseClicked(event -> {
            GameSession.clear();
            GameScene gameScene = new GameScene(menu.getStage());
            gameScene.show();
        });
        continu.setOnMouseClicked(event -> {
            Manager manager = GameSession.getSavedGame();
            GameScene gameScene = new GameScene(menu.getStage(), manager);
            gameScene.show();
        });

        HoverEffect.addHoverEffect(continu);
        HoverEffect.addHoverEffect(restart);
    }
}
