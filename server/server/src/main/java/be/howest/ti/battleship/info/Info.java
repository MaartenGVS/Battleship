package be.howest.ti.battleship.info;

import java.util.*;

public class Info {

    private String apiVersion;

    protected static final String API_NAME = "BattleShit";

    protected static final Set<Author> AUTHORS =
            new HashSet<>(
                    List.of(
                            new Author("Batoul Abou Khlil"),
                            new Author("Emiel Vandewalle"),
                            new Author("Richard Omorede"),
                            new Author("Maarten Van Santen"),
                            new Author("Jason Deschacht"))
            );

    protected static final Map<String, Method> METHODMAP = createMap();

    private static Map<String, Method> createMap() {
        return Map.of(
                "deleteGames", Method.DELETE,
                "getFleetDetails", Method.GET,
                "getGameById", Method.GET,
                "getGames", Method.GET,
                "getShips", Method.GET,
                "getInfo", Method.GET,
                "moveShip", Method.PATCH,
                "fireSalvo", Method.POST,
                "joinGame", Method.POST);
    }


    //Getters
    public String getApiVersion() {
        return apiVersion;
    }

    public Set<Author> getAuthors() {
        return AUTHORS;
    }

    public Map<String, Method> getMethodMap() {
        return METHODMAP;
    }

    public String getAPIName() {
        return API_NAME;
    }


    //Setters
    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }



    //toString

    @Override
    public String toString() {
        return apiVersion;
    }
}
