package Item;

import Entity.Player;

public class Speed extends Item{
    private double deltatime = 1.0/60;
    private int time = 5;
    public Speed() {
        super(50);
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void use(Player player) {
        player.SpeedUp(6.0);
    }
}
