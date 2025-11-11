package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@QuarkusTest
class GradeTest {
    @Test
    void testEqualsAndHashCode() {
        Grade g1 = new Grade(15.0, null, null);
        g1.id = 1L;
        Grade g2 = new Grade(15.0, null, null);
        g2.id = 1L;
        Grade g3 = new Grade(10.0, null, null);
        g3.id = 2L;
        assertEquals(g1, g2);
        assertNotEquals(g1, g3);
        assertEquals(g1.hashCode(), g2.hashCode());
    }

    @Test
    void testToString() {
        Grade g = new Grade(12.0, null, null);
        g.id = 1L;
        String str = g.toString();
        assertTrue(str.contains("12.0"));
    }

    @Test
    void testEqualsWithDifferentType() {
        Grade g = new Grade(12.0, null, null);
        g.id = 1L;
        String notAGrade = "not a grade";
        assertNotEquals(notAGrade, g);
    }
}
