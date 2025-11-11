package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.SchoolClass;
import com.folcolf.skool.server.service.SchoolClassService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/classes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class SchoolClassController {
    private final SchoolClassService schoolClassService;
    private final com.folcolf.skool.server.security.SecurityService securityService;

    @GET
    @RolesAllowed("admin")
    public List<SchoolClass> getAll() {
        return schoolClassService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "viewer"})
    public SchoolClass getById(@PathParam("id") Long id) {
        if (securityService.isAdmin() || securityService.isTeacherOfClass(id) || securityService.isStudentInClass(id)) {
            return schoolClassService.findById(id);
        }
        throw new ForbiddenException("Not allowed to access this class");
    }

    @POST
    @RolesAllowed("admin")
    public void create(SchoolClass schoolClass) {
        schoolClassService.persist(schoolClass);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        schoolClassService.delete(id);
    }
}
