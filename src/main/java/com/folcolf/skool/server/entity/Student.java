package com.folcolf.skool.server.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Student extends PanacheEntity {
    private String firstName;
    private String lastName;
    @ManyToOne
    @ToString.Exclude
    private SchoolClass schoolClass;
    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private List<Grade> grades;

    public static List<Student> findByClassId(Long classId) {
        return list("schoolClass.id", classId);
    }

    public static void addStudentsToClass(Long classId, List<Long> students) {
        update("schoolClass.id = ?1 where id in ?2", classId, students);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Student student)) {
            return false;
        }
        return Objects.equals(id, student.id) &&
                Objects.equals(getFirstName(), student.getFirstName()) &&
                Objects.equals(getLastName(), student.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getFirstName(), getLastName());
    }
}
