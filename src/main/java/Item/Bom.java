package Item;

import Entity.Entity;
import Entity.Player;
import Entity.Bot;
import Event.SoundManager;
import Event.input;
import Scene.TileMap;
import Entity.BomExplore;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import static Scene.TileMap.tileSize;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Bom extends Item {
    // Bx, By la toa do dat bom
    private double Bx, By;
    private boolean isbom = false;

    private ImageView sprite;
    private int frame = 0;
    private double animationTimer = 0;
    // bán kính bom nổ
    private boolean hasDamaged = false;

    private double timer = 0;
    private final double exploretime = 3.0;
    private boolean exploded = false;

    //mảng để lưu vị trí có thể chọn để spawn bom

    public Bom() {
        super(70);
        sprite = new ImageView(new Image(getClass().getResource("/image/Bom.png").toExternalForm()));
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
    public double getBx() {
        return Bx;
    }
    public double getBy() {
        return By;
    }

    public void plant(Player player) {
        this.Bx = player.getX();
        this.By = player.getY();
        isbom = true;
        timer = 0;
    }
    public void setPosition(Point P) {
        this.x = P.x * TileMap.tileSize;
        this.y = P.y * TileMap.tileSize ;
        sprite.setLayoutX(P.x * TileMap.tileSize);
        sprite.setLayoutY(P.y * TileMap.tileSize);
    }
    public void update(double dt) {
        timer += dt;
        animation();
        if (!isbom) {
            return;
        }
        if (timer >= exploretime && !exploded) {
            exploded = true;
        }
        hasDamaged = true;
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
    public void setExploded(boolean a) {
        this.exploded = a;
    }
    public double getExploretime() {
        return exploretime;
    }
    public boolean isExploded() {
        return exploded;
    }
}
