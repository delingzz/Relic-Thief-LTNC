package Entity;

import javafx.scene.image.ImageView;


public abstract class Entity {
    protected int hp;
    protected double x,y;
    protected double speed;
    protected ImageView sprite;
    public abstract double getX();
    public abstract double getY();
    public Entity(int hp, double speed) {
        this.hp = hp;
        this.speed = speed;
    }
}
