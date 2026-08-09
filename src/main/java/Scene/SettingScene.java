package Scene;

import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SettingScene {

    @FXML
    private ImageView exitButton;

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
}