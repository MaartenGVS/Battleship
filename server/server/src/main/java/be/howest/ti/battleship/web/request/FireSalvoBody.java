package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Position;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class FireSalvoBody {

    @JsonProperty
    private List<Position> salvo;

    public List<Position> getSalvo() {
        return salvo;
    }
}
