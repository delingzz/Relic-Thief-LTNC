package Logic;

import Entity.Player;
import Entity.Entity;
import Item.Item;
import Item.Bom;
import Item.Key;
import Item.Relic;
import Scene.mainmenu;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import Entity.Bot;
import Scene.TileMap;
import Item.Inventory;
import Scene.ReadMap;

import java.util.ArrayList;
import java.util.Map;


public class Manager {

    private final Stage stage;
    private Player player;
    private Entity entity;
    private ArrayList<Bot> bot = new ArrayList<>();
    private TileMap map;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Bom> bom = new ArrayList<>();
    private Inventory inventory = new Inventory();
    private AnimationTimer gameloop;
    // kích thước quy định khi vào vùng loot đồ
    private double size =30;


    private int score;

    private GameState state = GameState.PAUSE;

    public void clear() {
        player = null;
        bot.clear();
        items.clear();
        bom.clear();
        inventory.clear();
        score =0;
    }
    public void update() {
        player.update(map);
        for(Bot b : bot) {
            b.update(player);
        }
        for(Bom m : bom) {
            m.update(player, entity,map);
        }


    }
    public Manager(Stage stage) {
        this.stage = stage;
    }
    public void showMainMenu() {
        mainmenu main = new mainmenu(stage);
        main.show();
    }
    public void creatGameLoop() {
        gameloop = new AnimationTimer() {
            public void handle(long now) {
                update();
            }
        };
    }
    public void start() {
        clear();
        player = new Player();
        bot.add(new Bot(100,500,map));
        bot.add(new Bot(300,500,map));
        int[][] original = ReadMap.loadMap("");   //nhập link map
        map = new TileMap( original);
        state = GameState.RUNNING;
        creatGameLoop();
        gameloop.start();

    }
    public boolean canloot(Item item, Player player) {
        double dx = item.getX() - player.getX();
        double dy = item.getY() - player.getY();
        if(dx * dx + dy * dy <= size * size) {
            inventory.add(item);
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
    public void vacham() {
    }
}