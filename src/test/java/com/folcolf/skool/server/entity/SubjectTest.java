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
        Subject s = new Subject("Math", 2.0, null);
        s.id = 1L;
        assertEquals(1L, s.id);
        assertEquals("Math", s.getName());
        assertEquals(2.0, s.getCoefficient());
        String str = s.toString();
        assertTrue(str.contains("Math"));
    }

    @Test
    void testEqualsWithDifferentType() {
        Subject s = new Subject("Math", 2.0, null);
        s.id = 1L;
        String notASubject = "not a subject";
        assertNotEquals(notASubject, s);
    }

    @Test
    void testGettersAndSetters() {
        Subject s = new Subject();
        s.setName("Physics");
        s.setCoefficient(3.0);
        assertEquals("Physics", s.getName());
        assertEquals(3.0, s.getCoefficient());
        List<Grade> grades = new ArrayList<>();
        s.setGrades(grades);
        assertEquals(grades, s.getGrades());
    }

    @Test
    void testEqualsAndHashCode() {
        Subject s1 = new Subject("Math", 2.0, null);
        s1.id = 1L;
        Subject s2 = new Subject("Math", 2.0, null);
        s2.id = 1L;
        assertEquals(s1, s2);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testConstructor() {
        List<Grade> grades = new ArrayList<>();
        Subject s = new Subject("Math", 2.0, grades);
        assertEquals("Math", s.getName());
        assertEquals(2.0, s.getCoefficient());
        assertEquals(grades, s.getGrades());
    }

    @Test
    void testNoArgConstructor() {
        Subject s = new Subject();
        assertNull(s.getName());
        assertNull(s.getCoefficient());
    }

    @Test
    void testEqualsWithSameInstance() {
        Subject s = new Subject("Math", 2.0, null);
        s.id = 1L;
        assertEquals(s, s);
    }

    @Test
    void testEqualsWithNull() {
        Subject s = new Subject("Math", 2.0, null);
        s.id = 1L;
        assertNotEquals(null, s);
    }

    @Test
    void testEqualsDifferentName() {
        Subject s1 = new Subject("Math", 2.0, null);
        s1.id = 1L;
        Subject s2 = new Subject("Français", 2.0, null);
        s2.id = 1L;
        assertNotEquals(s1, s2);
    }

    @Test
    void testEqualsDifferentCoefficient() {
        Subject s1 = new Subject("Math", 2.0, null);
        s1.id = 1L;
        Subject s2 = new Subject("Math", 3.0, null);
        s2.id = 1L;
        assertNotEquals(s1, s2);
    }


}
