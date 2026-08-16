package Entity;

import Event.SoundManager;
import Item.Bom;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;



public class BomExplore extends Entity {
    private int frame = 0;
    private int k = 0;
    private double timer = 0;
    private ImageView sprite;
    private Bom bom;
    private boolean finished = false;
    private boolean soundPlayed = false;

    public BomExplore(Bom bom) {
        super(1000,0);
        this.bom = bom;
        sprite = new ImageView(getClass().getResource("/image/Bomex.png").toExternalForm());
        sprite.setViewport(new Rectangle2D(0,0,192,200));
        sprite.setFitWidth(50);
        sprite.setFitHeight(50);
        sprite.setLayoutX(bom.getX());
        sprite.setLayoutY(bom.getY());
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void animation(double dt) {
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
            if(frame == 5 && k ==2) {
                SoundManager.playSFX("/Sound/BomExplore.mp3");
                soundPlayed = true;
            }
            sprite.setViewport(new Rectangle2D(
                    frame * 192,
                    k * 200,
                    192,
                    200
            ));
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
