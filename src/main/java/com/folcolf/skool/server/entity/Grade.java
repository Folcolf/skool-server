package com.folcolf.skool.server.entity;

import com.folcolf.skool.server.dto.GradeClassDto;
import com.folcolf.skool.server.dto.GradeStudentDto;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Grade extends PanacheEntity {
    @Column(name = "grade_value")
    private Double value;
    @ManyToOne
    @ToString.Exclude
    private Student student;
    @ManyToOne
    @ToString.Exclude
    private Subject subject;

    public static List<Grade> findByStudentId(Long studentId) {
        return list("student.id", studentId);
    }

    public static List<Grade> findByClassId(Long classId) {
        return list("student.schoolClass.id", classId);
    }

    public static List<Grade> findByClassIdAndSubjectId(Long classId, Long subjectId) {
        return list("student.schoolClass.id = ?1 and subject.id = ?2", classId, subjectId);
    }

    public static List<GradeStudentDto> getAverageGradesBySubjectForStudent(Long studentId) {
        // Correction HQL : regrouper par matière et calculer la moyenne
        return getEntityManager().createQuery(
                        """
                                select new com.folcolf.skool.server.dto.GradeStudentDto(g.student.id, g.subject.name, avg(g.value))
                                from Grade g
                                where g.student.id = :studentId
                                group by g.student.id, g.subject.name""",
                        GradeStudentDto.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    public static List<GradeClassDto> getAverageGradesBySubjectForClass(Long classId) {
        // Correction HQL : regrouper par matière et calculer la moyenne pour la classe
        return getEntityManager().createQuery(
                        """
                                select new com.folcolf.skool.server.dto.GradeClassDto(g.student.schoolClass.id, g.subject.name, avg(g.value))
                                from Grade g
                                where g.student.schoolClass.id = :classId
                                group by g.student.schoolClass.id, g.subject.name""",
                        GradeClassDto.class)
                .setParameter("classId", classId)
                .getResultList();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Grade grade)) {
            return false;
        }
        return Objects.equals(id, grade.id) && Objects.equals(getValue(), grade.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getValue());
    }
}
