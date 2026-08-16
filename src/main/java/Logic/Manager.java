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
import Entity.BomExplore;


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
    private ArrayList<BomExplore> exploreList = new ArrayList<>();
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
    private double spawntime = 2;

    private GameScene gamescene;

    private boolean escpressed = false;

    private int numberspeed = 0;
    private int numberfood = 0;
    private int numberbom = 0;

    private boolean dooropened = false;

    private int select = 0;

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
        exploreList.clear();
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
            if(item instanceof Relic relic) {
                relic.update();
            }
        }
        if(pressed == true) {
            use();
        }
        gamescene.update(player);

        Iterator<Bom> bomIt = bom.iterator();
        while (bomIt.hasNext()) {
            Bom m = bomIt.next();
            m.update(player, bot, map, dt);
        }
        Iterator<BomExplore> exploreIt = exploreList.iterator();

        while (exploreIt.hasNext()) {
            BomExplore explore = exploreIt.next();

            explore.animation(dt);

            if (explore.isFinished()) {
                gamePane.getChildren().remove(explore.getSprite());
                exploreIt.remove();
            }
        }
        win();
        resetspeed();
        spawntime -= dt;
        if (spawntime <= 0) {
            randomSpawn(map);
            spawntime = 2;
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
            lose();
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
                }
                dt = Math.min(dt, 0.05);
                update(dt);
            }
        };
    }

    public void start() {
        //xoa gameloop neu con ton tai
        if (gameloop != null) {
            gameloop.stop();
        }

        //clear tat ca tu dau
        clear();

        //khoi tao map
        int[][] original = ReadMap.loadMap("/map.txt");
        map = new TileMap(original);
        map.draw(gamePane);
        player = new Player();
        gamePane.getChildren().add(player.getSprite());

        //khoi tao vi tri key
        Key key = new Key();
        items.add(key);
        gamePane.getChildren().add(key.getSprite());
        key.setPosition(map.KeyPoint());


        //khoi tao relic
        Relic relic = new Relic();
        items.add(relic);
        gamePane.getChildren().add(relic.getSprite());
        relic.setPosition(map.RelicPoint());

        //doc cac point cua bot de spawn bot
        for(Point p : map.BotPoint()) {
            Bot b = new Bot(p.x * tileSize, p.y*tileSize,map);
            bot.add(b);
            gamePane.getChildren().add(b.getSprite());
        }

        //khoi tao portal
        portal = new Portal(map.endpoint.x*tileSize,map.endpoint.y * tileSize);
        gamePane.getChildren().add(portal.getSprite());
        //day player len truoc
        player.getSprite().toFront();

        //bat dau random spawn food, speed, bom
        randomSpawn(map);

        //khoi tao trang thai game = running
        state = GameState.RUNNING;
        //khoi tao game loop
        creatGameLoop();
        gameloop.start();
    }

    //ham random cac vat pham trong game de nguoi choi su dung
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
                    possible.add(new Point(j, i));
                }
            }
        }
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
                bom.add(b);
                items.add(b);
                gamePane.getChildren().add(b.getSprite());
                break;
            case 1:
                numberfood++;
                Food f = new Food();
                f.setPosition(p);
                items.add(f);
                gamePane.getChildren().add(f.getSprite());
                break;
            case 2:
                numberspeed++;
                Speed s = new Speed();
                s.setPosition(p);
                items.add(s);
                gamePane.getChildren().add(s.getSprite());
                break;
        }
    }

    //ham kiem tra xem co the loot duoc vat pham khong
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

    //ham kiem tra xem tai vi tri nay co vat pham dang co tren map hay ko de spawn
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
    public void lose() {
        gamescene.gamelose();
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
    public void stop() {
        GameSession.save(this);
        state = GameState.PAUSE;
    }
    public void exit() {
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
        if(state == GameState.WIN ) {
            return;
        }
        if(inventory.haverelic() == false) {
            return;
        }
        double distancex = player.getX() - (map.endpoint.x*36);
        double distancey = player.getY() - (map.endpoint.y*36);
        double distance = sqrt(distancex*distancex + distancey*distancey);

        if(distance < player.hitbox +18) {
            map.changeTile(map.endpoint.y, map.endpoint.x, 0);
            state = GameState.WIN;
            gamescene.gamewin();
        }
    }
    public void opendoor() {
        if(dooropened == true) {
            return;
        }
        if(!inventory.havekey()) {
            return;
        }
            double distancex = player.getX() - (map.doorpoint.x*36 +18);
            double distancey = player.getY() - (map.doorpoint.y*36 +18);
            double distance = sqrt(distancex*distancex + distancey*distancey);

            if(distance < player.hitbox +18) {
                map.changeTile(map.doorpoint.y, map.doorpoint.x, 0);

        }

    }
    private boolean pressed = false;
    public void use() {
        pressed = true;
        switch (select) {
            case 0:
                return;
            case 1:
                if (inventory.dembom()) {
                    Bom b = new Bom();
                    b.plant(player);
                    BomExplore b1 = new BomExplore(b);
                    exploreList.add(b1);
                    gamePane.getChildren().add(b1.getSprite());
                    System.out.println(
                            "Explore: " +
                                    b1.getSprite().getLayoutX() + ", " +
                                    b1.getSprite().getLayoutY()
                    );
                    bom.add(b);
                    //inventory.remove(b);
                }
                pressed = false;
                break;

            case 3:
                if (inventory.demfood()) {
                    Food f = new Food();
                    player.heal(40);
                    inventory.remove(f);
                    System.out.println(select);
                    System.out.println("da dung food");
                }
                pressed = false;
                break;

            case 4:
                if (inventory.demspeed()) {
                    Speed s = new Speed();
                    speeduptime = 5;
                    player.SpeedUp(20);
                    inventory.remove(s);
                    System.out.println(select);
                    System.out.println("da dung speed");
                    System.out.println(player.getSpeed());
                }
                pressed = false;
                break;

            case 2:
                opendoor();
                pressed = false;
                System.out.println(select);
                break;

        }
    }

    private double speeduptime =5;
    public void resetspeed() {
        if(speeduptime >=0) {
            speeduptime -= 0.0167;
        }
        else if(speeduptime <0) {
            player.ResetSpeed();
        }
    }
    public void setSelect(int select) {
        this.select = select;
    }
}