package Scene;

import Event.HoverEffect;
import Event.SoundManager;
import Logic.GameSession;
import Scene.Controller.Name;
import Scene.Controller.Setting;
import Scene.Controller.Start;
import Scene.Tutorial;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

import static Application.RelicThief.*;

public class mainmenu {
    private Tutorial t;
    private Stage stage;
    private Pane startpane;
    private Start Start;

    private ImageView backgr = new ImageView();
    // Settings
    private Parent setting;

    private Pane namepane;      // Đã đổi tên biến tránh trùng tên class
    private Name namecontroller;

    public mainmenu(Stage stage) {
        this.stage = stage;
        this.t = new Tutorial(stage);
    }

    public void show() {
        StackPane root = new StackPane();

        Pane menup = new Pane();
        backgr.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/image/BackGround.png")).toExternalForm()));
        backgr.setFitWidth(SCREENWIDTH);
        backgr.setFitHeight(SCREENHEIGHT);
        try {
            FXMLLoader start = new FXMLLoader(getClass().getResource("/Scene/StartGame.fxml"));

            startpane = start.load();
            Start = start.getController();
            Start.setMainmenu(this);
            startpane.setVisible(false);
            StackPane.setAlignment(startpane, Pos.CENTER);
            startpane.setTranslateX(0);
            startpane.setTranslateY(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Image StartImg = new Image(
                Objects.requireNonNull(getClass().getResource("/image/StartButton.png")).toExternalForm());
        ImageView StartImgV = new ImageView(StartImg);
        StartImgV.setFitHeight(BUTTONHEIGHT);
        StartImgV.setFitWidth(BUTTONWIDTH);
        StartImgV.setPickOnBounds(false);
        StartImgV.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        StartImgV.setLayoutY(270);
        HoverEffect.addHoverEffect(StartImgV);

        StartImgV.setOnMouseClicked(e -> {
            if(GameSession.hasSavedGame()) {
                startpane.setVisible(true);
            }
            else {
                namepane.setVisible(true);
            }
        });

        Image OptImg = new Image(Objects.requireNonNull(getClass().getResource("/image/Setting.png")).toExternalForm());
        ImageView OptImgV = new ImageView(OptImg);
        OptImgV.setFitHeight(BUTTONHEIGHT);
        OptImgV.setFitWidth(BUTTONWIDTH);
        OptImgV.setPickOnBounds(false);
        OptImgV.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        OptImgV.setLayoutY(360);
        HoverEffect.addHoverEffect(OptImgV);

        Image TutorialImg = new Image(
                Objects.requireNonNull(getClass().getResource("/image/Tutorial.png")).toExternalForm());

        ImageView TTImgV = new ImageView(TutorialImg);
        TTImgV.setFitHeight(BUTTONHEIGHT);
        TTImgV.setFitWidth(BUTTONWIDTH);
        TTImgV.setPickOnBounds(false);
        TTImgV.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        TTImgV.setLayoutY(450);
        HoverEffect.addHoverEffect(TTImgV);

        TTImgV.setOnMouseClicked(e-> {
            t.show();
        });

        menup.getChildren().addAll(backgr, StartImgV, OptImgV, TTImgV);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Scene/Setting.fxml"));
            setting = loader.load();
            setting.setVisible(false);
            Setting controller = loader.getController();
            controller.setOnBack(() -> {
                setting.setVisible(false);
            });
            setting.setLayoutX((SCREENWIDTH - 400) / 2.0);
            setting.setLayoutY((SCREENHEIGHT - 400) / 2.0);
        } catch (Exception e) {
            e.printStackTrace();

        }
        try {
            FXMLLoader nameLoader = new FXMLLoader(getClass().getResource("/Scene/SetName.fxml"));
            namepane = nameLoader.load();
            namecontroller = nameLoader.getController();
            namecontroller.setMenu(this); // Truyền instance mainmenu cho Controller Name
            namepane.setVisible(false);
            StackPane.setAlignment(namepane, Pos.CENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        OptImgV.setOnMouseClicked(e -> {
            setting.setVisible(true);
        });
        SoundManager.playBGM("/Sound/MainMenu.mp3");
        root.getChildren().addAll(menup,setting, startpane, namepane);
        Scene scene = new Scene(root,SCREENWIDTH,SCREENHEIGHT);
        stage.setScene(scene);
        stage.show();
    }
    public void hideSetting() {
        if (setting != null) {
            setting.setVisible(false);
        }
    }
    public Stage getStage() {
        return this.stage;
    }
    public void hidesetname() {
        namepane.setVisible(false);
    }
    public void showSetName() {
        namepane.setVisible(true);
    }
    public void start(String name) {
        SoundManager.stopBGM();
        GameScene gamescene = new GameScene(stage,name);
        gamescene.show();
    }
}