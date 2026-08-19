package Scene.Controller;

import Event.HoverEffect;
import Event.SoundManager;
import Scene.GameScene;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Slider;

public class Pause {
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
    @FXML
    private ImageView clme;
    @FXML
    private ImageView ttmm;
    @FXML
    private ImageView ndcm;

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
        clme.setOnMouseClicked(e-> {
            SoundManager.playBGM("/Sound/ChamLanMoiEm.mp3");
        });
        ttmm.setOnMouseClicked(e-> {
            SoundManager.playBGM("/Sound/TrangThaiMongMo.mp3");
        });
        ndcm.setOnMouseClicked(e-> {
            SoundManager.playBGM("/Sound/NangDuoiChanMay.mp3");
        });


        HoverEffect.addHoverEffect(exi);
        HoverEffect.addHoverEffect(cont);
        HoverEffect.addHoverEffect(restart);
        HoverEffect.addHoverEffect(ttmm);
        HoverEffect.addHoverEffect(clme);
        HoverEffect.addHoverEffect(ndcm);
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