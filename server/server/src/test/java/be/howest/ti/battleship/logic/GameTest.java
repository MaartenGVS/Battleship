package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.web.BattleshipUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;
    private Game gameSalvo;

    @BeforeEach
    public void setUp() {
        Game game = new Game("gameId", GameType.SIMPLE.getGameMode(), new Commander("Alice", new Fleet(12, 12, List.of(

        ))), null);
        Game game2 = new Game("gameId", GameType.SIMPLE.getGameMode(), new Commander("Bob", new Fleet(12, 12, List.of(

        ))), null);
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();

        battleshipService.addGameToCollection(game);
        battleshipService.addGameToCollection(game2);
        this.game = battleshipService.getGames(null).get(0);


        Game salvo = new Game("gameId", GameType.SALVO.getGameMode(), new Commander("Alice", new Fleet(12, 12, List.of(
                new ShipLocation("carrier", List.of(
                        new Position(12, 13),
                        new Position(12, 14),
                        new Position(12, 20),
                        new Position(12, 18),
                        new Position(12, 12)
                ))
        ))), null);
        Game salvo2 = new Game("gameId", GameType.SALVO.getGameMode(), new Commander("Bob", new Fleet(12, 12, List.of(
                new ShipLocation("carrier", List.of(
                        new Position(12, 13),
                        new Position(12, 14),
                        new Position(12, 20),
                        new Position(12, 18),
                        new Position(12, 12)
                ))

        ))), null);

        battleshipService.addGameToCollection(salvo);
        battleshipService.addGameToCollection(salvo2);
        this.gameSalvo = battleshipService.getGames(null).get(1);
    }

    @Test
    void getWinner() {
        assertNull(game.getWinner());
    }


    @Test
    void getAttackingCommander() {
        assertEquals("Alice", game.getAttackingCommander().getName());
    }

    @Test
    void getDefendingCommander() {
        assertEquals("Bob", game.getDefendingCommander().getName());
    }

    @Test
    void isStarted() {
        assertTrue(game.isStarted());
    }

    @Test
    void getId() {
        assertEquals("1_gameId", game.getId());
    }

    @Test
    void getType() {
        assertEquals(GameType.SIMPLE.getGameMode(), game.getType());
    }


    @Test
    void validateCommander() {
        assertThrows(
                IllegalArgumentException.class,
                () -> game.validateCommander(new BattleshipUser("12", "Alice"), "Alice")
        );
    }

    @Test
    void gameIsEqual() {
        Game game1 = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        Game game2 = new Game("lol", "simple", new Commander("Maarten", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);

        assertEquals(game1, game2);
    }

    @Test
    void gameNotEqual() {
        //rows difference
        Game game1 = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        Game game2 = new Game("lol", "simple", new Commander("Maarten", new Fleet(9, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);


        assertNotEquals(game1, game2);

        //prefix difference
        Game game3 = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        Game game4 = new Game("hey", "simple", new Commander("Maarten", new Fleet(9, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);

        assertNotEquals(game3, game4);

        //type difference
        Game game5 = new Game("lol", "move", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        Game game6 = new Game("lol", "simple", new Commander("Maarten", new Fleet(9, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);

        assertNotEquals(game5, game6);
    }


    @Test
    void testGameIsNotOver() {
        Game game = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        assertDoesNotThrow(game::validateWinner);
    }


    @Test
    void switchRoles() {
        Game game = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        game.switchRoles();
        assertEquals("Richie", game.getDefendingCommander().getName());
    }


    @Test
    void validateSalvoSize() {
        Game game = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        List<Position> salvo = new ArrayList<>();
        salvo.add(new Position(11, 10));
        salvo.add(new Position(11, 11));
        salvo.add(new Position(11, 12));
        salvo.add(new Position(11, 13));
        salvo.add(new Position(11, 14));
        salvo.add(new Position(11, 15));
        salvo.add(new Position(11, 16));
        salvo.add(new Position(11, 17));
        salvo.add(new Position(11, 18));
        salvo.add(new Position(11, 19));
        salvo.add(new Position(11, 20));
        assertThrows(BattleshipException.class, () -> game.validateSalvoSize(game.getAttackingCommander(), salvo));
    }


    @Test
    void processHistory() {
        game.processHistory(Map.of(
                new Position(12, 12), "test"
        ));
        assertTrue(game.getAttackingCommander().history.toString().contains("L-12") && game.getAttackingCommander().history.toString().contains("test"));
        assertTrue(game.getDefendingCommander().history.toString().contains("L-12") && game.getDefendingCommander().history.toString().contains("test"));
    }

    @Test
    void isGameModeSalvoOrSalvoAndMove() {
        Game game = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        assertFalse(game.isGameModeSalvoOrSalvoAndMove("test"));
        assertTrue(game.isGameModeSalvoOrSalvoAndMove("salvo"));
    }


    @Test
    void isGameModeSimpleOrMove() {
        Game game = new Game("lol", "simple", new Commander("Richie", new Fleet(10, 20, List.of(new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), new ArrayList<>(List.of(
                new Position(15, 10),
                new Position(15, 10),
                new Position(15, 10)
        )))))), null);
        assertFalse(game.isGameModeSimpleOrMove("salvo"));
        assertTrue(game.isGameModeSimpleOrMove("simple"));
        assertTrue(game.isGameModeSimpleOrMove("move"));
    }


    @Test
    void processSalvo() {
        game.processSalvo("Bob", List.of(
                new Position(12, 16)
        ));
        assertEquals(1, game.getAttackingCommander().history.size());
        assertThrows(IllegalStateException.class, () -> game.processSalvo("Bob", List.of(
                new Position(12, 16)
        )));
        game.checkForSunkenShips();
        assertEquals(0, game.getAttackingCommander().sunkenShips.size());
        assertThrows(BattleshipException.class, () -> game.processSalvo("Alice", List.of(
                new Position(12, 16),
                new Position(12, 16),
                new Position(12, 16),
                new Position(12, 16)
        )));

        gameSalvo.processSalvo("Bob", List.of(
                new Position(12, 13),
                new Position(12, 14),
                new Position(12, 20),
                new Position(12, 18),
                new Position(12, 12)
        ));
        assertEquals(1, gameSalvo.getAttackingCommander().sunkenShips.size());
    }


}