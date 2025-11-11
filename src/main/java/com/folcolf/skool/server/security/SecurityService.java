package com.folcolf.skool.server.security;

import com.folcolf.skool.server.entity.SchoolClass;
import com.folcolf.skool.server.service.SchoolClassService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@RequestScoped
public class SecurityService {
    private final SecurityIdentity securityIdentity;
    private final SchoolClassService schoolClassService;

    @Inject
    public SecurityService(SecurityIdentity securityIdentity, SchoolClassService schoolClassService) {
        this.securityIdentity = securityIdentity;
        this.schoolClassService = schoolClassService;
    }

    public boolean isAdmin() {
        return securityIdentity.hasRole("admin");
    }

    public boolean isViewer() {
        return securityIdentity.hasRole("viewer");
    }

    public String getPrincipalId() {
        return securityIdentity.getPrincipal().getName();
    }

    public boolean isPrincipal(String userId) {
        return Objects.equals(userId, getPrincipalId());
    }

    public boolean isPrincipal(Long id) {
        return isPrincipal(String.valueOf(id));
    }

    public boolean isStudentInClass(Long classId) {
        SchoolClass schoolClass = schoolClassService.findById(classId);
        if (schoolClass == null || schoolClass.getStudents() == null)
            return false;
        return schoolClass.getStudents().stream()
                .anyMatch(s -> Objects.equals(String.valueOf(s.id), getPrincipalId()));
    }

    public boolean isTeacherOfClass(Long classId) {
        SchoolClass schoolClass = schoolClassService.findById(classId);
        if (schoolClass == null || schoolClass.getTeacher() == null)
            return false;
        return Objects.equals(String.valueOf(schoolClass.getTeacher().id), getPrincipalId());
    }

    public boolean canAccessClass(Long classId) {
        return isAdmin() || isStudentInClass(classId) || isTeacherOfClass(classId);
    }
}
