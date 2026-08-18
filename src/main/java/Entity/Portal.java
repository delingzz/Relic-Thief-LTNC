package Entity;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static Scene.TileMap.tileSize;

public class Portal extends Entity {
    private double animationTimer = 0;

    private ImageView sprite;
    private int frame = 0;

    public Portal(double x, double y) {
        super(x,y);
        this.x = x;
        this.y = y;
        sprite = new ImageView(new Image(getClass().getResource("/image/Portal.png").toExternalForm()));
        sprite.setFitWidth(62);
        sprite.setFitHeight(60);
        sprite.setLayoutX(x + tileSize / 2.0 - sprite.getFitWidth() / 2.0);
        sprite.setLayoutY(y + tileSize / 2.0 - sprite.getFitHeight() + 12);;
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public ImageView getSprite() {
        return this.sprite;
    }

    public void animation() {
        animationTimer += 0.0167;
        if(animationTimer >= 0.15){
            frame = (frame + 1) % 4;
            animationTimer = 0;
        }
        sprite.setViewport(new Rectangle2D(frame * 384, 0, 384, 594));
    }
    public void update() {
        animation();
    }
}

