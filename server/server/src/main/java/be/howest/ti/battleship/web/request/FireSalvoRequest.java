package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Position;
import io.vertx.ext.web.RoutingContext;
import java.util.List;
import java.util.Map;

public class FireSalvoRequest extends Request {


    private Map<Position, String> salvoResult;

    public FireSalvoRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    public void sendResponse() {
        Response.sendJson(ctx, 200, salvoResult);
    }


    public void setSalvoResult(Map<Position, String> salvoResult) {
        this.salvoResult = salvoResult;
    }


    public String getDefenderCommander() {
        return ctx.pathParam("defendingCommander");
    }

    public List<Position> getSalvo() {
        return ctx.body().asPojo(FireSalvoBody.class).getSalvo();
    }
}
