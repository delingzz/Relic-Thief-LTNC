package Scene;

import Event.HoverEffect;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static Application.RelicThief.*;

public class mainmenu {
    private static Font limelightFont;
    private static Font playwriteFont;

    private final Stage stage;

    public mainmenu(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        if (limelightFont == null) {
            limelightFont = Font.loadFont(
                    Objects.requireNonNull(getClass().getResourceAsStream("/font/Limelight-Regular.ttf")),
                    96
            );
            playwriteFont = Font.loadFont(
                    Objects.requireNonNull(getClass().getResourceAsStream("/font/PlaywriteIN-Regular.ttf")),
                    28
            );
        }

        Pane menup = new Pane();

        Image bgImage = new Image(
                Objects.requireNonNull(
                        getClass().getResource("/image/background/brick-bg.jpg")
                ).toExternalForm()
        );
        menup.setBackground(new Background(new BackgroundImage(
                bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        )));

        Label title = new Label("Relic Thief");
        title.setFont(limelightFont);
        title.setStyle(
                "-fx-text-fill: #f5d742;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 8, 0.5, 2, 2);"
        );
        title.applyCss();
        title.layout();
        title.setLayoutX((SCREENWIDTH - title.prefWidth(-1)) / 2);
        title.setLayoutY(60);

        Button startBtn = createMenuButton("Start Game");
        startBtn.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        startBtn.setLayoutY(280);

        Button optionsBtn = createMenuButton("Options");
        optionsBtn.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        optionsBtn.setLayoutY(430);

        Button tutorialBtn = createMenuButton("Tutorial");
        tutorialBtn.setLayoutX((SCREENWIDTH - BUTTONWIDTH) / 2);
        tutorialBtn.setLayoutY(580);
        tutorialBtn.setOnAction(e -> {
            Tutorial tutorial = new Tutorial(stage);
            tutorial.Show();
        });

        menup.getChildren().addAll(title, startBtn, optionsBtn, tutorialBtn);
        stage.setScene(new Scene(menup, SCREENWIDTH, SCREENHEIGHT));
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setFont(playwriteFont);
        button.setPrefSize(BUTTONWIDTH, BUTTONHEIGHT);
        button.setStyle(
                "-fx-background-color: rgba(139, 69, 19, 0.85);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #5c3317;" +
                "-fx-border-width: 3;" +
                "-fx-border-radius: 12;"
        );
        HoverEffect.addHoverEffect(button);
        return button;
    }
}
