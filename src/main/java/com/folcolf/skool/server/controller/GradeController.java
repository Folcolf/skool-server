package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Grade;
import com.folcolf.skool.server.security.CanAccessClass;
import com.folcolf.skool.server.security.PrincipalOrAdmin;
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

    @GET
    @RolesAllowed({"admin"})
    public List<Grade> getAll() {
        return gradeService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "viewer"})
    @PrincipalOrAdmin()
    public Grade getById(@PathParam("id") Long id) {
        return gradeService.findById(id);
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
    @PrincipalOrAdmin()
    public List<Grade> getGrades(@PathParam("id") Long id) {
        return gradeService.getGradesForStudent(id);
    }

    @GET
    @Path("/student/{id}/averages")
    @RolesAllowed({"admin", "viewer"})
    @PrincipalOrAdmin()
    public Map<String, Double> getAverages(@PathParam("id") Long id) {
        return gradeService.getAverageBySubject(id);
    }

    @GET
    @Path("/class/{classId}/average")
    @RolesAllowed({"admin", "viewer"})
    @CanAccessClass()
    public Double getClassAverage(@PathParam("classId") Long classId) {
        return gradeService.getClassAverage(classId);
    }

    @GET
    @Path("/class/{classId}/subject/{subjectId}/average")
    @RolesAllowed({"admin", "viewer"})
    @CanAccessClass()
    public Double getClassAverageForSubject(@PathParam("classId") Long classId, @PathParam("subjectId") Long subjectId) {
        return gradeService.getClassAverageForSubject(classId, subjectId);
    }
}
