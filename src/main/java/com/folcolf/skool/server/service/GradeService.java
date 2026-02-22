package com.folcolf.skool.server.service;

import com.folcolf.skool.server.dto.GradeClassDto;
import com.folcolf.skool.server.dto.GradeStudentDto;
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
    public void persistBatch(List<Grade> grades) {
        Grade.persist(grades);
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

    public List<GradeStudentDto> getClassAverageForStudent(Long studentId) {
        return Grade.getAverageGradesBySubjectForStudent(studentId);
    }

    public List<GradeClassDto> getOverallClassAverage(Long classId) {
        return Grade.getAverageGradesBySubjectForClass(classId);
    }
}
