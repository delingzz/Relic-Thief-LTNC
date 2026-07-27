package Item;

import Entity.Player;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Inventory {
    //biến đếm số lượng vật phầm
    private int bomcount = 0;
    private int foodcount = 0;
    private int speedcount = 0;
    // kích thước quy định khi vào vùng loot đồ
    private double size =30;
    // mảng lưu đồ loot được
    private ArrayList<Item> inventory = new ArrayList<>();
    // mảng lưu đồ hiển thị ra màn hình
    private ArrayList<Item> hotbar = new ArrayList<>();

    //hàm thêm item
    public void add(Item item) {
        inventory.add(item);
        if(item instanceof Bom) {
            bomcount++;
            if(bomcount == 1) {
                hotbar.add(item);
            }
        }
        if(item instanceof Food) {
            foodcount++;
            if(foodcount == 1) {
                hotbar.add(item);
            }
        }
        if(item instanceof Speed) {
            speedcount++;
            if(speedcount == 1) {
                hotbar.add(item);
            }
        }
    }
    //hàm xóa item
    public void remove(Item item) {
        inventory.remove(item);
        if(item instanceof Bom) {
            bomcount --;
            if(bomcount ==0) {
                hotbar.removeIf(i -> i instanceof Bom);
            }
        }
        if(item instanceof Food) {
            foodcount --;
            if(foodcount ==0) {
                hotbar.removeIf(i -> i instanceof Food);
            }
        }
        if(item instanceof Speed) {
            speedcount --;
            if(speedcount ==0) {
                hotbar.removeIf(i -> i instanceof Speed);
            }
        }
    }
    //hàm check loot đồ
    public boolean loot(Item item, Player player) {
        double dx = item.getX() - player.getX();
        double dy = item.getY() - player.getY();
        if(dx * dx + dy * dy <= size * size) {
            add(item);
            if(item instanceof Key) {
                Key key = (Key) item;
                key.sethavekey(true);
            }
            if(item instanceof Relic) {
                Relic relic = (Relic) item;
                relic.sethaverelic(true);
            }
            return true;
        }
        return false;
    }
    public ArrayList<Item> getItems() {
        return inventory;
    }
    public ArrayList<Item> getHotbar() {
        return hotbar;
    }
}
