package Item;

import Scene.TileMap;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;

public class Relic extends Item{
    private ImageView sprite;
    private int frame = 0;
    private double animationTimer = 0;
    private boolean haverelic = false;
    public Relic() {
        super(30);
        sprite = new ImageView(
                new Image(getClass().getResource("/image/Relic.png").toExternalForm())
        );
        sprite.setViewport(new Rectangle2D(0,0,384,450));
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
    public void sethaverelic(boolean haverelic) {
        this.haverelic = haverelic;
    }
    public void animation() {
        animationTimer += 0.0167;
        if(animationTimer >= 0.15){
            frame = (frame + 1) % 4;
            animationTimer = 0;
        }
        sprite.setViewport(
                new Rectangle2D(
                        frame * 384,
                        0,
                        384,
                        450
                )
        );
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
