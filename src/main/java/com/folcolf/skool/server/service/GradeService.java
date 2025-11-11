package com.folcolf.skool.server.service;

import com.folcolf.skool.server.entity.Grade;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class GradeService {
    public List<Grade> listAll() {
        return Grade.listAll();
    }

    public Grade findById(Long id) {
        return Grade.findById(id);
    }

    @Transactional
    public void persist(Grade grade) {
        grade.persist();
    }

    @Transactional
    public void delete(Long id) {
        Grade.deleteById(id);
    }

    public List<Grade> getGradesForStudent(Long studentId) {
        return Grade.findByStudentId(studentId);
    }

    public Map<String, Double> getAverageBySubject(Long studentId) {
        return Grade.findByStudentId(studentId).stream()
                .collect(Collectors.groupingBy(
                        g -> g.getSubject().getName(),
                        Collectors.averagingDouble(Grade::getValue)
                ));
    }

    public Double getClassAverage(Long classId) {
        List<Grade> allGrades = Grade.findByClassId(classId);
        if (allGrades.isEmpty()) {
            return null;
        }
        return allGrades.stream().collect(Collectors.averagingDouble(Grade::getValue));
    }

    public Double getClassAverageForSubject(Long classId, Long subjectId) {
        List<Grade> subjectGrades = Grade.findByClassIdAndSubjectId(classId, subjectId);
        if (subjectGrades.isEmpty()) {
            return null;
        }
        return subjectGrades.stream().collect(Collectors.averagingDouble(Grade::getValue));
    }
}
