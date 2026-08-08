package Item;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item {
    protected double x;
    protected double y;
    protected double size;
    private ImageView sprite;

    public Item(double size) {
        this.size = size;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public ImageView getSprite() {
        return sprite;
    }
    public void update() {

    }
}