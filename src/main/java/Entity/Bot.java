package Entity;

import Event.SoundManager;
import Scene.TileMap;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Bot extends Entity {

    private Image movingimage;
    private Image attackimage;
    private Image stunimage;

    //set tầm nhìn cho bot
    private double visible = 200;
    //set tầm đánh
    private double attackspace = 40;
    private double damage = 30;
    //set thời gian đấm 1 phát
    private double attacktimer = 0;
    private double timetoattack = 0.5;
    //khoảng cách xa nhất mà bot đuổi ng
    private double maxdistance = 100;
    private double k;

    private int PatrolTimer = 0;
    private double Sx, Sy; // tọa độ spawn của bot
    private double deltatime = 0.0167;
    public double stuntimer = 5.0;
    private boolean isStun = false;
    private double oldspeed = speed;
    private ImageView sprite;

    private TileMap map;

    private double targetX;
    private double targetY;
    private boolean hastarget = false;

    private boolean moving = false;
    private Direction direction = Direction.DOWN;
    private double hitbox= 20;
    private double animationTimer = 0;
    public double undeathtime = 3.0;

    private int frame = 0;

    private boolean attack = false;

    public Bot(double Sx,double Sy,TileMap map) {
        super(2000, 1.5);

        this.Sx = Sx;
        this.Sy = Sy;
        this.x = Sx;
        this.y = Sy;
        this.map= map;
        movingimage = new Image(getClass().getResource("/image/BotMoving.png").toExternalForm());
        attackimage = new Image(getClass().getResource("/image/BotAttack.png").toExternalForm());
        stunimage = new Image(getClass().getResource("/image/BotStun.png").toExternalForm());

        // Mặc định lúc sinh ra là dùng ảnh di chuyển
        sprite = new ImageView(movingimage);
        sprite.setFitWidth(92);
        sprite.setFitHeight(72);
        sprite.setLayoutX(x-(60-36)/2.0);
        sprite.setLayoutY(y-(60-36)/2.0);
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
        attack = true;moving = true;
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
        attack = false;
        boolean moved = false;
        double deltaX = X - x;
        double deltaY = Y - y;

        double distance = sqrt(deltaX * deltaX + deltaY * deltaY);

        if (distance <= 0) return;

        double nextX = x + (deltaX / distance) * speed;
        double nextY = y + (deltaY / distance) * speed;

        double offsetX = (48 - hitbox) / 2;
        double offsetY = (48 - hitbox) / 2;
        if (map.canMove(nextX + offsetX, y + offsetY, hitbox, hitbox)) {
            x = nextX;
            moved = true;
        }
        if (map.canMove(x + offsetX, nextY + offsetY, hitbox, hitbox)) {
            y = nextY;
            moved = true;
        }
        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                direction = Direction.RIGHT;
                k = 3;
            } else {
                direction = Direction.LEFT;
                k = 2;
            }
        } else {
            if (deltaY > 0) {
                direction = Direction.DOWN;
                k = 0;
            } else {
                direction = Direction.UP;
                k = 1;
            }
        }
        moving = moved;
        sprite.setLayoutX(x-(72-36)/2.0);
        sprite.setLayoutY(y-(72-36)/2.0);
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
        if (stun) {
            this.stuntimer = 5.0;
            this.frame = 0;
        }
    }
    public void update(Player player) {
        moving = false;
        if(undeathtime >0) {
            undeathtime -= deltatime;
        }
        else {
            if(undeathtime <=0) {
                player.setcantakedame(true);
            }
        }
        double distance = sqrt((player.getX() - x) *(player.getX()-x) + (player.getY()-y)* (player.getY() - y));
        double disspawn = sqrt((Sx - x) *(Sx-x) + (Sy-y)* (Sy - y));
        if(isStun==true) {
            attack = false;
            moving = false;
            stuntimer -= deltatime;
            stun();
            if(stuntimer <=0) {
                isStun = false;
                speed = oldspeed;
            }
            animation();
            return;
        }
        if(distance <= attackspace) {
            attack(player);
        }
        else if(distance <= visible) {
            catchplayer(player.getX(), player.getY());
        }
        else if(disspawn >= maxdistance) {
            returntospawnpoint();
        }
        else {
            patrol();
        }
        animation();
    }

    public void animation() {
        if(isStun) {
            animationTimer += deltatime;
            if(animationTimer >= 0.15) {
                frame = frame +1;
                animationTimer = 0;
                if(frame == 3) {
                    frame = 0;
                }
            }
        }
        else if(attack) {
            animationTimer += 0.0167;
            if(animationTimer >= 0.15){
                frame = (frame + 1);
                animationTimer = 0;
                if(frame == 2) {
                    SoundManager.playSFX("/Sound/Punch.mp3");
                }
                if(frame ==3) {
                    frame = 0;
                    attack = false;
                }
            }
        }
        else if(moving){
            animationTimer += 0.0167;
            if(animationTimer >= 0.15){
                frame = (frame + 1) % 4;
                animationTimer = 0;
            }

        }
        else {
            frame =0;
        }
        if(isStun) {
            if(sprite.getImage() != stunimage) {
                sprite.setImage(stunimage);
                frame = 0;
            }
            sprite.setViewport(new Rectangle2D(frame*384,k*256,384,256));
        }
        else if(attack) {
            if (sprite.getImage() != attackimage) {
                sprite.setImage(attackimage);
                frame = 0;
            }
            sprite.setViewport(new Rectangle2D(frame *384,k*245,384,245));
        }
        else if(moving) {
            if (sprite.getImage() != movingimage) {
                sprite.setImage(movingimage);
            }
            sprite.setViewport(new Rectangle2D(frame *148,k*105,148,105));
        }
    }
    public ImageView getSprite() {
        return sprite;
    }
}
