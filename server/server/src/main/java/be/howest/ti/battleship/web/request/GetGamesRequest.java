package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;

import io.vertx.ext.web.RoutingContext;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class GetGamesRequest extends Request {

    private List<GameBodyToShow> games;

    public void setGames(List<Game> games) {
        List<GameBodyToShow> gamesToShow = new ArrayList<>();

        for (Game game : games){
            if (game.isStarted()){
                gamesToShow.add(new StartedGameToShow(game));
            }else{
                gamesToShow.add(new WaitedGameToShow(game));
            }
        }
        this.games = gamesToShow;
    }
    public String getStatus() {
        return ctx.request().getParam("status");
    }
    public GetGamesRequest(RoutingContext ctx) {
        super(ctx);

    }
    // Response

    @Override
    public void sendResponse() {
        Response.sendJson(ctx,200, Map.of(
                "games", games
        ));
    }
}
