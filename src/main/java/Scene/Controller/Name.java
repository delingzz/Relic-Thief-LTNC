package Scene.Controller;

import Scene.GameScene;
import Scene.mainmenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Name {
    private GameScene gamescene;
    @FXML
    private TextField nametext;
    @FXML
    private Button button;

    private mainmenu mainmenu;
    private String playerName; // Biến lưu tên nhân vật

    public void setScene(GameScene scene) {
        this.gamescene = scene;
    }
    public void setMenu(mainmenu m) {
        this.mainmenu = m;
    }
    @FXML
    public void initialize() {
        button.setOnAction(event -> handleOkButton());
    }
    @FXML
    private void handleOkButton() {
        this.playerName = nametext.getText().trim();
        if (this.playerName.isEmpty()) {
            return;
        }
        if (gamescene != null) {
        }
        mainmenu.hidesetname();
        mainmenu.start(playerName);
    }
    public String getplayername() {
        return playerName;
    }
}