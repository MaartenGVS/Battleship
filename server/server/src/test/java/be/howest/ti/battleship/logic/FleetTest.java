package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FleetTest {


    @Test
    void getRows() {
        Fleet f1 = new Fleet(20, 23, new ArrayList<>());
        Fleet f2 = new Fleet(50, 23, new ArrayList<>());
        Fleet f3 = new Fleet(5, 23, new ArrayList<>());

        assertEquals(20, f1.getRows());
        assertEquals(50, f2.getRows());
        assertEquals(5, f3.getRows());

    }

    @Test
    void getCols() {
        Fleet f1 = new Fleet(20, 23, new ArrayList<>());
        Fleet f2 = new Fleet(20, 5, new ArrayList<>());
        Fleet f3 = new Fleet(20, 50, new ArrayList<>());

        assertEquals(23, f1.getCols());
        assertEquals(5, f2.getCols());
        assertEquals(50, f3.getCols());
    }

    @Test
    void outOfBoundRows() {
        assertThrows(IllegalArgumentException.class, () -> new Fleet(2, 15, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> new Fleet(55, 15, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> new Fleet(100, 15, new ArrayList<>()));

    }

    @Test
    void outOfBoundCols() {
        assertThrows(IllegalArgumentException.class, () -> new Fleet(15, 0, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> new Fleet(15, 51, new ArrayList<>()));
        assertThrows(IllegalArgumentException.class, () -> new Fleet(15, 105, new ArrayList<>()));
    }

    @Test
    void fleetIsBeingHit() {
        Fleet fleet = new Fleet(10, 10, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(12, 12),
                        new Position(9, 5),
                        new Position(13, 7)
                )))));
        assertTrue(fleet.isHit(new Position(12, 12)));
        assertTrue(fleet.isHit(new Position(13, 7)));
        assertTrue(fleet.isHit(new Position(9, 5)));

        assertFalse(fleet.isHit(new Position(23, 4)));
        assertFalse(fleet.isHit(new Position(-7, 3)));
        assertFalse(fleet.isHit(new Position(4, 51)));
    }

    @Test
    void displayFleetDetails() {
        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(15, 10),
                        new Position(15, 10),
                        new Position(15, 10)

                )))));

        assertEquals(15, fleet.getCols());
        assertEquals(10, fleet.getRows());
    }

    @Test
    void getBoardSize(){
        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(15, 10),
                        new Position(15, 10),
                        new Position(15, 10)

                )))));
        assertEquals("BoardSize{rows=10, cols=15}", fleet.getBoardSize().toString());
    }
    @Test
    void getShips() {
        Fleet fleet = new Fleet(10, 10, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(12, 12),
                        new Position(9, 5),
                        new Position(13, 7)
                )))));
        System.out.println(fleet.getShips().toString());
        assertTrue(fleet.getShips().toString().contains("SUBMARINE") &&
                fleet.getShips().toString().contains("L-12") &&
                fleet.getShips().toString().contains("I-5") &&
                fleet.getShips().toString().contains("M-7")
        );
    }

    @Test
    void getShipName() {
        Fleet fleet = new Fleet(10, 10, List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(12, 12),
                        new Position(9, 5),
                        new Position(13, 7)
                )),
                new ShipLocation(GamePiece.CRUISER.toString().toUpperCase(), List.of(
                        new Position(14, 9),
                        new Position(18, 12),
                        new Position(22, 23)
                ))));


        assertEquals("CRUISER", fleet.getShipName(new Position(14, 9)));
    }


    @Test
    void getOneShip() {
        Fleet fleet = new Fleet(10, 10, List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(12, 12),
                        new Position(9, 5),
                        new Position(13, 7)
                )),
                new ShipLocation(GamePiece.CRUISER.toString().toUpperCase(), List.of(
                        new Position(14, 9),
                        new Position(18, 12),
                        new Position(22, 23)
                ))));


        assertNull(fleet.getOneShip("notAValidShip"));
        assertNotNull(fleet.getOneShip(GamePiece.SUBMARINE.toString().toUpperCase()));
    }
}
