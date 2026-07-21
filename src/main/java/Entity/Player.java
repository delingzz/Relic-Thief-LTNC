package Entity;

import Event.input;
import static java.lang.Math.sqrt;

public class Player extends Entity {
    private double deltatime = 1.0/60;
    private double mana = 2000;
    private double maxmana = 2000;
    private double oldspeed ;
    private int maxhp;

    //set up cho tiêu hao Mana;
    private double manaTimer = 0;
    private final int space = 1;
    private double manacons = 3.5;
    private boolean ismanaconsume = true;

    //set up cho hồi phục mana;
    private double timeheal = 0;
    private double timetoheal = 1;


    public Player() {
        super(100, 4.0);
        this.maxhp = 100;
        this.oldspeed = speed;
        this.mana = mana;
        this.maxmana = maxmana;
    }

    public void update() {
        move();
        healmana();
        manacost();

    }

    //lấy tọa độ X và Y;
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    //chính sửa tốc độ
    public void SpeedUp(double speed) {
        this.speed = speed;
    }

    public void ResetSpeed() {
        this.speed = oldspeed;
    }

    //cơ chế trừ máu
    public void takedame(double damage) {
        this.hp -= damage;
        if (this.hp <= 0) {
            this.hp = 0;
        }
    }

    //cơ chế cộng máu
    public void heal(int healHP) {
        this.hp += healHP;
        if (this.hp >= maxhp) {
            this.hp = maxhp;
        }
    }

    //cơ chế trừ mana
    public void manacost() {
        if (ismanaconsume && mana > 0) {
            manaTimer += deltatime;
            if (manaTimer > space) {
                mana -= manacons;
                if (mana <= 0) {
                    mana = 0;
                }
                manaTimer = 0;
            }
        }
        if (mana <= 0) {
            ismanaconsume = false;
        }
    }

    // cơ chế hồi mana
    public void healmana() {
        if (mana < maxmana && !ismanaconsume) {
            timeheal += 0.25;
            if (timeheal == timetoheal) {
                timeheal = 0;
                mana += manacons;
                if (mana >= maxmana) {
                    mana = 2000;
                }
            }
        }
    }

    public void move() {
        double dx = 0;
        double dy = 0;
        if (input.up) {
            dy -= 1;
        }
        if (input.down) {
            dy += 1;
        }
        if (input.right) {
            dx += 1;
        }
        if (input.left) {
            dx -= 1;
        }
        double length = sqrt(dx * dx + dy * dy);
        if (length > 0) {
            dx /= length;
            dy /= length;
            x += dx * speed;
            y += dy * speed;
        }
    }
}
