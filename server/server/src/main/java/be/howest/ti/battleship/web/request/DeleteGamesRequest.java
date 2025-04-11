package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import io.vertx.ext.web.RoutingContext;

import java.util.List;
import java.util.Map;

public class DeleteGamesRequest extends Request {


    private List<Game> gamesAfterDelete;

    public DeleteGamesRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, Map.of(
                "games", gamesAfterDelete
        ));
    }


    public void setGames(List<Game> gamesAfterDelete) {
        this.gamesAfterDelete = gamesAfterDelete;
    }
}
