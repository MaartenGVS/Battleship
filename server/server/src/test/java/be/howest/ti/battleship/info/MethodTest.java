package be.howest.ti.battleship.info;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MethodTest {

    @Test
    void values() {
        Method[] methods = Method.values();

        assertEquals(4, methods.length);
    }

    @Test
    void valueOf() {
        Method method = Method.valueOf("POST");
        assertSame(Method.POST, method);
    }
}