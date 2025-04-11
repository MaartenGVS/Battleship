package be.howest.ti.battleship.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PositionTest {


    @Test
    void fromJson() throws JsonProcessingException {
        ObjectMapper JSON = new ObjectMapper();

        assertEquals(new Position(1, 1), JSON.readValue("\"A-1\"", Position.class));
        assertEquals(new Position(2, 3), JSON.readValue("\"B-3\"", Position.class));
    }

    @Test
    void setColumn() {
        Position position = new Position(2, 3);
        position.setColumn(5);

        assertEquals(5,position.retrieveCol());
    }

    @Test
    void setRow() {
        Position position = new Position(2, 3);
        position.setRow(5);

        assertEquals(5,position.retrieveRow());
    }

    @Test
    void retrieveRow() {
        Position position = new Position(2, 3);

        assertEquals(2,position.retrieveRow());
    }

    @Test
    void retrieveCol() {
        Position position = new Position(2, 3);

        assertEquals(2,position.retrieveRow());
    }

    @Test
    void testToString() {
        Position position = new Position(2, 3);


        assertEquals("B-3", position.toString());

    }
}