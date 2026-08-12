package Item;

import Entity.Entity;
import Entity.Player;
import Entity.Bot;
import Event.input;
import Scene.TileMap;
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
    private double Bx, By;
    private double R = 108;
    private double bomdame = 200000000;
    private boolean isbom = false;

    private ImageView sprite;
    private int frame = 0;
    private double animationTimer = 0;
    // bán kính bom nổ
    private double blastradius = 100;
    private boolean hasDamaged = false;

    private double timer = 0;
    private final double exploretime = 3.0;
    private boolean exploded = false;

    //mảng để lưu vị trí có thể chọn để spawn bom

    public Bom() {
        super(70);
        sprite = new ImageView(
                new Image(getClass().getResource("/image/Bom.png").toExternalForm())
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

    public void plant(Player player) {
        this.Bx = player.getX();
        this.By = player.getY();
        isbom = true;
    }
    public void dame(Entity entity) {
        if(hasDamaged == false) {
            return;
        }
        double deltaX = Bx - entity.getX();
        double deltaY = By - entity.getY();
        if(sqrt(deltaX*deltaX + deltaY*deltaY) <= R) {
            if(entity instanceof Player player) {
                player.takedame(bomdame);
            }
            if(entity instanceof Bot bot) {
                bot.setstun(true);
            }
        }
        hasDamaged = true;
    }
    public void destroy(TileMap map) {

        int bombCol = (int)(Bx / tileSize);
        int bombRow = (int)(By / tileSize);

        for (int row = bombRow - 2; row <= bombRow + 2; row++) {
            for (int col = bombCol - 2; col <= bombCol + 2; col++) {

                if (row < 0 || row >= map.getRows()
                        || col < 0 || col >= map.getCols()) {
                    continue;
                }

                double centerX = col * tileSize + tileSize / 2.0;
                double centerY = row * tileSize + tileSize / 2.0;

                double dx = Bx - centerX;
                double dy = By - centerY;

                if (dx * dx + dy * dy <= blastradius * blastradius) {
                    if (map.getTile(row, col) == 4) {
                        map.changeTile(row,col,0);
                        map.setTile(row, col, 0);
                    }
                }
            }
        }
    }
    public void setPosition(Point P) {
        this.x = P.x * TileMap.tileSize;
        this.y = P.y * TileMap.tileSize ;
        sprite.setLayoutX(P.x * TileMap.tileSize);
        sprite.setLayoutY(P.y * TileMap.tileSize);
    }
    public void update(Player player, ArrayList<Bot> bots, TileMap map, double dt) {
        timer += dt;
        animation();
        if (timer >= exploretime && !exploded) {
            exploded = true;
            dame(player);
            destroy(map);
        }
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
}
