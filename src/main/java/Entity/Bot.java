package Entity;

public class Bot extends Entity{

    //set tầm nhìn cho bot
    private int visible = 4;

    public Bot() {
        super(2000,3.5);
    }
    public void update() {

    }

    public void setvisible(int visible) {
        this.visible = visible;
    }

}
