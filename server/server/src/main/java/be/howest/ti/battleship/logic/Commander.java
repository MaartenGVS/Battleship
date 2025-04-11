package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.web.request.FleetDetailsToShow;

import java.util.*;

public class Commander {

    private final String name;

    private final Fleet fleet;

    private int salvoSize;

    Set<Ship> sunkenShips;

    List<History> history;

    private final Map<String, List<Position>> positionsOfHitShips = new HashMap<>();


    //Constructor
    public Commander(String name, Fleet fleet) {
        this.name = name;
        this.fleet = fleet;
        this.salvoSize = 1;
        this.sunkenShips = new HashSet<>();
        this.history = new ArrayList<>();
    }


    //Getters
    public String getName() {
        return name;
    }


    public Fleet getFleet() {
        return fleet;
    }

    public int getSalvoSize() {
        return this.salvoSize;
    }

    public Map<String, List<Position>> getPositionsOfHitShips() {
        return positionsOfHitShips;
    }

    public List<History> getHistory() {
        return history;
    }


    //Setters
    public void setSalvoSize(int salvoSize) {
        this.salvoSize = salvoSize;
    }


    //Logic
    public Set<Ship> getSunkShips() {
        return sunkenShips;
    }

    public void addOnHistory(History history) {
        this.history.add(history);
    }

    public void saveTheHitPosition(String shipName, Position position) {
        positionsOfHitShips.computeIfAbsent(shipName, shipPositions -> new ArrayList<>())
                .add(position);
    }
    
    public ShipInfo updatePlaceToMove(Game game, String shipName) {
        for (ShipLocation ship : getFleet().getShips()) {
            if (ship.getName().equalsIgnoreCase(shipName)) {
                ship.move(fleet.getBoardSize(), fleet);
                ShipInfo shipInfo = new ShipInfo(shipName, ship.getSize(), new FleetDetailsToShow(getFleet()).convertLocationsToString(ship.getLocations()));
                game.getAttackingCommander().addOnHistory(new History(game.getAttackingCommander().getName(), shipInfo, true));
                game.getDefendingCommander().addOnHistory(new History(game.getAttackingCommander().getName(), shipInfo, false));
                return shipInfo;
            }
        }
        throw new BattleshipException("No such ship");
    }


    //ToString, equals and hashCode
    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commander commander = (Commander) o;

        return Objects.equals(fleet, commander.fleet);
    }

    @Override
    public int hashCode() {
        return fleet != null ? fleet.hashCode() : 0;
    }
}
