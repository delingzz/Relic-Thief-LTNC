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
import Item.Food;
import Item.Speed;
import Scene.ReadMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import static Scene.TileMap.tileSize;
import static java.lang.Math.random;


public class Manager {

    private static final int MAPWIDTH = 60 * 36;
    private static final int MAPHEIGHT = 40 * 36;
    private final Stage stage;
    private Pane gamePane;
    private Player player;
    private ArrayList<Bot> bot = new ArrayList<>();
    private TileMap map;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Bom> bom = new ArrayList<>();
    private Inventory inventory = new Inventory();
    private AnimationTimer gameloop;
    private long lastTime = 0;
    // kích thước quy định khi vào vùng loot đồ
    private double size = 30;
    private static final double deltatime = 0.0167;
    Random random = new Random();

    private int score;
    private Camera camera = new Camera();

    private GameState state = GameState.PAUSE;
    ArrayList<Point> possible = new ArrayList<>();
    private double spawntime = 10;


    private int numberspeed = 0;
    private int numberfood = 0;
    private int numberbom = 0;

    ArrayList<Integer> a = new ArrayList<>();

    public void clear() {
        player = null;
        bot.clear();
        items.clear();
        bom.clear();
        inventory.clear();
        score = 0;
    }

    public void update(double dt) {
        if (state != GameState.RUNNING)
            return;
        for (Bot b : bot) {
            b.update(player);
        }
        for (Bom m : bom) {
            m.update(player, bot, map);
        }

        spawntime -= deltatime;
        if (spawntime <= 0) {
            randomSpawn(map);
            spawntime = 10;
        }

        player.update(map);
        camera.update(player);
        gamePane.setLayoutX(-camera.getCameraX());
        gamePane.setLayoutY(-camera.getCameraY());

        Iterator<Item> it = items.iterator();

        while (it.hasNext()) {
            Item item = it.next();

            if (canloot(item, player)) {
                inventory.add(item);

                gamePane.getChildren().remove(item.getSprite());

                if (item instanceof Bom)
                    numberbom--;
                else if (item instanceof Food)
                    numberfood--;
                else if (item instanceof Speed)
                    numberspeed--;

                it.remove();
            }
        }
        if (player.getHP() <= 0) {
            state = GameState.LOSE;
        }
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
        map = new TileMap(original);
        map.draw(gamePane);
        player = new Player();
        gamePane.getChildren().add(player.getSprite());
        Key key = new Key();
        items.add(key);
        gamePane.getChildren().add(key.getSprite());
        key.setPosition(map.KeyPoint());
        for(Point p : map.BotPoint()) {
            Bot b = new Bot(p.x * tileSize, p.y*tileSize,map);
            bot.add(b);
            gamePane.getChildren().add(b.getSprite());
        }
        randomSpawn(map);

        state = GameState.RUNNING;
        creatGameLoop();
        gameloop.start();
    }

    public void randomSpawn(TileMap map) {
        a.clear();

        if (numberbom < 5)
            a.add(0);

        if (numberfood < 7)
            a.add(1);

        if (numberspeed < 7)
            a.add(2);

        if (a.isEmpty())
            return;
        possible.clear();
        for (int i = 0; i < MAPHEIGHT / tileSize; i++) {
            for (int j = 0; j < MAPWIDTH / tileSize; j++) {

                if (map.getTile(i, j) == 0) {
                    possible.add(new Point(j, i));
                }
            }
        }
        if (possible.isEmpty()) return;

        Point p = new Point();
        do {
            p = possible.get(random.nextInt(possible.size()));
        } while (hasItem(p.x, p.y));

        if (hasItem(p.x, p.y))
            return;
        Random random1 = new Random();
        int k = a.get(random1.nextInt(a.size()));
        switch (k) {
            case 0:
                numberbom++;
                Bom b = new Bom();
                b.setPosition(p.x * tileSize, p.y * tileSize);
                System.out.println(numberbom);
                bom.add(b);
                items.add(b);
                gamePane.getChildren().add(b.getSprite());
                break;
            case 1:
                numberfood++;
                Food f = new Food();
                f.setPosition(p.x * tileSize, p.y * tileSize);
                System.out.println(numberfood);
                items.add(f);
                gamePane.getChildren().add(f.getSprite());
                break;
            case 2:
                numberspeed++;
                Speed s = new Speed();
                s.setPosition(p.x * tileSize, p.y * tileSize);
                System.out.println(numberspeed);
                items.add(s);
                gamePane.getChildren().add(s.getSprite());
                break;
        }
    }

    public boolean canloot(Item item, Player player) {
        double dx = item.getX() - player.getX();
        double dy = item.getY() - player.getY();
        if(item instanceof Key key) {
            key.sethavekey(true);
        }
        if(item instanceof Relic relic) {
            relic.sethaverelic(true);
        }
        return dx * dx + dy * dy <= size * size;
    }

    private boolean hasItem(int col, int row) {
        double x = col * tileSize;
        double y = row * tileSize;

        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return true;
            }
        }
        return false;
    }
}