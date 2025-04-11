package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class History {

    private final String commander;
    private Map<Position, String> positions;
    private final boolean isAttackingCommander;
    private ShipInfo shipInfo = null;


    //Constructor
    public History(String commander, Map<Position, String> positions, boolean isAttackingCommander) {
        this.commander = commander;
        this.positions = positions;
        this.isAttackingCommander = isAttackingCommander;
    }

    public History(String commander, ShipInfo shipInfo, boolean isAttackingCommander) {
        this.commander = commander;
        this.shipInfo = shipInfo;
        this.isAttackingCommander = isAttackingCommander;
    }


    //Getters
    @JsonProperty("attackingCommander")
    public String getCommander() {
        return commander;
    }

    public Object getResult() {
        if (shipInfo != null) {
            return retrieveShipInfResult();
        }
        Map<Position, Serializable> result = new HashMap<>();
        for (Map.Entry<Position, String> entry : positions.entrySet()) {
            Serializable value;
            if (isAttackingCommander) {
                boolean missAsFalse = false;
                value = entry.getValue() == null ? missAsFalse : entry.getValue();
            } else {
                value = entry.getValue() != null;
            }
            result.put(entry.getKey(), value);
        }
        return result;
    }

    public Object retrieveShipInfResult() {
        if (isAttackingCommander) {
            return shipInfo;
        } else {
            return "ship movement detected";
        }
    }

    @JsonIgnore
    public boolean isAttackingCommander() {
        return isAttackingCommander;
    }


    //toString
    @Override
    public String toString() {
        return "History{" +
                "commander='" + commander + '\'' +
                ", positions=" + positions +
                ", isAttackingCommander=" + isAttackingCommander +
                ", shipInfo=" + shipInfo +
                '}';
    }
}