package be.howest.ti.battleship.web.request;

import be.howest.ti.battleship.logic.Game;
import be.howest.ti.battleship.web.BattleshipUser;
import io.vertx.ext.web.RoutingContext;

public class GetGameByIdRequest extends Request {


    private GameStateToShow gameStateToShow;
    private WaitedGameToShow waitedGame;

    public GetGameByIdRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        if (gameStateToShow != null) {
            Response.sendJson(ctx, 200, gameStateToShow);
        } else if (waitedGame != null) {
            Response.sendJson(ctx, 200, waitedGame);
        }
    }

    public void setGameById(Game game, BattleshipUser bearerTokenOfUser) {
        if(game.isStarted()){
            this.gameStateToShow = new GameStateToShow(game, bearerTokenOfUser);
        }else{
            this.waitedGame = new WaitedGameToShow(game);
        }
    }
}
