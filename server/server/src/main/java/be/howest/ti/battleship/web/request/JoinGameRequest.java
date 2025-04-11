package be.howest.ti.battleship.web.request;
import be.howest.ti.battleship.logic.Fleet;
import io.vertx.ext.web.RoutingContext;

import java.util.Map;


public class JoinGameRequest extends Request {

    private Map<String, String> response;

    public JoinGameRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, response);
    }

    public String getCommander() {
        return ctx.body().asPojo(PostGameBody.class).getCommander();
    }

    public String getType() {
        return ctx.body().asPojo(PostGameBody.class).getType();
    }

    public Fleet getFleet() {
        return ctx.body().asPojo(PostGameBody.class).getFleet();
    }

    public String getPrefix() {
        return ctx.body().asPojo(PostGameBody.class).getPrefix();
    }

    public void setResponse(String gameId, String playerToken) {
        this.response = Map.of(
                "gameId", gameId,
                "playerToken", playerToken
        );
    }
}
