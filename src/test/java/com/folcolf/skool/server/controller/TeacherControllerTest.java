package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Teacher;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.TeacherService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.ForbiddenException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

@QuarkusTest
class TeacherControllerTest {
    private TeacherService teacherService;
    private SecurityService securityService;
    private TeacherController controller;

    @BeforeEach
    void setUp() {
        teacherService = mock(TeacherService.class);
        securityService = mock(SecurityService.class);
        controller = new TeacherController(teacherService, securityService);
    }

    @Test
    void getAll_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        when(teacherService.listAll()).thenReturn(List.of());
        assertNotNull(controller.getAll());
        verify(teacherService).listAll();
    }

    @Test
    void getById_shouldReturnForAdmin() {
        Teacher t = new Teacher();
        when(securityService.isAdmin()).thenReturn(true);
        when(teacherService.findById(1L)).thenReturn(t);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldReturnForPrincipal() {
        Teacher t = new Teacher();
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(true);
        when(teacherService.findById(1L)).thenReturn(t);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldThrowForbiddenForOtherViewer() {
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getById(1L));
    }

    @Test
    void create_shouldDelegateToService_adminOnly() {
        Teacher t = new Teacher();
        when(securityService.isAdmin()).thenReturn(true);
        controller.create(t);
        verify(teacherService).persist(t);
    }

    @Test
    void delete_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        controller.delete(1L);
        verify(teacherService).delete(1L);
    }
}
