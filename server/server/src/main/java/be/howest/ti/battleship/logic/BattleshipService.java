package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.info.Info;
import be.howest.ti.battleship.web.BattleshipUser;
import be.howest.ti.battleship.web.request.JoinGameRequest;

import java.util.List;
import java.util.Map;

public interface BattleshipService {

    String getVersion();

    List<Ship> getShips();

    Info getInfo();

    String processGameRequest(JoinGameRequest request);

    Fleet getFleetDetails(String bearerGameId, String bearerCommander, String gameId, String commanderName);

    Game getGameById(BattleshipUser user, String gameId);

    List<Game> getGames(String status);


    Map<Position, String> getSalvoResult(BattleshipUser bearerUser, String gameId, String defenderCommander, List<Position> salvo);

    List<Game> getGamesAfterDelete(String playerToken);

    ShipInfo getLocationsAfterMove(String gameId, String commanderName, String shipName, BattleshipUser bearerUser);


}

