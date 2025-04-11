package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Ship {

    private final String name;
    private final int size;


    //Constructor
    public Ship(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "size", required = true) int size
    ) {
        this.name = name;
        this.size = size;
    }


    //Getters
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }


    //toString, equals and hashCode
    @Override
    public String toString() { //
        return "Ship{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ship ship = (Ship) o;

        if (size != ship.size) return false;
        return Objects.equals(name, ship.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + size;
        return result;
    }
}