package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class TeacherService {
    public List<Teacher> listAll() {
        return Teacher.listAll();
    }

    public Teacher findById(Long id) {
        return Teacher.findById(id);
    }

    @Transactional
    public void persist(Teacher teacher) {
        teacher.persist();
    }

    @Transactional
    public void delete(Long id) {
        Teacher.deleteById(id);
    }
}
