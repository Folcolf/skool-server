package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.SchoolClass;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class SchoolClassService {
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
    public void delete(Long id) {
        SchoolClass.deleteById(id);
    }
}
