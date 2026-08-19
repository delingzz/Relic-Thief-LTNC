package Scene;

import Event.HoverEffect;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;

public class Tutorial {

    private final Stage stage;


    public Tutorial(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Pane tutorialPane = new Pane();

        Pane overlay = new Pane();
        overlay.setPrefSize(SCREENWIDTH, SCREENHEIGHT);
        overlay.setStyle("-fx-background-color: rgba(30, 30, 30, 0.75);");

        String[] paths = {
                "/image/Tutorial/Tutorial1.png",
                "/image/Tutorial/Tutorial2.png",
                "/image/Tutorial/Tutorial3.png",
                "/image/Tutorial/Tutorial4.png",
                "/image/Tutorial/Tutorial5.png",
                "/image/Tutorial/Tutorial6.png",
                "/image/Tutorial/Tutorial7.png"
        };

        Image[] images = new Image[paths.length];

        for (int i = 0; i < paths.length; i++) {
            images[i] = new Image(
                    Objects.requireNonNull(
                            getClass().getResource(paths[i])
                    ).toExternalForm()
            );
        }

        double width = 800;
        double height = 320;

        ImageView tutorialView = new ImageView(images[0]);
        tutorialView.setFitWidth(width);
        tutorialView.setFitHeight(height);
        tutorialView.setLayoutX((SCREENWIDTH - width) / 2);
        tutorialView.setLayoutY((SCREENHEIGHT - height) / 2);

        ImageView backButton = createButton(
                "/image/left.png",
                120,
                60
        );
        backButton.setLayoutX(tutorialView.getLayoutX() - 140);
        backButton.setLayoutY((SCREENHEIGHT - 60) / 2);
        backButton.setVisible(false);

        ImageView nextButton = createButton(
                "/image/right.png",
                120,
                60
        );
        nextButton.setLayoutX(tutorialView.getLayoutX() + width + 20);
        nextButton.setLayoutY((SCREENHEIGHT - 60) / 2);

        ImageView exitButton = createButton("/image/exitbutton.png", 200, 65
        );
        exitButton.setLayoutX((SCREENWIDTH - 200) / 2);
        exitButton.setLayoutY(tutorialView.getLayoutY() + height + 25);

        int[] currentPage = {0};

        backButton.setOnMouseClicked(e -> {
            if (currentPage[0] > 0) {
                currentPage[0]--;
                tutorialView.setImage(images[currentPage[0]]);

                backButton.setVisible(currentPage[0] > 0);
                nextButton.setVisible(currentPage[0] < images.length - 1);
            }
        });

        nextButton.setOnMouseClicked(e -> {
            if (currentPage[0] < images.length - 1) {
                currentPage[0]++;
                tutorialView.setImage(images[currentPage[0]]);

                backButton.setVisible(currentPage[0] > 0);
                nextButton.setVisible(currentPage[0] < images.length - 1);
            }
        });

        exitButton.setOnMouseClicked(e -> {
            new mainmenu(stage).show();
        });

        tutorialPane.getChildren().addAll(overlay, tutorialView, backButton, nextButton, exitButton);

        Scene scene = new Scene(tutorialPane,SCREENWIDTH,SCREENHEIGHT);
        stage.setScene(scene);
        stage.show();
    }

    private ImageView createButton(String path, double width, double height) {
        Image image = new Image(Objects.requireNonNull(getClass().getResource(path)).toExternalForm());

        ImageView view = new ImageView(image);
        view.setFitWidth(width);
        view.setFitHeight(height);
        view.setPickOnBounds(false);

        HoverEffect.addHoverEffect(view);

        return view;
    }
}