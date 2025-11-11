package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@QuarkusTest
class TeacherTest {
    @Test
    void testEqualsAndHashCode() {
        Teacher t1 = new Teacher("John", "Doe", null, null);
        t1.id = 1L;
        Teacher t2 = new Teacher("John", "Doe", null, null);
        t2.id = 1L;
        Teacher t3 = new Teacher("Jane", "Smith", null, null);
        t3.id = 2L;
        assertEquals(t1, t2);
        assertNotEquals(t1, t3);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testToString() {
        Teacher t = new Teacher("John", "Doe", null, null);
        t.id = 1L;
        String str = t.toString();
        assertTrue(str.contains("John"));
    }

    @Test
    void testEqualsWithDifferentType() {
        Teacher t = new Teacher("John", "Doe", null, null);
        t.id = 1L;
        String notATeacher = "not a teacher";
        assertNotEquals(notATeacher, t);
    }
}
