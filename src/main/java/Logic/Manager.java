package Logic;

import Entity.Player;
import Entity.Entity;
import Item.Item;
import Item.Bom;
import Item.Key;
import Item.Relic;
import Scene.Camera;
import Scene.mainmenu;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Entity.Bot;
import Scene.TileMap;
import Item.Inventory;
import Scene.ReadMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import static Scene.TileMap.tileSize;


public class Manager {

    private final Stage stage;
    private Pane gamePane;
    private Player player;
    private Entity entity;
    private ArrayList<Bot> bot = new ArrayList<>();
    private TileMap map;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Bom> bom = new ArrayList<>();
    private Inventory inventory = new Inventory();
    private AnimationTimer gameloop;
    private long lastTime = 0;
    // kích thước quy định khi vào vùng loot đồ
    private double size =30;
    private static final double deltatime = 0.0167;
    Random random = new Random();

    private int score;
    private Camera camera = new Camera();

    private GameState state = GameState.PAUSE;

    public void clear() {
        player = null;
        bot.clear();
        items.clear();
        bom.clear();
        inventory.clear();
        score =0;
    }
    public void update(double dt) {
        if(state != GameState.RUNNING)
            return;
        player.update(map);
        for(Bot b : bot) {
            b.update(player);
        }
        for(Bom m : bom) {
            m.update(player, entity,map);
        }
        player.update(map);
        camera.update(player);
        gamePane.setLayoutX(-camera.getCameraX());
        gamePane.setLayoutY(-camera.getCameraY());
    }
    public Manager(Stage stage, Pane gamePane) {
        this.stage = stage;
        this.gamePane = gamePane;

    }
    public void showMainMenu() {
        mainmenu main = new mainmenu(stage);
        main.show();
    }
    public void creatGameLoop() {
        gameloop = new AnimationTimer() {
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                update(dt);
            }
        };
    }
    public void start() {
        clear();

        int[][] original = ReadMap.loadMap("/map.txt");   //nhập link map
        map = new TileMap( original);
        System.out.println(original.length);
        System.out.println(original[0].length);
        player = new Player();
        gamePane.getChildren().add(player.getSprite());
        Bot b1 = new Bot(100,500,map);
        Bot b2 = new Bot(300,500,map);
        bot.add(b1);
        bot.add(b2);

        gamePane.getChildren().addAll(
                b1.getSprite(),
                b2.getSprite()
        );
        state = GameState.RUNNING;
        creatGameLoop();
        gameloop.start();
    }
    public void randomSpawn(TileMap map) {

        ArrayList<Point> possible = new ArrayList<>();

        for (int i = 0; i < SCREENHEIGHT / tileSize; i++) {
            for (int j = 0; j < SCREENWIDTH / tileSize; j++) {

                if (map.getTile(i, j) == 0) {
                    possible.add(new Point(i, j));
                }
            }
        }

        if (possible.isEmpty()) return;

        Point p = possible.get(random.nextInt(possible.size()));

        Bom bom = new Bom();
        bom.setPosition(p.x * tileSize, p.y * tileSize);
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

}