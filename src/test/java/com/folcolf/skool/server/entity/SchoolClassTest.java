package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@QuarkusTest
class SchoolClassTest {
    @Test
    void testEqualsAndHashCode() {
        SchoolClass c1 = new SchoolClass("A", null);
        c1.id = 1L;
        SchoolClass c2 = new SchoolClass("A", null);
        c2.id = 1L;
        SchoolClass c3 = new SchoolClass("B", null);
        c3.id = 2L;
        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertEquals(c1.hashCode(), c2.hashCode());
    }

    @Test
    void testToString() {
        SchoolClass c = new SchoolClass("A", null);
        c.id = 1L;
        String str = c.toString();
        assertTrue(str.contains("A"));
    }

    @Test
    void testEqualsWithDifferentType() {
        SchoolClass c = new SchoolClass("A", null);
        c.id = 1L;
        String notAClass = "not a class";
        assertNotEquals(notAClass, c);
    }

    @Test
    void testGettersAndSetters() {
        SchoolClass c = new SchoolClass();
        c.setName("B");
        assertEquals("B", c.getName());
        List<Student> students = new ArrayList<>();
        c.setStudents(students);
        assertEquals(students, c.getStudents());
    }

    @Test
    void testConstructor() {
        List<Student> students = new ArrayList<>();
        SchoolClass c = new SchoolClass("A", students);
        assertEquals("A", c.getName());
        assertEquals(students, c.getStudents());
    }

    @Test
    void testNoArgConstructor() {
        SchoolClass c = new SchoolClass();
        assertNull(c.getName());
        assertNull(c.getStudents());
    }

    @Test
    void testEqualsWithSameInstance() {
        SchoolClass c = new SchoolClass("A", null);
        c.id = 1L;
        assertEquals(c, c);
    }

    @Test
    void testEqualsWithNull() {
        SchoolClass c = new SchoolClass("A", null);
        c.id = 1L;
        assertNotEquals(null, c);
    }


}
