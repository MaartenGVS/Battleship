package be.howest.ti.battleship.web.request;


import be.howest.ti.battleship.info.Info;
import io.vertx.ext.web.RoutingContext;



public class GetInfoRequest extends Request {
    private Info info;
    public GetInfoRequest(RoutingContext ctx) {
        super(ctx);
    }

    // Response
    @Override
    public void sendResponse() {
        Response.sendJson(ctx, 200, info);
    }



    public void setInfo(Info info) {
        this.info = info;
    }
}
