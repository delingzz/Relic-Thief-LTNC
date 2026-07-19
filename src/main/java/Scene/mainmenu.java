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

     public mainmenu(Stage stage) {
         this.stage = stage;
     }

     public void show() {

         Pane menup = new Pane();

         //set nút start game
         Image StartImg = new Image(
                 Objects.requireNonNull(
                         getClass().getResource("/image/images (1).jfif")
                 ).toExternalForm()
         );
         ImageView StartImgV = new ImageView(StartImg);
         StartImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
         StartImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
         StartImgV.setPickOnBounds(false);
         StartImgV.setLayoutX((SCREENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
         StartImgV.setLayoutY(230); // tọa độ Y của đầu nút
         HoverEffect.addHoverEffect(StartImgV);

         //set nút option
         Image OptImg = new Image(
                 Objects.requireNonNull(
                         getClass().getResource("/image/images (1).jfif")
                 ).toExternalForm()
         );
         ImageView OptImgV = new ImageView(OptImg);
         OptImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
         OptImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
         OptImgV.setPickOnBounds(false);
         OptImgV.setLayoutX((SCREENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
         OptImgV.setLayoutY(430); // tọa độ Y của đầu nút
         HoverEffect.addHoverEffect(OptImgV);

         //set nút tutorial
         Image TutorialImg = new Image(
                 Objects.requireNonNull(
                         getClass().getResource("/image/images (1).jfif")
                 ).toExternalForm()
         );
         ImageView TTImgV = new ImageView(TutorialImg);
         TTImgV.setFitHeight(BUTTONHEIGHT); // set chieu cao
         TTImgV.setFitWidth(BUTTONWIDTH); // set chiều rong
         TTImgV.setPickOnBounds(false);
         TTImgV.setLayoutX((SCREENWIDTH-BUTTONWIDTH)/2); // tọa độ X của đầu nút
         TTImgV.setLayoutY(630); // tọa độ Y của đầu nút
         HoverEffect.addHoverEffect(TTImgV);

         TTImgV.setOnMouseClicked(e-> {
             Tutorial tutorial = new Tutorial(stage);
             tutorial.Show();
         });

         menup.getChildren().addAll(StartImgV,OptImgV,TTImgV);
         Scene scene = new Scene(menup, SCREENWIDTH, SCREENHEIGHT);
         stage.setScene(scene);
     }

}

