package be.howest.ti.battleship.logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShipInfoTest {

    @Test
    void getShipLocation() {
        ShipInfo shipInfo = new ShipInfo("destroyer", 5, new ArrayList<>(List.of(
                "a-10",
                "a-11,"
        )));
        assertEquals("[a-10, a-11,]", shipInfo.getLocation().toString());

    }

    @Test
    void getShip() {
        ShipInfo shipInfo = new ShipInfo("destroyer", 5, new ArrayList<>(List.of(
                "a-10",
                "a-11,"
        )));

        assertEquals("destroyer", shipInfo.getShip().getName());
        assertEquals(5, shipInfo.getShip().getSize());


    }

    @Test
    void testToString() {
        ShipInfo shipInfo = new ShipInfo("destroyer", 5, new ArrayList<>(List.of(
                "a-10",
                "a-11,"
        )));

        assertEquals("ShipInfo{ship=Ship{name='destroyer', size=5}, shipLocation=[a-10, a-11,]}", shipInfo.toString());


        ShipInfo shipInfo1 = new ShipInfo("destroyer", 5, new ArrayList<>(List.of(
                "a-10",
                "a-11,"
        )));


        ShipInfo shipInfo2 = new ShipInfo("destroyer", 5, new ArrayList<>(List.of(
                "a-13",
                "a-23,"
        )));

        boolean isEqual = shipInfo1.equals(shipInfo2);
        assertFalse(isEqual);

        int hashCode1 = shipInfo1.hashCode();
        int hashCode2 = shipInfo2.hashCode();
        assertNotEquals(hashCode1, hashCode2);
    }
}