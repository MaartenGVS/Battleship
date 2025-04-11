package be.howest.ti.battleship.web.request;

import io.vertx.ext.web.RoutingContext;

public class GetFleetDetailsRequest extends Request {


    private FleetDetailsToShow fleetDetails;
    public GetFleetDetailsRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx,200,  fleetDetails);
    }

    public void setFleetDetails(FleetDetailsToShow fleetDetails) {
        this.fleetDetails = fleetDetails;
    }

    public String getCommanderName() {
        return ctx.pathParam("commander");
    }
}