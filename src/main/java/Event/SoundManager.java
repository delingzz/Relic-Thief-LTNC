package Event;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class SoundManager {

    private static MediaPlayer bgmPlayer;

    private static double bgmVolume = 0.5;
    private static double sfxVolume = 1.0;

    public static void playSFX(String file) {
        URL resource = SoundManager.class.getResource(file);

        if (resource == null) {
            System.out.println("Không tìm thấy sound: " + file);
            return;
        }

        Media media = new Media(resource.toExternalForm());
        MediaPlayer player = new MediaPlayer(media);

        player.setVolume(sfxVolume);

        player.setOnEndOfMedia(player::dispose);
        player.play();
    }

    public static void playBGM(String file) {
        URL resource = SoundManager.class.getResource(file);

        if (resource == null) {
            System.out.println("Không tìm thấy BGM: " + file);
            return;
        }

        Media media = new Media(resource.toExternalForm());

        if (bgmPlayer != null) {
            bgmPlayer.stop();
            bgmPlayer.dispose();
        }

        bgmPlayer = new MediaPlayer(media);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);

        // dùng volume hiện tại
        bgmPlayer.setVolume(bgmVolume);

        bgmPlayer.play();
    }

    public static void stopBGM() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
        }
    }

    public static void setBGMVolume(double volume) {
        bgmVolume = volume;

        if (bgmPlayer != null) {
            bgmPlayer.setVolume(bgmVolume);
        }
    }

    public static void setSFXVolume(double volume) {
        sfxVolume = volume;
    }

    public static double getBGMVolume() {
        return bgmVolume;
    }

    public static double getSFXVolume() {
        return sfxVolume;
    }
}