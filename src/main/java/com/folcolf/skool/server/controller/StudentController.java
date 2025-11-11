package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.entity.Student;
import com.folcolf.skool.server.security.SecurityService;
import com.folcolf.skool.server.service.StudentService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    private final SecurityService securityService;

    @GET
    @RolesAllowed("admin")
    public List<Student> getAll() {
        return studentService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "viewer"})
    public Student getById(@PathParam("id") Long id) {
        if (securityService.isAdmin() || securityService.isPrincipal(id)) {
            return studentService.findById(id);
        }
        throw new ForbiddenException("Not allowed to access this student");
    }

    @POST
    @RolesAllowed("admin")
    public void create(Student student) {
        studentService.persist(student);
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Long id) {
        studentService.delete(id);
    }
}
