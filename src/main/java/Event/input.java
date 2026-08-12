package Event;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class input {

    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    public static boolean enter;
    public static boolean esc;

    public static boolean s1;
    public static boolean s2;
    public static boolean s3;
    public static boolean s4;
    public static boolean s5;

    public void handleKeyPressed(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.W || code == KeyCode.UP) {
            up = true;
        }
        if (code == KeyCode.S || code == KeyCode.DOWN) {
            down = true;
        }
        if (code == KeyCode.A || code == KeyCode.LEFT) {
            left = true;
        }
        if (code == KeyCode.D || code == KeyCode.RIGHT) {
            right = true;
        }
        if(code == KeyCode.ENTER) {
            enter = true;
        }
        if(code == KeyCode.ESCAPE) {
            esc = true;
        }
        if(code == KeyCode.DIGIT1) {
            s1 = true;
        }
        if(code == KeyCode.DIGIT2) {
            s2 = true;
        }
        if(code == KeyCode.DIGIT3) {
            s3 = true;
        }
        if(code == KeyCode.DIGIT4) {
            s4 = true;
        }
        if(code == KeyCode.DIGIT5) {
            s5 = true;
        }
    }
    public void handleKeyReleased(KeyEvent event) {
        KeyCode code = event.getCode();
        if (code == KeyCode.W || code == KeyCode.UP) {
            up = false;
        }
        if (code == KeyCode.S || code == KeyCode.DOWN) {
            down = false;
        }
        if (code == KeyCode.A || code == KeyCode.LEFT) {
            left = false;
        }
        if (code == KeyCode.D || code == KeyCode.RIGHT) {
            right = false;
        }
        if(code == KeyCode.ENTER) {
            enter = false;
        }
        if(code == KeyCode.ESCAPE) {
            esc = false;
        }
        if(code == KeyCode.DIGIT1) {
            s1 = false;
        }
        if(code == KeyCode.DIGIT2) {
            s2 = false;
        }
        if(code == KeyCode.DIGIT3) {
            s3 = false;
        }
        if(code == KeyCode.DIGIT4) {
            s4 = false;
        }
        if(code == KeyCode.DIGIT5) {
            s5 = false;
        }
    }
}