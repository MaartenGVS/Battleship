package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Ship;
import io.vertx.ext.web.RoutingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetShipsRequest extends Request {

    private List<Ship> ships = new ArrayList<>();

    public GetShipsRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, Map.of(
                "ships", ships
        ));
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
