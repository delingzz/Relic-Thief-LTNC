package Entity;

import static java.lang.Math.abs;

public class Bot extends Entity {

    //set tầm nhìn cho bot
    private double visible = 200;
    private double attackspace = 20;
    private double damage = 40;
    private double attacktimer = 0;
    private double timetoattack = 1.5;
    private double maxdistance = 100;
    private double k = 0.5;//set tạm thế
    private int PatrolTimer = 0;
    private double Sx, Sy; // tọa độ spawn của bot

    public double stuntimer;

    private double targetX;
    private double targetY;
    private boolean hastarget = false;

    public Bot() {
        super(2000, 3.5);
    }

    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void setvisible(int visible) {
        this.visible = visible;
    }

    public void attack(Player player) {
        attacktimer += 0.0167;
        if (attacktimer >= timetoattack) {
            player.takedame(this.damage);
            attacktimer = 0;
        }
    }
    public void stun(double time) {
        speed = 0;
        stuntimer = time;
    }
    // hàm này sủa logic sau
    public boolean iswall(double next, double location) {
        if (location == next) {
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

        double nextX = x + (deltaX / distance) * speed;
        double nextY = y + (deltaY / distance) * speed;

        if (!iswall(nextX, y)) {
            x = nextX;
        }
        if (!iswall(x, nextY)) {
            y = nextY;
        }
    }

    //hàm đuổi theo người chơi
    public void catchplayer(double Px, double Py) {
        move(Px, Py);
    }

    //hàm quay về điểm spawn
    public void returntospawnpoint() {
        move(Sx, Sy);
    }

    public void patrol() {
        if (PatrolTimer > 0) {
            PatrolTimer--;
        }
        if (PatrolTimer == 0) {
            if (hastarget == false) {
                targetX = (int) (Math.random() * 72) + (Sx - 36);
                targetY = (int) (Math.random() * 72) + (Sy - 36);
                hastarget = true;
            }
            if (hastarget == true) {
                move(targetX, targetY);
                if (abs(x - targetX) < 3.5 && abs(y - targetY) < 3.5) {
                    PatrolTimer = 120;
                    hastarget = false;
                }
            }
        }
    }
    public void update(Player player) {
        double distance = Math.sqrt((player.getX() - x) *(player.getX()-x) + (player.getY()-y)* (player.getY() - y));
        double disspawn = Math.sqrt((Sx - x) *(Sx-x) + (Sy-y)* (Sy - y));
        if(distance <= visible) {
            catchplayer(player.getX(), player.getY());
            return;
        }
        if(distance <= attackspace) {
            attack(player);
            return;
        }

        if(disspawn >= maxdistance) {
            returntospawnpoint();
        }
        patrol();
    }
}
