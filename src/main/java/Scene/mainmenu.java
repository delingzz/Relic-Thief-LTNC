package Scene;

import Event.HoverEffect;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

import static Application.RelicThief.*;
import Event.HoverEffect;

public class mainmenu {
     private Stage stage;
     private ImageView backgr = new ImageView();
     public mainmenu(Stage stage) {
         this.stage = stage;
     }

     public void show() {

         Pane menup = new Pane();

         //set nút start game


         backgr.setImage(new Image(
                 getClass().getResource("/image/BackGround.png").toExternalForm()
         ));
         backgr.setFitWidth(SCREENWIDTH);
         backgr.setFitHeight(SCREENHEIGHT);

         Image StartImg = new Image(
                 Objects.requireNonNull(
                         getClass().getResource("/image/StartButton.png")
                 ).toExternalForm()
         );
         ImageView StartImgV = new ImageView(StartImg);
         StartImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
         StartImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
         StartImgV.setPickOnBounds(false);
         StartImgV.setLayoutX((SCREENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
         StartImgV.setLayoutY(230); // tọa độ Y của đầu nút
         HoverEffect.addHoverEffect(StartImgV);

         StartImgV.setOnMouseClicked(e-> {
             GameScene gameScene = new GameScene(stage);
             gameScene.show();
         });

         //set nút option
         Image OptImg = new Image(
                 Objects.requireNonNull(
                         getClass().getResource("/image/Setting.png")
                 ).toExternalForm()
         );
         ImageView OptImgV = new ImageView(OptImg);
         OptImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
         OptImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
         OptImgV.setPickOnBounds(false);
         OptImgV.setLayoutX((SCREENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
         OptImgV.setLayoutY(350); // tọa độ Y của đầu nút
         HoverEffect.addHoverEffect(OptImgV);

         //set nút tutorial
         Image TutorialImg = new Image(
                 Objects.requireNonNull(
                         getClass().getResource("/image/Tutorial.png")
                 ).toExternalForm()
         );
         ImageView TTImgV = new ImageView(TutorialImg);
         TTImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
         TTImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
         TTImgV.setPickOnBounds(false);
         TTImgV.setLayoutX((SCREENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
         TTImgV.setLayoutY(470); // tọa độ Y của đầu nút
         HoverEffect.addHoverEffect(TTImgV);

         menup.getChildren().addAll(backgr,StartImgV,OptImgV,TTImgV);
         Scene scene = new Scene(menup, SCREENWIDTH, SCREENHEIGHT);
         stage.setScene(scene);
     }

}

