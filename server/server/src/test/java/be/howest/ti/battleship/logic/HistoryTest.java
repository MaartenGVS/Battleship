package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    @Test
    void getCommander() {
        History history = new History("Alice", Map.of(
                new Position(12, 16), GamePiece.CARRIER.name(),
                new Position(14, 18), GamePiece.CARRIER.name(),
                new Position(6, 15), GamePiece.CARRIER.name(),
                new Position(8, 13), GamePiece.CARRIER.name(),
                new Position(5, 24), GamePiece.CARRIER.name()

        ), true);

        assertEquals("Alice", history.getCommander());
    }

    @Test
    void getResultToString() {
        History history = new History("Alice", Map.of(
                new Position(12, 16), GamePiece.CARRIER.name(),
                new Position(14, 18), GamePiece.CARRIER.name(),
                new Position(6, 15), GamePiece.CARRIER.name(),
                new Position(8, 13), GamePiece.CARRIER.name(),
                new Position(5, 24), GamePiece.CARRIER.name()

        ), true);

        assertTrue(
                history.toString().contains("Alice") &&
                        history.toString().contains("H-13") &&
                        history.toString().contains("L-16") &&
                        history.toString().contains("E-24") &&
                        history.toString().contains("F-15") &&
                        history.toString().contains("N-18") &&
                        history.toString().contains("true")
        );
    }

    @Test
    void isAttackingCommander() {
        History historyTrue = new History("Alice", Map.of(
                new Position(12, 16), GamePiece.CARRIER.name(),
                new Position(14, 18), GamePiece.CARRIER.name(),
                new Position(6, 15), GamePiece.CARRIER.name(),
                new Position(8, 13), GamePiece.CARRIER.name(),
                new Position(5, 24), GamePiece.CARRIER.name()

        ), true);


        History historyFalse = new History("Alice", Map.of(
                new Position(12, 16), GamePiece.CARRIER.name(),
                new Position(14, 18), GamePiece.CARRIER.name(),
                new Position(6, 15), GamePiece.CARRIER.name(),
                new Position(8, 13), GamePiece.CARRIER.name(),
                new Position(5, 24), GamePiece.CARRIER.name()

        ), false);

        assertTrue(historyTrue.isAttackingCommander());
        assertFalse(historyFalse.isAttackingCommander());
    }


    @Test
    void getResultForString() {
        Map<Position, String> positions = new HashMap<>();
        positions.put(new Position(11, 12), "hit");
        positions.put(new Position(21, 22), null);
        History history = new History("commander", positions, true);

        Map<Position, Serializable> expected = new HashMap<>();
        expected.put(new Position(11, 12), "hit");
        expected.put(new Position(21, 22), false);

        Object actual = history.getResult();

        assertEquals(expected, actual);
    }


    @Test
    void getResultForBoolean() {
        Map<Position, String> positions = new HashMap<>();
        positions.put(new Position(11, 12), "destroyer");
        positions.put(new Position(22, 22), null);
        History history = new History("commander", positions, false);

        Map<Position, Boolean> expected = new HashMap<>();
        expected.put(new Position(11, 12), true);
        expected.put(new Position(22, 22), false);

        Object actual = history.getResult();

        assertEquals(expected, actual);
    }


    @Test
    void historyAfterMove() {
        History history = new History("Alice", new ShipInfo(GamePiece.SUBMARINE.toString().toUpperCase(), 3, List.of(
                "F-7",
                "F-8",
                "F-9"
        )), true);

        assertTrue(history.toString().contains("F-7"));

        assertFalse(history.retrieveShipInfResult().toString().contains("ship movement detected"));

        History history2 = new History("Alice", new ShipInfo(GamePiece.SUBMARINE.toString().toUpperCase(), 3, List.of(
                "F-7",
                "F-8",
                "F-9"
        )), false);
        assertTrue(history2.retrieveShipInfResult().toString().contains("ship movement detected"));


        assertEquals(history.retrieveShipInfResult(), new ShipInfo(GamePiece.SUBMARINE.toString().toUpperCase(), 3, List.of(
                "F-7",
                "F-8",
                "F-9"
        )));
    }
}