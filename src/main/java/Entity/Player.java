package Entity;

import Event.input;
import Scene.TileMap;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.geometry.Rectangle2D;

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

    private ImageView sprite;
    private boolean moving = false;
    private Direction direction = Direction.DOWN;
    private int frame = 0;
    private int k=0;
    private double animationTimer = 0;
    public double hitbox= 20;

    private boolean cantakedame = false;

    public Player() {
        super(100, 10.0);
        x=72;
        y=36;
        this.maxhp = 100;
        this.oldspeed = speed;
        this.mana = mana;
        this.maxmana = maxmana;
        sprite = new ImageView(
                new Image(getClass().getResource("/image/Player.png").toExternalForm())
        );
        sprite.setViewport(new Rectangle2D(0,0,48,48));
        sprite.setFitWidth(48);
        sprite.setFitHeight(48);

    }

    public void update(TileMap map) {
        move(map);
        healmana();
        manacost();
        animation();
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
        if(cantakedame == true) {
            this.hp -= damage;
            if (this.hp <= 0) {
                this.hp = 0;
            }
        }
        else {
            this.hp -= 0;
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
    public void move(TileMap map) {
        moving = false;
        double dx = 0;
        double dy = 0;

        if(input.up) {
            direction = Direction.UP;
            k=3;
            dy--;
            moving = true;
        }
        if(input.down) {
            direction = Direction.DOWN;
            k=0;
            dy++;
            moving = true;
        }
        if(input.left) {
            direction = Direction.LEFT;
            k=1;
            dx--;
            moving = true;
        }
        if(input.right) {
            direction = Direction.RIGHT;
            k=2;
            dx++;
            moving = true;
        }

        double length = sqrt(dx * dx + dy * dy);

        if(length > 0) {
            dx /= length;
            dy /= length;
        }

        double nextX = x + dx * speed;
        double nextY = y + dy * speed;
        double offsetX = (48 - hitbox) / 2;
        double offsetY = (48 - hitbox) / 2;
        if (map.canMove(nextX + offsetX, y + offsetY, hitbox, hitbox)) {
            x = nextX;
        }
        if (map.canMove(x + offsetX, nextY + offsetY, hitbox, hitbox)) {
            y = nextY;
        }

        sprite.setLayoutX(x);
        sprite.setLayoutY(y);
    }
    public void animation() {
        if(moving){
            animationTimer += 0.0167;
            if(animationTimer >= 0.15){
                frame = (frame + 1) % 4;
                animationTimer = 0;
            }

        }
        else {
            frame =0;
        }
        sprite.setViewport(
                new Rectangle2D(
                        frame * 125,
                        k * 125,
                        125,
                        125
                )
        );
    }
    public ImageView getSprite() {
        return sprite;
    }
    public double getHP() {
        return this.hp;
    }
    public double getMana() {
        return this.mana;
    }
    public double getMaxHP() {
        return this.maxhp;
    }
    public double getMaxMana() {
        return this.maxmana;
    }
    public void setcantakedame(boolean a) {
        this.cantakedame = a;
    }
}
