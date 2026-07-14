package Entity;

public class Player extends Entity{

    private double mana =2000;
    private double maxmana = 2000;
    private double oldspeed;
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
        super(100,4.0);
        this.maxhp =100;
        this.oldspeed = speed;
    }
    @Override
    public void update() {
        HealMana();
        ManaCost();
    }

    //chính sửa tốc độ
    public void SpeedUp(double speed) {
        this.speed = speed;
    }
    public void ResetSpeed() {
        this.speed = oldspeed;
    }

    //cơ chế trừ máu
    public void TakeDame(double damage) {
        this.hp -= damage;
        if(this.hp <=0 ) {
            this.hp =0;
        }
    }

    //cơ chế cộng máu
    public void heal(int healHP) {
        this.hp += healHP;
        if(this.hp >= maxhp) {
            this.hp = maxhp;
        }
    }

    //cơ chế trừ mana
    public void ManaCost() {
        if(ismanaconsume && mana >0) {
            manaTimer += 0.0167;
            if(manaTimer > space ) {
                mana -= manacons;

                if(mana <=0) {
                    mana = 0;
                }

                manaTimer = 0;
            }
        }

        if(mana <=0) {
            ismanaconsume = false;
        }
    }

    public void HealMana() {
        if(mana < maxmana && !ismanaconsume) {
            timeheal += 0.25;
            if(timeheal == timetoheal) {
                timeheal = 0;
                mana += manacons;
                if(mana >= maxmana) {
                    mana = 2000;
                }
            }
        }
    }
}
