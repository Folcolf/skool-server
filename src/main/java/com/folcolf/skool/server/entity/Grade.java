package com.folcolf.skool.server.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Grade grade)) {
            return false;
        }
        return Objects.equals(id, grade.id) && Objects.equals(getValue(), grade.getValue()) && Objects.equals(getStudent(), grade.getStudent()) && Objects.equals(getSubject(), grade.getSubject());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getValue(), getStudent(), getSubject());
    }
}
