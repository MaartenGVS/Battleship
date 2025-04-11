package be.howest.ti.battleship.web.request;

import java.util.List;

public class ShipLocationsToShow {

    private final String name;
    private final List<String> locations;


    public ShipLocationsToShow(String name, List<String> locations) {
        this.name = name;
        this.locations = locations;
    }


    public String getName() {
        return name;
    }


    public List<String> getLocation() {
        return locations;
    }
}
