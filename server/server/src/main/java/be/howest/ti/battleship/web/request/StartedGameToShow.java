package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({
        "id", "type", "winner", "history", "attackingCommander", "defendingCommander",
        "started", "size"
})

public class StartedGameToShow extends GameBodyToShow {

    private final String winner;
    private final String attackingCommander;
    private final String defendingCommander;


    public StartedGameToShow(Game game) {
        super(game.getId(), game.getType(), game.isStarted(), game.getFleetOfAttackingCommander().getRows(), game.getFleetOfAttackingCommander().getCols());
        this.winner = game.getWinner();
        this.attackingCommander = game.getAttackingCommander().toString();
        this.defendingCommander = game.getDefendingCommander().toString();

    }


    public String getWinner() {
        return winner;
    }

    public String getAttackingCommander() {
        return attackingCommander;
    }

    public String getDefendingCommander() {
        return defendingCommander;
    }
}
