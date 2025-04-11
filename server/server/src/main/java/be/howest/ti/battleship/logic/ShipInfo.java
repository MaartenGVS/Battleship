package be.howest.ti.battleship.logic;

import java.util.List;
import java.util.Objects;

public class ShipInfo {

    private final Ship ship;
    private final List<String> location;


    public ShipInfo(String name, int size, List<String> shipLocation) {
        this.ship = new Ship(name, size);
        this.location = shipLocation;
    }


    //Getters
    public List<String> getLocation() {
        return location;
    }

    public Ship getShip() {
        return ship;
    }


    //toString
    @Override
    public String toString() {
        return "ShipInfo{" +
                "ship=" + ship +
                ", shipLocation=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipInfo shipInfo = (ShipInfo) o;

        if (!Objects.equals(ship, shipInfo.ship)) return false;
        return Objects.equals(location, shipInfo.location);
    }

    @Override
    public int hashCode() {
        int result = ship.hashCode();
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
