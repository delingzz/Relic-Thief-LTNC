package Item;

public class Key extends Item{
    private boolean havekey = false;
    public Key() {
        super(30);
    }
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public void sethavekey(boolean havekey) {
        this.havekey = havekey;
    }
}
