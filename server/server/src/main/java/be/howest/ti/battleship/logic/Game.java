package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.web.BattleshipUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;


public class Game {

    private static final int NUMBER_OF_SHIPS_PER_PLAYER = 5;
    private String status;
    private String id = null;
    private final String prefix;
    private String type;
    private String winner = null;
    private boolean started = false;
    private Commander attackingCommander;
    private Commander defendingCommander = null;


    //Constructor
    public Game(String prefix, String type, Commander commander, String status) {
        this.prefix = prefix;
        this.attackingCommander = commander;
        validateGameMode(type);
        this.status = status;
    }


    //Getters
    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public boolean isStarted() {
        return started;
    }

    public String getWinner() {
        return winner;
    }

    public String getPrefix() {
        return prefix;
    }

    public Commander getCommander() {
        return getAttackingCommander();
    }

    public Commander getAttackingCommander() {
        return attackingCommander;
    }

    public Commander getDefendingCommander() {
        return defendingCommander;
    }

    public Fleet getFleetOfAttackingCommander() {
        return attackingCommander.getFleet();
    }//

    public Fleet getFleetOfDefendingCommander() {
        return defendingCommander.getFleet();
    }//

    @JsonIgnore
    public String getStatus() {
        return this.status;
    }

    //Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setAttackingCommander(Commander commander) {
        this.attackingCommander = commander;
    }//

    public void setDefendingCommander(Commander commander) {
        this.defendingCommander = commander;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public void setSalvoSize(String type, Commander commander) {//
        if (isGameModeSimpleOrMove(type)) {
            commander.setSalvoSize(GameType.SIMPLE.getStartingSalvoSize());
        } else if (isGameModeSalvoOrSalvoAndMove(type)) {
            commander.setSalvoSize(GameType.SALVO.getStartingSalvoSize());
        } else {
            throw new BattleshipException("Invalid Game mode!");
        }
    }

    public void setStatusToStarted() {
        this.status = "started";
    }

    public boolean isGameModeSalvoOrSalvoAndMove(String type) {//
        return type.equals(GameType.SALVO.getGameMode()) || type.equals(GameType.MOVE_SALVO.getGameMode());
    }

    public boolean isGameModeSimpleOrMove(String type) {//
        return type.equals(GameType.SIMPLE.getGameMode()) || type.equals(GameType.MOVE.getGameMode());
    }


    //Logic
    public void checkForWinner() {//
        if (defendingCommander.sunkenShips.size() == NUMBER_OF_SHIPS_PER_PLAYER) {
            winner = attackingCommander.getName();
        }
    }

    private void validateGameMode(String type) {//throw
        for (GameType gameType : GameType.values()) {
            if (gameType.getGameMode().equalsIgnoreCase(type.toUpperCase())) {
                setSalvoSize(type, getCommander());
                this.type = gameType.getGameMode();
                return;
            }
        }
        throw new BattleshipException("Not available gameMode");
    }


    public void validateCommander(BattleshipUser bearerTokenOfUser, String queryCommander) {//
        if (bearerTokenOfUser.getCommander().equals(queryCommander)) {
            throw new IllegalArgumentException("You are not allowed to attack your own army.");
        }
    }

    public Map<Position, String> processSalvo(String queryCommander, List<Position> salvos) {
        validateAttackingCommander(queryCommander);
        validateSalvoSize(getAttackingCommander(), salvos);

        Map<Position, String> result = fireSalvo(salvos);

        processHistory(result);
        checkForSunkenShips();
        updateSalvoSize();
        checkForWinner();
        switchRoles();

        return result;
    }

    private void validateAttackingCommander(String queryCommander) {
        if (!getDefendingCommander().getName().equals(queryCommander)) {
            throw new IllegalStateException("Only the attacking commander can fire a salvo");
        }
    }

    private Map<Position, String> fireSalvo(List<Position> salvos) {
        Map<Position, String> result = new HashMap<>();
        Fleet fleetOfDefender = getFleetOfDefendingCommander();

        for (Position shotPosition : salvos) {
            if (fleetOfDefender.isHit(shotPosition)) {
                String hitShipName = fleetOfDefender.getShipName(shotPosition);
                result.put(shotPosition, hitShipName);
                getAttackingCommander().saveTheHitPosition(hitShipName, new Position(shotPosition.retrieveRow(), shotPosition.retrieveCol()));
            } else {
                result.put(shotPosition, null);
            }
        }

        return result;
    }


    public void processHistory(Map<Position, String> result) {
        getAttackingCommander().addOnHistory(new History(getAttackingCommander().toString(), result, true));
        getDefendingCommander().addOnHistory(new History(getAttackingCommander().toString(), result, false));
    }

    public void updateSalvoSize() {//
        if (getType().contains("salvo")) {
            getDefendingCommander().setSalvoSize(GameType.SALVO.getStartingSalvoSize() - getDefendingCommander().sunkenShips.size());
        }
    }

    public void checkForSunkenShips() {//
        for (GamePiece gamePiece : GamePiece.values()) {
            for (Map.Entry<String, List<Position>> hit : getAttackingCommander().getPositionsOfHitShips().entrySet()) {
                if (isShipSunken(gamePiece, hit) && !shipNameExistsInSunkenShips(gamePiece)) {
                    addSunkenShip(gamePiece);
                }
            }
        }
    }

    public boolean isShipSunken(GamePiece gamePiece, Map.Entry<String, List<Position>> hit) {//
        return gamePiece.name().equals(hit.getKey()) && gamePiece.getSize() == hit.getValue().size();
    }

    public boolean shipNameExistsInSunkenShips(GamePiece gamePiece) {//
        String shipName = gamePiece.name().toLowerCase();
        Set<Ship> theSunkenShips = getDefendingCommander().sunkenShips;
        return shipNameExists(theSunkenShips, shipName);
    }


    private boolean shipNameExists(Set<Ship> sunkenShips, String shipName) {//
        for (Ship ship : sunkenShips) {
            if (ship.getName().equals(shipName)) {
                return true;
            }
        }
        return false;
    }

    private void addSunkenShip(GamePiece gamePiece) {//
        String shipName = gamePiece.name().toLowerCase();
        int shipSize = gamePiece.getSize();
        Ship ship = new Ship(shipName, shipSize);
        getDefendingCommander().sunkenShips.add(ship);
    }

    public void validateSalvoSize(Commander attackingCommander, List<Position> salvos) {
        if (attackingCommander.getSalvoSize() != salvos.size()) {
            throw new BattleshipException("You have to fire exactly once!");
        }
    }

    public void switchRoles() {
        Commander temp = getAttackingCommander();
        setAttackingCommander(getDefendingCommander());
        setDefendingCommander(temp);
    }

    public void validateWinner() {
        if (winner != null) {
            throw new BattleshipException("The game is over");
        }
    }


    public void validatePossibleMove(BattleshipUser bearerUser, String commanderName) {
        if (!bearerUser.getCommander().equals(commanderName) || !getAttackingCommander().getName().equals(bearerUser.getCommander())) {
            throw new IllegalArgumentException("You are not allowed to move this ship.");
        }
    }

    public void validateIfGameModeIsWithMove() {
        if (type.equals(GameType.SALVO.getGameMode()) || type.equals(GameType.SIMPLE.getGameMode())) {
            throw new BattleshipException("It is not possible to move in the chosen game mode");
        }
    }

    //Equals and hashcode
    @Override
    public String toString() {
        return "Game{" +
                "id='" + id + '\'' +
                ", prefix='" + prefix + '\'' +
                ", type='" + type + '\'' +
                ", winner='" + winner + '\'' +
                ", started=" + started +
                ", attackingCommander=" + attackingCommander +
                ", defendingCommander=" + defendingCommander +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        if (started != game.started) return false;
        if (!Objects.equals(prefix, game.prefix)) return false;
        if (!Objects.equals(type, game.type)) return false;
        if (!Objects.equals(winner, game.winner)) return false;
        if (!Objects.equals(attackingCommander, game.attackingCommander))
            return false;
        return Objects.equals(defendingCommander, game.defendingCommander);
    }

    @Override
    public int hashCode() {
        int result = prefix != null ? prefix.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (winner != null ? winner.hashCode() : 0);
        result = 31 * result + (attackingCommander != null ? attackingCommander.hashCode() : 0);
        result = 31 * result + (started ? 1 : 0);
        return result;
    }
}

