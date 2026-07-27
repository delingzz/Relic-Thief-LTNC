package Item;

import Entity.Player;

public class Food extends Item{
    public Food() {
        super(50);
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void use(Player player) {
        player.heal(67);
    }
    public void clear() {

    }
}
