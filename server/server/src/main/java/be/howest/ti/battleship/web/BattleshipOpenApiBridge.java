package be.howest.ti.battleship.web;

import be.howest.ti.battleship.logic.*;
import be.howest.ti.battleship.web.request.*;
import be.howest.ti.battleship.web.tokens.PlainTextTokens;
import be.howest.ti.battleship.web.tokens.TokenManager;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BearerAuthHandler;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import io.vertx.ext.web.openapi.RouterBuilder;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BattleshipOpenApiBridge {
    private static final Logger LOGGER = Logger.getLogger(BattleshipOpenApiBridge.class.getName());
    public static final String BATTLESHIP_REQUEST = "battleship-request";

    private final TokenManager tokenManager;
    private final BattleshipService service;

    public BattleshipOpenApiBridge() {
        this(new BattleshipServiceImpl(), new PlainTextTokens());
    }

    BattleshipOpenApiBridge(BattleshipService service, TokenManager tokenManager) {
        this.service = service;
        this.tokenManager = tokenManager;
    }


    public Router buildRouter(RouterBuilder routerBuilder) {
        LOGGER.log(Level.INFO, "Check if service is set");
        Objects.requireNonNull(service); // mainly to remove 'unused' warning in initial/empty implementation.

        LOGGER.log(Level.INFO, "Installing CORS handlers");
        routerBuilder.rootHandler(createCorsHandler());

        LOGGER.log(Level.INFO, "Installing CORS handlers");
        routerBuilder.rootHandler(BodyHandler.create());

        LOGGER.log(Level.INFO, "Installing security handlers");
        routerBuilder.securityHandler("playerToken", BearerAuthHandler.create(tokenManager));


        LOGGER.log(Level.INFO, "Installing Failure handlers");
        routerBuilder.operations().forEach(op -> op.failureHandler(this::onFailedRequest));

        LOGGER.log(Level.INFO, "Installing Actual handlers");

        routerBuilder.operation("get-info")
                .handler(ctx -> getInfo(new GetInfoRequest(ctx)));

        routerBuilder.operation("get-ships")
                .handler(ctx -> getShips(new GetShipsRequest(ctx)));

        routerBuilder.operation("delete-games")
                .handler(ctx -> deleteGames(new DeleteGamesRequest(ctx)));

        routerBuilder.operation("get-games")
                .handler(ctx -> getGames(new GetGamesRequest(ctx)));

        routerBuilder.operation("join-game")
                .handler(ctx -> joinGame(new JoinGameRequest(ctx)));

        routerBuilder.operation("get-game-by-id")
                .handler(ctx -> getGameById(new GetGameByIdRequest(ctx)));

        routerBuilder.operation("fire-salvo")
                .handler(ctx -> fireSalvo(new FireSalvoRequest(ctx)));

        routerBuilder.operation("move-ship")
                .handler(ctx -> moveShip(new MoveShipRequest(ctx)));

        routerBuilder.operation("get-fleet-details")
                .handler(ctx -> getFleetDetails(new GetFleetDetailsRequest(ctx)));

        return routerBuilder.createRouter();
    }

    private void getInfo(GetInfoRequest request) {
        request.setInfo(service.getInfo());
        request.sendResponse();
    }

    private void deleteGames(DeleteGamesRequest request) {
        BattleshipUser battleshipUser = request.getUser();
        String playerToken = tokenManager.createToken(new BattleshipUser(battleshipUser.getGameId(), battleshipUser.getCommander()));
        request.setGames(service.getGamesAfterDelete(playerToken));
        request.sendResponse();
    }

    private void getShips(GetShipsRequest request) {
        request.setShips(service.getShips());
        request.sendResponse();
    }

    private void getGames(GetGamesRequest request) {
        String status = request.getStatus();
        request.setGames(service.getGames(status));
        request.sendResponse();
    }

    private void joinGame(JoinGameRequest request) {
        String gameId = service.processGameRequest(request);
        String playerToken = tokenManager.createToken(new BattleshipUser(gameId, request.getCommander()));
        request.setResponse(gameId, playerToken);
        request.sendResponse();
    }


    private void getGameById(GetGameByIdRequest request) {
        BattleshipUser bearerTokenOfUser = request.getUser();
        String gameId = request.getGameId();
        request.setGameById(service.getGameById(bearerTokenOfUser, gameId), bearerTokenOfUser);
        request.sendResponse();
    }

    private void fireSalvo(FireSalvoRequest request) {
        BattleshipUser bearerTokenOfUser = request.getUser();
        String gameId = request.getGameId();
        String defenderCommander = request.getDefenderCommander();
        List<Position> salvo = request.getSalvo();
        request.setSalvoResult(service.getSalvoResult(bearerTokenOfUser, gameId, defenderCommander, salvo));
        request.sendResponse();
    }

    private void moveShip(MoveShipRequest request) {
        BattleshipUser bearerUser = request.getUser();
        String gameId = request.getGameId();
        String commanderName = request.getCommanderName();
        request.setUpdatedPositions(service.getLocationsAfterMove(gameId, commanderName, request.getShipName(), bearerUser));
        request.sendResponse();
    }

    private void getFleetDetails(GetFleetDetailsRequest request) {
        BattleshipUser bearerTokenOfUser = request.getUser();
        String bearerGameId = bearerTokenOfUser.getGameId();
        String bearerCommander = bearerTokenOfUser.getCommander();
        String gameId = request.getGameId();
        String commanderName = request.getCommanderName();
        FleetDetailsToShow body = new FleetDetailsToShow(service.getFleetDetails(bearerGameId, bearerCommander, gameId, commanderName));
        request.setFleetDetails(body);
        request.sendResponse();
    }


    private void onFailedRequest(RoutingContext ctx) {
        Throwable cause = ctx.failure();
        int code = ctx.statusCode();
        String quote = Objects.isNull(cause) ? "" + code : cause.getMessage();

        // Map custom runtime exceptions to a HTTP status code.
        LOGGER.log(Level.INFO, "Failed request cause", cause);

        if (cause instanceof InvalidRequestException) {
            code = 400;
        } else if (cause instanceof IllegalArgumentException) {
            code = 400;
        } else if (cause instanceof ForbiddenAccessException) {
            code = 403;
        } else if (cause instanceof BattleshipResourceNotFoundException) {
            code = 404;
        } else if (cause instanceof BattleshipException) {
            code = 409;
        } else if (cause instanceof NotYetImplementedException) {
            code = 501;
        } else {
            LOGGER.log(Level.WARNING, "Failed request", cause);
        }

        Response.sendFailure(ctx, code, quote);
    }

    private CorsHandler createCorsHandler() {
        return CorsHandler.create(".*.")
                .allowedHeader("x-requested-with")
                .allowedHeader("Access-Control-Allow-Origin")
                .allowedHeader("Access-Control-Allow-Credentials")
                .allowCredentials(true)
                .allowedHeader("origin")
                .allowedHeader("Content-Type")
                .allowedHeader("Authorization")
                .allowedHeader("accept")
                .allowedMethod(HttpMethod.HEAD)
                .allowedMethod(HttpMethod.GET)
                .allowedMethod(HttpMethod.POST)
                .allowedMethod(HttpMethod.OPTIONS)
                .allowedMethod(HttpMethod.PATCH)
                .allowedMethod(HttpMethod.DELETE)
                .allowedMethod(HttpMethod.PUT);
    }
}
