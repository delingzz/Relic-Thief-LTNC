package Logic;

import Entity.Player;
import Item.Item;
import Item.Bom;
import Scene.mainmenu;
import javafx.stage.Stage;
import Entity.Bot;
import Scene.TileMap;

import java.util.ArrayList;
import java.util.Map;


public class Manager {

    private final Stage stage;
    private Player player;
    private ArrayList<Bot> bot = new ArrayList<>();
    private TileMap map;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Bom> bombs = new ArrayList<>();

    private int score;

    private GameState state = GameState.RUNNING;

    public void update() {}



    public Manager(Stage stage) {
        this.stage = stage;
    }
    //stage.setTitle("RelicThief");//
    public void showMainMenu() {
        mainmenu main = new mainmenu(stage);
        main.show();
    }
    public void start() {
        int[][] mapdata =;
        map = new Map(32,mapdata);
        player = new Player();
        score =0;


    }
}