package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void getName() {
        Ship ship = new Ship(GamePiece.SUBMARINE.toString(), GamePiece.SUBMARINE.getSize());
        assertEquals("SUBMARINE", ship.getName());
    }

    @Test
    void getSize() {
        Ship ship = new Ship(GamePiece.SUBMARINE.toString(), GamePiece.SUBMARINE.getSize());
        assertEquals(3, ship.getSize());
    }


    @Test
    void getShipsByUsingTheGamePieceEnum() {
        BattleshipService service = new BattleshipServiceImpl();

        List<Ship> serviceShips = service.getShips();
        List<Ship> ships = List.of(
                new Ship(GamePiece.CARRIER.toString().toLowerCase(), GamePiece.CARRIER.getSize()),
                new Ship(GamePiece.BATTLESHIP.toString().toLowerCase(), GamePiece.BATTLESHIP.getSize()),
                new Ship(GamePiece.CRUISER.toString().toLowerCase(), GamePiece.CRUISER.getSize()),
                new Ship(GamePiece.SUBMARINE.toString().toLowerCase(), GamePiece.SUBMARINE.getSize()),
                new Ship(GamePiece.DESTROYER.toString().toLowerCase(), GamePiece.DESTROYER.getSize())
        );

        assertEquals(ships, serviceShips);
    }

    @Test
    void getShips() {
        BattleshipService service = new BattleshipServiceImpl();

        List<Ship> serviceShips = service.getShips();
        List<Ship> ships = List.of(
                new Ship("carrier", 5),
                new Ship("battleship", 4),
                new Ship("cruiser", 3),
                new Ship("submarine",3),
                new Ship("destroyer", 2)
        );

        assertEquals(ships, serviceShips);
    }


    @Test
    void toStringTest(){
        Ship ship = new Ship(GamePiece.SUBMARINE.toString(), GamePiece.SUBMARINE.getSize());
        assertTrue(ship.toString().contains("SUBMARINE"));
    }
}