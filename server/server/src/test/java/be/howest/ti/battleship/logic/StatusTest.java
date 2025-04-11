package be.howest.ti.battleship.logic;

import be.howest.ti.battleship.info.Method;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusTest {

    @Test
    void getStatusInLowerCase() {
        Status status = Status.valueOf("STARTED");
        assertSame(Status.STARTED, status);
    }

    @Test
    void values() {
        Status[] statuses = Status.values();

        assertEquals(2, statuses.length);
    }

    @Test
    void valueOf() {
        Status status = Status.valueOf("WAITING");
        assertSame(Status.WAITING, status);
    }
}