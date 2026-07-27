package Scene;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileMap {

    private int[][] map;
    public static final int tileSize = 36;

    // Các ảnh tile
    private final Image wall;
    private final Image dirt;
    private final Image breakWall;
    private final Image door;
    private final Image diamond;
    private final Image food;

    public TileMap(int[][] original) {
        map = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            map[i] = original[i].clone();
        }
        wall = new Image(getClass().getResource("/image/wall.png").toExternalForm());
        dirt = new Image(getClass().getResource("/image/dirt.png").toExternalForm());
        breakWall = new Image(getClass().getResource("/image/breakWall.png").toExternalForm());
        door = new Image(getClass().getResource("/image/door.png").toExternalForm());
        diamond = new Image(getClass().getResource("/image/diamond.png").toExternalForm());
        food = new Image(getClass().getResource("/image/food.png").toExternalForm());
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

        int col = (int)(x / tileSize);
        int row = (int)(y / tileSize);
        if (row < 0 || row >= map.length ||
                col < 0 || col >= map[0].length) {
            return true; // set ngoài map là tường luôn để không đi được
        }
        return map[row][col] == 1;
    }
    public void draw(GraphicsContext gc) {

        for (int row = 0; row < map.length; row++) {

            for (int col = 0; col < map[row].length; col++) {

                int tile = map[row][col];

                Image img = null;

                switch (tile) {

                    case 1:
                        img = wall;
                        break;

                    case 2:
                        img = dirt;
                        break;

                    case 3:
                        img = breakWall;
                        break;

                    case 4:
                        img = door;
                        break;

                    case 5:
                        img = diamond;
                        break;

                    case 6:
                        img = food;
                        break;

                    default:
                        break;
                }

                if (img != null) {
                    gc.drawImage(
                            img,
                            col * tileSize,
                            row * tileSize,
                            tileSize,
                            tileSize
                    );
                }
            }
        }
    }

}