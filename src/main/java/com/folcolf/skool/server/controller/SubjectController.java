package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Subject;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.SubjectService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;
    private final SecurityService securityService;

    @GET
    @RolesAllowed("admin")
    public List<Subject> getAll() {
        if (!securityService.isAdmin()) {
            throw new ForbiddenException("Not allowed to access subjects");
        }
        return subjectService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Subject getById(@PathParam("id") Long id) {
        if (securityService.isAdmin()) {
            return subjectService.findById(id);
        }
        throw new ForbiddenException("Not allowed to access this subject");
    }

    @POST
    @RolesAllowed("admin")
    public void create(Subject subject) {
        subjectService.persist(subject);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        subjectService.delete(id);
    }
}
