package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Position;
import be.howest.ti.battleship.logic.Ship;

import java.util.List;

public class ShipInfoToShowAfterMove {
    private final Ship ship;
    private final List<Position> shipLocation;

    public ShipInfoToShowAfterMove(Ship ship, List<Position> shipLocation) {
        this.ship = ship;
        this.shipLocation = shipLocation;
    }

    public Ship getShip() {
        return ship;
    }

    public List<Position> getShipLocation() {
        return shipLocation;
    }
}
