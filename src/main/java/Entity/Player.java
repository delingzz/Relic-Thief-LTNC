    package Entity;

    import Event.input;
    import Scene.TileMap;
    import Event.SoundManager;

    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.scene.text.Text;

    import javafx.geometry.Rectangle2D;

    import static java.lang.Math.sqrt;

    public class Player extends Entity {
        private double deltatime = 1.0/60;
        private double mana = 2000;
        private double maxmana = 2000;
        private double oldspeed ;
        private int maxhp;

        private double steptimer = 0.4;

        //set up cho tiêu hao Mana;
        private double manaTimer = 0;
        private final int space = 1;
        private double manaheal = 10;

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

        private String name;
        private Text nameText;

        public Player(String name) {
            super(100000, 5.0);
            x=3*36;
            y=5* 36;
            this.maxhp = 100;
            this.oldspeed = speed;
            if(name == null) {
                this.name = "Player";
            }
            else {
                this.name = name;
            }
            sprite = new ImageView(
                    new Image(getClass().getResource("/image/Player.png").toExternalForm())
            );
            nameText = new Text(this.name);
            nameText.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            nameText.setFill(Color.WHITE); // Màu chữ tên
            nameText.setStroke(Color.BLACK); // Viền đen xung quanh chữ để dễ nhìn
            nameText.setStrokeWidth(0.5);
            sprite.setViewport(new Rectangle2D(0,0,48,48));
            sprite.setFitWidth(48);
            sprite.setFitHeight(48);

        }
        private void updateNamePosition() {
            double textWidth = nameText.getBoundsInLocal().getWidth();
            nameText.setX(x + (48 - textWidth) / 2);
            nameText.setY(y - 10);
        }

        public void update(TileMap map) {
            move(map);
            if(moving) {
                steptimer -= deltatime;
                if(steptimer <=0) {
                    SoundManager.playSFX("/Sound/PlayerWalk.mp3");
                    steptimer = 0.4;
                }
            }
            else {
                steptimer = 0;
            }
            healmana();
            if(speed > oldspeed) {
                manacost(40);
            }
            else if(moving) {
                manacost(20);
            }
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
            if (damage > 1000) {
                Thread.dumpStack();
                return;
            }
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
        public void manacost(double manacons) {
            if (mana > 0) {
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
                ResetSpeed();
            }
        }

        // cơ chế hồi mana
        public void healmana() {
            if (mana < maxmana && !moving) {
                timeheal += deltatime;
                if (timeheal >= timetoheal) {
                    timeheal = 0;
                    mana += manaheal;
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
            if (map.canMove(x + offsetX, nextY + offsetY , hitbox, hitbox)) {
                y = nextY;
            }
            sprite.setLayoutX(x);
            sprite.setLayoutY(y);
            updateNamePosition();
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
        public double getSpeed() {
            return this.speed;
        }
        public Text getNameText() {
            return nameText;
        }
        public String getName() {
            return this.name;
        }
    }
