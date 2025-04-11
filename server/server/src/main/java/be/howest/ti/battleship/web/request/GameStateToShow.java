package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import be.howest.ti.battleship.logic.History;
import be.howest.ti.battleship.logic.Ship;
import be.howest.ti.battleship.web.BattleshipUser;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Map;
import java.util.Set;

@JsonPropertyOrder({
        "salvoSize", "sunkShips", "winner", "history", "attackingCommander", "defendingCommander",
        "started", "id", "type", "size"
})

public class GameStateToShow {

    private final Game game;
    private final BattleshipUser asker;

    GameStateToShow(Game game, BattleshipUser bearerTokenOfUser) {
        this.game = game;
        this.asker = bearerTokenOfUser;
    }


    public Map<String, Integer> getSalvoSize() {
            return Map.of(
                    game.getAttackingCommander().getName(), game.getAttackingCommander().getSalvoSize(),
                    game.getDefendingCommander().getName(), game.getDefendingCommander().getSalvoSize()
            );
    }

    public Map<String, Set<Ship>> getSunkShips() {
        return Map.of(
                getAttackingCommander(), game.getAttackingCommander().getSunkShips(),
                getDefendingCommander(), game.getDefendingCommander().getSunkShips()
        );

    }
    public String getWinner() {
        return game.getWinner();
    }

    public List<History> getHistory() {
        if (asker.getCommander().equals(game.getAttackingCommander().getName())){
            return game.getAttackingCommander().getHistory();
        }else{
            return game.getDefendingCommander().getHistory();
        }
    }

    public String getAttackingCommander() {
        return game.getAttackingCommander().getName();
    }

    public String getDefendingCommander() {
        return game.getDefendingCommander().getName();
    }

    public boolean isStarted() {
        return game.isStarted();
    }

    public String getId() {
        return game.getId();
    }

    public String getType() {
        return game.getType();
    }


    public Map<String, Integer> getSize() {
        return Map.of(
                "rows", game.getCommander().getFleet().getRows(),
                "cols", game.getCommander().getFleet().getCols()
        );
    }
}
