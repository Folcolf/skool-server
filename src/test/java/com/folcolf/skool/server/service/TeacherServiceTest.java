package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Teacher;
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
class TeacherServiceTest {
    private TeacherService teacherService;

    @BeforeEach
    void setUp() {
        mock(Teacher.class);
        teacherService = new TeacherService();
    }

    @Test
    void listAll_shouldReturnTeachers() {
        Teacher t1 = new Teacher();
        Teacher t2 = new Teacher();
        when(Teacher.listAll()).thenReturn(List.of(t1, t2));
        List<Teacher> result = teacherService.listAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnTeacher() {
        Teacher t = new Teacher();
        when(Teacher.findById(1L)).thenReturn(t);
        assertEquals(t, teacherService.findById(1L));
    }

    @Test
    void findById_shouldReturnNullIfNotFound() {
        when(Teacher.findById(2L)).thenReturn(null);
        assertNull(teacherService.findById(2L));
    }

    @Test
    @Transactional
    void persist_shouldCallEntity() {
        Teacher t = spy(new Teacher());
        teacherService.persist(t);
        verify(t, times(1)).persist();
    }

    @Test
    void delete_shouldCallStatic() {
        teacherService.delete(1L);
        PanacheMock.verify(Teacher.class, times(1)).deleteById(1L);
    }
}
