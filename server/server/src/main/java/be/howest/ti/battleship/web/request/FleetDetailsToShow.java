package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.BoardSize;
import be.howest.ti.battleship.logic.Fleet;
import be.howest.ti.battleship.logic.Position;
import be.howest.ti.battleship.logic.ShipLocation;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.*;

    @JsonPropertyOrder({
            "rows", "cols", "ships", "size"
    })
    public class FleetDetailsToShow {


        private final BoardSize boardSize;
        private final List<ShipLocation> ships;


        public FleetDetailsToShow(Fleet fleet) {
            this.ships = fleet.getShips();
            this.boardSize = new BoardSize(fleet.getRows(), fleet.getCols());
        }


        @JsonProperty("rows")
        public int getRows() {
            return this.boardSize.getRows();
        }

        @JsonProperty("cols")
        private int getCols() {
            return this.boardSize.getCols();
        }

        @JsonProperty("ships")
        public List<ShipLocationsToShow> getShipsDetails() {
            List<ShipLocationsToShow> shipDetails = new ArrayList<>();
            for (ShipLocation shipLocation : this.ships) {
                shipDetails.add(getShipDetails(shipLocation));
            }
            return shipDetails;
        }

        private ShipLocationsToShow getShipDetails(ShipLocation shipLocation) {
            return new ShipLocationsToShow
                    (
                            shipLocation.getName(),
                            convertLocationsToString(shipLocation.getLocations())
                    );
        }


        public List<String> convertLocationsToString(List<Position> positions) {
            List<String> locationStrings = new ArrayList<>();
            for (Position position : positions) {
                locationStrings.add(positionToString(position));
            }
            return locationStrings;
        }

        public String positionToString(Position position) {
            return Character.toString(position.retrieveRow() + 64) + "-" + position.retrieveCol();
        }


        @JsonProperty("size")
        public Map<String, Integer> getSize() {
            Map<String, Integer> size = new HashMap<>();
            size.put("rows", this.boardSize.getRows());
            size.put("cols", this.boardSize.getCols());
            return size;
        }

    }
