package Scene;

import Entity.Player;
import Logic.GameSession;
import Logic.Manager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import Event.input;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;
import Item.Inventory;

public class GameScene {
    private Stage stage;
    private Pane root = new Pane();
    private Pane gamePane = new Pane();
    private Pane UiPane = new Pane();
    private Manager manager;
    private GameSession savegame;
    private Parent setting;
    private Parent lose;
    private Parent win;

    private ImageView bomicon;
    private ImageView foodicon;
    private ImageView speedicon;
    private ImageView keyicon;

    private Rectangle hpBackground;
    private Rectangle hpBar;

    private Rectangle manaBackground;
    private Rectangle manaBar;

    private static final double BAR_WIDTH = 220;
    private static final double BAR_HEIGHT = 20;

    private Pane PausePane;
    private PauseController pauseController;

    private ImageView selectionframe;
    private int select = 0;


    public GameScene(Stage stage, Manager manager) {
        this.stage = stage;
        this.manager = manager;
    }
    public GameScene(Stage stage) {
        this.stage = stage;
        this.manager = null;
    }

    public void Hotbar() {
        ImageView hotbar = new ImageView(
                new Image(getClass().getResource("/image/HotBarr.png").toExternalForm())
        );
        bomicon = new ImageView(new Image(getClass().getResource("/image/bomicon.png").toExternalForm()));
        foodicon = new ImageView(new Image(getClass().getResource("/image/foodicon.png").toExternalForm()));
        speedicon = new ImageView(new Image(getClass().getResource("/image/speedicon.png").toExternalForm()));
        keyicon = new ImageView(new Image(getClass().getResource("/image/keyicon.png").toExternalForm()));

        bomicon.setFitWidth(40);
        bomicon.setFitHeight(40);
        bomicon.setLayoutX(
                280+60);

        bomicon.setLayoutY(
                654
        );

        keyicon.setFitWidth(40);
        keyicon.setFitHeight(40);
        keyicon.setLayoutX(
                280+60*2
        );

        keyicon.setLayoutY(
                654
        );

        foodicon.setFitWidth(40);
        foodicon.setFitHeight(40);
        foodicon.setLayoutX(
               280+60*3
        );

        foodicon.setLayoutY(
                654
        );

        speedicon.setFitWidth(40);
        speedicon.setFitHeight(40);
        speedicon.setLayoutX(
               280+60*4
        );

        speedicon.setLayoutY(
                654
        );

        hotbar.setFitWidth(SCREENWIDTH / 2);
        hotbar.setFitHeight(SCREENHEIGHT / 10 -15);
        hotbar.setLayoutX(
                (SCREENWIDTH - hotbar.getFitWidth()) / 2
        );

        hotbar.setLayoutY(
                SCREENHEIGHT - hotbar.getFitHeight() - 20
        );

        UiPane.getChildren().addAll(hotbar,bomicon,speedicon,keyicon,foodicon);


    }

    private void createStatusBar() {
        hpBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        hpBackground.setFill(Color.DARKGRAY);
        hpBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        hpBar.setFill(Color.RED);


        manaBackground = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        manaBackground.setFill(Color.DARKGRAY);
        manaBar = new Rectangle(BAR_WIDTH, BAR_HEIGHT);
        manaBar.setFill(Color.DODGERBLUE);

        hpBackground.setLayoutX(30);
        hpBackground.setLayoutY(30);
        hpBar.setLayoutX(30);
        hpBar.setLayoutY(30);

        manaBackground.setLayoutX(30);
        manaBackground.setLayoutY(60);
        manaBar.setLayoutX(30);
        manaBar.setLayoutY(60);

        UiPane.getChildren().addAll(
                hpBackground,
                hpBar,
                manaBackground,
                manaBar
        );
    }
    
    public void show() {

        root.getChildren().add(gamePane);
        root.getChildren().add(UiPane);

        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/Scene/Setting.fxml")
            );
            setting = loader.load();
            setting.setVisible(false);

            FXMLLoader pause = new FXMLLoader(
                    getClass().getResource("/Scene/GamePause.fxml")
            );
            FXMLLoader loader2 = new FXMLLoader(
                    getClass().getResource("/Scene/GameLose.fxml")
            );
            FXMLLoader loader3 = new FXMLLoader(
                    getClass().getResource("/Scene/GameWin.fxml")
            );

