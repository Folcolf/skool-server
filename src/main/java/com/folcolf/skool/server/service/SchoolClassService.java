package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.SchoolClass;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class SchoolClassService {

    private final StudentService studentService;

    @Inject
    public SchoolClassService(StudentService studentService) {
        this.studentService = studentService;
    }

    public List<SchoolClass> listAll() {
        return SchoolClass.listAll();
    }

    public SchoolClass findById(Long id) {
        return SchoolClass.findById(id);
    }

    @Transactional
    public void persist(SchoolClass schoolClass) {
        schoolClass.persist();
    }

    @Transactional
    public void addStudentsToClass(SchoolClass schoolClass, List<Long> studentIds) {
        studentService.addStudentsToClass(schoolClass.id, studentIds);
        schoolClass.persist();
    }

    @Transactional
    public void delete(Long id) {
        SchoolClass.deleteById(id);
    }
}
