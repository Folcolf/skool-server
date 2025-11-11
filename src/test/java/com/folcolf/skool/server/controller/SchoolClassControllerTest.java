package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.SchoolClass;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.SchoolClassService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.ForbiddenException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

@QuarkusTest
class SchoolClassControllerTest {
    private SchoolClassService schoolClassService;
    private SecurityService securityService;
    private SchoolClassController controller;

    @BeforeEach
    void setUp() {
        schoolClassService = mock(SchoolClassService.class);
        securityService = mock(SecurityService.class);
        controller = new SchoolClassController(schoolClassService, securityService);
    }

    @Test
    void getAll_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        when(schoolClassService.listAll()).thenReturn(List.of());
        assertNotNull(controller.getAll());
        verify(schoolClassService).listAll();
    }

    @Test
    void getById_shouldReturnForAdmin() {
        SchoolClass c = new SchoolClass();
        when(securityService.isAdmin()).thenReturn(true);
        when(schoolClassService.findById(1L)).thenReturn(c);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldReturnForTeacherOfClass() {
        SchoolClass c = new SchoolClass();
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isTeacherOfClass(1L)).thenReturn(true);
        when(securityService.isStudentInClass(1L)).thenReturn(false);
        when(schoolClassService.findById(1L)).thenReturn(c);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldReturnForStudentInClass() {
        SchoolClass c = new SchoolClass();
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isTeacherOfClass(1L)).thenReturn(false);
        when(securityService.isStudentInClass(1L)).thenReturn(true);
        when(schoolClassService.findById(1L)).thenReturn(c);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldThrowForbiddenForOtherViewer() {
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isTeacherOfClass(1L)).thenReturn(false);
        when(securityService.isStudentInClass(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getById(1L));
    }

    @Test
    void create_shouldDelegateToService_adminOnly() {
        SchoolClass c = new SchoolClass();
        when(securityService.isAdmin()).thenReturn(true);
        controller.create(c);
        verify(schoolClassService).persist(c);
    }

    @Test
    void delete_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        controller.delete(1L);
        verify(schoolClassService).delete(1L);
    }
}
