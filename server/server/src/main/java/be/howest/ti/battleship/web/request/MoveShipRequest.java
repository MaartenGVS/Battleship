package be.howest.ti.battleship.web.request;


import be.howest.ti.battleship.logic.ShipInfo;
import io.vertx.ext.web.RoutingContext;


public class MoveShipRequest extends Request {

    private ShipInfo shipInfo;

    public MoveShipRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {

        Response.sendJson(ctx, 200, shipInfo);
    }

    public String getCommanderName() {
        return ctx.pathParam("movingCommander");
    }

    public String getShipName() {
        return ctx.pathParam("ship");
    }


    public void setUpdatedPositions(ShipInfo updatedFleet) {
        shipInfo = updatedFleet;
    }
}
