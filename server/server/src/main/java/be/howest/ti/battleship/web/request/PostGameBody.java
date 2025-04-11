package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Fleet;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PostGameBody {
    @JsonProperty private String commander;

    @JsonProperty private String type;

    @JsonProperty private String prefix;

    @JsonProperty private Fleet fleet;

    public String getCommander() {
        return commander;
    }

    public String getType() {
        return type;
    }

    public Fleet getFleet() {
        return fleet;
    }

    public String getPrefix() {
        return prefix;
    }
}
