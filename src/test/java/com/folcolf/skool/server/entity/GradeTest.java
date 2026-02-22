package com.folcolf.skool.server.entity;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.List;

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

    @Test
    void testGettersAndSetters() {
        Grade g = new Grade();
        g.setValue(18.5);
        Student s = new Student();
        Subject sub = new Subject();
        g.setStudent(s);
        g.setSubject(sub);
        assertEquals(18.5, g.getValue());
        assertEquals(s, g.getStudent());
        assertEquals(sub, g.getSubject());
    }

    @Test
    @Transactional
    void testFinders() {
        Student s = new Student();
        s.setFirstName("Test");
        s.setLastName("Student");
        s.persist();
        Subject sub = new Subject();
        sub.setName("Math");
        sub.setCoefficient(2.0);
        sub.persist();
        Grade g = new Grade(20.0, s, sub);
        g.persist();
        // flush pour s'assurer que les entités sont bien en BDD
        assertDoesNotThrow(() -> Grade.findByStudentId(s.id));
        assertDoesNotThrow(() -> Grade.findByClassId(s.getSchoolClass() != null ? s.getSchoolClass().id : null));
        assertDoesNotThrow(() -> Grade.findByClassIdAndSubjectId(s.getSchoolClass() != null ? s.getSchoolClass().id : null, sub.id));
        List<Grade> byStudent = Grade.findByStudentId(s.id);
        assertFalse(byStudent.isEmpty());
        assertEquals(20.0, byStudent.getFirst().getValue());
    }

    @Test
    void testConstructor() {
        Student s = new Student();
        Subject sub = new Subject();
        Grade g = new Grade(15.0, s, sub);
        assertEquals(15.0, g.getValue());
        assertEquals(s, g.getStudent());
        assertEquals(sub, g.getSubject());
    }

    @Test
    void testNoArgConstructor() {
        Grade g = new Grade();
        assertNull(g.getValue());
        assertNull(g.getStudent());
        assertNull(g.getSubject());
    }

    @Test
    @Transactional
    void testGetAverageGradesBySubjectForStudent() {
        Student s = new Student();
        s.setFirstName("Test");
        s.setLastName("Student");
        s.persist();
        Subject sub1 = new Subject();
        sub1.setName("Math");
        sub1.setCoefficient(2.0);
        sub1.persist();
        Subject sub2 = new Subject();
        sub2.setName("Français");
        sub2.setCoefficient(1.0);
        sub2.persist();

        Grade g1 = new Grade(10.0, s, sub1);
        g1.persist();
        Grade g2 = new Grade(20.0, s, sub1);
        g2.persist();
        Grade g3 = new Grade(15.0, s, sub2);
        g3.persist();

        var result = Grade.getAverageGradesBySubjectForStudent(s.id);
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    @Transactional
    void testGetAverageGradesBySubjectForClass() {
        SchoolClass sc = new SchoolClass();
        sc.setName("ClassA");
        sc.persist();

        Student s = new Student();
        s.setFirstName("Test");
        s.setLastName("Student");
        s.setSchoolClass(sc);
        s.persist();

        Subject sub = new Subject();
        sub.setName("Math");
        sub.setCoefficient(2.0);
        sub.persist();

        Grade g1 = new Grade(10.0, s, sub);
        g1.persist();
        Grade g2 = new Grade(20.0, s, sub);
        g2.persist();

        var result = Grade.getAverageGradesBySubjectForClass(sc.id);
        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}
