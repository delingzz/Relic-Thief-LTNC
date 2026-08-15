package Scene;

import Event.HoverEffect;
import Event.SoundManager;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;

public class PauseController {
    private GameScene gamescene;
    @FXML
    private ImageView restart;
    @FXML
    private ImageView cont;
    @FXML
    private ImageView exi;
    @FXML
    private Slider EffectSlider;

    @FXML
    private Slider SoundSlider;

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
            gamescene.stopgame();
        });
        HoverEffect.addHoverEffect(exi);
        HoverEffect.addHoverEffect(cont);
        HoverEffect.addHoverEffect(restart);
        SoundSlider.setValue(SoundManager.getBGMVolume() * 100);
        EffectSlider.setValue(SoundManager.getSFXVolume() * 100);
        // Volume nhạc nền
        SoundSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100.0;
            SoundManager.setBGMVolume(volume);
        });
        // Volume hiệu ứng
        EffectSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100.0;
            SoundManager.setSFXVolume(volume);
        });
    }
}