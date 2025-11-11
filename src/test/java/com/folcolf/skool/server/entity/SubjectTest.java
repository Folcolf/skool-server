package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@QuarkusTest
class SubjectTest {
    @Test
    void testConstructorsAndToString() {
        Subject s = new Subject("Math", 2.0, null, null);
        s.id = 1L;
        assertEquals(1L, s.id);
        assertEquals("Math", s.getName());
        assertEquals(2.0, s.getCoefficient());
        String str = s.toString();
        assertTrue(str.contains("Math"));
    }

    @Test
    void testEqualsWithDifferentType() {
        Subject s = new Subject("Math", 2.0, null, null);
        s.id = 1L;
        String notASubject = "not a subject";
        assertNotEquals(notASubject, s);
    }
}
