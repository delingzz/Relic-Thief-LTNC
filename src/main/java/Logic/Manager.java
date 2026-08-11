package Logic;

import Entity.Player;
import Entity.Entity;
import Item.Item;
import Item.Bom;
import Item.Key;
import Item.Relic;
import Scene.*;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Entity.Bot;
import Item.Inventory;
import Item.Food;
import Item.Speed;
import Event.input;
import Entity.Portal;


import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import static Scene.TileMap.tileSize;
import static java.lang.Math.random;
import static java.lang.Math.sqrt;


public class Manager {

    private static final int MAPWIDTH = 60 * 36;
    private static final int MAPHEIGHT = 40 * 36;

    private final Stage stage;
    private Pane gamePane;
    private Player player;
    private Portal portal;
    private ArrayList<Bot> bot = new ArrayList<>();
    private TileMap map;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Bom> bom = new ArrayList<>();
    private Inventory inventory = new Inventory();
    private AnimationTimer gameloop;
    private long lastTime = 0;
    // kích thước quy định khi vào vùng loot đồ
    private double size = 36;
    private static final double deltatime = 0.0167;
    Random random = new Random();

    private int score;
    private Camera camera = new Camera();

    private GameState state = GameState.PAUSE;
    ArrayList<Point> possible = new ArrayList<>();
    private double spawntime = 5;

    private GameScene gamescene;

    private boolean escpressed = false;

    private int numberspeed = 0;
    private int numberfood = 0;
    private int numberbom = 0;

    private boolean dooropened = false;

    ArrayList<Integer> a = new ArrayList<>();

    public Manager(Stage stage, Pane gamePane, GameScene gameScene) {
        this.stage = stage;
        this.gamePane = gamePane;
        this.gamescene = gameScene;
    }

    public void clear() {
        numberspeed = 0;
        numberfood = 0;
        numberbom = 0;
        player = null;
        bot.clear();
        items.clear();
        bom.clear();
        inventory.clear();
        score = 0;
    }

    public void update(double dt) {
        if (input.esc && !escpressed) {
            pause();
            escpressed = true;
        }
        if (!input.esc) {
            escpressed = false;
        }else if(state != GameState.RUNNING)
            return;
        player.update(map);
        portal.update();
        for (Bot b : bot) {
            b.update(player);
        }
        for (Bom m : bom) {
            m.update(player, bot, map);
        }
        for (Item item : items) {

            if (item instanceof Food food) {
                food.update();
            }

            if (item instanceof Speed speed) {
                speed.update();
            }

            if (item instanceof Key key) {
                key.update();
            }
        }
        gamescene.update(player);
        opendoor();
        spawntime -= dt;
        if (spawntime <= 0) {
            randomSpawn(map);
            spawntime = 10;
        }
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
                if(state != GameState.RUNNING) {
                    return;
                }
                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                if (input.esc) {
                    System.out.println("ESC NHAN");
                }
                dt = Math.min(dt, 0.05);
                update(dt);
            }
        };
    }

    public void start() {
        if (gameloop != null) {
            gameloop.stop();
        }
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
        portal = new Portal(map.endpoint.x*tileSize,map.endpoint.y * tileSize);
        gamePane.getChildren().add(portal.getSprite());
        player.getSprite().toFront();
        randomSpawn(map);
        System.out.println(
                "HP SAU KHI TAO PLAYER: "
                        + player.getHP() + " / " + player.getMaxHP()
        );
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
        for (int i = 0; i < map.getRows(); i++) {
            for (int j = 0; j < map.getCols(); j++) {

                if (map.getTile(i, j) == 0) {
                    possible.add(new Point(i, j));
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
                b.setPosition(p);
                System.out.println(numberbom);
                bom.add(b);
                items.add(b);
                gamePane.getChildren().add(b.getSprite());
                break;
            case 1:
                numberfood++;
                Food f = new Food();
                f.setPosition(p);
                System.out.println(numberfood);
                items.add(f);
                gamePane.getChildren().add(f.getSprite());
                break;
            case 2:
                numberspeed++;
                Speed s = new Speed();
                s.setPosition(p);
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
    public Inventory getInventory() {
        return inventory;
    }

    public void pause() {
        if(state == GameState.RUNNING) {
            gamescene.showgamepause();
            state = GameState.PAUSE;
        }
        else if(state == GameState.PAUSE) {
            gamescene.hidegamepause();
            state  = GameState.RUNNING;
        }
    }
    public void continuegame() {
        lastTime = 0;
        state = GameState.RUNNING;
    }
    public void exit() {
        GameSession.save(this);
        state = GameState.PAUSE;
    }
    public void setGamePane(Pane gamePane) {
        this.gamePane = gamePane;
    }

    public void setGameScene(GameScene gameScene) {
        this.gamescene = gameScene;
    }
    public void continuegame2() {
        map.draw(gamePane);
        gamePane.getChildren().add(player.getSprite());
        for (Bot b : bot) {
            gamePane.getChildren().add(b.getSprite());
        }
        for (Item item : items) {
            gamePane.getChildren().add(item.getSprite());
        }
        state = GameState.RUNNING;
    }
    public void win() {
        state = GameState.WIN;
    }
    public void opendoor() {
        if(dooropened == true) {
            return;
        }
        if(!inventory.havekey()) {
            return;
        }
        double distancex = player.getX() - (map.doorpoint.x*36 + 18);
        double distancey = player.getY() - (map.doorpoint.y*36 + 18);
        double distance = sqrt(distancex*distancex + distancey*distancey);

        if(distance < player.hitbox +18) {
            map.changeTile(map.doorpoint.y, map.doorpoint.x, 0);

        }
    }
}