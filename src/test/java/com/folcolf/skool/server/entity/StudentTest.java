package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    void testGettersAndSetters() {
        Student s = new Student();
        s.setFirstName("Alice");
        s.setLastName("Martin");
        SchoolClass c = new SchoolClass();
        s.setSchoolClass(c);
        assertEquals("Alice", s.getFirstName());
        assertEquals("Martin", s.getLastName());
        assertEquals(c, s.getSchoolClass());
    }

    @Test
    void testGettersAndSettersGrades() {
        Student s = new Student();
        List<Grade> grades = new ArrayList<>();
        s.setGrades(grades);
        assertEquals(grades, s.getGrades());
    }
}
