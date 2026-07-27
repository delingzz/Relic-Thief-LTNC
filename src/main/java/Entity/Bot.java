package Entity;

import Scene.TileMap;

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
    private double deltatime = 0.0167;
    public double stuntimer = 5.0;
    private boolean isStun = false;
    private double oldspeed = speed;

    private TileMap map;

    private double targetX;
    private double targetY;
    private boolean hastarget = false;

    public Bot(double Sx,double Sy,TileMap map) {
        super(2000, 3.5);
        this.Sx = Sx;
        this.Sy = Sy;
        this.map= map;
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
        attacktimer += deltatime;
        if (attacktimer >= timetoattack) {
            player.takedame(this.damage);
            attacktimer = 0;
        }
    }
    public void stun() {
        speed = 0;
    }
    // hàm này sủa logic sau

    //hàm di chuyển cho bot
    public void move(double X, double Y, TileMap map) {
        double deltaX = X - x;
        double deltaY = Y - y;

        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance <= 0) return;

        double nextX = x + (deltaX / distance) * speed;
        double nextY = y + (deltaY / distance) * speed;

        if (!map.isWall(nextX, y)) {
            x = nextX;
        }
        if (!map.isWall(x, nextY)) {
            y = nextY;
        }
    }

    //hàm đuổi theo người chơi
    public void catchplayer(double Px, double Py) {
        move(Px, Py,map);
    }

    //hàm quay về điểm spawn
    public void returntospawnpoint() {
        move(Sx, Sy,map);
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
                move(targetX, targetY,map);
                if (abs(x - targetX) < 3.5 && abs(y - targetY) < 3.5) {
                    PatrolTimer = 120;
                    hastarget = false;
                }
            }
        }
    }
    public void setstun(boolean stun) {
        this.isStun = stun;
    }
    public void update(Player player) {
        double distance = Math.sqrt((player.getX() - x) *(player.getX()-x) + (player.getY()-y)* (player.getY() - y));
        double disspawn = Math.sqrt((Sx - x) *(Sx-x) + (Sy-y)* (Sy - y));
        if(isStun==true) {
            stuntimer -= deltatime;
            stun();
            if(stuntimer <=0) {
                isStun = false;
                speed = oldspeed;
            }
            return;
        }
        if(distance <= attackspace) {
            attack(player);
            return;
        }
        if(distance <= visible) {
            catchplayer(player.getX(), player.getY());
            return;
        }
        if(disspawn >= maxdistance) {
            returntospawnpoint();
        }
        patrol();
    }
}
