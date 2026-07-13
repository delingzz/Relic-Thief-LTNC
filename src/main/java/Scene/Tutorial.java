package Scene;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import Logic.Manager;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;

public class Tutorial {

    private Stage stage;
    public Tutorial(Stage stage) {
        this.stage = stage;
    }
    // phần này hoàn thiện sau!!
    public void Show() {
        Pane TutorialPane = new Pane();

        File[] loadTutorial = {
                new File("/image/images (1).jfif"),//nhap hinh vao day,
                new File(""),// nhap dia chi vao day,
                new File(""),
                new File(""),
                new File(""),
                new File(""),
                new File("")
        };
        Image[] TutorialImg = new Image[loadTutorial.length];
        for(int i = 0; i< 7;++i) {
            TutorialImg[i] = new Image(loadTutorial[i].toURI().toString());
        }
        ImageView TutorialView = new ImageView(TutorialImg[0]);
        TutorialView.setFitWidth(1080);
        TutorialView.setFitHeight(720);

        File loadBackButton = new File("/image/images (1).jfif");
        Image backImg = new Image(loadBackButton.toURI().toString());
        ImageView backImgV = new ImageView(backImg);
        backImgV.setFitHeight(80);
        backImgV.setFitWidth(160);
        backImgV.setPickOnBounds(false);
        backImgV.setVisible(false);
        backImgV.setLayoutX(5);
        backImgV.setLayoutY(5);
        HoverEffect.addHoverEffect(backImgV);

        File loadNextButton = new File("/image/images (1).jfif");
        Image nextImg = new Image(loadNextButton.toURI().toString());
        ImageView nextImgV = new ImageView(nextImg);
        nextImgV.setFitHeight(80);
        nextImgV.setFitWidth(160);
        nextImgV.setPickOnBounds(false);
        nextImgV.setLayoutX(900);
        nextImgV.setLayoutY(635);
        HoverEffect.addHoverEffect(nextImgV);

        int[] currentPage = {0};

        backImgV.setOnMouseClicked(e-> {
            currentPage[0] --;
            TutorialView.setImage(TutorialImg[currentPage[0]]);
            backImgV.setVisible(currentPage[0] >0);
        });

        nextImgV.setOnMouseClicked(e->{
            currentPage[0]++;
            if(currentPage[0] == 7) {
                currentPage[0] = 0;
            }
            TutorialView.setImage(TutorialImg[currentPage[0]]);
            backImgV.setVisible(currentPage[0] >0);
        });

        File LoadMainMenuT = new File("/image/images (1).jfif");
        Image BackToMenuImg = new Image(LoadMainMenuT.toURI().toString());
        ImageView BackToMenuImgV = new ImageView(BackToMenuImg);
        BackToMenuImgV.setFitWidth(240);
        BackToMenuImgV.setFitHeight(80);
        BackToMenuImgV.setPickOnBounds(false);
        BackToMenuImgV.setLayoutX((1080-240)/2);
        BackToMenuImgV.setLayoutY(630);
        HoverEffect.addHoverEffect(BackToMenuImgV);

        TutorialPane.getChildren().addAll(BackToMenuImgV,nextImgV,backImgV);
        Scene scene = new Scene(TutorialPane,SCREENWIDTH, SCREENHEIGHT);
        stage.setScene(scene);
    }
}
