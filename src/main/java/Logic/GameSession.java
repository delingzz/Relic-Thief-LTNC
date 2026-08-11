package Logic;

public class GameSession {

    private static Manager manager;

    public static void save(Manager gameManager) {
        manager = gameManager;
    }

    public static Manager getSavedGame() {
        return manager;
    }

    public static boolean hasSavedGame() {
        return manager != null;
    }

    public static void clear() {
        manager = null;
    }
}