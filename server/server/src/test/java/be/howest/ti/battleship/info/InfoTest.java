package be.howest.ti.battleship.info;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InfoTest {


    @Test
    void getApiVersion() {
        Info info = new Info();
        info.setApiVersion("1.0");
        assertEquals("1.0", info.getApiVersion());
    }


    @Test
    void getAuthors() {
        Info info = new Info();
        System.out.println(info.getAuthors().toString());
        assertTrue(info.getAuthors().toString().contains("Batoul Abou Khlil"));
        assertTrue(info.getAuthors().toString().contains("Emiel Vandewalle"));
        assertTrue(info.getAuthors().toString().contains("Richard Omorede"));
        assertTrue(info.getAuthors().toString().contains("Maarten Van Santen"));
        assertTrue(info.getAuthors().toString().contains("Jason Deschacht"));
        assertEquals(5, info.getAuthors().size());

    }

    @Test
    void getMethodMap() {
        Info info = new Info();
        assertTrue(info.getMethodMap().containsKey("deleteGames"));
        assertTrue(info.getMethodMap().containsKey("getFleetDetails"));
        assertTrue(info.getMethodMap().containsKey("getGameById"));
        assertTrue(info.getMethodMap().containsKey("getGames"));
        assertTrue(info.getMethodMap().containsKey("getShips"));
        assertTrue(info.getMethodMap().containsKey("getInfo"));
        assertTrue(info.getMethodMap().containsKey("moveShip"));
        assertTrue(info.getMethodMap().containsKey("fireSalvo"));
        assertTrue(info.getMethodMap().containsKey("joinGame"));
        assertEquals(9, info.getMethodMap().size());
    }

    @Test
    void getAPIName() {
        Info info = new Info();
        assertEquals("BattleShit", info.getAPIName());

    }
    @Test
    void setApiVersion() {
        Info info = new Info();
        info.setApiVersion("1.0");
        assertEquals("1.0", info.getApiVersion());

    }
}