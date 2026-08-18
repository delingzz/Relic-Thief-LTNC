package Item;

import Entity.Player;
import Event.SoundManager;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Inventory {
    //biến đếm số lượng vật phầm
    public static int bomcount = 0;
    public static int foodcount = 0;
    public static int speedcount = 0;
    private boolean havekey = false;
    private boolean haverelic = false;
    // kích thước quy định khi vào vùng loot đồ
    private double size =30;
    // mảng lưu đồ loot được
    private ArrayList<Item> inventory = new ArrayList<>();
    // mảng lưu đồ hiển thị ra màn hình
    private ArrayList<Item> hotbar = new ArrayList<>();

    //hàm thêm item
    public void add(Item item) {
        SoundManager.playSFX("/Sound/Loot.mp3");
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
        if(item instanceof Key) {
            havekey = true;
            hotbar.add(item);
        }
        if(item instanceof Relic) {
            haverelic = true;
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
    //clear toan bo set lai tu dau game
    public void clear() {
        bomcount = 0;
        speedcount =0;
        foodcount =0;
        havekey = false;
        inventory.clear();
        hotbar.clear();
    }
    public ArrayList<Item> getItems() {
        return inventory;
    }    //ham tra ve item trong inventory
    public ArrayList<Item> getHotbar() {
        return hotbar;
    }    // ham tra ve cac item co trong hotbar

    public void sethavekey(boolean key) {
        this.havekey = key;
    }   //set co key khi nhat duoc key

    public boolean haverelic() {
        return haverelic;
    }   //set co relic khi nhat duoc relic
    public boolean havekey() {
        return havekey;
    }     // ham tra ve key de kiem tra xem co key hay chua

    public boolean dembom() {
        return bomcount >=1;
    }
    public boolean demfood() {
        return foodcount >=1;
    }
    public boolean demspeed() {
        return speedcount >=1;
    }
}
