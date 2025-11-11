package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@QuarkusTest
class StudentTest {
    @Test
    void testEqualsAndHashCode() {
        Student s1 = new Student("John", "Doe", null, null);
        s1.id = 1L;
        Student s2 = new Student("John", "Doe", null, null);
        s2.id = 1L;
        Student s3 = new Student("Jane", "Smith", null, null);
        s3.id = 2L;
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testToString() {
        Student s = new Student("John", "Doe", null, null);
        s.id = 1L;
        String str = s.toString();
        assertTrue(str.contains("John"));
    }

    @Test
    void testEqualsWithDifferentType() {
        Student s = new Student("John", "Doe", null, null);
        s.id = 1L;
        String notAStudent = "not a student";
        assertNotEquals(notAStudent, s);
    }
}
