package com.folcolf.skool.server.security;

import com.folcolf.skool.server.entity.SchoolClass;
import com.folcolf.skool.server.entity.Student;
import com.folcolf.skool.server.entity.Teacher;
import com.folcolf.skool.server.service.SchoolClassService;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.junit.QuarkusTest;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

@QuarkusTest
class SecurityServiceTest {
    private SecurityIdentity securityIdentity;
    private SchoolClassService schoolClassService;
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        securityIdentity = mock(SecurityIdentity.class);
        schoolClassService = mock(SchoolClassService.class);
        securityService = new SecurityService(securityIdentity, schoolClassService);
    }

    @Test
    void isAdmin_shouldReturnTrueIfAdmin() {
        when(securityIdentity.hasRole("admin")).thenReturn(true);
        assertTrue(securityService.isAdmin());
    }

    @Test
    void isAdmin_shouldReturnFalseIfNotAdmin() {
        when(securityIdentity.hasRole("admin")).thenReturn(false);
        assertFalse(securityService.isAdmin());
    }

    @Test
    void isViewer_shouldReturnTrueIfViewer() {
        when(securityIdentity.hasRole("viewer")).thenReturn(true);
        assertTrue(securityService.isViewer());
    }

    @Test
    void isViewer_shouldReturnFalseIfNotViewer() {
        when(securityIdentity.hasRole("viewer")).thenReturn(false);
        assertFalse(securityService.isViewer());
    }

    @Test
    void getPrincipalId_shouldReturnPrincipalName() {
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertEquals("42", securityService.getPrincipalId());
    }

    @Test
    void isPrincipal_shouldReturnTrueIfIdMatches() {
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertTrue(securityService.isPrincipal(42L));
        assertTrue(securityService.isPrincipal("42"));
    }

    @Test
    void isPrincipal_shouldReturnFalseIfIdDoesNotMatch() {
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertFalse(securityService.isPrincipal(99L));
        assertFalse(securityService.isPrincipal("99"));
    }

    @Test
    void isStudentInClass_shouldReturnTrueIfStudentInClass() {
        Student s = new Student();
        s.id = 42L;
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setStudents(List.of(s));
        when(schoolClassService.findById(1L)).thenReturn(sc);
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertTrue(securityService.isStudentInClass(1L));
    }

    @Test
    void isStudentInClass_shouldReturnFalseIfNotInClass() {
        Student s = new Student();
        s.id = 99L;
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setStudents(List.of(s));
        when(schoolClassService.findById(1L)).thenReturn(sc);
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertFalse(securityService.isStudentInClass(1L));
    }

    @Test
    void isStudentInClass_shouldReturnFalseIfClassNullOrNoStudents() {
        when(schoolClassService.findById(1L)).thenReturn(null);
        assertFalse(securityService.isStudentInClass(1L));
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setStudents(null);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        assertFalse(securityService.isStudentInClass(1L));
    }

    @Test
    void isTeacherOfClass_shouldReturnTrueIfTeacher() {
        Teacher t = new Teacher();
        t.id = 42L;
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setTeacher(t);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertTrue(securityService.isTeacherOfClass(1L));
    }

    @Test
    void isTeacherOfClass_shouldReturnFalseIfNotTeacher() {
        Teacher t = new Teacher();
        t.id = 99L;
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setTeacher(t);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertFalse(securityService.isTeacherOfClass(1L));
    }

    @Test
    void isTeacherOfClass_shouldReturnFalseIfClassNullOrNoTeacher() {
        when(schoolClassService.findById(1L)).thenReturn(null);
        assertFalse(securityService.isTeacherOfClass(1L));
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setTeacher(null);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        assertFalse(securityService.isTeacherOfClass(1L));
    }

    @Test
    void canAccessClass_shouldReturnTrueIfAdmin() {
        when(securityIdentity.hasRole("admin")).thenReturn(true);
        assertTrue(securityService.canAccessClass(1L));
    }

    @Test
    void canAccessClass_shouldReturnTrueIfStudentOrTeacher() {
        when(securityIdentity.hasRole("admin")).thenReturn(false);
        // Student
        Student s = new Student();
        s.id = 42L;
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setStudents(List.of(s));
        sc.setTeacher(null);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertTrue(securityService.canAccessClass(1L));
        // Teacher
        Teacher t = new Teacher();
        t.id = 42L;
        sc.setStudents(List.of());
        sc.setTeacher(t);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        assertTrue(securityService.canAccessClass(1L));
    }

    @Test
    void canAccessClass_shouldReturnFalseIfNotAdminStudentOrTeacher() {
        when(securityIdentity.hasRole("admin")).thenReturn(false);
        SchoolClass sc = new SchoolClass();
        sc.id = 1L;
        sc.setStudents(List.of());
        sc.setTeacher(null);
        when(schoolClassService.findById(1L)).thenReturn(sc);
        when(securityIdentity.getPrincipal()).thenReturn(() -> "42");
        assertFalse(securityService.canAccessClass(1L));
    }
}
