package Scene;

import Entity.Player;

import static Application.RelicThief.SCREENHEIGHT;
import static Application.RelicThief.SCREENWIDTH;

public class Camera {

    public static final int MAPWIDTH = 60*36;
    public static final int MAPHEIGHT = 40*36;
    private double cameraX;
    private double cameraY;

    public double getCameraX() {
        return cameraX;
    }

    public double getCameraY() {
        return cameraY;
    }

    public void update(Player player) {

        cameraX = player.getX() - SCREENWIDTH / 2.0;
        cameraY = player.getY() - SCREENHEIGHT / 2.0;

        // Không cho camera ra ngoài map
        cameraX = Math.max(0, cameraX);
        cameraY = Math.max(0, cameraY);

        cameraX = Math.min(cameraX, MAPWIDTH - SCREENWIDTH);
        cameraY = Math.min(cameraY, MAPHEIGHT - SCREENHEIGHT);
    }
}