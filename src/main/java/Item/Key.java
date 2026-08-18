package Item;

import Scene.TileMap;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Key extends Item{
    private boolean havekey = false;
    private ImageView sprite;
    private int frame = 0;
    private double animationTimer = 0;
    public Key() {
        super(70);
        sprite = new ImageView(
                new Image(getClass().getResource("/image/Key.png").toExternalForm())
        );
        sprite.setViewport(new Rectangle2D(0,0,274,277));
        sprite.setPreserveRatio(false);
        sprite.setFitWidth(36);
        sprite.setFitHeight(36);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void sethavekey(boolean havekey) {
        this.havekey = havekey;
    }
    public void draw() {

    }
    public void animation() {
        animationTimer += 0.0167;
        if(animationTimer >= 0.15){
            frame = (frame + 1) % 4;
            animationTimer = 0;
        }
        sprite.setViewport(new Rectangle2D(frame * 274, 0, 274, 277));
    }
    public ImageView getSprite() {
        return sprite;
    }
    public void update() {
        animation();
    }
    public void setPosition(Point P) {
        this.x = P.x * TileMap.tileSize;
        this.y = P.y * TileMap.tileSize ;
        sprite.setLayoutX(P.x * TileMap.tileSize);
        sprite.setLayoutY(P.y * TileMap.tileSize);
    }
}
