package Item;

public class Relic extends Item{
    private boolean haverelic = false;
    public Relic() {
        super(30);
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
}
