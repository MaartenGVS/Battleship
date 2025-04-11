package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipLocationTest {

    @Test
    void getName() {

        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(12, 12),
                new Position(12, 12)
        ));
        assertEquals("SUBMARINE", shipLocation.getName());

    }


    @Test
    void getLocation() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));
        assertTrue(shipLocation.toString().contains("L-12") && shipLocation.toString().contains("A-8"));
        assertFalse(shipLocation.toString().contains("D-9") && shipLocation.toString().contains("A-0"));
    }

    @Test
    void getSize() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));

        assertEquals(3, shipLocation.getSize());
    }


    @Test
    void getLocationName() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));

        ShipLocation shipLocation2 = new ShipLocation(GamePiece.CRUISER.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));
        assertEquals("SUBMARINE", shipLocation.getLocationName(new Position(12, 12)));
        assertEquals("CRUISER", shipLocation2.getLocationName(new Position(12, 12)));
    }


    @Test
    void setPositions() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));

        List<Position> list = List.of(
                new Position(11, 10),
                new Position(12, 20),
                new Position(4, 9)
        );
        shipLocation.setPositions(list);
        assertSame(list, shipLocation.getLocations());
    }

    @Test
    void getByIndex() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));
        assertEquals("D-9", shipLocation.getByIndex(2).toString());
    }

    @Test
    void allOccupiedPlaces() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));

        assertEquals("[]", shipLocation.allOccupiedPlaces(new Fleet(
                5, 10, List.of())).toString());
    }


    @Test
    void validateLengthOfPositionsList() {
        assertThrows(BattleshipException.class, () -> {
            new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                    new Position(12, 12),
                    new Position(1, 8)
            ));
        });
    }

    @Test
    void isPositionFree() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(12, 12),
                new Position(1, 8),
                new Position(4, 9)
        ));

        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(15, 10),
                        new Position(15, 10),
                        new Position(15, 10)

                )))));
        boolean isFree = shipLocation.isPositionFree(fleet, new Position(12, 12));

        assertTrue(isFree);
    }

    @Test
    void verticalShip() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(1, 5),
                new Position(2, 5),
                new Position(3, 5)
        ));
        boolean isVertical = shipLocation.verticalShip();
        assertTrue(isVertical);
    }

    @Test
    void up() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(1, 5),
                new Position(2, 5),
                new Position(3, 5)
        ));
        boolean isUp = shipLocation.up();
        assertFalse(isUp);
    }

    @Test
    void leftShip() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(1, 5),
                new Position(2, 5),
                new Position(3, 5)
        ));
        boolean isLeftShip = shipLocation.leftShip();
        assertFalse(isLeftShip);
    }

    @Test
    void move() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(2, 5),
                new Position(3, 5),
                new Position(4, 5)
        ));

        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(4, 10),
                        new Position(5, 10),
                        new Position(6, 10)

                )))));

        BoardSize board = new BoardSize(10, 10);
        shipLocation.move(board, fleet);

        assertTrue(shipLocation.getLocations().contains(new Position(5, 5)));
    }

    @Test
    void moveOnSameRow() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(10, 6),
                new Position(10, 7),
                new Position(10, 8)
        ));

        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(10, 6),
                        new Position(10, 7),
                        new Position(10, 8)

                )))));

        ShipLocation expectedShipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(10, 7),
                new Position(10, 8),
                new Position(10, 9)

        ));


        BoardSize board = new BoardSize(15, 15);

        shipLocation.move(board, fleet);
        assertEquals( expectedShipLocation.getLocations().toString(),shipLocation.getLocations().toString());

        ShipLocation expectedShipLocationAfterMoveOnRow = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(10, 8),
                new Position(10, 9),
                new Position(10, 10)

        ));
        shipLocation.moveOnSameRow(board, fleet);
        assertEquals( expectedShipLocationAfterMoveOnRow.getLocations().toString(),shipLocation.getLocations().toString());

    }

    @Test
    void moveOnSameCol() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(4, 10),
                new Position(5, 10),
                new Position(6, 10)
        ));

        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(4, 10),
                        new Position(5, 10),
                        new Position(6, 10)

                )))));

        ShipLocation expectedShipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(5, 10),
                new Position(6, 10),
                new Position(7, 10)

        ));


        BoardSize board = new BoardSize(15, 15);

        shipLocation.move(board, fleet);
        assertEquals( expectedShipLocation.getLocations().toString(),shipLocation.getLocations().toString());
        shipLocation.moveOnSameCol(board, fleet);

        ShipLocation expectedShipLocationAfterMoveOnCol = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(7, 10),
                new Position(8, 10),
                new Position(9, 10)

        ));
        shipLocation.moveOnSameCol(board, fleet);
        assertEquals( expectedShipLocationAfterMoveOnCol.getLocations().toString(),shipLocation.getLocations().toString());
    }

    @Test
    void checkIfNewSizesAreValid() {
        ShipLocation shipLocation = new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                new Position(7, 10),
                new Position(8, 10),
                new Position(9, 10)

        ));
        BoardSize boardSize = new BoardSize(20, 20);
        assertThrows(BattleshipException.class, () -> {shipLocation.checkIfNewSizesAreValid(boardSize, 40, 32);});
    }
}