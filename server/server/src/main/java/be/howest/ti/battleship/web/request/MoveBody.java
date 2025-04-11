package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Position;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MoveBody {
    @JsonProperty
    private List<Position> location;

    public List<Position> getLocation() {
        return location;
    }
}