package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.BoardSize;

import java.util.Map;

public abstract class GameBodyToShow {

    private final String id;

    private final String type;

    private final boolean started;

    private final BoardSize boardSize;


    protected GameBodyToShow(String id, String type, boolean started, int rows, int cols) {
        this.id = id;
        this.type = type;
        this.started = started;
        this.boardSize = new BoardSize(rows, cols);
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isStarted() {
        return started;
    }

    public Map<String, Integer> getSize() {
        return Map.of(
                "rows", boardSize.getRows(),
                "cols", boardSize.getCols()
        );
    }
}