            win = loader3.load();
            GameWin controller = loader3.getController();
            controller.setGamescene(this);
            win.setVisible(false);
            win.setLayoutX((SCREENWIDTH-500)/2);
            win.setLayoutY((SCREENHEIGHT-400)/2);

            lose = loader2.load();
            GameLose controller2 = loader2.getController();
            controller2.setGameScene(this);
            lose.setVisible(false);
            lose.setLayoutX((SCREENWIDTH-500)/2);
            lose.setLayoutY((SCREENHEIGHT-400)/2);

            PausePane = pause.load();
            pauseController = pause.getController();
            pauseController.setGameScene(this);

            PausePane.setLayoutX((SCREENWIDTH-600)/2);
            PausePane.setLayoutY((SCREENHEIGHT-500)/2);
            PausePane.setVisible(false);

            root.getChildren().addAll(setting,PausePane,lose,win);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root, SCREENWIDTH, SCREENHEIGHT);

        Hotbar();
        createStatusBar();
        setSelectionframe();

        input inputHandler = new input();
        scene.setOnKeyPressed(event -> {
            inputHandler.handleKeyPressed(event);

            switch (event.getCode()) {
                case DIGIT1 -> select = 1;
                case DIGIT2 -> select = 2;
                case DIGIT3 -> select = 3;
                case DIGIT4 -> select = 4;
                case DIGIT5 -> select = 5;
                case ENTER -> {
                    manager.use();
                }

                default -> {
                }
            }
            manager.setSelect(select);
            selectionframe.setLayoutX(270 + select * 60);
            selectionframe.toFront();
        });
        scene.setOnKeyReleased(inputHandler::handleKeyReleased);
        if (manager == null) {
            manager = new Manager(stage, gamePane, this);
            manager.start();
        } else {
            manager.setGamePane(gamePane);
            manager.setGameScene(this);
            manager.continuegame2();
        }
        stage.setScene(scene);
    }
    public void update(Player player) {

        Inventory inventory = manager.getInventory();

        if (inventory.dembom()) {
            bomicon.setOpacity(1.0);
        } else {
            bomicon.setOpacity(0.3);
        }

        if (inventory.demfood()) {
            foodicon.setOpacity(1.0);
        } else {
            foodicon.setOpacity(0.3);
        }

        if (inventory.demspeed()) {
            speedicon.setOpacity(1.0);
        } else {
            speedicon.setOpacity(0.3);
        }

        if (inventory.havekey()) {
            keyicon.setOpacity(1.0);
        } else {
            keyicon.setOpacity(0.3);
        }


        double hpPercent =
                player.getHP() / player.getMaxHP();

        double manaPercent =
                player.getMana() / player.getMaxMana();

        hpPercent = Math.max(0, Math.min(1, hpPercent));
        manaPercent = Math.max(0, Math.min(1, manaPercent));

        hpBar.setWidth(BAR_WIDTH * hpPercent);
        manaBar.setWidth(BAR_WIDTH * manaPercent);
    }
    public void showSetting() {
        setting.setVisible(true);
    }
    public void hideSetting() {
        setting.setVisible(false);
    }
    public void showgamepause() {
        PausePane.setVisible(true);
    }
    public void hidegamepause() {
        PausePane.setVisible(false);
    }
    public void continuegame() {
        manager.continuegame();
        hidegamepause();
    }
    public void clear() {
        GameSession.clear();
    }

    public void gamelose() {
        lose.setVisible(true);
    }
    public void hidegamelose() {
        lose.setVisible(false);
    }

    public void restartgame() {
        GameSession.clear();
        manager.start();
        hidegamepause();
    }
    public void stopgame() {
        manager.stop();
        mainmenu menu = new mainmenu(stage);
        menu.show();
    }
    public void exitgame() {
        GameSession.clear();
        mainmenu menu = new mainmenu(stage);
        menu.show();
    }

    public void setSelectionframe() {
        selectionframe = new ImageView(new Image(getClass().getResource("/image/selectionframe.png").toExternalForm()));

        selectionframe.setFitWidth(55);
        selectionframe.setFitHeight(55);
        select = 1;
        selectionframe.setLayoutX(270 + select * 60);
        selectionframe.setLayoutY(644);
        UiPane.getChildren().add(selectionframe);
    }
    public void gamewin() {
        win.setVisible(true);
    }
}