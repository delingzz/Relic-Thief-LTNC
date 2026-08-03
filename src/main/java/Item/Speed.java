package Item;

import Entity.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Speed extends Item{
    private double deltatime = 1.0/60;
    private ImageView sprite;
    private int frame = 0;
    private double animationTimer = 0;
    private int time = 5;
    public Speed() {
        super(50);
        sprite = new ImageView(
                new Image(getClass().getResource("/image/Key.png").toExternalForm())
        );
        sprite.setViewport(new Rectangle2D(0,0,274,277));
        sprite.setPreserveRatio(false);
        sprite.setFitWidth(36);
        sprite.setFitHeight(36);
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void use(Player player) {
        player.SpeedUp(6.0);
    }
    public void animation() {
        animationTimer += 0.0167;
        if(animationTimer >= 0.15){
            frame = (frame + 1) % 4;
            animationTimer = 0;
        }
        sprite.setViewport(
                new Rectangle2D(
                        frame * 274,
                        0,
                        274,
                        277
                )
        );
    }
    public ImageView getSprite() {
        return sprite;
    }
    public void update() {
        animation();
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
    }
}
