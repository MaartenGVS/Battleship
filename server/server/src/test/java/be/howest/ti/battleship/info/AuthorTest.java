package be.howest.ti.battleship.info;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @Test
    void getName() {
        Author author = new Author("Maarten");
        assertEquals("Maarten", author.getName());
    }

    @Test
    void getStatus() {
        Author author = new Author("Maarten");
        assertEquals("Student", author.getStatus());
    }
}