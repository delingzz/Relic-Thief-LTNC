package Item;

import javafx.scene.image.Image;

public abstract class Item {
    protected double x;
    protected double y;
    protected double size;

    public Item(double size) {
        this.size = size;
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public abstract void clear();
}