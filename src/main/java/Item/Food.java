package Item;

import Entity.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Food extends Item{
    private ImageView sprite;
    private int frame = 0;
    private double animationTimer = 0;
    public Food() {
        super(50);
        sprite = new ImageView(
                new Image(getClass().getResource("/image/Food.png").toExternalForm())
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
        player.heal(67);
    }
    public void clear() {

    }
    public void animation() {
        animationTimer += 0.0167;
        if(animationTimer >= 0.15){
            frame = (frame + 1) % 4;
            animationTimer = 0;
        }
        sprite.setViewport(
                new Rectangle2D(
                        frame * 265,
                        0,
                        265,
                        227
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
