package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Grade;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.GradeService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Path("/grades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class GradeController {
    private final GradeService gradeService;
    private final SecurityService securityService;

    @GET
    @RolesAllowed({"admin"})
    public List<Grade> getAll() {
        return gradeService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "viewer"})
    public Grade getById(@PathParam("id") Long id) {
        if (securityService.isAdmin()) {
            return gradeService.findById(id);
        }
        Grade grade = gradeService.findById(id);
        if (grade != null && grade.getStudent() != null && securityService.isPrincipal(grade.getStudent().id)) {
            return grade;
        }
        throw new ForbiddenException("Not allowed to access this grade");
    }

    @POST
    @RolesAllowed("admin")
    public void create(Grade grade) {
        gradeService.persist(grade);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        gradeService.delete(id);
    }

    @GET
    @Path("/student/{id}")
    @RolesAllowed({"admin", "viewer"})
    public List<Grade> getGrades(@PathParam("id") Long id) {
        if (securityService.isAdmin() || securityService.isPrincipal(id)) {
            return gradeService.getGradesForStudent(id);
        }
        throw new ForbiddenException("Not allowed to access these grades");
    }

    @GET
    @Path("/student/{id}/averages")
    @RolesAllowed({"admin", "viewer"})
    public Map<String, Double> getAverages(@PathParam("id") Long id) {
        if (securityService.isAdmin() || securityService.isPrincipal(id)) {
            return gradeService.getAverageBySubject(id);
        }
        throw new ForbiddenException("Not allowed to access these averages");
    }

    @GET
    @Path("/class/{classId}/average")
    @RolesAllowed({"admin", "viewer"})
    public Double getClassAverage(@PathParam("classId") Long classId) {
        if (securityService.canAccessClass(classId)) {
            return gradeService.getClassAverage(classId);
        }
        throw new ForbiddenException("Not allowed to access this class average");
    }

    @GET
    @Path("/class/{classId}/subject/{subjectId}/average")
    @RolesAllowed({"admin", "viewer"})
    public Double getClassAverageForSubject(@PathParam("classId") Long classId, @PathParam("subjectId") Long subjectId) {
        if (securityService.canAccessClass(classId)) {
            return gradeService.getClassAverageForSubject(classId, subjectId);
        }
        throw new ForbiddenException("Not allowed to access this class/subject average");
    }
}
