package Entity;

public class Bot extends Entity{

    //set tầm nhìn cho bot
    private double visible = 200;
    private double attackspace = 20;
    private double damage = 40;
    private double attacktimer = 0;
    private double timetoattack = 1.5;

    private boolean canseeplayer = false;
    protected double Px,Py; // tọa độ của player
    protected double lastPx,lastPy; //tọa độ cuối cùng thấy player
    private double Sx,Sy; // tọa độ spawn của bot

    public Bot() {
        super(2000,3.5);
    }
    public void setvisible(int visible) {
        this.visible = visible;
    }


    public void attack(Player player) {
        attacktimer += 0.0167;
        if(attacktimer >= timetoattack) {
            player.TakeDame(this.damage);
            attacktimer = 0;
        }
    }
    public void update() {
    }
}
