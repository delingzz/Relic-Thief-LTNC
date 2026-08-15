package Scene;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.util.ArrayList;

import static Scene.Camera.MAPHEIGHT;
import static Scene.Camera.MAPWIDTH;

public class TileMap {

    private int[][] map;
    public static final int tileSize = 36;

    private final Image wall;
    private final Image ground;
    private final Image breakable;
    private final Image endpoin;
    private final Image grass;
    private final Image botspawn;
    private final Image door;
    private final Image banspace;

    private ImageView[][] tileViews;

    private ArrayList<Point> botpoint = new ArrayList<>();

    public Point endpoint;
    public Point doorpoint;

    public TileMap(int[][] original) {
        map = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            map[i] = original[i].clone();
        }
        tileViews = new ImageView[map.length][map[0].length];
        wall = new Image(getClass().getResource("/image/Wall.png").toExternalForm()); //1
        ground = new Image(getClass().getResource("/image/Ground.png").toExternalForm());  //0
        breakable = new Image(getClass().getResource("/image/Breakable.png").toExternalForm());  //4
        grass = new Image(getClass().getResource("/image/Grass.png").toExternalForm());  //3
        botspawn = new Image(getClass().getResource("/image/botspawn.png").toExternalForm());  //2
        endpoin = new Image(getClass().getResource("/image/endpoint.png").toExternalForm());//6
        door = new Image(getClass().getResource("/image/door.png").toExternalForm());
        banspace = new Image(getClass().getResource("/image/banwall.png").toExternalForm());
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
        if(map[row][col] ==1 || map[row][col]==4 || map[row][col]==3 || map[row][col]==9) {return true;}
        return false;
    }

    public void draw(Pane pane) {

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {

                int x = col * tileSize;
                int y = row * tileSize;

                Image image = null;

                switch (map[row][col]) {
                    case 0:
                        image = ground;
                        break;
                    case 1:
                        image = wall;
                        break;
                    case 2:
                        image = botspawn;
                        break;
                    case 8:
                        image = grass;
                        break;
                    case 4:
                        image = breakable;
                        break;
                    case 5:
                        image = ground;
                        break;
                    case 7:
                        image = endpoin;
                        break;
                    case 9:
                        image = door;
                        break;
                    case 3:
                        image = banspace;
                        break;
                    case 6:
                        image = ground;
                        break;
                }
                if (image != null) {
                    ImageView tile = makeTile(image, x, y);
                    tileViews[row][col] = tile;
                    pane.getChildren().add(tile);
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
    public int getRows() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }
    public ArrayList<Point> BotPoint() {   //hàm này cholaays luôn endpoint
        botpoint.clear();
        for(int i =0;i<MAPHEIGHT/tileSize;++i) {
            for(int j =0;j<MAPWIDTH/tileSize;++j) {
                if(map[i][j] == 2) {
                    botpoint.add(new Point(j,i));
                }
                else if(map[i][j]==7) {
                    endpoint = new Point(j,i);
                }
                else if(map[i][j]==9) {
                    doorpoint = new Point(j,i);
                }
            }
        }
        return botpoint;
    }
    public Point RelicPoint() {
        for(int i =0;i<MAPHEIGHT/tileSize;++i) {
            for(int j =0;j<MAPWIDTH/tileSize;++j) {
                if(map[i][j] == 5) {
                    return new Point(j,i);
                }
            }
        }
        return null;
    }
    public Point KeyPoint() {
        for(int i =0;i<MAPHEIGHT/tileSize;++i) {
            for(int j =0;j<MAPWIDTH/tileSize;++j) {
                if(map[i][j] == 6) {
                    return new Point(j,i);
                }
            }
        }
        return null;
    }
    public void changeTile(int row, int col, int newTile) {

        map[row][col] = newTile;

        Image image = null;

        switch (newTile) {
            case 0:
                image = ground;
                break;
            case 1:
                image = wall;
                break;
            case 2:
                image = botspawn;
                break;
            case 8:
                image = grass;
                break;
            case 4:
                image = breakable;
                break;
            case 5:
                image = ground;
                break;
            case 7:
                image = endpoin;
                break;
            case 9:
                image = door;
                break;
            case 3:
                image = banspace;
                break;
            case 6:
                image = ground;
                break;
        }

        if (image != null && tileViews[row][col] != null) {
            tileViews[row][col].setImage(image);
        }
    }
}