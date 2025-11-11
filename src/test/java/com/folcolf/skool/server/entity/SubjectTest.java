package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testGettersAndSetters() {
        Subject s = new Subject();
        s.setName("Physics");
        s.setCoefficient(3.0);
        Teacher t = new Teacher();
        s.setTeacher(t);
        assertEquals("Physics", s.getName());
        assertEquals(3.0, s.getCoefficient());
        assertEquals(t, s.getTeacher());
    }

    @Test
    void testGettersAndSettersGrades() {
        Subject s = new Subject();
        List<Grade> grades = new ArrayList<>();
        s.setGrades(grades);
        assertEquals(grades, s.getGrades());
    }

    @Test
    void testEqualsAndHashCode() {
        Subject s1 = new Subject("Math", 2.0, null, null);
        s1.id = 1L;
        Subject s2 = new Subject("Math", 2.0, null, null);
        s2.id = 1L;
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }
}
