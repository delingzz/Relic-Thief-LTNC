package Item;

import Entity.Entity;
import Entity.Player;
import Entity.Bot;
import Event.input;
import Scene.TileMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import static Scene.TileMap.tileSize;
import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Bom extends Item {
    private double Bx, By;
    private double R = 108;
    private double bomdame = 200000000;
    private boolean isbom = false;
    // bán kính bom nổ
    private double blastradius = 100;
    //mảng để lưu vị trí có thể chọn để spawn bom

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
        if(sqrt(deltaX*deltaX + deltaY*deltaY) <= R) {
            if(entity instanceof Player player) {
                player.takedame(bomdame);
            }
            if(entity instanceof Bot bot) {
                bot.setstun(true);
            }
        }
    }
    public void destroy(TileMap map) {
        for(double col= Bx-2;col <= Bx +2;col += 1) {
            for(double  row = By -2;row<= By +2; row +=1) {
                double centreX = col * tileSize + tileSize/2;
                double centreY = row * tileSize + tileSize/2;

                double dx = Bx - centreX;
                double dy = By - centreY;
                if(Math.sqrt(dx*dx + dy*dy) <= blastradius) {
                    if(map.getTile((int)col,(int)row)==2) {
                        map.setTile((int)col,(int)row,0);
                    }
                }
            }
        }
    }
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public void update(Player player,Entity entity, TileMap map) {
        if(input.enter) {
            plant(player);
            input.enter = false;
        }
        dame(entity);
        destroy(map);
    }
}
