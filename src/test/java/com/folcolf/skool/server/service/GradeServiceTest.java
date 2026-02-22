package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Grade;
import com.folcolf.skool.server.entity.Student;
import com.folcolf.skool.server.entity.Subject;
import io.quarkus.panache.mock.PanacheMock;
import static io.quarkus.panache.mock.PanacheMock.mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

@QuarkusTest
class GradeServiceTest {
    private GradeService gradeService;

    @BeforeEach
    void setUp() {
        mock(Grade.class);
        gradeService = new GradeService();
    }

    @Test
    void getGradesForStudent_shouldReturnGrades() {
        Student s = new Student();
        s.id = 1L;
        Grade g1 = new Grade();
        g1.setStudent(s);
        g1.setValue(10.0);
        Grade g2 = new Grade();
        g2.setStudent(s);
        g2.setValue(15.0);
        when(Grade.findByStudentId(1L)).thenReturn(List.of(g1, g2));
        List<Grade> result = gradeService.getGradesForStudent(1L);
        assertEquals(2, result.size());
    }

    @Test
    void getGradesForStudent_shouldReturnEmptyListIfNoGrades() {
        when(Grade.findByStudentId(1L)).thenReturn(List.of());
        List<Grade> result = gradeService.getGradesForStudent(1L);
        assertEquals(0, result.size());
    }

    @Test
    void getAverageBySubject_shouldReturnCorrectAverages() {
        Student s = new Student();
        s.id = 1L;
        Subject subj = new Subject();
        subj.id = 1L;
        subj.setName("Math");
        Grade g1 = new Grade();
        g1.setStudent(s);
        g1.setSubject(subj);
        g1.setValue(10.0);
        Grade g2 = new Grade();
        g2.setStudent(s);
        g2.setSubject(subj);
        g2.setValue(20.0);
        when(Grade.findByStudentId(1L)).thenReturn(List.of(g1, g2));
        Map<String, Double> avg = gradeService.getAverageBySubject(1L);
        assertEquals(15.0, avg.get("Math"));
    }

    @Test
    void getAverageBySubject_shouldReturnEmptyMapIfNoGrades() {
        when(Grade.findByStudentId(1L)).thenReturn(List.of());
        Map<String, Double> avg = gradeService.getAverageBySubject(1L);
        assertEquals(0, avg.size());
    }

    @Test
    void listAll_shouldDelegateToStatic() {
        when(Grade.listAll()).thenReturn(List.of());
        List<Grade> result = gradeService.listAll();
        assertEquals(List.of(), result);
    }

    @Test
    void findById_shouldDelegateToStatic() {
        Grade g = new Grade();
        when(Grade.findById(42L)).thenReturn(g);
        Grade result = gradeService.findById(42L);
        assertEquals(g, result);
    }

    @Test
    @Transactional
    void persist_shouldDelegateToEntity() {
        Grade g = spy(new Grade());
        gradeService.persist(g);
        verify(g, times(1)).persist();
    }

    @Test
    void delete_shouldDelegateToStatic() {
        gradeService.delete(99L);
        PanacheMock.verify(Grade.class, times(1)).deleteById(99L);
    }
}
