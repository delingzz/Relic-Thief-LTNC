package Entity;

import Event.SoundManager;
import Item.Bom;
import Scene.TileMap;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import Scene.TileMap;

import java.util.ArrayList;

import static Scene.TileMap.tileSize;
import static java.lang.Math.sqrt;

public class BomExplore extends Entity {
    private int frame = 0;
    private int k = 0;
    //thời gian để bom nổ
    private double timer = 0;
    private ImageView sprite;
    private Bom bom;
    //kiem tra xem đã chạy hết sprite chưa để dừng chạy
    private boolean finished = false;
    private boolean soundPlayed = false;
    //bán kính nổ
    private double R = 108;
    private double bomdame = 100;

    public BomExplore(Bom bom) {
        super(1000,0);
        this.bom = bom;
        this.x = bom.getBx();
        this.y = bom.getBy();
        sprite = new ImageView(getClass().getResource("/image/Bomex.png").toExternalForm());
        sprite.setViewport(new Rectangle2D(0,0,192,200));
        sprite.setFitWidth(50);
        sprite.setFitHeight(50);
        sprite.setLayoutX(this.x);
        sprite.setLayoutY(this.y);
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void animation(double dt, Player player, ArrayList<Bot> bots,TileMap map) {
        if(finished) {
            return;
        }
        timer += dt;
        if (timer >= bom.getExploretime() / 32) {
            timer = 0;
            frame++;
            if (frame >= 8) {
                frame = 0;
                k++;
            }
            if(k>=4) {
                finished = true;
                return;
            }
            if(frame == 5 && k ==1) {
                SoundManager.playSFX("/Sound/BomExplore.mp3");
                destroy(map);
                soundPlayed = true;
                dame(player);
                for(Bot b : bots) {
                    dame(b);
                }
            }
            sprite.setViewport(new Rectangle2D(
                    frame * 192,
                    k * 200,
                    192,
                    200
            ));
        }
    }
    public void dame(Entity entity) {
        double deltaX = x - entity.getX();
        double deltaY = y - entity.getY();
        if(sqrt(deltaX*deltaX + deltaY*deltaY) <= R) {
            if(entity instanceof Player player) {
                player.takedame(bomdame);
            }
            if(entity instanceof Bot bot) {
                bot.setstun(true);
            }
        }
    }
    public void destroy(TileMap map) {

        int bombCol = (int)(x / tileSize);
        int bombRow = (int)(y / tileSize);

        for (int row = bombRow - 2; row <= bombRow + 2; row++) {
            for (int col = bombCol - 2; col <= bombCol + 2; col++) {

                if (row < 0 || row >= map.getRows()
                        || col < 0 || col >= map.getCols()) {
                    continue;
                }

                double centerX = col * tileSize + tileSize / 2.0;
                double centerY = row * tileSize + tileSize / 2.0;

                double dx = x - centerX;
                double dy = y - centerY;

                if (dx * dx + dy * dy <= R * R) {
                    if (map.getTile(row, col) == 4) {
                        map.changeTile(row,col,0);
                        map.setTile(row, col, 0);
                    }
                }
            }
        }
    }
    public boolean isFinished() {
        return finished;
    }
    public ImageView getSprite() {return sprite;}
    public double getFrame() {
        return this.frame;
    }
    public double getK() {
        return this.k;
    }
}
