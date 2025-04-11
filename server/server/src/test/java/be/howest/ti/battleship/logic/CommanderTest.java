
package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommanderTest {

    @Test
    void getName() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of()
        ));

        assertEquals("freddy", commander.getName());
    }

    @Test
    void getFleet() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        assertTrue(
                commander.getFleet().toString().contains("5") &&
                        commander.getFleet().toString().contains("10") &&
                        commander.getFleet().toString().contains("BATTLESHIP") &&
                        commander.getFleet().toString().contains("A-15")
        );
    }

    @Test
    void getSalvoSize() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        assertEquals(1, commander.getSalvoSize());
    }

    @Test
    void addSunkenShipPosition() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        commander.saveTheHitPosition(GamePiece.CARRIER.toString().toLowerCase(), new Position(20, 25));
        assertTrue(
                commander.getPositionsOfHitShips().containsKey("carrier") &&
                        commander.getPositionsOfHitShips().toString().contains("T-25"));

    }

    @Test
    void setSalvoSize() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        commander.setSalvoSize(5);
        assertEquals(5, commander.getSalvoSize());

    }


    @Test
    void getSunkShips() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        assertTrue(commander.getSunkShips().isEmpty());
    }


    @Test
    void getHistory() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        assertTrue(commander.getHistory().isEmpty());
    }


    @Test
    void toStringTest() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        assertTrue(commander.toString().contains("freddy"));
    }


    @Test
    void addOnHistory() {
        Commander commander = new Commander("freddy", new Fleet(
                5, 10, List.of(new ShipLocation(GamePiece.BATTLESHIP.toString(), List.of(
                new Position(1, 15),
                new Position(33, 15),
                new Position(33, 15),
                new Position(33, 15)
        )))
        ));
        commander.addOnHistory(new History(
                "freddy", Map.of(new Position(1, 15), "test"), true));
        assertTrue(commander.getHistory().toString().contains("freddy") &&
                commander.getHistory().toString().contains("A-15") &&
                commander.getHistory().toString().contains("true"));
    }


    @Test
    void updatePlaceToMove() {

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
        Commander commander = new Commander("Freddy",fleet);
        Game game = new Game("lol", "simple", commander, null);


        BoardSize board = new BoardSize(10, 10);
        shipLocation.move(board, fleet);
        assertTrue(shipLocation.getLocations().contains(new Position(5, 5)));
        assertThrows(BattleshipException.class, () -> commander.updatePlaceToMove(game, "No such ship"));
        assertThrows(BattleshipException.class, () -> commander.updatePlaceToMove(game, GamePiece.BATTLESHIP.toString()));


    }
}


