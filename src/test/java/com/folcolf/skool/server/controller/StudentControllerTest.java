package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Student;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.StudentService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.ForbiddenException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

@QuarkusTest
class StudentControllerTest {
    private StudentService studentService;
    private SecurityService securityService;
    private StudentController controller;

    @BeforeEach
    void setUp() {
        studentService = mock(StudentService.class);
        securityService = mock(SecurityService.class);
        controller = new StudentController(studentService, securityService);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(studentService.listAll()).thenReturn(List.of());
        assertNotNull(controller.getAll());
        verify(studentService).listAll();
    }

    @Test
    void getAll_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        when(studentService.listAll()).thenReturn(List.of());
        assertNotNull(controller.getAll());
        verify(studentService).listAll();
    }

    @Test
    void getById_shouldReturnForAdmin() {
        Student s = new Student();
        when(securityService.isAdmin()).thenReturn(true);
        when(studentService.findById(1L)).thenReturn(s);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldReturnForPrincipal() {
        Student s = new Student();
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(true);
        when(studentService.findById(1L)).thenReturn(s);
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
        Student s = new Student();
        when(securityService.isAdmin()).thenReturn(true);
        controller.create(s);
        verify(studentService).persist(s);
    }

    @Test
    void delete_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        controller.delete(1L);
        verify(studentService).delete(1L);
    }
}
