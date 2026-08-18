package Event;

import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {

    private final ImageView imageView;

    private final int frameCount;
    private final int columns;

    private final int frameWidth;
    private final int frameHeight;

    private int lastFrame = -1;

    public SpriteAnimation(ImageView imageView, int frameCount, int columns, int frameWidth, int frameHeight, double durationMillis) {
        this.imageView = imageView;
        this.frameCount = frameCount;
        this.columns = columns;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        setCycleDuration(Duration.millis(durationMillis));
        setCycleCount(INDEFINITE);
    }

    @Override
    protected void interpolate(double frac) {int frame = Math.min((int)(frac * frameCount), frameCount - 1);
        if(frame == lastFrame) return;
        int x = (frame % columns) * frameWidth;
        int y = (frame / columns) * frameHeight;
        imageView.setViewport(new Rectangle2D(x, y, frameWidth, frameHeight));
        lastFrame = frame;
    }
}