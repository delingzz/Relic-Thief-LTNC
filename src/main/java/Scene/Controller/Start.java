package Scene.Controller;

import Event.HoverEffect;
import Event.SoundManager;
import Logic.GameSession;
import Logic.Manager;
import Scene.GameScene;
import Scene.mainmenu;
import javafx.scene.image.ImageView;

public class Start {
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
            menu.showSetName();
            SoundManager.stopBGM();
        });
        continu.setOnMouseClicked(event -> {
            Manager manager = GameSession.getSavedGame();
            GameScene gameScene = new GameScene(menu.getStage(), manager);
            gameScene.show();
            SoundManager.stopBGM();
        });

        HoverEffect.addHoverEffect(continu);
        HoverEffect.addHoverEffect(restart);
    }
}
