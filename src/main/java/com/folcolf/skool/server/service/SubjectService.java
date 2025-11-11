package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Subject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class SubjectService {
    public List<Subject> listAll() {
        return Subject.listAll();
    }

    public Subject findById(Long id) {
        return Subject.findById(id);
    }

    @Transactional
    public void persist(Subject subject) {
        subject.persist();
    }

    @Transactional
    public void delete(Long id) {
        Subject.deleteById(id);
    }
}
