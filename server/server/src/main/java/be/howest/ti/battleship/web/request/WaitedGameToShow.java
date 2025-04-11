package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.List;


@JsonPropertyOrder({
        "id", "type", "commanders", "started", "size"
})

public class WaitedGameToShow extends GameBodyToShow {

    private final List<String> commanders = new ArrayList<>();


    public WaitedGameToShow(Game game) {
        super(game.getId(), game.getType(), game.isStarted(), game.getFleetOfAttackingCommander().getRows(), game.getFleetOfAttackingCommander().getCols());
        commanders.add(game.getCommander().toString());
    }

    public List<String> getCommanders() {
        return this.commanders;
    }
}
