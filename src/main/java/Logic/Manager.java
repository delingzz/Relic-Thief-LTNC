package Logic;

import Scene.mainmenu;
import javafx.stage.Stage;

public class Manager {

    private final Stage stage;

    public Manager(Stage stage) {
        this.stage = stage;
    }
    //stage.setTitle("RelicThief");//
    public void showMainMenu() {
        mainmenu main = new mainmenu(stage);
        main.show();
    }
}
