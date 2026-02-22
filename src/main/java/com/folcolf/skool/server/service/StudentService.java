package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class StudentService {
    public List<Student> listAll() {
        return Student.listAll();
    }

    public List<Student> findByClassId(Long classId) {
        return Student.findByClassId(classId);
    }

    public Student findById(Long id) {
        return Student.findById(id);
    }

    @Transactional
    public void persist(Student student) {
        student.persist();
    }

    @Transactional
    public void addStudentsToClass(Long classId, List<Long> studentIds) {
        Student.addStudentsToClass(classId, studentIds);
    }

    @Transactional
    public void delete(Long id) {
        Student.deleteById(id);
    }
}
