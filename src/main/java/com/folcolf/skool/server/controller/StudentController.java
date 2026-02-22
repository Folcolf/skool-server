package com.folcolf.skool.server.controller;

import com.folcolf.skool.server.dto.GradeStudentDto;
import com.folcolf.skool.server.entity.Student;
import com.folcolf.skool.server.security.OwnerOrAdmin;
import com.folcolf.skool.server.service.GradeService;
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
    private final GradeService gradeService;
    private final StudentService studentService;

    @GET
    @RolesAllowed("admin")
    public List<Student> getAll() {
        return studentService.listAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "viewer"})
    @OwnerOrAdmin
    public Student getById(@PathParam("id") Long id) {
        return studentService.findById(id);
    }

    @GET
    @Path("/{id}/grades/average")
    @RolesAllowed({"admin", "viewer"})
    @OwnerOrAdmin
    public List<GradeStudentDto> getAverageGrades(@PathParam("id") Long id) {
        return gradeService.getClassAverageForStudent(id);
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
