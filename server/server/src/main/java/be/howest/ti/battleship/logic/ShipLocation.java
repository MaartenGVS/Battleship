package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


public class ShipLocation {

    private final Ship ship;
    private List<Position> positions;
    private static final int MOVE_ONE_CELL = 1;


    public ShipLocation(
            @JsonProperty(value = "name", required = true) String name,
            @JsonProperty(value = "location", required = true) List<Position> positions) {
        this.ship = new Ship(GamePiece.valueOf(name.toUpperCase()).name(), GamePiece.valueOf(name.toUpperCase()).getSize());
        validateLengthOfPositionsList(positions);
        this.positions = positions;
    }


    //Getters
    public String getName() {
        return ship.getName();
    }

    public List<Position> getLocations() {
        return positions;
    }

    @JsonIgnore
    public int getSize() {
        return positions.size();
    }

    public String getLocationName(Position location) {
        for (Position currentLocation : positions) {
            if (currentLocation.equals(location)) {
                return ship.getName();
            }
        }
        return null;
    }


    //Setters
    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }


    //Logic
    private void validateLengthOfPositionsList(List<Position> locations) {
        if (this.ship.getSize() != locations.size()) {
            throw new BattleshipException("Size of the ship does not match with its location: ship " + ship.getName() + " must have exact " + ship.getSize() + " positions.");
        }
    }


    //Move logic
    public Position getByIndex(int index) {
        return positions.get(index);
    }

    public List<Position> allOccupiedPlaces(Fleet fleet) {
        List<Position> allPosition = new ArrayList<>();
        for (ShipLocation location : fleet.getShips()) {
            for (int i = 0; i < location.getSize(); i++) {
                allPosition.add(location.getByIndex(i));
            }
        }
        return allPosition;
    }

    @JsonIgnore
    public boolean isPositionFree(Fleet fleet, Position position) {
        List<Position> all = allOccupiedPlaces(fleet);
        for (Position value : all) {
            if (value.equals(position) || fleet.isHit(position)) {
                throw new BattleshipException("Ship can't move!");
            }
        }
        return true;
    }

    public boolean verticalShip() {
        Position head = positions.get(positions.size() - MOVE_ONE_CELL);
        Position tail = positions.get(0);
        return head.retrieveRow() != tail.retrieveRow() && head.retrieveCol() == tail.retrieveCol();
    }

    public boolean up() {
        Position head = positions.get(positions.size() - MOVE_ONE_CELL);
        Position tail = positions.get(0);
        return head.retrieveRow() < tail.retrieveRow();
    }

    public boolean leftShip() {
        Position head = positions.get(positions.size() - MOVE_ONE_CELL);
        Position tail = positions.get(0);
        return head.retrieveCol() < tail.retrieveCol();
    }


    public void move(BoardSize boardSize, Fleet fleet) {
        if (verticalShip()) {
            moveOnSameCol(boardSize, fleet);
        } else {
            moveOnSameRow(boardSize, fleet);
        }
    }

    public void moveOnSameRow(BoardSize boardSize, Fleet fleet) {
        int newCol = 0;
        for (Position pos : positions) {
            if (leftShip()) {
                if (isPositionFree(fleet, new Position(pos.retrieveRow(), positions.get(positions.size() - MOVE_ONE_CELL).retrieveCol() - MOVE_ONE_CELL))) {
                    newCol = pos.retrieveCol() - MOVE_ONE_CELL;
                    checkIfNewSizesAreValid(boardSize, pos.retrieveRow(), positions.get(positions.size() - MOVE_ONE_CELL).retrieveCol() - MOVE_ONE_CELL);

                }
            } else {
                if (isPositionFree(fleet, new Position(pos.retrieveRow(), positions.get(positions.size() - MOVE_ONE_CELL).retrieveCol() + MOVE_ONE_CELL))) {
                    newCol = pos.retrieveCol() + MOVE_ONE_CELL;
                    checkIfNewSizesAreValid(boardSize, pos.retrieveRow(), positions.get(positions.size() - MOVE_ONE_CELL).retrieveCol() + MOVE_ONE_CELL);
                }
            }
            pos.setColumn(newCol);
        }

    }

    public void moveOnSameCol(BoardSize boardSize, Fleet fleet) {
        int newRow = 0;
        for (Position pos : positions) {
            if (up()) {
                if (isPositionFree(fleet, new Position(positions.get(positions.size() - 1).retrieveRow() - MOVE_ONE_CELL, pos.retrieveCol()))) {
                    newRow = pos.retrieveRow() - MOVE_ONE_CELL;
                    checkIfNewSizesAreValid(boardSize, positions.get(positions.size() - 1).retrieveRow() - MOVE_ONE_CELL, pos.retrieveCol());
                }
            } else {
                if (isPositionFree(fleet, new Position(positions.get(positions.size() - 1).retrieveRow() + MOVE_ONE_CELL, pos.retrieveCol()))) {
                    newRow = pos.retrieveRow() + MOVE_ONE_CELL;
                    checkIfNewSizesAreValid(boardSize, positions.get(positions.size() - 1).retrieveRow() + MOVE_ONE_CELL, pos.retrieveCol());

                }
            }
            pos.setRow(newRow);
        }
    }

    public void checkIfNewSizesAreValid(BoardSize boardSize, int row, int col) {
        if (!boardSize.isInside(row, col)) {
            throw new BattleshipException("Ships must be placed inside the grid.");
        }
    }


    //toString
    @Override
    public String toString() {
        return "ShipLocation{" +
                "name='" + ship.getName() + '\'' +
                ", locations=" + positions +
                '}';
    }
}
