package Scene;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.util.Objects;

import static Application.RelicThief.*;

public class mainmenu {
     private Stage stage;

     public mainmenu(Stage stage) {
         this.stage = stage;
     }

     public void show() {
         Pane menup = new Pane();

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

         menup.getChildren().addAll(StartImgV);
         Scene scene = new Scene(menup, SCREENWIDTH, SCREENHEIGHT);
         stage.setScene(scene);
     }

}

