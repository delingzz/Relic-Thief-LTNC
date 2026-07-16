package Entity;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Bot extends Entity{

    //set tầm nhìn cho bot
    private double visible = 200;
    private double attackspace = 20;
    private double damage = 40;
    private double attacktimer = 0;
    private double timetoattack = 1.5;
    private double maxdistance = 100;
    private double k = 0.5;//set tạm thế

    private boolean canseeplayer = false;
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
    // hàm này sủa logic sau
    public boolean iswall(double next, double location) {
        if(location == next) {
            return true;
        }
        return false;
    }
    //hàm di chuyển cho bot
    public void move(double X, double Y) {
        double deltaX = X - x;
        double deltaY = Y - y;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance <= 0) return;

        double nextX = x + (deltaX/distance) * speed;
        double nextY = y + (deltaY/distance) * speed;

        if (!iswall(nextX, y)) {
            x = nextX;
        }
        if (!iswall(x, nextY)) {
            y = nextY;
        }
    }
    //hàm đuổi theo người chơi
    public void catchplayer(double Px,double Py) {
        move(Px,Py);
    }
    //hàm quay về điểm spawn
    public void returntospawn() {
        move(Sx,Sy);
    }
    public void patrol() {
    }
}
