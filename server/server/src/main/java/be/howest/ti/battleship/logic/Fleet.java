package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Fleet {

    private final BoardSize boardSize;

    private final List<ShipLocation> ships;


    //Constructor
    @JsonCreator
    public Fleet(
            @JsonProperty(value = "rows", required = true) int rows,
            @JsonProperty(value = "cols", required = true) int cols,
            @JsonProperty(value = "ships", required = true) List<ShipLocation> ships
    ) {
        this.boardSize = new BoardSize(rows, cols);
        this.ships = ships;
    }


    //Getters
    public int getRows() {
        return boardSize.getRows();
    }

    public int getCols() {
        return boardSize.getCols();
    }

    public BoardSize getBoardSize() {
        return boardSize;
    }

    public List<ShipLocation> getShips() {
        return ships;
    }

    public String getShipName(Position position) {
        for (ShipLocation ship : ships) {
            for (Position jik : ship.getLocations()) {
                if (jik.equals(position)) {
                    return ship.getLocationName(position);

                }
            }
        }
        return null;
    }




    //Logic
    public boolean isHit(Position position) {
        for (ShipLocation shipLocation : ships) {
            for (Position currentPosition : shipLocation.getLocations()) {
                if (currentPosition.equals(position)) {
                    return true;
                }
            }
        }
        return false;
    }

    public ShipLocation getOneShip(String shipName) {
        for (ShipLocation ship : ships) {
            if (ship.getName().equals(shipName)){
                return ship;
            }
        }
        return null;
    }


    //toString, equals and hashCode
    @Override
    public String toString() {
        return "Fleet{" +
                "boardSize=" + boardSize +
                ", ships=" + ships +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fleet fleet = (Fleet) o;

        if (boardSize.getRows() != fleet.boardSize.getRows()) return false;
        return boardSize.getCols() == fleet.boardSize.getCols();
    }

    @Override
    public int hashCode() {
        int result = boardSize.getRows();
        result = 31 * result + boardSize.getCols();
        return result;
    }
}
