package Scene;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import Event.SoundManager;

public class SettingController {

    @FXML
    private ImageView exitButton;

    @FXML
    private Slider EffectSlider;

    @FXML
    private Slider SoundSlider;

    private Runnable onBack;

    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }
    @FXML
    private void handleExitHover() {
        ScaleTransition st = new ScaleTransition(
                Duration.millis(100),
                exitButton
        );
        st.setToX(1.15);
        st.setToY(1.15);
        st.play();
    }


    @FXML
    private void handleExitUnhover() {
        ScaleTransition st = new ScaleTransition(
                Duration.millis(100),
                exitButton
        );
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();
    }
    @FXML
    private void handleExit() {
        if (onBack != null) {
            onBack.run();
        }
    }
    public void initialize() {

        // Hiển thị volume hiện tại
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