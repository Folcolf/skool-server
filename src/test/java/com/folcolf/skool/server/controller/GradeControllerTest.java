package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Grade;
import com.folcolf.skool.server.entity.Student;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.GradeService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.ws.rs.ForbiddenException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

@QuarkusTest
class GradeControllerTest {
    private GradeService gradeService;
    private SecurityService securityService;
    private GradeController controller;

    @BeforeEach
    void setUp() {
        gradeService = mock(GradeService.class);
        securityService = mock(SecurityService.class);
        controller = new GradeController(gradeService, securityService);
    }

    @Test
    void getAll_shouldDelegateToService() {
        when(gradeService.listAll()).thenReturn(List.of());
        assertNotNull(controller.getAll());
        verify(gradeService).listAll();
    }

    @Test
    void getById_shouldReturnGradeForAdmin() {
        Grade g = new Grade();
        when(securityService.isAdmin()).thenReturn(true);
        when(gradeService.findById(1L)).thenReturn(g);
        assertEquals(g, controller.getById(1L));
        verify(gradeService).findById(1L);
    }

    @Test
    void getById_shouldReturnGradeForViewerPrincipal() {
        reset(gradeService, securityService);
        Grade g = new Grade();
        Student s = new Student();
        s.id = 1L;
        g.setStudent(s);
        when(securityService.isAdmin()).thenReturn(false);
        when(gradeService.findById(1L)).thenReturn(g);
        when(securityService.getPrincipalId()).thenReturn("1");
        when(securityService.isPrincipal(1L)).thenReturn(true);
        assertEquals(g, controller.getById(1L));
    }

    @Test
    void getById_shouldThrowForbiddenForViewerNonPrincipal() {
        Grade g = new Grade();
        Student s = new Student();
        s.id = 99L;
        g.setStudent(s);
        when(securityService.isAdmin()).thenReturn(false);
        when(gradeService.findById(1L)).thenReturn(g);
        when(securityService.getPrincipalId()).thenReturn("2");
        assertThrows(ForbiddenException.class, () -> controller.getById(1L));
    }

    @Test
    void getById_shouldThrowForbiddenForViewerNoStudent() {
        Grade gNoStudent = new Grade();
        when(securityService.isAdmin()).thenReturn(false);
        when(gradeService.findById(2L)).thenReturn(gNoStudent);
        assertThrows(ForbiddenException.class, () -> controller.getById(2L));
    }

    @Test
    void create_shouldDelegateToService() {
        Grade g = new Grade();
        controller.create(g);
        verify(gradeService).persist(g);
    }

    @Test
    void delete_shouldDelegateToService() {
        controller.delete(1L);
        verify(gradeService).delete(1L);
    }

    @Test
    void getGrades_shouldDelegateToService() {
        when(securityService.isAdmin()).thenReturn(true);
        when(gradeService.getGradesForStudent(1L)).thenReturn(List.of());
        assertNotNull(controller.getGrades(1L));
        verify(gradeService).getGradesForStudent(1L);
        // viewer principal
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(true);
        assertNotNull(controller.getGrades(1L));
        // viewer non principal
        when(securityService.isPrincipal(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getGrades(1L));
    }

    @Test
    void getAverages_shouldDelegateToService() {
        when(securityService.isAdmin()).thenReturn(true);
        when(gradeService.getAverageBySubject(1L)).thenReturn(Map.of());
        assertNotNull(controller.getAverages(1L));
        verify(gradeService).getAverageBySubject(1L);
        // viewer principal
        when(securityService.isAdmin()).thenReturn(false);
        when(securityService.isPrincipal(1L)).thenReturn(true);
        assertNotNull(controller.getAverages(1L));
        // viewer non principal
        when(securityService.isPrincipal(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getAverages(1L));
    }

    @Test
    void getClassAverage_shouldDelegateToService() {
        when(securityService.canAccessClass(1L)).thenReturn(true);
        when(gradeService.getClassAverage(1L)).thenReturn(10.0);
        assertEquals(10.0, controller.getClassAverage(1L));
        // accès refusé
        when(securityService.canAccessClass(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getClassAverage(1L));
    }

    @Test
    void getClassAverageForSubject_shouldDelegateToService() {
        when(securityService.canAccessClass(1L)).thenReturn(true);
        when(gradeService.getClassAverageForSubject(1L, 2L)).thenReturn(15.0);
        assertEquals(15.0, controller.getClassAverageForSubject(1L, 2L));
        // accès refusé
        when(securityService.canAccessClass(1L)).thenReturn(false);
        assertThrows(ForbiddenException.class, () -> controller.getClassAverageForSubject(1L, 2L));
    }
}
