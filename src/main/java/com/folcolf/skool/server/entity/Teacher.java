package com.folcolf.skool.server.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
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
public class Teacher extends PanacheEntity {
    private String firstName;
    private String lastName;
    @OneToMany(mappedBy = "teacher")
    @ToString.Exclude
    private List<SchoolClass> classes;
    @OneToMany(mappedBy = "teacher")
    @ToString.Exclude
    private List<Subject> subjects;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Teacher teacher)) {
            return false;
        }
        return Objects.equals(id, teacher.id) &&
                Objects.equals(getFirstName(), teacher.getFirstName()) &&
                Objects.equals(getLastName(), teacher.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getFirstName(), getLastName());
    }
}
