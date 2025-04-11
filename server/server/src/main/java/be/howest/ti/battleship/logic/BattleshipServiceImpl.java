package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.info.Info;
import be.howest.ti.battleship.web.BattleshipUser;
import be.howest.ti.battleship.web.ForbiddenAccessException;
import be.howest.ti.battleship.web.request.JoinGameRequest;

import java.util.*;

public class BattleshipServiceImpl implements BattleshipService {


    private int numberOfCurrentGame = 1;
    private final Map<String, Game> games = new HashMap<>();


    //Getters
    @Override
    public String getVersion() {
        return "0.0.1";
    }

    @Override
    public List<Ship> getShips() {
        List<Ship> ships = new ArrayList<>();
        for (GamePiece gamePiece : GamePiece.values()) {
            ships.add(new Ship(gamePiece.toString().toLowerCase(), gamePiece.getSize()));
        }
        return ships;
    }

    @Override
    public Info getInfo() {
        Info info = new Info();
        info.setApiVersion(getVersion());
        return info;
    }

    @Override
    public String processGameRequest(JoinGameRequest request) {
        String prefix = request.getPrefix();
        String type = request.getType();
        Commander commander = new Commander(request.getCommander(), request.getFleet());
        Game game = new Game(prefix, type, commander, "waiting");
        return addGameToCollection(game);
    }

    public Fleet getFleetDetails(String bearerGameId, String bearerCommander, String gameId, String commanderName) {
        if (bearerGameId.equals(gameId) && bearerCommander.equals(commanderName)) {
            return getFleetDetailsForCommander(gameId, commanderName);
        } else {
            throw new ForbiddenAccessException("Access denied!");
        }
    }

    @Override
    public Game getGameById(BattleshipUser user, String gameId) {
        if (!isGamesMapEmpty()) {
            for (Map.Entry<String, Game> entry : games.entrySet()) {
                if (entry.getKey().equals(gameId)) {
                    return entry.getValue();
                }
            }
        }
        throw new IllegalStateException("Game not found");
    }

    public List<Game> getGames(String status) {
        List<Game> gamesToShow = new LinkedList<>();
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            Game game = entry.getValue();
            if (status == null || game.getStatus().equals(status)) {
                gamesToShow.add(game);
            }
        }
        return gamesToShow;
    }


    @Override
    public Map<Position, String> getSalvoResult(BattleshipUser bearerTokenOfUser, String gameId, String defenderCommander, List<Position> salvo) {
        Game game = getGameById(bearerTokenOfUser, gameId);
        game.validateCommander(bearerTokenOfUser, defenderCommander);
        game.validateWinner();
        return game.processSalvo(defenderCommander, salvo);
    }

    public List<Game> getGamesAfterDelete(String playerToken) {
        if (Objects.equals(playerToken, "0-Admin")) {
            games.clear();
            return getGames(null);
        }
        throw new ForbiddenAccessException("Access denied!!");
    }


    //Logic
    public String addGameToCollection(Game game) {
        if (!checkForPossibleMerge(game)) {
            String gameIdOfCurrentGame = setGameIdOfCurrentGame(game.getPrefix());
            numberOfCurrentGame++;
            game.setId(gameIdOfCurrentGame);
            games.put(gameIdOfCurrentGame, game);
        } else {
            return mergeAndGetId(game);
        }

        return game.getId();
    }

    private String setGameIdOfCurrentGame(String prefix) {
        return (prefix == null) ? String.valueOf(numberOfCurrentGame) : numberOfCurrentGame + "_" + prefix;
    }

    private String mergeAndGetId(Game game) {
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            Game selectedGame = entry.getValue();
            if (selectedGame.equals(game)) {
                merge(game, selectedGame);
                return selectedGame.getId();
            }
        }
        throw new IllegalStateException("Merge failed");
    }

    private void merge(Game game, Game selectedGame) {
        selectedGame.setSalvoSize(game.getType(), game.getCommander());
        selectedGame.setDefendingCommander(game.getCommander());
        selectedGame.setStarted(true);
        selectedGame.setStatusToStarted();
    }

    private boolean checkForPossibleMerge(Game game) {
        for (Map.Entry<String, Game> entry : games.entrySet()) {
            if (entry.getValue().equals(game)) {
                if (!entry.getValue().getCommander().getName().equals(game.getCommander().getName())) {
                    return true;
                } else {
                    throw new BattleshipException("This commanders name is already taken");
                }
            }
        }
        return false;
    }

    private boolean isGamesMapEmpty() {
        return games.isEmpty();
    }

    public Fleet getFleetDetailsForCommander(String gameId, String commanderName) {
        try {
            Game game = games.get(gameId);
            if (game.getAttackingCommander().toString().equals(commanderName)) {
                return game.getFleetOfAttackingCommander();
            } else {
                return game.getFleetOfDefendingCommander();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public ShipInfo getLocationsAfterMove(String gameId, String commanderName, String shipName, BattleshipUser bearerUser) {
        Game game = getGameById(bearerUser, gameId);
        game.validateIfGameModeIsWithMove();
        game.validatePossibleMove(bearerUser, commanderName);
        ShipInfo shipInfo = game.getAttackingCommander().updatePlaceToMove(game, shipName);
        game.checkForWinner();
        game.switchRoles();
        return shipInfo;
    }

}


