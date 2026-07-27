package Scene;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TileMap {

    private final int[][] mapData;
    private final int tileSize;

    // Các ảnh tile
    private final Image wall;
    private final Image dirt;
    private final Image breakWall;
    private final Image door;
    private final Image diamond;
    private final Image food;

    public TileMap(int tileSize, int[][] mapData) {

        this.tileSize = tileSize;
        this.mapData = mapData;

        wall = new Image(getClass().getResource("/image/wall.png").toExternalForm());
        dirt = new Image(getClass().getResource("/image/dirt.png").toExternalForm());
        breakWall = new Image(getClass().getResource("/image/breakWall.png").toExternalForm());
        door = new Image(getClass().getResource("/image/door.png").toExternalForm());
        diamond = new Image(getClass().getResource("/image/diamond.png").toExternalForm());
        food = new Image(getClass().getResource("/image/food.png").toExternalForm());
    }

    public void draw(GraphicsContext gc) {

        for (int row = 0; row < mapData.length; row++) {

            for (int col = 0; col < mapData[row].length; col++) {

                int tile = mapData[row][col];

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