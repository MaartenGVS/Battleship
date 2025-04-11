package be.howest.ti.battleship.logic;

public enum GameType {

    SIMPLE("simple", 1),
    MOVE("move", 1),
    SALVO("salvo", 5),
    MOVE_SALVO("move+salvo", 5);


    private final String gameMode;
    private final int startingSalvoSize;

    GameType(String gameMode, int startingSalvoSize) {
        this.gameMode = gameMode;
        this.startingSalvoSize = startingSalvoSize;
    }

    public int getStartingSalvoSize() {
        return startingSalvoSize;
    }

    public String getGameMode() {
        return gameMode;
    }
}
