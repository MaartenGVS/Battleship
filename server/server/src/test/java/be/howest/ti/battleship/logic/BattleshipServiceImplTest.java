package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.info.Info;
import be.howest.ti.battleship.web.BattleshipUser;
import be.howest.ti.battleship.web.ForbiddenAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipServiceImplTest {


    private Game game;

    @BeforeEach
    public void setUp() {
        Game game = new Game("gameId", GameType.SIMPLE.getGameMode(), new Commander("Alice", new Fleet(12, 10, List.of(

        ))), null);
        Game game2 = new Game("gameId", GameType.SIMPLE.getGameMode(), new Commander("Bob", new Fleet(12, 10, List.of(

        ))), null);
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();

        battleshipService.addGameToCollection(game);
        battleshipService.addGameToCollection(game2);
        this.game = battleshipService.getGames(null).get(0);
    }

    @Test
    void getVersion() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        assertEquals("0.0.1", battleshipService.getVersion());
    }

    @Test
    void getShips() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        assertTrue(
                battleshipService.getShips().toString().contains("carrier") &&
                        battleshipService.getShips().toString().contains("5") &&
                        battleshipService.getShips().toString().contains("battleship") &&
                        battleshipService.getShips().toString().contains("4") &&
                        battleshipService.getShips().toString().contains("cruiser") &&
                        battleshipService.getShips().toString().contains("3") &&
                        battleshipService.getShips().toString().contains("submarine") &&
                        battleshipService.getShips().toString().contains("3") &&
                        battleshipService.getShips().toString().contains("destroyer") &&
                        battleshipService.getShips().toString().contains("2")
        );
    }

    @Test
    void getInfo() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        Info info = new Info();
        info.setApiVersion(battleshipService.toString());
        assertTrue(info.getApiVersion().contains(battleshipService.toString()));
        assertEquals("0.0.1", battleshipService.getInfo().toString());
    }


    @Test
    void processGameRequest() {
        String prefix = "lol";
        String type = "simple";
        Fleet fleet = new Fleet(10, 15, new ArrayList<>(List.of(
                new ShipLocation(GamePiece.SUBMARINE.toString().toUpperCase(), List.of(
                        new Position(15, 10),
                        new Position(15, 10),
                        new Position(15, 10)

                )))));
        Commander commander = new Commander("Jason", fleet);
        Game game = new Game(prefix, type, commander, null);


        assertEquals("lol", game.getPrefix());
        assertEquals("simple", game.getType());
        assertEquals("Jason", game.getCommander().getName());
        assertEquals(15, game.getCommander().getFleet().getCols());
        assertEquals(10, game.getCommander().getFleet().getRows());

        assertTrue(game.getCommander().getFleet().getShipName(new Position(15, 10)).contains("SUBMARINE"));

    }


    @Test
    void getFleetDetails() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        battleshipService.addGameToCollection(game);

        assertDoesNotThrow(() -> battleshipService.getFleetDetails("1_gameId", "Alice", "1_gameId", "Alice"));
        assertThrows(ForbiddenAccessException.class,
                () -> battleshipService.getFleetDetails("1_gameId", "BOB BOBBELINO", "1_gameId", "Alice"));
        assertEquals(10, battleshipService.getFleetDetails("1_gameId", "Alice", "1_gameId", "Alice").getCols());
        assertEquals(12, battleshipService.getFleetDetails("1_gameId", "Alice", "1_gameId", "Alice").getRows());
    }

    @Test
    void getGameById() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        battleshipService.addGameToCollection(game);
        BattleshipUser user = new BattleshipUser("1_gameId", "Alice");

        assertEquals("1_gameId", battleshipService.getGameById(user, "1_gameId").getId());

    }


    @Test
    void getSalvoResult() {

        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        battleshipService.addGameToCollection(game);
        assertEquals("{O-10=null}", battleshipService.getSalvoResult(
                new BattleshipUser("1_gameId", game.getAttackingCommander().getName()),
                "1_gameId",
                game.getDefendingCommander().getName(),
                List.of(
                        new Position(15, 10))).toString());


    }

    @Test
    void getGamesAfterDelete() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        battleshipService.addGameToCollection(game);
        assertEquals(1, battleshipService.getGames(null).size());
        assertThrows(ForbiddenAccessException.class, () -> battleshipService.getGamesAfterDelete("test"));

        battleshipService.getGamesAfterDelete("0-Admin");
        assertEquals(0, battleshipService.getGames(null).size());

    }

    @Test
    void getFleetDetailsForCommander() {
        BattleshipServiceImpl battleshipService = new BattleshipServiceImpl();
        battleshipService.addGameToCollection(game);

        assertDoesNotThrow(() -> battleshipService.getFleetDetailsForCommander("1_gameId", "Alice"));
        assertThrows(ForbiddenAccessException.class,
                () -> battleshipService.getFleetDetails("1_gameId", "BOB BOBBELINO", "1_gameId", "Alice"));


        assertTrue(battleshipService.getFleetDetailsForCommander("1_gameId","Alice").toString().contains("12") &&
                        battleshipService.getFleetDetailsForCommander("1_gameId","Alice").toString().contains("10"));

        assertEquals(0, battleshipService.getFleetDetailsForCommander("1_gameId","Alice").getShips().size());
    }


}