package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Subject;
import io.quarkus.panache.mock.PanacheMock;
import static io.quarkus.panache.mock.PanacheMock.mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

@QuarkusTest
class SubjectServiceTest {
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        mock(Subject.class);
        subjectService = new SubjectService();
    }

    @Test
    void listAll_shouldReturnSubjects() {
        Subject s1 = new Subject();
        Subject s2 = new Subject();
        when(Subject.listAll()).thenReturn(List.of(s1, s2));
        List<Subject> result = subjectService.listAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnSubject() {
        Subject s = new Subject();
        when(Subject.findById(1L)).thenReturn(s);
        assertEquals(s, subjectService.findById(1L));
    }

    @Test
    void findById_shouldReturnNullIfNotFound() {
        when(Subject.findById(2L)).thenReturn(null);
        assertNull(subjectService.findById(2L));
    }

    @Test
    @Transactional
    void persist_shouldCallEntity() {
        Subject s = spy(new Subject());
        subjectService.persist(s);
        verify(s, times(1)).persist();
    }

    @Test
    void delete_shouldCallStatic() {
        subjectService.delete(1L);
        PanacheMock.verify(Subject.class, times(1)).deleteById(1L);
    }
}
