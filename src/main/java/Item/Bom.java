package Item;

import Entity.Entity;
import Entity.Player;
import Entity.Bot;
import static java.lang.Math.abs;

public class Bom extends Item {
    private double Bx, By;
    private double R = 108;
    private double bomdame = 200000000;
    private boolean isbom = false;
    public Bom() {
        super(50);
    }
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    public void plant(Player player) {
        this.Bx = player.getX();
        this.By = player.getY();
        isbom = true;
    }
    public void dame(Entity entity) {
        double deltaX = Bx - entity.getX();
        double deltaY = By - entity.getY();
        if(Math.sqrt(deltaX*deltaX + deltaY*deltaY) <= R) {
            if(entity instanceof Player) {
                Player player = (Player) entity;
                player.takedame(bomdame);
            }
            if(entity instanceof Bot) {
                Bot bot = (Bot) entity;
                bot.stun(5);
            }
        }
    }
    public void destroy() {}

}
