package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Subject;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.SubjectService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.ForbiddenException;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;

@QuarkusTest
class SubjectControllerTest {
    private SubjectService subjectService;
    private SubjectController controller;
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        subjectService = mock(SubjectService.class);
        securityService = mock(SecurityService.class);
        controller = new SubjectController(subjectService, securityService);
    }

    @Test
    void getAll_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        when(subjectService.listAll()).thenReturn(List.of());
        assertNotNull(controller.getAll());
        verify(subjectService).listAll();
    }

    @Test
    void getAll_shouldThrowForbiddenForNonAdmin() {
        when(securityService.isAdmin()).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getAll());
    }

    @Test
    void getById_shouldReturnForAdmin() {
        Subject s = new Subject();
        when(securityService.isAdmin()).thenReturn(true);
        when(subjectService.findById(1L)).thenReturn(s);
        assertNotNull(controller.getById(1L));
    }

    @Test
    void getById_shouldThrowForbiddenForNonAdmin() {
        when(securityService.isAdmin()).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getById(1L));
    }

    @Test
    void create_shouldDelegateToService_adminOnly() {
        Subject s = new Subject();
        when(securityService.isAdmin()).thenReturn(true);
        controller.create(s);
        verify(subjectService).persist(s);
    }

    @Test
    void delete_shouldDelegateToService_adminOnly() {
        when(securityService.isAdmin()).thenReturn(true);
        controller.delete(1L);
        verify(subjectService).delete(1L);
    }
}
