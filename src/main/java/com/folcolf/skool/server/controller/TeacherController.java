package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Teacher;
import com.folcolf.skool.server.security.PrincipalOrAdmin;
import com.folcolf.skool.server.service.TeacherService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/teachers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class TeacherController {
    private final TeacherService teacherService;

    @GET
    @RolesAllowed("admin")
    public List<Teacher> getAll() {
        return teacherService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "viewer"})
    @PrincipalOrAdmin()
    public Teacher getById(@PathParam("id") Long id) {
        return teacherService.findById(id);
    }

    @POST
    @RolesAllowed("admin")
    public void create(Teacher teacher) {
        teacherService.persist(teacher);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        teacherService.delete(id);
    }
}
