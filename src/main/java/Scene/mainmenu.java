package Scene;

import Event.HoverEffect;
import javafx.fxml.FXMLLoader;
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

    private Stage stage;

    private ImageView backgr = new ImageView();

    // Settings
    private Parent setting;

    public mainmenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        StackPane root = new StackPane();
        Pane menup = new Pane();
        backgr.setImage(new Image(
                Objects.requireNonNull(getClass().getResource("/image/BackGround.png")).toExternalForm()));
        backgr.setFitWidth(SCREENWIDTH);
        backgr.setFitHeight(SCREENHEIGHT);
        Image StartImg = new Image(
                Objects.requireNonNull(getClass().getResource("/image/StartButton.png")).toExternalForm());
        ImageView StartImgV = new ImageView(StartImg);
        StartImgV.setFitHeight(BUTTONHEIGHT);
        StartImgV.setFitWidth(BUTTONWIDTH);
        StartImgV.setPickOnBounds(false);
        StartImgV.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        StartImgV.setLayoutY(230);
        HoverEffect.addHoverEffect(StartImgV);

        StartImgV.setOnMouseClicked(e -> {
            GameScene gameScene = new GameScene(stage);
            gameScene.show();
        });

        Image OptImg = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/image/Setting.png")
                ).toExternalForm()
        );
        ImageView OptImgV = new ImageView(OptImg);
        OptImgV.setFitHeight(BUTTONHEIGHT);
        OptImgV.setFitWidth(BUTTONWIDTH);
        OptImgV.setPickOnBounds(false);
        OptImgV.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        OptImgV.setLayoutY(350);
        HoverEffect.addHoverEffect(OptImgV);

        Image TutorialImg = new Image(
                Objects.requireNonNull(getClass().getResource("/image/Tutorial.png")).toExternalForm());

        ImageView TTImgV = new ImageView(TutorialImg);
        TTImgV.setFitHeight(BUTTONHEIGHT);
        TTImgV.setFitWidth(BUTTONWIDTH);
        TTImgV.setPickOnBounds(false);
        TTImgV.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        TTImgV.setLayoutY(470);
        HoverEffect.addHoverEffect(TTImgV);
        menup.getChildren().addAll(backgr, StartImgV, OptImgV, TTImgV);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/relicthief/Setting.fxml"));
            setting = loader.load();
            setting.setVisible(false);
            SettingScene controller = loader.getController();
            controller.setOnBack(() -> {
                setting.setVisible(false);
            });
            setting.setLayoutX((SCREENWIDTH - 400) / 2.0);
            setting.setLayoutY((SCREENHEIGHT - 400) / 2.0);
        } catch (Exception e) {
            e.printStackTrace();

        }

        OptImgV.setOnMouseClicked(e -> {
            setting.setVisible(true);
        });
        root.getChildren().addAll(menup,setting);
        Scene scene = new Scene(root,SCREENWIDTH,SCREENHEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    public void hideSetting() {
        if (setting != null) {
            setting.setVisible(false);
        }
    }
}