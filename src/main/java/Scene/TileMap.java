package Scene;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TileMap {

    private int[][] map;
    public static final int tileSize = 36;

    private final Image wall;
    private final Image ground;
    private final Image breakWall;
    private final Image door;
    private final Image diamond;
    private final Image food;


    public TileMap(int[][] original) {
        map = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            map[i] = original[i].clone();
        }
        wall = new Image(getClass().getResource("/image/test.png").toExternalForm());
        ground = new Image(getClass().getResource("/image/Ground.png").toExternalForm());
        breakWall = new Image(getClass().getResource("/image/test.png").toExternalForm());
        door = new Image(getClass().getResource("/image/test.png").toExternalForm());
        diamond = new Image(getClass().getResource("/image/test.png").toExternalForm());
        food = new Image(getClass().getResource("/image/test.png").toExternalForm());

    }

    public int getTile(int row, int col) {
        return map[row][col];
    }

    public void setTile(int row, int col, int value) {
        map[row][col] = value;
    }

    public int[][] getMap() {
        return map;
    }

    public boolean isWall(double x, double y) {

        int col = (int) Math.floor(x / tileSize);
        int row = (int) Math.floor(y / tileSize);
        if (row < 0 || row >= map.length ||
                col < 0 || col >= map[0].length) {
            return true;
        }
        return map[row][col] == 1;
    }

    public void draw(Pane pane) {

        for(int row = 0; row < map.length; row++) {
            for(int col = 0; col < map[0].length; col++) {

                int x = col * tileSize;
                int y = row * tileSize;

                switch(map[row][col]) {

                    case 1:
                        pane.getChildren().add(makeTile(wall, x, y));
                        break;

                    case 2:
                        pane.getChildren().add(makeTile(ground, x, y));
                        break;

                    case 3:
                        pane.getChildren().add(makeTile(door, x, y));
                        break;

                    case 4:
                        pane.getChildren().add(makeTile(food, x, y));
                        break;
                }
            }
        }
    }
    private ImageView makeTile(Image img, int x, int y) {

        ImageView tile = new ImageView(img);

        tile.setFitWidth(tileSize);
        tile.setFitHeight(tileSize);

        tile.setLayoutX(x);
        tile.setLayoutY(y);

        return tile;
    }
    public boolean canMove(double x, double y, double width, double height) {
        return !isWall(x, y)
                && !isWall(x + width - 1, y)
                && !isWall(x, y + height - 1)
                && !isWall(x + width - 1, y + height - 1);
    }
}