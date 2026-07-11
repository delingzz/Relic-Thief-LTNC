package Scene;

import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import javafx.animation.ScaleTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class HoverEffect {
    public static void addHoverEffect(ImageView btn) {
        btn.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.1);
            st.setToY(1.1);
            st.play();
        });
        btn.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), btn);
            st.setToX(1.0);
            st.setToY(1.0);
            st.play();
        });
    }
}
