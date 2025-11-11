package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.SchoolClass;
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
class SchoolClassServiceTest {
    private SchoolClassService schoolClassService;

    @BeforeEach
    void setUp() {
        mock(SchoolClass.class);
        schoolClassService = new SchoolClassService();
    }

    @Test
    void listAll_shouldReturnClasses() {
        SchoolClass c1 = new SchoolClass();
        SchoolClass c2 = new SchoolClass();
        when(SchoolClass.listAll()).thenReturn(List.of(c1, c2));
        List<SchoolClass> result = schoolClassService.listAll();
        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnClass() {
        SchoolClass c = new SchoolClass();
        when(SchoolClass.findById(1L)).thenReturn(c);
        assertEquals(c, schoolClassService.findById(1L));
    }

    @Test
    void findById_shouldReturnNullIfNotFound() {
        when(SchoolClass.findById(2L)).thenReturn(null);
        assertNull(schoolClassService.findById(2L));
    }

    @Test
    @Transactional
    void persist_shouldCallEntity() {
        SchoolClass c = spy(new SchoolClass());
        schoolClassService.persist(c);
        verify(c, times(1)).persist();
    }

    @Test
    void delete_shouldCallStatic() {
        schoolClassService.delete(1L);
        PanacheMock.verify(SchoolClass.class, times(1)).deleteById(1L);
    }
}
